package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product_Entity {
  @Id
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"ID_PA\"")
  private String ID_PA;
  @Column(name = "\"NAME_SP\"")
  private String NAME_SP;
  @Column(name = "\"DES_SP\"")
  private String DES_SP;
  @Column(name = "\"TYPE\"")
  private String TYPE;
  @Column(name = "\"PRICE_SP\"")
  private double PRICE_SP;
  @Column(name = "\"IMAGE_SP\"")
  private String IMAGE_SP;

  public Product_Entity(String ID_SP, String ID_PA, String NAME_SP, String DES_SP, String TYPE, double PRICE_SP, String IMAGE_SP) {
    this.ID_SP = ID_SP;
    this.ID_PA = ID_PA;
    this.NAME_SP = NAME_SP;
    this.DES_SP = DES_SP;
    this.TYPE = TYPE;
    this.PRICE_SP = PRICE_SP;
    this.IMAGE_SP = IMAGE_SP;
  }

  public Product_Entity() {}

  public String getID_SP() {
    return ID_SP;
  }

  public void setID_SP(String ID_SP) {
    this.ID_SP = ID_SP;
  }

  public String getID_PA() {
    return ID_PA;
  }

  public void setID_PA(String ID_PA) {
    this.ID_PA = ID_PA;
  }

  public String getNAME_SP() {
    return NAME_SP;
  }

  public void setNAME_SP(String NAME_SP) {
    this.NAME_SP = NAME_SP;
  }

  public String getDES_SP() {
    return DES_SP;
  }

  public void setDES_SP(String DES_SP) {
    this.DES_SP = DES_SP;
  }

  public String getTYPE() {
    return TYPE;
  }

  public void setTYPE(String TYPE) {
    this.TYPE = TYPE;
  }

  public double getPRICE_SP() {
    return PRICE_SP;
  }

  public void setPRICE_SP(double PRICE_SP) {
    this.PRICE_SP = PRICE_SP;
  }

  public String getIMAGE_SP() {
    return IMAGE_SP;
  }

  public void setIMAGE_SP(String IMAGE_SP) {
    this.IMAGE_SP = IMAGE_SP;
  }
}