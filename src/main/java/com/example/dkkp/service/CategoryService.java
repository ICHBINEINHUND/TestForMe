package com.example.dkkp.service;

import com.example.dkkp.dao.CategoryDao;
import com.example.dkkp.model.EnumType;
import com.example.dkkp.model.Category_Entity;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.dkkp.model.EnumType.Bug_Type.UI;

public class CategoryService {
  private final CategoryDao categoryDao ;

  public CategoryService() {
    this.categoryDao  = new CategoryDao ();
  }


  public void createNewCategory(){
    String ID_REPORT =" 12ee22";
    String NAME_CATEGORY ="dcm";
    String ID_PARENT = "098748950";
    String ID_BRAND = "dc";
    boolean IS_BASE_PRODUCT = true;
    Category_Entity category = new Category_Entity(ID_REPORT,NAME_CATEGORY,ID_PARENT,ID_BRAND,IS_BASE_PRODUCT);
    categoryDao.createCategory(category);
    System.out.println("da push thanh cong");
  }
  public List<Category_Entity> getFilteredCategories(
          String id,
          String name,
          String idParent,
          String idBrand,
          Boolean isBaseProduct,
          String sortField,
          String sortOrder,
          int offset,
          int setOff
  ) {
    return categoryDao.getFilteredCategories(
            id, name, idParent, idBrand, isBaseProduct, sortField, sortOrder, offset, setOff
    );
  }

  public void deleteCategory(String id){
      categoryDao.deleteCategoryById(id);
  }



  public boolean updateCategory(String id, String name,String id_parent,String id_brand,boolean is_product) {
    // add check
    return categoryDao.updateCategory(id, name, id_parent, id_brand, is_product);
  }
}