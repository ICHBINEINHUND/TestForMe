package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand_Entity {
  @Id
  @Column(name = "\"ID_BRAND\"")
  private String ID_BRAND;
  @Column(name = "\"NAME_BRAND\"")
  private String NAME_BRAND;
  @Column(name = "\"DETAIL\"")
  private String DETAIL;

  public String getID_CATEGORY() {
    return ID_BRAND;
  }

  public void setID_CATEGORY(String ID_CATEGORY) {
    this.ID_BRAND = ID_CATEGORY;
  }

  public String getNAME_BRAND() {
    return NAME_BRAND;
  }

  public void setNAME_BRAND(String NAME_BRAND) {
    this.NAME_BRAND = NAME_BRAND;
  }

  public String getDETAIL() {
    return DETAIL;
  }

  public void setDETAIL(String DETAIL) {
    this.DETAIL = DETAIL;
  }

  public Brand_Entity(String ID_BRAND, String NAME_BRAND, String DETAIL) {
    this.ID_BRAND = ID_BRAND;
    this.NAME_BRAND = NAME_BRAND;
    this.DETAIL = DETAIL;
  }

  public Brand_Entity() {}
}