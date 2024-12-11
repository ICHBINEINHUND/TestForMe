package com.example.dkkp.service;

import com.example.dkkp.dao.ImportDao;
import com.example.dkkp.dao.ImportDetailDao;
import com.example.dkkp.dao.ProductDao;
import com.example.dkkp.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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
    private final EntityManager entityManager;
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public ImportService() {
        this.importDao = new ImportDao();
        this.importDetailDao = new ImportDetailDao();
        this.productDao = new ProductDao();
        this.entityManager = entityManagerFactory.createEntityManager();
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
                boolean isMinus = minusImportProduct(idParent);
                if (!isMinus) {
                    throw new RuntimeException("Failed to minus import product.");
                }
                transaction.commit();
                return true;
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                return false;
            }
        }
        return false;
    }

    public boolean registerNewImport(Import_Entity importEntity, List<Import_Detail_Entity> listImportDetail) {
        //add check
        EntityTransaction transaction = importDao.getEntityManager().getTransaction();
        try {
            transaction.begin();
            LocalDateTime DATE_JOIN = LocalDateTime.now();
            importEntity.setDATE_IMP(DATE_JOIN);
            if (!importDao.createImport(importEntity)) throw new RuntimeException("Failed to creat import general");
            if (!registerNewImportDetail(listImportDetail))
                throw new RuntimeException("Failed to register new import detail.");
            String id = importEntity.getID_IMP();
            if (!plusImportProduct(id)) throw new RuntimeException("Failed to plus product.");
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    private boolean registerNewImportDetail(List<Import_Detail_Entity> listImportDetail) {
        //add check
        if (listImportDetail != null) {
            try {
                boolean isCreated = importDetailDao.createImportDetail(listImportDetail);
                if (isCreated) {
                    Double sumPrice = 0.0;
                    String id = null;
                    for (Import_Detail_Entity importDetail : listImportDetail) {
                        sumPrice += importDetail.getPRICE_IMP_SP();
                        id = importDetail.getID_IPARENT();
                    }
                    if (importDao.addSumPrice(id, sumPrice)) throw new RuntimeException("Failed to add sum price.");
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean plusImportProduct(String id) {
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

    public boolean minusImportProduct(String id) {
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
                        Integer newQuantity = productE.getQUANTITY() - quantity;
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
