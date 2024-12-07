package com.example.dkkp.service;

import com.example.dkkp.dao.OptionValuesDao;
import com.example.dkkp.dao.ProductDao;
import com.example.dkkp.dao.BillDetailDao;
import com.example.dkkp.dao.ProductOptionDao;
import com.example.dkkp.model.Option_Values_Entity;
import com.example.dkkp.model.Product_Entity;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Product_Option_Entity;
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

public class ProductService {
    private final ProductDao productDao;
    private final ProductOptionDao productOptionDao;
    private final OptionValuesDao optionValuesDao;

    public ProductService() {
        this.productDao = new ProductDao();
        this.productOptionDao = new ProductOptionDao();
        this.optionValuesDao = new OptionValuesDao();
    }

    public List<Product_Entity> getBillByCombinedCondition(
            Product_Entity product_entity,
            String sortField,
            String sortOrder,
            int setOff
    ) {
        String id = product_entity.getID_SP();
        String NAME_SP = product_entity.getNAME_SP();
        String DES_SP = product_entity.getDES_SP();
        String ID_CATEGORY = product_entity.getID_CATEGORY();
        Double PRICE_SP = product_entity.getPRICE_SP();
        String IMAGE_SP = product_entity.getIMAGE_SP();
        Integer VIEW_COUNT = product_entity.getVIEW_COUNT();
        Integer QUANTITY = product_entity.getQUANTITY();
        Double DISCOUNT = product_entity.getDISCOUNT();
        List<Integer> IDS_OPTION_VALUES = product_entity.getIDS_OPTION_VALUES();

        ExecutorService executor = Executors.newFixedThreadPool(3);
        AtomicBoolean continueFlag = new AtomicBoolean(true);

        ConcurrentLinkedQueue<Integer> offsetsQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            offsetsQueue.add(i * setOff);
        }

        List<CompletableFuture<List<Product_Entity>>> futures = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> {
                List<Product_Entity> results = new ArrayList<>();
                while (continueFlag.get()) {
                    Integer offset = offsetsQueue.poll();
                    if (offset == null) {
                        continueFlag.set(false);
                        break;
                    }
                    List<Product_Entity> partialResult = productDao.getFilteredProduct(
                            id, NAME_SP, DES_SP, ID_CATEGORY, PRICE_SP, IMAGE_SP, VIEW_COUNT, QUANTITY, DISCOUNT, IDS_OPTION_VALUES, sortField, sortOrder, offset, setOff
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
        List<Product_Entity> results = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        executor.shutdown();
        return results;
    }

    public Product_Entity getProductByIDS(String id) {
        if(id != null) {
            return productDao.getProductById(id);
        }
        return null;
    }

    public boolean deleteProduct(String id) {
        if (id != null) {
            EntityTransaction transaction = productDao.getEntityManager().getTransaction();
            try {
                transaction.begin();
                boolean delBill = productDao.deleteProduct(id);
                if (!delBill) {
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

    public boolean changeProduct(Product_Entity product_entity) {
        String id = product_entity.getID_SP();
        String NAME_SP = product_entity.getNAME_SP();
        String DES_SP = product_entity.getDES_SP();
        String ID_CATEGORY = product_entity.getID_CATEGORY();
        Double PRICE_SP = product_entity.getPRICE_SP();
        String IMAGE_SP = product_entity.getIMAGE_SP();
        Integer VIEW_COUNT = product_entity.getVIEW_COUNT();
        Integer QUANTITY = product_entity.getQUANTITY();
        Double DISCOUNT = product_entity.getDISCOUNT();
        List<Integer> IDS_OPTION_VALUES = product_entity.getIDS_OPTION_VALUES();
    //add check
        if(checkIDS_OPTION_VALUES(IDS_OPTION_VALUES)){
        return productDao.updateProduct(id,NAME_SP,DES_SP,ID_CATEGORY,PRICE_SP,IMAGE_SP,VIEW_COUNT,QUANTITY,DISCOUNT,IDS_OPTION_VALUES);
        }
        return false;

    }


    public boolean createProduct(Product_Entity product_entity) {
        String id = product_entity.getID_SP();
        String NAME_SP = product_entity.getNAME_SP();
        String DES_SP = product_entity.getDES_SP();
        String ID_CATEGORY = product_entity.getID_CATEGORY();
        Double PRICE_SP = product_entity.getPRICE_SP();
        String IMAGE_SP = product_entity.getIMAGE_SP();
        Integer VIEW_COUNT = product_entity.getVIEW_COUNT();
        Integer QUANTITY = product_entity.getQUANTITY();
        Double DISCOUNT = product_entity.getDISCOUNT();
        List<Integer> IDS_OPTION_VALUES = product_entity.getIDS_OPTION_VALUES();
        //add check ở phía dưới
        if(checkIDS_OPTION_VALUES(IDS_OPTION_VALUES)){
            return productDao.createProdcut(product_entity);
        }
        return false;
    }
    public boolean createProductOption(Product_Option_Entity product_option_entity) {
        String id = product_option_entity.getID_OPTION();
        String NAME_OPTION = product_option_entity.getNAME_OPTION();
        String TYPE = product_option_entity.getTYPE();
        String ID_BASEPRODUCT = product_option_entity.getID_BASEPRODUCT();
        //acdd check
            return productOptionDao.createProductOption(product_option_entity);
    }
    public boolean createOptionValue(Option_Values_Entity option_values_entity) {
        String ID_VALUE = option_values_entity.getID_OPTION();
        String ID_PARENT = option_values_entity.getID_PARENT();
        String ID_OPTION = option_values_entity.getID_OPTION();
        String VALUE = option_values_entity.getVALUE();
        //acdd check
            return optionValuesDao.createOptionValues(option_values_entity);
    }

    public boolean checkIDS_OPTION_VALUES(List<Integer> IDS_OPTION_VALUES){
        for(Integer i : IDS_OPTION_VALUES){
            if(!optionValuesDao.existsOptionValueById(i)){
                return false;
            };
        }
        return true;
    }


}
