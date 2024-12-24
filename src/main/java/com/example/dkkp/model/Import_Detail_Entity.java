package com.example.dkkp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "import_detail")
public class Import_Detail_Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"ID_IMPD\"")//
  private Integer ID_IMPD;
  @Column(name = "\"ID_IMPORT\"")//
  private Integer ID_IMPORT;
  @Column(name = "\"IS_AVAILABLE\"")//
  private boolean IS_AVAILABLE;
  @Column(name = "\"ID_BASE_PRODUCT\"")//
  private Integer ID_BASE_PRODUCT;
  @Column(name = "\"ID_FINAL_PRODUCT\"")//
  private Integer ID_FINAL_PRODUCT;
  @Column(name = "\"QUANTITY\"")//
  private Integer QUANTITY;
  @Column(name = "\"UNIT_PRICE\"")//
  private Double UNIT_PRICE;
  @Column(name = "\"TOTAL_PRICE\"")//
  private Double TOTAL_PRICE;
  @Column(name = "\"DESCRIPTION\"")//
  private String DESCRIPTION;

  public Integer getID_IMPD() {
    return ID_IMPD;
  }

  public void setID_IMPD(Integer ID_IMPD) {
    this.ID_IMPD = ID_IMPD;
  }

  public Integer getID_IMPORT() {
    return ID_IMPORT;
  }

  public void setID_IMPORT(Integer ID_IMPORT) {
    this.ID_IMPORT = ID_IMPORT;
  }

  public boolean getIS_AVAILABLE() {
    return IS_AVAILABLE;
  }

  public void setIS_AVAILABLE(boolean IS_AVAILABLE) {
    this.IS_AVAILABLE = IS_AVAILABLE;
  }

  public Integer getID_BASE_PRODUCT() {
    return ID_BASE_PRODUCT;
  }

  public void setID_BASE_PRODUCT(Integer ID_BASE_PRODUCT) {
    this.ID_BASE_PRODUCT = ID_BASE_PRODUCT;
  }

  public Integer getID_FINAL_PRODUCT() {
    return ID_FINAL_PRODUCT;
  }

  public void setID_FINAL_PRODUCT(Integer ID_FINAL_PRODUCT) {
    this.ID_FINAL_PRODUCT = ID_FINAL_PRODUCT;
  }

  public Integer getQUANTITY() {
    return QUANTITY;
  }

  public void setQUANTITY(Integer QUANTITY) {
    this.QUANTITY = QUANTITY;
  }

  public Double getUNIT_PRICE() {
    return UNIT_PRICE;
  }

  public void setUNIT_PRICE(Double UNIT_PRICE) {
    this.UNIT_PRICE = UNIT_PRICE;
  }

  public Double getTOTAL_PRICE() {
    return TOTAL_PRICE;
  }

  public void setTOTAL_PRICE(Double TOTAL_PRICE) {
    this.TOTAL_PRICE = TOTAL_PRICE;
  }

  public String getDESCRIPTION() {
    return DESCRIPTION;
  }

  public void setDESCRIPTION(String DESCRIPTION) {
    this.DESCRIPTION = DESCRIPTION;
  }

  public Import_Detail_Entity(Integer ID_IMPD, Integer ID_IMPORT, boolean IS_AVAILABLE, Integer ID_BASE_PRODUCT, Integer ID_FINAL_PRODUCT, Integer QUANTITY, Double UNIT_PRICE, Double TOTAL_PRICE, String DESCRIPTION) {
    this.ID_IMPD = ID_IMPD;
    this.ID_IMPORT = ID_IMPORT;
    this.IS_AVAILABLE = IS_AVAILABLE;
    this.ID_BASE_PRODUCT = ID_BASE_PRODUCT;
    this.ID_FINAL_PRODUCT = ID_FINAL_PRODUCT;
    this.QUANTITY = QUANTITY;
    this.UNIT_PRICE = UNIT_PRICE;
    this.TOTAL_PRICE = TOTAL_PRICE;
    this.DESCRIPTION = DESCRIPTION;
  }

  public Import_Detail_Entity() {
  }
}