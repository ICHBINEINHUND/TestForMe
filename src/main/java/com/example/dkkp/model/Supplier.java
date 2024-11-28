package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier {
  @Id
  @Column(name = "\"ID_SUP\"")
  private String ID_SUP;
  @Column(name = "\"NAME_SUP\"")
  private String NAME_SUP;
  @Column(name = "\"ADD_SUP\"")
  private String ADD_SUP;
  @Column(name = "\"PHONE_SUP\"")
  private String PHONE_SUP;

  public Supplier(String ID_SUP, String NAME_SUP, String ADD_SUP, String PHONE_SUP) {
    this.ID_SUP = ID_SUP;
    this.NAME_SUP = NAME_SUP;
    this.ADD_SUP = ADD_SUP;
    this.PHONE_SUP = PHONE_SUP;
  }

  public Supplier() {}

  public String getID_SUP() {
    return ID_SUP;
  }

  public void setID_SUP(String ID_SUP) {
    this.ID_SUP = ID_SUP;
  }

  public String getNAME_SUP() {
    return NAME_SUP;
  }

  public void setNAME_SUP(String NAME_SUP) {
    this.NAME_SUP = NAME_SUP;
  }

  public String getADD_SUP() {
    return ADD_SUP;
  }

  public void setADD_SUP(String ADD_SUP) {
    this.ADD_SUP = ADD_SUP;
  }

  public String getPHONE_SUP() {
    return PHONE_SUP;
  }

  public void setPHONE_SUP(String PHONE_SUP) {
    this.PHONE_SUP = PHONE_SUP;
  }
}