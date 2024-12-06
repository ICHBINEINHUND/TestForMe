package com.example.dkkp.service;

import com.example.dkkp.dao.ImportDao;
import com.example.dkkp.dao.ImportDetailDao;
import com.example.dkkp.dao.ProductDao;
import com.example.dkkp.model.*;
import jakarta.persistence.EntityTransaction;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ImportService {
    private final ImportDao importDao;
    private final ImportDetailDao importDetailDao;
    private final ProductDao productDao;

    public ImportService() {
        this.importDao = new ImportDao();
        this.importDetailDao = new ImportDetailDao();
        this.productDao = new ProductDao();
    }

    public List<Import_Entity> getImportByCombinedCondition(
            Import_Entity import_entity,
            String typeDate,
            String sortField,
            String sortOrder,
            int setOff // Số bản ghi mỗi luồng xử lý
    ) {
        LocalDateTime dateJoin = import_entity.getDATE_IMP();
        String id = import_entity.getID_IMP();
        Boolean status = import_entity.getSTATUS();
        String idReplace = import_entity.getID_REPLACE();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        AtomicBoolean continueFlag = new AtomicBoolean(true);

        ConcurrentLinkedQueue<Integer> offsetsQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            offsetsQueue.add(i * setOff);
        }

        List<CompletableFuture<List<Import_Entity>>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                List<Import_Entity> results = new ArrayList<>();
                while (continueFlag.get()) {
                    Integer offset = offsetsQueue.poll();
                    if (offset == null) {
                        continueFlag.set(false);
                        break;
                    }
                    List<Import_Entity> partialResult = importDao.getFilteredImports(
                            dateJoin, typeDate, id, status, idReplace, sortField, sortOrder, offset, setOff
                    );

                    if (partialResult.isEmpty()) {
                        continueFlag.set(false);
                        break;
                    }
                    results.addAll(partialResult);
                }
                return results;
            }, executor));
        }


        List<Import_Entity> results = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        executor.shutdown();
        return results;
    }

    public List<Import_Detail_Entity> getImportDetailByCombinedCondition(
            Import_Detail_Entity importQuery,
            String sortField,
            String sortOrder,
            int setOff // Số bản ghi mỗi luồng xử lý
    ) {
        String id = importQuery.getID_IMPD();
        String idParent = importQuery.getID_IPARENT();
        String idSP = importQuery.getID_SP();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        AtomicBoolean continueFlag = new AtomicBoolean(true);

        ConcurrentLinkedQueue<Integer> offsetsQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            offsetsQueue.add(i * setOff);
        }
        List<CompletableFuture<List<Import_Detail_Entity>>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                List<Import_Detail_Entity> results = new ArrayList<>();
                while (continueFlag.get()) {
                    Integer offset = offsetsQueue.poll();
                    if (offset == null) {
                        continueFlag.set(false);
                        break;
                    }
                    List<Import_Detail_Entity> partialResult = importDetailDao.getFilteredImportDetails(
                            id, idParent, idSP, sortField, sortOrder, offset, setOff
                    );

                    if (partialResult.isEmpty()) {
                        continueFlag.set(false);
                        break;
                    }
                    results.addAll(partialResult);
                }
                return results;
            }, executor));
        }
        List<Import_Detail_Entity> results = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        executor.shutdown();
        return results;
    }

    public boolean confirmImportAddProduct(String id) {
        if (id != null) {
            EntityTransaction transaction = importDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                if (importDao.checkImport(id)) {
                    List<Import_Detail_Entity> listImportDetail = importDetailDao.getFilteredImportDetails(null, id, null, null, null, null, null);
                    for (Import_Detail_Entity importDetail : listImportDetail) {
                        String idSp = importDetail.getID_SP();
                        Integer quantity = importDetail.getQUANTITY_SP();
                        ProductService productService = new ProductService();
                        Product_Entity productE = productService.getProductByIDS(idSp);
                        if (productE == null) {
                            throw new RuntimeException("Error");
                        }
                        Integer newQuantity = productE.getQUANTITY() + quantity;
                        Product_Entity productEntity = new Product_Entity(id, null, null, null, null, null, null, newQuantity, null, null);
                        if (!productService.changeProduct(productEntity)) {
                            throw new RuntimeException("Error");
                        }
                    }
                    transaction.commit();
                }

            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
        return false;
    }

    public boolean deleteImport(String idParent) {
        // add check
        if (idParent != null) {
            EntityTransaction transaction = importDao.getEntityManager().getTransaction();
            try {
                transaction.begin();

                boolean isDeleted = importDetailDao.deleteImportDetail(idParent);
                if (!isDeleted) {
                    throw new RuntimeException("Failed to delete import detail.");
                }
                boolean isUpdated = importDao.deleteImport(idParent);
                if (!isUpdated) {
                    throw new RuntimeException("Failed to delete import general.");
                }
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean registerNewImport(Import_Entity importEntity) {
        //add check
        LocalDateTime DATE_JOIN = LocalDateTime.now();
        importEntity.setDATE_IMP(DATE_JOIN);
        return importDao.createImport(importEntity);
    }

    public boolean registerNewImportDetail(List<Import_Detail_Entity> listImportDetail) {
        //add check
        if (listImportDetail != null) {
            try {
                importDetailDao.createImportDetail(listImportDetail);
                for (Import_Detail_Entity importDetail : listImportDetail) {
                    String id = importDetail.getID_IPARENT();
                    plusProduct(id);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean plusProduct(String id) {
        if (id != null) {
            EntityTransaction transaction = importDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                if (importDao.getFilteredImports(null, null, id, null, null, null, null, null, null) != null) {
                    List<Import_Detail_Entity> listImportDetail = importDetailDao.getFilteredImportDetails(null, id, null, null, null, null, null);
                    for (Import_Detail_Entity importDetail : listImportDetail) {
                        String idSp = importDetail.getID_SP();
                        System.out.println(idSp);
                        Integer quantity = importDetail.getQUANTITY_SP();
                        ProductService productService = new ProductService();
                        Product_Entity productE = productService.getProductByIDS(idSp);
                        if (productE == null) {
                            throw new RuntimeException("Error");
                        }
                        Integer newQuantity = productE.getQUANTITY() + quantity;
                        productE.setQUANTITY(newQuantity);

                        return productService.changeProduct(productE);
                    }
                    transaction.commit();
                }

            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
        return false;
    }
}
