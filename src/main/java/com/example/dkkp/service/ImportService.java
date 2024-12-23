package com.example.dkkp.service;

import com.example.dkkp.dao.ImportDao;
import com.example.dkkp.dao.ImportDetailDao;
import com.example.dkkp.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ImportService {
    private final ImportDao importDao;
    private final ImportDetailDao importDetailDao;
    private static final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ImportService(EntityManager entityManager) {
        this.importDao = new ImportDao(entityManager);
        this.importDetailDao = new ImportDetailDao(entityManager);
        this.entityManager = entityManager;

    }

    public List<Import_Entity> getImportByCombinedCondition(
            Import_Entity import_entity,
            String typeDate,
            String sortField,
            String sortOrder,
            int setOff, // Số bản ghi mỗi luồng xử lý
            int offset
    ) {
        if ((reflectField.isPropertyNameMatched(Import_Entity.class, sortField) && sortOrder != null) || sortField == null) {
            LocalDateTime dateImport = import_entity.getDATE_IMP();
            Integer id = import_entity.getID_IMP();
            Boolean status = import_entity.getIS_AVAILABLE();
            Integer idReplace = import_entity.getID_REPLACE();
            Double totalPrice = import_entity.getTOTAL_PRICE();
            return importDao.getFilteredImports(
                    dateImport, typeDate, id, status, idReplace, totalPrice, sortField, sortOrder, offset, setOff
            );
        }
        throw new RuntimeException("Error with sort");
    }

    public List<Import_Detail_Entity> getImportDetailByCombinedCondition(
            Import_Detail_Entity importQuery,
            String sortField,
            String sortOrder,
            int setOff, // Số bản ghi mỗi luồng xử lý
            int offset
    ) {
        Integer id = importQuery.getID_IMPD();
        Integer idImport = importQuery.getID_IMPORT();
        Integer idFinalProduct = importQuery.getID_FINAL_PRODUCT();
        Integer idBaseProduct = importQuery.getID_BASE_PRODUCT();
        Boolean isAvailable = importQuery.getIS_AVAILABLE();
        Integer quantity = importQuery.getQUANTITY();
        Double totalPrice = importQuery.getTOTAL_PRICE();
        Double unitPrice = importQuery.getUNIT_PRICE();
        if ((reflectField.isPropertyNameMatched(Import_Detail_Entity.class, sortField) && sortOrder != null) || sortField == null) {
            return importDetailDao.getFilteredImportDetails(
                    id, idImport, idFinalProduct, isAvailable, idBaseProduct, quantity, unitPrice, totalPrice, sortField, sortOrder, offset, setOff
            );
        }
        throw new RuntimeException("Error with sort");
    }


    public void deleteImport(Integer idParent) {
        // add check
        if (idParent != null) {
            boolean isDeleted = importDetailDao.deleteImportDetail(idParent);
            if (!isDeleted) {
                throw new RuntimeException("Failed to delete import detail.");
            }
            importDao.deleteImport(idParent);
            minusImportProduct(idParent);
        }
        throw new RuntimeException("Error with sort");
    }

    public void registerNewImport(Import_Entity importEntity) throws
            SQLException {
        //add check
        LocalDateTime DATE_JOIN = LocalDateTime.now();
        importEntity.setDATE_IMP(DATE_JOIN);
        importDao.createImport(importEntity);
    }

    private void registerNewImportDetail(List<Import_Detail_Entity> listImportDetail) throws SQLException {
        //add check
        if (importDao.checkImport(listImportDetail.getFirst().getID_IMPD())) {
            importDetailDao.createImportDetail(listImportDetail);
            Double sumPrice = 0.0;
            Integer id = null;
            for (Import_Detail_Entity importDetail : listImportDetail) {
                sumPrice += importDetail.getTOTAL_PRICE();
                id = importDetail.getID_IMPORT();
            }
            plusImportProduct(id);
            importDao.addSumPrice(id, sumPrice);
        }
        throw new RuntimeException("Can not find id import general to add import detail");
    }

    private void plusImportProduct(Integer id) {
        if (id != null) {

            if (importDao.getFilteredImports(null, null, id, null, null, null, null, null, null, null) != null) {
                List<Import_Detail_Entity> listImportDetail = importDetailDao.getFilteredImportDetails(null, id,null,null,null,null,null, null, null, null, null, null);
                for (Import_Detail_Entity importDetail : listImportDetail) {
                    Integer idSp = importDetail.getID_IMPORT();
                    Integer quantity = importDetail.getQUANTITY();
                    ProductBaseService productBaseService = new ProductBaseService(entityManager);
                    Product_Base_Entity productE = productBaseService.getProductBaseByID(idSp);
                    if (productE == null) {
                        throw new RuntimeException("Error cant find product to plus quantity in plus product form import process");
                    }
                    Integer newQuantity = productE.getQUANTITY() + quantity;
                    productE.setQUANTITY(newQuantity);
                    productBaseService.updateProductBase(productE);
                }
            }
        }
        throw new RuntimeException("ID Import general to plus product from import  is null");
    }
    private void minusImportProduct(Integer id) {
        if (id != null) {
            if (importDao.getFilteredImports(null, null, id, null, null, null, null, null, null, null) != null) {
                List<Import_Detail_Entity> listImportDetail = importDetailDao.getFilteredImportDetails(null, id,null,null,null,null,null, null, null, null, null, null);
                for (Import_Detail_Entity importDetail : listImportDetail) {
                    Integer idSp = importDetail.getID_IMPORT();
                    Integer quantity = importDetail.getQUANTITY();
                    ProductBaseService productBaseService = new ProductBaseService(entityManager);
                    Product_Base_Entity productE = productBaseService.getProductBaseByID(idSp);
                    if (productE == null) {
                        throw new RuntimeException("Error cant find product to minus quantity in plus product form import process");
                    }
                    if(productE.getQUANTITY() < quantity) {
                        throw new RuntimeException("Product quantity is less than to minus quantity");
                    }
                    Integer newQuantity = productE.getQUANTITY() - quantity;
                    productE.setQUANTITY(newQuantity);
                    productBaseService.updateProductBase(productE);
                }
            }
        }
        throw new RuntimeException("ID Import general to plus product from import  is null");
    }
}
