package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "import_detail")
public class Import_Detail_Entity {
  @Id
  @Column(name = "\"ID_IMPD\"")
  private String ID_IMPD;
  @Column(name = "\"ID_IPARENT\"")
  private String ID_IPARENT;
  @Column(name = "\"QUANTITY_SP\"")
  private Integer QUANTITY_SP;
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"DESCRIPTION\"")
  private String DESCRIPTION;
  @Column(name = "\"PRICE_IMP_SP\"")
  private Double PRICE_IMP_SP;
  @Column(name = "\"AVAILABLE\"")
  private boolean AVAILABLE;

  public boolean isAVAILABLE() {
    return AVAILABLE;
  }

  public void setAVAILABLE(boolean AVAILABLE) {
    this.AVAILABLE = AVAILABLE;
  }

  public Import_Detail_Entity(String ID_IMPD, String ID_IPARENT, Integer QUANTITY_SP, String ID_SP, String DESCRIPTION, Double PRICE_IMP_SP, boolean AVAILABLE) {
    this.ID_IMPD = ID_IMPD;
    this.ID_IPARENT = ID_IPARENT;
    this.QUANTITY_SP = QUANTITY_SP;
    this.ID_SP = ID_SP;
    this.DESCRIPTION = DESCRIPTION;
    this.PRICE_IMP_SP = PRICE_IMP_SP;
    this.AVAILABLE = AVAILABLE;
  }

  public String getID_IPARENT() {
    return ID_IPARENT;
  }

  public void setID_IPARENT(String ID_IPARENT) {
    this.ID_IPARENT = ID_IPARENT;
  }

  public String getDESCRIPTION() {
    return DESCRIPTION;
  }

  public void setDESCRIPTION(String DESCRIPTION) {
    this.DESCRIPTION = DESCRIPTION;
  }

  public Import_Detail_Entity() {}

  public String getID_IMPD() {
    return ID_IMPD;
  }

  public void setID_IMPD(String ID_IMP) {
    this.ID_IMPD = ID_IMP;
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