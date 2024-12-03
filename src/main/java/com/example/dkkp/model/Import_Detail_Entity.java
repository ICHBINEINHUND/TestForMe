package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "import_detail")
public class Import_Detail_Entity {
  @Id
  @Column(name = "\"ID_IMP\"")
  private String ID_IMP;
  @Column(name = "\"QUANTITY_SP\"")
  private Integer QUANTITY_SP;
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"PRICE_IMP_SP\"")
  private Double PRICE_IMP_SP;
  @Column(name = "\"AVAILABLE\"")
  private boolean AVAILABLE;
  @Column(name = "\"EDITED_ID\"")
  private String EDITED_ID;

  public boolean isAVAILABLE() {
    return AVAILABLE;
  }

  public void setAVAILABLE(boolean AVAILABLE) {
    this.AVAILABLE = AVAILABLE;
  }

  public String getEDITED_ID() {
    return EDITED_ID;
  }

  public void setEDITED_ID(String EDITED_ID) {
    this.EDITED_ID = EDITED_ID;
  }

  public Import_Detail_Entity(String ID_IMP, Integer QUANTITY_SP, String ID_SP, Double PRICE_IMP_SP, boolean AVAILABLE, String EDITED_ID) {
    this.ID_IMP = ID_IMP;
    this.QUANTITY_SP = QUANTITY_SP;
    this.ID_SP = ID_SP;
    this.PRICE_IMP_SP = PRICE_IMP_SP;
    this.AVAILABLE = AVAILABLE;
    this.EDITED_ID = EDITED_ID;
  }

  public Import_Detail_Entity() {}

  public String getID_IMP() {
    return ID_IMP;
  }

  public void setID_IMP(String ID_IMP) {
    this.ID_IMP = ID_IMP;
  }

  public Integer getQUANTITY_SP() {
    return QUANTITY_SP;
  }

  public void setQUANTITY_SP(Integer QUANTITY_SP) {
    this.QUANTITY_SP = QUANTITY_SP;
  }

  public String getID_SP() {
    return ID_SP;
  }

  public void setID_SP(String ID_SP) {
    this.ID_SP = ID_SP;
  }

  public Double getPRICE_IMP_SP() {
    return PRICE_IMP_SP;
  }

  public void setPRICE_IMP_SP(Double PRICE_IMP_SP) {
    this.PRICE_IMP_SP = PRICE_IMP_SP;
  }
}