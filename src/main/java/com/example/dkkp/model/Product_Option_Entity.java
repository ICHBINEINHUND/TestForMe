package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_options")
public class Product_Option_Entity {
  @Id
  @Column(name = "\"ID_OPTION\"")
  private String ID_OPTION;
  @Column(name = "\"NAME_OPTION\"")
  private String NAME_OPTION;
  @Column(name = "\"TYPE\"")
  private String TYPE;
  @Column(name = "\"ID_CATEGORY\"")
  private String ID_CATEGORY;

  public Product_Option_Entity(String ID_OPTION, String NAME_OPTION, String TYPE, String ID_CATEGORY) {
    this.ID_OPTION = ID_OPTION;
    this.NAME_OPTION = NAME_OPTION;
    this.TYPE = TYPE;
    this.ID_CATEGORY = ID_CATEGORY;
  }

  public Product_Option_Entity() {}

  public String getID_OPTION() {
    return ID_OPTION;
  }

  public void setID_OPTION(String ID_OPTION) {
    this.ID_OPTION = ID_OPTION;
  }

  public String getNAME_OPTION() {
    return NAME_OPTION;
  }

  public void setNAME_OPTION(String NAME_OPTION) {
    this.NAME_OPTION = NAME_OPTION;
  }

  public String getTYPE() {
    return TYPE;
  }

  public void setTYPE(String TYPE) {
    this.TYPE = TYPE;
  }

  public String getID_CATEGORY() {
    return ID_CATEGORY;
  }

  public void setID_CATEGORY(String ID_CATEGORY) {
    this.ID_CATEGORY = ID_CATEGORY;
  }
}