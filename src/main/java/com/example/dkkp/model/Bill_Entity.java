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
  @Column(name = "\"PHONE_BILL\"")
  private String PHONE_BILL;
  @Column(name = "\"ADD_BILL\"")
  private String ADD_BILL;
  @Column(name = "\"ID_USER\"")
  private String ID_USER;
  @Column(name = "\"BILL_STATUS\"")
  @Enumerated(EnumType.ORDINAL)
  private Status_Bill BILL_STATUS;

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

  public Bill_Entity(String ID_BILL, LocalDateTime date_EXP, String PHONE_BILL, String ADD_BILL, String ID_USER, Status_Bill BILL_STATUS) {
    this.ID_BILL = ID_BILL;
    Date_EXP = date_EXP;
    this.PHONE_BILL = PHONE_BILL;
    this.ADD_BILL = ADD_BILL;
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