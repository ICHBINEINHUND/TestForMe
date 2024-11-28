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
  @Column(name = "\"QUANTITY_BILL\"")
  private int QUANTITY_BILL;
  @Column(name = "\"PHONE_BILL\"")
  private String PHONE_BILL;
  @Column(name = "\"ADD_BILL\"")
  private String ADD_BILL;
  @Column(name = "\"PRICE_BUY\"")
  private Double PRICE_BUY;
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"ID_VOU\"")
  private String ID_VOU;

  public Bill_Detail_Entity(String ID_BILL, int QUANTITY_BILL, String PHONE_BILL, String ADD_BILL, Double PRICE_BUY, String ID_SP, String ID_VOU) {
    this.ID_BILL = ID_BILL;
    this.QUANTITY_BILL = QUANTITY_BILL;
    this.PHONE_BILL = PHONE_BILL;
    this.ADD_BILL = ADD_BILL;
    this.PRICE_BUY = PRICE_BUY;
    this.ID_SP = ID_SP;
    this.ID_VOU = ID_VOU;
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

  public String getPHONE_BILL() {
    return PHONE_BILL;
  }

  public void setPHONE_BILL(String PHONE_BILL) {
    this.PHONE_BILL = PHONE_BILL;
  }

  public String getADD_BILL() {
    return ADD_BILL;
  }

  public void setADD_BILL(String ADD_BILL) {
    this.ADD_BILL = ADD_BILL;
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

  public String getID_VOU() {
    return ID_VOU;
  }

  public void setID_VOU(String ID_VOU) {
    this.ID_VOU = ID_VOU;
  }
}