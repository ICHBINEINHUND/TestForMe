package com.example.dkkp.service;

import com.example.dkkp.dao.ImportDao;
import com.example.dkkp.dao.ImportDetailDao;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Import_Detail_Entity;
import com.example.dkkp.model.Import_Entity;
import com.example.dkkp.model.User_Entity;
import jakarta.persistence.EntityTransaction;

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

    public ImportService() {
        this.importDao = new ImportDao();
        this.importDetailDao = new ImportDetailDao();
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
//        LocalDateTime DATE_JOIN = LocalDateTime.now();
        Import_Entity importE = importEntity;
        return importDao.createImport(importE);
    }

    public boolean registerNewImportDetail(List<Import_Detail_Entity> listImportDetail) {
        //add check
        if (listImportDetail != null) {
            EntityTransaction transaction = importDetailDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                for (Import_Detail_Entity importDetail : listImportDetail) {
                    Import_Detail_Entity importD = importDetail;
                    importDetailDao.createImportDetail(importD);
                    //add product
                    return true;
                }

            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }

                return false;
            }
        }
        return false;
    }
}
