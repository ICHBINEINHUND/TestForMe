package com.example.dkkp.service;

import com.example.dkkp.dao.BillDao;
import com.example.dkkp.dao.BillDetailDao;
import com.example.dkkp.model.*;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
            Bill_Entity billEntity,
            String typeDate,
            String sortField,
            String sortOrder,
            int setOff
    ) throws Exception {
        LocalDateTime dateExport = billEntity.getDate_EXP();
        String id = billEntity.getID_BILL();
        String phone = SecutiryFunction.encrypt(billEntity.getPHONE_BILL());
        String add = SecutiryFunction.encrypt(billEntity.getADD_BILL());
        String idUser = billEntity.getID_USER();
        EnumType.Status_Bill statusBill = billEntity.getBILL_STATUS();
        ExecutorService executor = Executors.newFixedThreadPool(3); // số lượng luồng
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
                        break;}
                    List<Bill_Entity> partialResult = billDao.getFilteredBills(
                            dateExport, typeDate, id, phone, add, idUser, statusBill, add, sortField, sortOrder, offset, setOff);
                    if (partialResult.isEmpty()) {
                        continueFlag.set(false);
                        break;}
                    results.addAll(partialResult);}
                return results;
            }, executor));}
        List<Bill_Entity> results = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        if (Objects.equals(sortField, "PHONE_BILL")) {
            if (sortOrder.equals("desc")) {
                results.sort(Comparator.comparing(Bill_Entity::getPHONE_BILL).reversed());
            } else {
                results.sort(Comparator.comparing(Bill_Entity::getPHONE_BILL));}}
        if (Objects.equals(sortField, "ADD_BILL")) {
            if (sortOrder.equals("desc")) {
                results.sort(Comparator.comparing(Bill_Entity::getADD_BILL).reversed());
            } else {
                results.sort(Comparator.comparing(Bill_Entity::getADD_BILL));}}
        executor.shutdown();
        return results;
    }
    public boolean deleteBillAndDetail(String id) {
        if (id != null) {
            EntityTransaction transaction = billDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                boolean delBill = billDao.deleteBill(id);
                if (!delBill) {
                    throw new RuntimeException("Error");
                }

                boolean delBillDetail = billDetailDao.cancelBillDetail(id);
                if (!delBillDetail) {
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
            EntityTransaction transaction = billDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                if (statusBill == EnumType.Status_Bill.CANC) {
                    billDao.changeBillStatus(id, statusBill);
                    transaction.commit();
                    return plusProduct(id);
                }
                if (billDao.getBillByID(id).getBILL_STATUS() == EnumType.Status_Bill.PEN) {
                    billDao.changeBillStatus(id, statusBill);
                    transaction.commit();
                    return minusProduct(id);
                }
                transaction.commit();
                return billDao.changeBillStatus(id, statusBill);
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean minusProduct(String id) {
        if (id != null) {
            EntityTransaction transaction = billDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                if (billDao.getFilteredBills(null, null, id, null, null, null, null, null, null, null, null, null) != null) {
                    List<Bill_Detail_Entity> listBillDetail = billDetailDao.getFilteredBillDetails(null, null, null, null, id, null, null, null, null, null);
                    for (Bill_Detail_Entity billDetail : listBillDetail) {
                        String idSp = billDetail.getID_SP();
                        Integer quantity = billDetail.getQUANTITY_BILL();
                        ProductService productService = new ProductService();
                        Product_Entity productE = productService.getProductByIDS(idSp);
                        if (productE == null) {
                            throw new RuntimeException("Error");
                        }
                        if (productE.getQUANTITY() < quantity) {
                            throw new RuntimeException("Error");
                        }
                        Integer newQuantity = productE.getQUANTITY() - quantity;

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

    public boolean plusProduct(String id) {
        if (id != null) {
            EntityTransaction transaction = billDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                if (billDao.getFilteredBills(null, null, id, null, null, null, null, null, null, null, null, null) != null) {
                    List<Bill_Detail_Entity> listBillDetail = billDetailDao.getFilteredBillDetails(null, null, null, null, id, null, null, null, null, null);
                    for (Bill_Detail_Entity billDetail : listBillDetail) {
                        String idSp = billDetail.getID_SP();
                        Integer quantity = billDetail.getQUANTITY_BILL();
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

    public boolean registerNewBill(Bill_Entity billEntity) throws Exception {
        //add check
        LocalDateTime DATE_JOIN = LocalDateTime.now();
        String idUser = billEntity.getID_USER();
        UserService userService = new UserService();
        String phone = userService.getUsersByID(idUser).getPHONE_ACC();
        String add = userService.getUsersByID(idUser).getADDRESS();

        billEntity.setADD_BILL(add);
        billEntity.setPHONE_BILL(phone);
        billEntity.setDate_EXP(DATE_JOIN);
        return billDao.createBill(billEntity);
    }

    public boolean registerNewImportDetail(List<Bill_Detail_Entity> listBillDetail) {
        //add check
        if (listBillDetail != null) {
            EntityTransaction transaction = billDetailDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                billDetailDao.createBillDetail(listBillDetail);
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
}
