package com.example.dkkp.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "product")
public class Product_Entity {
  @Id
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"NAME_SP\"")
  private String NAME_SP;
  @Column(name = "\"DES_SP\"")
  private String DES_SP;
  @Column(name = "\"ID_CATEGORY\"")
  private String ID_CATEGORY;
  @Column(name = "\"PRICE_SP\"")
  private double PRICE_SP;
  @Column(name = "\"IMAGE_SP\"")
  private String IMAGE_SP;
  @Column(name = "\"VIEW_COUNT\"")
  private Integer VIEW_COUNT;
  @Column(name = "\"QUANTITY\"")
  private Integer QUANTITY;
  @Column(name = "\"DISCOUNT\"")
  private Double DISCOUNT;
  @Column(name = "\"IDS_OPTION_VALUES\"", columnDefinition = "integer[]")
  @Type(ListArrayType.class)
  private List<Integer> IDS_OPTION_VALUES;

  public String getID_SP() {
    return ID_SP;
  }

  public void setID_SP(String ID_SP) {
    this.ID_SP = ID_SP;
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

  public String getID_CATEGORY() {
    return ID_CATEGORY;
  }

  public void setID_CATEGORY(String ID_CATEGORY) {
    this.ID_CATEGORY = ID_CATEGORY;
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

  public Integer getVIEW_COUNT() {
    return VIEW_COUNT;
  }

  public void setVIEW_COUNT(Integer VIEW_COUNT) {
    this.VIEW_COUNT = VIEW_COUNT;
  }

  public Integer getQUANTITY() {
    return QUANTITY;
  }

  public void setQUANTITY(Integer QUANTITY) {
    this.QUANTITY = QUANTITY;
  }

  public Double getDISCOUNT() {
    return DISCOUNT;
  }

  public void setDISCOUNT(Double DISCOUNT) {
    this.DISCOUNT = DISCOUNT;
  }

  public List<Integer> getIDS_OPTION_VALUES() {
    return IDS_OPTION_VALUES;
  }

  public void setIDS_OPTION_VALUES(List<Integer> IDS_OPTION_VALUES) {
    this.IDS_OPTION_VALUES = IDS_OPTION_VALUES;
  }

  public Product_Entity(String ID_SP, String NAME_SP, String DES_SP, String ID_CATEGORY, double PRICE_SP, String IMAGE_SP, Integer VIEW_COUNT, Integer QUANTITY, Double DISCOUNT, List<Integer> IDS_OPTION_VALUES) {
    this.ID_SP = ID_SP;
    this.NAME_SP = NAME_SP;
    this.DES_SP = DES_SP;
    this.ID_CATEGORY = ID_CATEGORY;
    this.PRICE_SP = PRICE_SP;
    this.IMAGE_SP = IMAGE_SP;
    this.VIEW_COUNT = VIEW_COUNT;
    this.QUANTITY = QUANTITY;
    this.DISCOUNT = DISCOUNT;
    this.IDS_OPTION_VALUES = IDS_OPTION_VALUES;
  }

  public Product_Entity() {}
}