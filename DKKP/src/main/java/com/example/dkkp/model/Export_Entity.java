package com.example.dkkp.model;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;

@Entity
@Table(name = "export")
public class Export_Entity {
  @Id
  @Column(name = "\"ID_BILL\"")
  private String ID_BILL;
  @Column(name = "\"ID_USER\"")
  private String ID_USER;
  @Column(name = "\"PHONE_BILL\"")
  private String PHONE_BILL;
  @Column(name = "\"ADD_BILL\"")
  private String ADD_BILL;
  @Column(name = "\"BILL_STATUS\"")
  private String BILL_STATUS;
  @Column(name = "\"TOTAL_PRICE\"")
  private String TOTAL_PRICE;
  @Column(name = "\"DATE_EXP\"")
  private String DATE_EXP;
  @Column(name = "\"DESCRIPTION\"")
  private String DESCRIPTION;

  @Type(ListArrayType.class)
  public String getID_BILL() {
    return ID_BILL;
  }

  public String getID_USER() {
    return ID_USER;
  }

  public String getPHONE_BILL() {
    return PHONE_BILL;
  }

  public String getADD_BILL() {
    return ADD_BILL;
  }

  public String getBILL_STATUS() {
    return BILL_STATUS;
  }

  public String getTOTAL_PRICE() {
    return TOTAL_PRICE;
  }

  public String getDATE_EXP() {
    return DATE_EXP;
  }

  public String getDESCRIPTION() {
    return DESCRIPTION;
  }

  public Export_Entity(String ID_BILL, String ID_USER, String PHONE_BILL, String ADD_BILL, String BILL_STATUS, String TOTAL_PRICE, String DATE_EXP, String DESCRIPTION) {
    this.ID_BILL = ID_BILL;
    this.ID_USER = ID_USER;
    this.PHONE_BILL = PHONE_BILL;
    this.ADD_BILL = ADD_BILL;
    this.BILL_STATUS = BILL_STATUS;
    this.TOTAL_PRICE = TOTAL_PRICE;
    this.DATE_EXP = DATE_EXP;
    this.DESCRIPTION = DESCRIPTION;
  }

  public Export_Entity() {}
}