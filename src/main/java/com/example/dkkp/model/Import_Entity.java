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
  @Column(name = "\"PRICE_IMP\"")
  private Double PRICE_IMP;

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

  public Double getPRICE_IMP() {
    return PRICE_IMP;
  }

  public void setPRICE_IMP(Double PRICE_IMP) {
    this.PRICE_IMP = PRICE_IMP;
  }

  public Import_Entity(String ID_IMP, LocalDateTime DATE_IMP, String DESCRIPTION, Double PRICE_IMP) {
    this.ID_IMP = ID_IMP;
    this.DATE_IMP = DATE_IMP;
    this.DESCRIPTION = DESCRIPTION;
    this.PRICE_IMP = PRICE_IMP;
  }

  public Import_Entity() {
  }
}