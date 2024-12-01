package com.example.dkkp.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import com.example.dkkp.model.EnumType.Status_Bill;
@Entity
@Table(name = "bill")
public class Bill_Entity {
  @Id
  @Column(name = "\"ID_BILL\"")
  private String ID_BILL;
  @Column(name = "\"DATE_EXP\"")
  private LocalDateTime Date_EXP;
  @Column(name = "\"PRICE_TOTAL\"")
  private String PRICE_TOTAL;
  @Column(name = "\"ID_USER\"")
  private String ID_USER;
  @Column(name = "\"BILL_STATUS\"")
  @Enumerated(EnumType.ORDINAL)
  private Status_Bill BILL_STATUS;

  public Bill_Entity(String ID_BILL, LocalDateTime Date_EXP, String PRICE_TOTAL, String ID_USER, Status_Bill BILL_STATUS) {
    this.ID_BILL = ID_BILL;
    this.Date_EXP = Date_EXP;
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

  public LocalDateTime getDate_EXP() {
    return Date_EXP;
  }

  public void setDate_EXP(LocalDateTime Date_EXP) {
    this.Date_EXP = Date_EXP;
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

  public Status_Bill getBILL_STATUS() {
    return BILL_STATUS;
  }

  public void setBILL_STATUS(Status_Bill BILL_STATUS) {
    this.BILL_STATUS = BILL_STATUS;
  }
}