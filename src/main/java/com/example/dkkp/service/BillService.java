package com.example.dkkp.service;

import com.example.dkkp.dao.BillDao;
import com.example.dkkp.dao.BillDetailDao;
import com.example.dkkp.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class BillService {
    private final BillDao billDao;
    private final BillDetailDao billDetailDao;
    private static final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("DKKPPersistenceUnit");
    }

    public BillService(EntityManager entityManager) {
        this.billDao = new BillDao(entityManager);
        this.billDetailDao = new BillDetailDao(entityManager);
        this.entityManager = entityManager;
    }

    public List<Bill_Entity> getBillByCombinedCondition(
            Bill_Entity billEntity,
            String typeDate,
            String sortField,
            String sortOrder,
            int setOff,
            int offset
    ) throws Exception {
        LocalDateTime dateExport = billEntity.getDate_EXP();
        Integer id = billEntity.getID_BILL();
        String phone = SecurityFunction.encrypt(billEntity.getPHONE_BILL());
        String add = SecurityFunction.encrypt(billEntity.getADD_BILL());
        String idUser = billEntity.getID_USER();
        Double totalPrice = billEntity.getTOTAL_PRICE();
        EnumType.Status_Bill statusBill = billEntity.getBILL_STATUS();
        if ((reflectField.isPropertyNameMatched(Bill_Entity.class, sortField) && sortOrder != null) || sortField == null) {
            List<Bill_Entity> results = billDao.getFilteredBills(
                    dateExport, typeDate, id, phone, idUser, statusBill, add, totalPrice, sortField, sortOrder, offset, setOff);
            for (Bill_Entity bill : results) {
                String phoneDe = SecurityFunction.decrypt(bill.getPHONE_BILL());
                String addDe = SecurityFunction.decrypt(bill.getADD_BILL());
                bill.setPHONE_BILL(phoneDe);
                bill.setADD_BILL(addDe);
            }
            return results;
        }
        throw new RuntimeException("Erro with sort");
    }

    public List<Bill_Detail_Entity> getBillDetailByCombinedCondition(
            Bill_Detail_Entity billDetailEntity,
            String sortField,
            String sortOrder,
            int setOff,
            int offset
    ) throws Exception {
        Integer idBillDetail = billDetailEntity.getID_BILL_DETAIL();
        Integer idBill = billDetailEntity.getID_BILL();
        Double totalPrice = billDetailEntity.getTOTAL_DETAIL_PRICE();
        Double unitPrice = billDetailEntity.getUNIT_PRICE();
        Boolean available = billDetailEntity.getAVAILABLE();
        Integer idFinalProduct = billDetailEntity.getID_FINAL_PRODUCT();
        Integer quantityBill = billDetailEntity.getQUANTITY_BILL();
        if (reflectField.isPropertyNameMatched(Bill_Detail_Entity.class, sortField) || sortField == null) {
            return billDetailDao.getFilteredBillDetails(
                    idBillDetail, totalPrice, unitPrice, idFinalProduct, quantityBill, idBill, available, sortField, sortOrder, offset, setOff);
        }
        throw new RuntimeException("Erro with sort");
    }


    public void changeBillStatus(Integer id, EnumType.Status_Bill statusBill) {

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


    public void minusProduct(Integer id) {
        if (billDao.getFilteredBills(null, null, id, null, null, null, null, null, null, null, null, null) != null) {
            List<Bill_Detail_Entity> listBillDetail = billDetailDao.getFilteredBillDetails(null, null, null, null, null, id, null, null, null, null, null);
            for (Bill_Detail_Entity billDetail : listBillDetail) {
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
                Product_Base_Entity productE = productBaseService.getProductBaseByID(billDetail.getID_FINAL_PRODUCT());
                if (productE.getQUANTITY() < billDetail.getQUANTITY_BILL()) {
                    throw new RuntimeException("Error the quantity in warehouse is lower than the quantity to minus");
                }
                Integer newQuantity = productE.getQUANTITY() - billDetail.getQUANTITY_BILL();
                Product_Base_Entity productEntity = new Product_Base_Entity(id, null, newQuantity, null, null, null, null, null);
                productBaseService.updateProductBase(productEntity);
            }
        }

    }

    public void plusBillProduct(Integer id) {
        if (billDao.getFilteredBills(null, null, id, null, null, null, null, null, null, null, null, null) != null) {
            List<Bill_Detail_Entity> listBillDetail = billDetailDao.getFilteredBillDetails(null, null, null, null, null, id, null, null, null, null, null);
            for (Bill_Detail_Entity billDetail : listBillDetail) {
                ProductBaseService productBaseService = new ProductBaseService(entityManager);
                Product_Base_Entity productE = productBaseService.getProductBaseByID(billDetail.getID_FINAL_PRODUCT());
                Integer newQuantity = productE.getQUANTITY() + billDetail.getQUANTITY_BILL();
                Product_Base_Entity productEntity = new Product_Base_Entity(id, null, newQuantity, null, null, null, null, null);
                productBaseService.updateProductBase(productEntity);
            }
        }
    }


    public void registerNewBill(Bill_Entity billEntity) throws Exception {
        //add check
        LocalDateTime DATE_JOIN = LocalDateTime.now();
        String idUser = billEntity.getID_USER();
        UserService userService = new UserService(entityManager);
        User_Entity user = userService.getUsersByID(idUser);
        userService.decryptUserSensitiveData(user);
        String phone = user.getPHONE_ACC();
        String add = user.getADDRESS();

        billEntity.setADD_BILL(add);
        billEntity.setPHONE_BILL(phone);
        billEntity.setDate_EXP(DATE_JOIN);
         billDao.createBill(billEntity);
    }

    private boolean registerNewBillDetail(List<Bill_Detail_Entity> listBillDetail) {
        //add check
        if (listBillDetail != null) {
            billDetailDao.createBillDetail(listBillDetail);
            Double sumPrice = 0.0;
            Integer id = null;
            for (Bill_Detail_Entity billDetail : listBillDetail) {
                id = billDetail.getID_BILL();
                sumPrice += billDetail.getTOTAL_DETAIL_PRICE();
            }
            if (id == null) throw new RuntimeException("Error cant find bill general to create bill detail");
            billDao.addSumPrice(id, sumPrice);
            return true;
        }
        throw new RuntimeException("Error list bill detail is null");
    }
}
