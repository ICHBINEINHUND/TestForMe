package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill_detail")
public class Bill_Detail_Entity {
  @Id
  @Column(name = "\"ID_BILL_DETAIL\"")
  private Integer ID_BILL_DETAIL;
  @Column(name = "\"ID_BILL\"")
  private Integer ID_BILL;
  @Column(name = "\"QUANTITY_SP\"")
  private Integer QUANTITY_SP;
  @Column(name = "\"TOTAL_DETAIL_PRICE\"")
  private Double TOTAL_DETAIL_PRICE;
  @Column(name = "\"UNIT_PRICE\"")
  private Double UNIT_PRICE;
  @Column(name = "\"ID_FINAL_PRODUCT\"")
  private Integer ID_FINAL_PRODUCT;
  @Column(name = "\"AVAILABLE\"")
  private Boolean AVAILABLE;

  public Boolean getAVAILABLE() {
    return AVAILABLE;
  }

  public void setAVAILABLE(Boolean AVAILABLE) {
    this.AVAILABLE = AVAILABLE;
  }

  public Integer getID_FINAL_PRODUCT() {
    return ID_FINAL_PRODUCT;
  }

  public void setID_FINAL_PRODUCT(Integer ID_FINAL_PRODUCT) {
    this.ID_FINAL_PRODUCT = ID_FINAL_PRODUCT;
  }

  public Double getUNIT_PRICE() {
    return UNIT_PRICE;
  }

  public void setUNIT_PRICE(Double UNIT_PRICE) {
    this.UNIT_PRICE = UNIT_PRICE;
  }

  public Double getTOTAL_DETAIL_PRICE() {
    return TOTAL_DETAIL_PRICE;
  }

  public void setTOTAL_DETAIL_PRICE(Double TOTAL_DETAIL_PRICE) {
    this.TOTAL_DETAIL_PRICE = TOTAL_DETAIL_PRICE;
  }

  public Integer getQUANTITY_BILL() {
    return QUANTITY_SP;
  }

  public void setQUANTITY_BILL(Integer QUANTITY_BILL) {
    this.QUANTITY_SP = QUANTITY_BILL;
  }

  public Integer getID_BILL() {
    return ID_BILL;
  }

  public void setID_BILL(Integer ID_BILL) {
    this.ID_BILL = ID_BILL;
  }

  public Integer getID_BILL_DETAIL() {
    return ID_BILL_DETAIL;
  }

  public void setID_BILL_DETAIL(Integer ID_BILL_DETAIL) {
    this.ID_BILL_DETAIL = ID_BILL_DETAIL;
  }

  public Bill_Detail_Entity(Integer ID_BILL_DETAIL, Integer ID_BILL, Integer QUANTITY_BILL, Double TOTAL_DETAIL_PRICE, Double UNIT_PRICE, Integer ID_FINAL_PRODUCT, Boolean AVAILABLE) {
    this.ID_BILL_DETAIL = ID_BILL_DETAIL;
    this.ID_BILL = ID_BILL;
    this.QUANTITY_SP = QUANTITY_BILL;
    this.TOTAL_DETAIL_PRICE = TOTAL_DETAIL_PRICE;
    this.UNIT_PRICE = UNIT_PRICE;
    this.ID_FINAL_PRODUCT = ID_FINAL_PRODUCT;
    this.AVAILABLE = AVAILABLE;
  }

  public Bill_Detail_Entity() {
  }
}