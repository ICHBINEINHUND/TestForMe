package com.example.dkkp.service;

import com.example.dkkp.dao.BillDao;
import com.example.dkkp.dao.BillDetailDao;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Import_Detail_Entity;
import com.example.dkkp.model.Bill_Entity;
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

public class BillService {
    private final BillDao billDao;
    private final BillDetailDao billDetailDao;

    public BillService() {
        this.billDao = new BillDao();
        this.billDetailDao = new BillDetailDao();
    }

    public List<Bill_Entity> getBillByCombinedCondition(
            LocalDateTime dateExport,
            String typeDate,
            String id,
            String phone,
            String add,
            String idUser,
            EnumType.Status_Bill statusBill,
            String sortField,
            String sortOrder,
            int setOff
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        AtomicBoolean continueFlag = new AtomicBoolean(true);

        ConcurrentLinkedQueue<Integer> offsetsQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            offsetsQueue.add(i * setOff);
        }

        List<CompletableFuture<List<Bill_Entity>>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                List<Bill_Entity> results = new ArrayList<>();
                while (continueFlag.get()) {
                    Integer offset = offsetsQueue.poll();
                    if (offset == null) {
                        continueFlag.set(false);
                        break;
                    }
                    List<Bill_Entity> partialResult = billDao.getFilteredBills(
                            dateExport, typeDate, id, phone,add,idUser,statusBill, sortField, sortOrder, offset, setOff
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
        List<Bill_Entity> results = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        executor.shutdown();
        return results;
    }

    public boolean deleteBillAndDetail(String id){
        if (id != null) {
            EntityTransaction transaction = billDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                boolean delBill = billDao.deleteBill(id);
                if (!delBill) {
                    throw new RuntimeException("Error");
                }

                boolean delBillDetail = billDetailDao.cancelBillDetail(id);
                if(!delBillDetail){
                    throw new RuntimeException("Error");
                }
                transaction.commit();
                return true;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean changeBillStatus(String id, EnumType.Status_Bill statusBill) {
        if (id != null && statusBill != null) {
            billDao.changeBillStatus(id, statusBill);
            return true;
        }
        return false;
    }
}
