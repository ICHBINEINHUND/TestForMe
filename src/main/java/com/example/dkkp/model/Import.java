package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "import")
public class Import {
  @Id
  @Column(name = "\"ID_IMP\"")
  private String ID_IMP;
  @Column(name = "\"DATE_IMP\"")
  private Date DATE_IMP;
  @Column(name = "\"ID_SUP\"")
  private String ID_SUP;
  @Column(name = "\"PRICE_IMP\"")
  private Double PRICE_IMP;

  public Import(String ID_IMP, Date DATE_IMP, String ID_SUP, Double PRICE_IMP) {
    this.ID_IMP = ID_IMP;
    this.DATE_IMP = DATE_IMP;
    this.ID_SUP = ID_SUP;
    this.PRICE_IMP = PRICE_IMP;
  }

  public Import() {}

  public String getID_IMP() {
    return ID_IMP;
  }

  public void setID_IMP(String ID_IMP) {
    this.ID_IMP = ID_IMP;
  }

  public Date getDATE_IMP() {
    return DATE_IMP;
  }

  public void setDATE_IMP(Date DATE_IMP) {
    this.DATE_IMP = DATE_IMP;
  }

  public String getID_SUP() {
    return ID_SUP;
  }

  public void setID_SUP(String ID_SUP) {
    this.ID_SUP = ID_SUP;
  }

  public Double getPRICE_IMP() {
    return PRICE_IMP;
  }

  public void setPRICE_IMP(Double PRICE_IMP) {
    this.PRICE_IMP = PRICE_IMP;
  }
}