package com.example.dkkp.service;

import com.example.dkkp.dao.BillDao;
import com.example.dkkp.dao.BillDetailDao;
import com.example.dkkp.model.*;
import jakarta.persistence.EntityManager;


import java.time.LocalDateTime;
import java.util.List;

public class BillService {
    private final BillDao billDao;
    private final BillDetailDao billDetailDao;
    private final EntityManager entityManager;


    public BillService(EntityManager entityManager) {
        this.billDao = new BillDao(entityManager);
        this.billDetailDao = new BillDetailDao(entityManager);
        this.entityManager = entityManager;
    }

    public List<Bill_Entity> getBillByCombinedCondition(
            Bill_Entity billEntity,
            String typeDate,
            String typePrice,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offset
    ) throws Exception {
        LocalDateTime dateExport = billEntity.getDATE_EXP();
        String id = billEntity.getID_BILL();
//        String phone = SecurityFunction.encrypt(billEntity.getPHONE_BILL());
        String phone = billEntity.getPHONE_BILL();
        String add = billEntity.getADD_BILL();
//        String add = SecurityFunction.encrypt(billEntity.getADD_BILL());
        String idUser = billEntity.getID_USER();
        Double totalPrice = billEntity.getTOTAL_PRICE();
        String EMAIL_ACC = billEntity.getEMAIL_ACC();
        EnumType.Status_Bill statusBill = billEntity.getBILL_STATUS();

        List<Bill_Entity> results = billDao.getFilteredBills(
                dateExport, typeDate, id, phone, idUser, EMAIL_ACC, statusBill, add, totalPrice, typePrice, sortField, sortOrder, setOff, offset);
//        for (Bill_Entity bill : results) {
//            decryptBillSensitiveData(bill);
//        }
        return results;
    }

    public Integer getCountBillByCombinedCondition(
            Bill_Entity billEntity,
            String typeDate,
            String typePrice
    ) throws Exception {
        LocalDateTime dateExport = billEntity.getDATE_EXP();
        String id = billEntity.getID_BILL();
        String phone = SecurityFunction.encrypt(billEntity.getPHONE_BILL());
        String add = SecurityFunction.encrypt(billEntity.getADD_BILL());
        String idUser = billEntity.getID_USER();
        Double totalPrice = billEntity.getTOTAL_PRICE();
        String EMAIL_ACC = billEntity.getEMAIL_ACC();
        EnumType.Status_Bill statusBill = billEntity.getBILL_STATUS();
        return billDao.getFilteredBillCount(
                dateExport, typeDate, id, phone, idUser, EMAIL_ACC, statusBill, add, totalPrice, typePrice);
    }

    public List<Bill_Detail_Entity> getBillDetailByCombinedCondition(
            Bill_Detail_Entity billDetailEntity,
            String typeUPrice,
            String typeQuantity,
            String typePPrice,
            String sortField,
            String sortOrder,
            Integer setOff,
            Integer offset
    ) {
        Integer idBillDetail = billDetailEntity.getID_BILL_DETAIL();
        String idBill = billDetailEntity.getID_BILL();
        Double totalPrice = billDetailEntity.getTOTAL_DETAIL_PRICE();
        Double unitPrice = billDetailEntity.getUNIT_PRICE();
        Boolean available = billDetailEntity.getAVAILABLE();
        Integer idFinalProduct = billDetailEntity.getID_FINAL_PRODUCT();
        Integer quantityBill = billDetailEntity.getQUANTITY_BILL();
        String NAME_FINAL_PRODUCT = billDetailEntity.getNAME_FINAL_PRODUCT();
        return billDetailDao.getFilteredBillDetails(
                idBillDetail, totalPrice, typePPrice, unitPrice, typeUPrice, idFinalProduct, NAME_FINAL_PRODUCT, quantityBill, typeQuantity, idBill, available, sortField, sortOrder, offset, setOff);
    }

    public Integer getCountBillDetailByCombinedCondition(
            Bill_Detail_Entity billDetailEntity,
            String typeUPrice,
            String typeQuantity,
            String typePPrice
    ) {
        Integer idBillDetail = billDetailEntity.getID_BILL_DETAIL();
        String idBill = billDetailEntity.getID_BILL();
        Double totalPrice = billDetailEntity.getTOTAL_DETAIL_PRICE();
        Double unitPrice = billDetailEntity.getUNIT_PRICE();
        Boolean available = billDetailEntity.getAVAILABLE();
        Integer idFinalProduct = billDetailEntity.getID_FINAL_PRODUCT();
        Integer quantityBill = billDetailEntity.getQUANTITY_BILL();
        String NAME_FINAL_PRODUCT = billDetailEntity.getNAME_FINAL_PRODUCT();
        return billDetailDao.getFilteredBillDetailsCount(
                idBillDetail, totalPrice, typePPrice, unitPrice, typeUPrice, idFinalProduct, NAME_FINAL_PRODUCT, quantityBill, typeQuantity, idBill, available);
    }


    public void changeBillStatus(String id, EnumType.Status_Bill statusBill) {

        if (statusBill == EnumType.Status_Bill.CANC) {
            billDao.changeBillStatus(id, statusBill);
            billDetailDao.cancelBillDetail(id);
            plusBillProduct(id);
        }
        if (billDao.getBillByID(id).getBILL_STATUS() == EnumType.Status_Bill.PEN) {
            billDao.changeBillStatus(id, statusBill);
            minusProduct(id);
        }
        billDao.changeBillStatus(id, statusBill);
    }


    public void minusProduct(String id) {
        if (billDao.getFilteredBills(null, null, id, null, null, null, null, null, null, null, null, null, null, null) != null) {
            List<Bill_Detail_Entity> listBillDetail = billDetailDao.getFilteredBillDetails(null, null, null, null, null, null, null, null, null, id, null, null, null, null, null);
            for (Bill_Detail_Entity billDetail : listBillDetail) {
                ProductFinalService productFinalService = new ProductFinalService(entityManager);
                Product_Final_Entity productE = productFinalService.getProductByID(billDetail.getID_FINAL_PRODUCT());
                if (productE == null) throw new RuntimeException("Cant find product final to minus quantity");
                if (productE.getQUANTITY() < billDetail.getQUANTITY_BILL())
                    throw new RuntimeException("Quantity product in final storage is smaller to minus");
                Integer newQuantity = productE.getQUANTITY() - billDetail.getQUANTITY_BILL();
                productE.setQUANTITY(newQuantity);
                productFinalService.updateProductFinal(productE);
            }
        }

    }

    public void plusBillProduct(String id) {
        System.out.println("day la " + id);
        List<Bill_Detail_Entity> listBillDetail = billDetailDao.getFilteredBillDetails(null, null, null, null, null, null, null, null, null, id, null, null, null, null, null);
        for (Bill_Detail_Entity billDetail : listBillDetail) {
            ProductFinalService productFinalService = new ProductFinalService(entityManager);
            Product_Final_Entity productE = productFinalService.getProductByID(billDetail.getID_FINAL_PRODUCT());
            if (productE == null) throw new RuntimeException("Cant find product final to plus quantity");
            Integer newQuantity = productE.getQUANTITY() + billDetail.getQUANTITY_BILL();
            productE.setQUANTITY(newQuantity);
            productFinalService.updateProductFinal(productE);

        }
    }


    public void registerNewBill(Bill_Entity billEntity, String phone, String add) throws Exception {
        //add check
        LocalDateTime DATE_JOIN = LocalDateTime.now();
        if (billEntity.getID_USER() != null) {
            System.out.println("mem ay beo1");
            String idUser = billEntity.getID_USER();
            System.out.println("mem ay beo2");
            UserService userService = new UserService(entityManager);
            System.out.println("mem ay beo3");
            User_Entity user = userService.getUsersByID(idUser);
            System.out.println("mem ay beo4");
            if (phone == null) {
                phone = user.getPHONE_ACC();
            } else {
                phone = SecurityFunction.encrypt(phone);
            }
            System.out.println("mem ay beo5");
            if (add == null) {
                add = user.getADDRESS();
            } else {
                add = SecurityFunction.encrypt(add);
            }
            System.out.println("mem ay beo6");

        } else {
            phone = SecurityFunction.encrypt(phone);
            add = SecurityFunction.encrypt(add);
        }
        System.out.println("mm beo111");
        billEntity.setADD_BILL(add);
        System.out.println("mm beo122");
        billEntity.setPHONE_BILL(phone);
        System.out.println("mm beo33");
        billEntity.setDATE_EXP(DATE_JOIN);
        System.out.println("mm beo44");
        billDao.createBill(billEntity);
        System.out.println("mm beo55");
    }

    public void registerNewBillDetail(List<Bill_Detail_Entity> listBillDetail) {
        //add check
        if (listBillDetail != null) {
            System.out.println();
            billDetailDao.createBillDetail(listBillDetail);
            Double sumPrice = 0.0;
            String id = listBillDetail.getFirst().getID_BILL();
            if (id == null) throw new RuntimeException("Error cant find bill general to create bill detail");
            for (Bill_Detail_Entity billDetail : listBillDetail) {
                sumPrice += billDetail.getTOTAL_DETAIL_PRICE();
            }
            billDao.addSumPrice(id, sumPrice);
        }
    }

    public void decryptBillSensitiveData(Bill_Entity bill) throws Exception {
        if (bill.getADD_BILL() != null) {
            bill.setADD_BILL(SecurityFunction.decrypt(bill.getADD_BILL()));
        }
        if (bill.getPHONE_BILL() != null) {
            bill.setPHONE_BILL(SecurityFunction.decrypt(bill.getPHONE_BILL()));
        }

    }

    public void encryptBillSensitiveData(Bill_Entity bill) throws Exception {
        if (bill.getADD_BILL() != null) {
            bill.setADD_BILL(SecurityFunction.encrypt(bill.getADD_BILL()));
        }
        if (bill.getPHONE_BILL() != null) {
            bill.setPHONE_BILL(SecurityFunction.encrypt(bill.getPHONE_BILL()));
        }

    }
}
