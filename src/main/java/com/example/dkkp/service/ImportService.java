package com.example.dkkp.service;

import com.example.dkkp.dao.ImportDao;
import com.example.dkkp.dao.ImportDetailDao;
import com.example.dkkp.model.Import_Detail_Entity;
import com.example.dkkp.model.Import_Entity;
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
            LocalDateTime dateJoin,
            String typeDate,
            String id,
            boolean edited,
            String sortField,
            String sortOrder,
            int setOff // Số bản ghi mỗi luồng xử lý
    ) {
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
                            dateJoin, typeDate, id, edited, sortField, sortOrder, offset, setOff
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

    public boolean changeImportDetail(String id, Import_Detail_Entity import_detail_entity){
        // add check
        if (id != null) {
            EntityTransaction transaction = importDao.getEntityManager().getTransaction();
            try {
                transaction.begin();

                boolean isDeleted = importDetailDao.deleteImportDetail(id);
                if (!isDeleted) {
                    throw new RuntimeException("Failed to delete import detail.");
                }

                boolean isUpdated = importDao.updateEditedImport(id);
                if (!isUpdated) {
                    throw new RuntimeException("Failed to update import.");
                }
                if(import_detail_entity.getEDITED_ID() != id){
                    throw new Exception("Error");
                }
                importDetailDao.createImportDetail(import_detail_entity);

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
}
