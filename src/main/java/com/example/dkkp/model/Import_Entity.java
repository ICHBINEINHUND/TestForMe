package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "import")
public class Import_Entity {
  @Id
  @Column(name = "\"ID_IMP\"")
  private String ID_IMP;
  @Column(name = "\"DATE_IMP\"")
  private LocalDateTime DATE_IMP;
  @Column(name = "\"DESCRIPTION\"")
  private String DESCRIPTION;
  @Column(name = "\"STATUS\"")
  private Boolean STATUS;
  @Column(name = "\"ID_REPLACE\"")
  private String ID_REPLACE;
  @Column(name = "\"SUM_PRICE\"")
  private Double SUM_PRICE;



  public String getID_IMP() {
    return ID_IMP;
  }

  public void setID_IMP(String ID_IMP) {
    this.ID_IMP = ID_IMP;
  }

  public LocalDateTime getDATE_IMP() {
    return DATE_IMP;
  }

  public void setDATE_IMP(LocalDateTime DATE_IMP) {
    this.DATE_IMP = DATE_IMP;
  }

  public String getDESCRIPTION() {
    return DESCRIPTION;
  }

  public void setDESCRIPTION(String DESCRIPTION) {
    this.DESCRIPTION = DESCRIPTION;
  }


  public Boolean getSTATUS() {
    return STATUS;
  }

  public void setSTATUS(Boolean STATUS) {
    this.STATUS = STATUS;
  }

  public String getID_REPLACE() {
    return ID_REPLACE;
  }

  public void setID_REPLACE(String ID_REPLACE) {
    this.ID_REPLACE = ID_REPLACE;
  }

  public Import_Entity(String ID_IMP, LocalDateTime DATE_IMP, String DESCRIPTION, Boolean STATUS, String ID_REPLACE, Double SUM_PRICE) {
    this.ID_IMP = ID_IMP;
    this.DATE_IMP = DATE_IMP;
    this.DESCRIPTION = DESCRIPTION;
    this.STATUS = STATUS;
    this.ID_REPLACE = ID_REPLACE;
    this.SUM_PRICE = SUM_PRICE;
  }

  public Double getSUM_PRICE() {
    return SUM_PRICE;
  }

  public void setSUM_PRICE(Double SUM_PRICE) {
    this.SUM_PRICE = SUM_PRICE;
  }

  public Import_Entity() {
  }
}