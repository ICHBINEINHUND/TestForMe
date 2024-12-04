package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bill_detail")
public class Bill_Detail_Entity {
  @Id
  @Column(name = "\"ID_BILL\"")
  private String ID_BILL;
  @Column(name = "\"ID_PARENT\"")
  private String ID_PARENT;
  @Column(name = "\"QUANTITY_BILL\"")
  private int QUANTITY_BILL;
  @Column(name = "\"PRICE_BUY\"")
  private Double PRICE_BUY;
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"AVAILABLE\"")
  private Boolean AVAILABLE;

  public Bill_Detail_Entity(String ID_BILL, String ID_PARENT, int QUANTITY_BILL, Double PRICE_BUY, String ID_SP, Boolean AVAILABLE) {
    this.ID_BILL = ID_BILL;
    this.ID_PARENT = ID_PARENT;
    this.QUANTITY_BILL = QUANTITY_BILL;
    this.PRICE_BUY = PRICE_BUY;
    this.ID_SP = ID_SP;
    this.AVAILABLE = AVAILABLE;
  }

  public Boolean getAVAILABLE() {
    return AVAILABLE;
  }

  public void setAVAILABLE(Boolean AVAILABLE) {
    this.AVAILABLE = AVAILABLE;
  }

  public String getID_PARENT() {
    return ID_PARENT;
  }

  public void setID_PARENT(String ID_PARENT) {
    this.ID_PARENT = ID_PARENT;
  }

  public Bill_Detail_Entity() {}

  public String getID_BILL() {
    return ID_BILL;
  }

  public void setID_BILL(String ID_BILL) {
    this.ID_BILL = ID_BILL;
  }

  public int getQUANTITY_BILL() {
    return QUANTITY_BILL;
  }

  public void setQUANTITY_BILL(int QUANTITY_BILL) {
    this.QUANTITY_BILL = QUANTITY_BILL;
  }

  public Double getPRICE_BUY() {
    return PRICE_BUY;
  }

  public void setPRICE_BUY(Double PRICE_BUY) {
    this.PRICE_BUY = PRICE_BUY;
  }

  public String getID_SP() {
    return ID_SP;
  }

  public void setID_SP(String ID_SP) {
    this.ID_SP = ID_SP;
  }

}