package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "bill")
public class Bill_Entity {
  @Id
  @Column(name = "\"ID_BILL\"")
  private String ID_BILL;
  @Column(name = "\"DATE_EXP\"")
  private Date DATE_EXP;
  @Column(name = "\"PRICE_TOTAL\"")
  private String PRICE_TOTAL;
  @Column(name = "\"ID_USER\"")
  private String ID_USER;
  @Column(name = "\"BILL_STATUS\"")
  private String BILL_STATUS;

  public Bill_Entity(String ID_BILL, Date DATE_EXP, String PRICE_TOTAL, String ID_USER, String BILL_STATUS) {
    this.ID_BILL = ID_BILL;
    this.DATE_EXP = DATE_EXP;
    this.PRICE_TOTAL = PRICE_TOTAL;
    this.ID_USER = ID_USER;
    this.BILL_STATUS = BILL_STATUS;
  }

  public Bill_Entity() {}

  public String getID_BILL() {
    return ID_BILL;
  }

  public void setID_BILL(String ID_BILL) {
    this.ID_BILL = ID_BILL;
  }

  public Date getDATE_EXP() {
    return DATE_EXP;
  }

  public void setDATE_EXP(Date DATE_EXP) {
    this.DATE_EXP = DATE_EXP;
  }

  public String getPRICE_TOTAL() {
    return PRICE_TOTAL;
  }

  public void setPRICE_TOTAL(String PRICE_TOTAL) {
    this.PRICE_TOTAL = PRICE_TOTAL;
  }

  public String getID_USER() {
    return ID_USER;
  }

  public void setID_USER(String ID_USER) {
    this.ID_USER = ID_USER;
  }

  public String getBILL_STATUS() {
    return BILL_STATUS;
  }

  public void setBILL_STATUS(String BILL_STATUS) {
    this.BILL_STATUS = BILL_STATUS;
  }
}