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

  public void deleteCategory(String id){
      categoryDao.deleteCategoryById(id);
  }

  public List<Category_Entity> getCategoryByCombinedCondition(String ID_CATEGORY, String NAME_CATEGORY, String ID_PARENT, String ID_BRAND, boolean IS_BASE_PRODUCT, String sortField, String sortOrder) {
    List<Category_Entity> result;

    if (ID_CATEGORY == null && NAME_CATEGORY == null && ID_PARENT == null&& ID_BRAND == null) {
      result = categoryDao.getAllCategory();
    } else  {
      result = null;
      List<List<Category_Entity>> conditions = List.of(
              ID_CATEGORY != null ? categoryDao.getCategoryByID(ID_CATEGORY) : null,
              NAME_CATEGORY != null ? categoryDao.getCategoryByName(NAME_CATEGORY) : null,
              ID_PARENT != null ? categoryDao.getCategoryByID_PARENT(ID_PARENT) : null,
              ID_BRAND != null ? categoryDao.getCategoryByID_BRAND(ID_BRAND) : null,
              categoryDao.getCategoryByIS_BASE_PRODUCT(IS_BASE_PRODUCT)
      );

      for (List<Category_Entity> condition : conditions) {
        if (condition != null) {
          if (result == null) {
            result = condition;
          } else {
            result.retainAll(condition);
          }
        }
      }
    }

    if (result != null && sortField != null && sortOrder != null) {
      result = categoryDao.sortResults(result, sortField, sortOrder);
    }
    return result != null ? result : List.of();
  }

  public boolean updateCategory(String id, String name,String id_parent,String id_brand,boolean is_product) {
    // add check
    return categoryDao.updateCategory(id, name, id_parent, id_brand, is_product);
  }
}