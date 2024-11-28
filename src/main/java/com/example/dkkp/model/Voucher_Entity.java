package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "voucher")
public class Voucher_Entity {
  @Id
  @Column(name = "\"ID_VOU\"")
  private String ID_VOU;
  @Column(name = "\"VOU_NAME\"")
  private String VOU_NAME;
  @Column(name = "\"VOU_DESCRIPT\"")
  private String VOU_DESCRIPT;
  @Column(name = "\"MAX_DISCOUNT_PRICE\"")
  private Double MAX_DISCOUNT_PRICE;
  @Column(name = "\"DISCOUNT_PER\"")
  private Double DISCOUNT_PER;
  @Column(name = "\"DATE_BEGIN\"")
  private Date DATE_BEGIN;
  @Column(name = "\"DATE_END\"")
  private Date DATE_END;
  @Column(name = "\"MAX_USE\"")
  private Integer MAX_USE;
  @Column(name = "\"USAGE_COUNT\"")
  private Integer USAGE_COUNT;
  @Column(name = "\"VOU_STATUS\"")
  private String VOU_STATUS;

  public Voucher_Entity(String ID_VOU, String VOU_NAME, String VOU_DESCRIPT, Double MAX_DISCOUNT_PRICE, Double DISCOUNT_PER, Date DATE_BEGIN, Date DATE_END, Integer MAX_USE, Integer USAGE_COUNT, String VOU_STATUS) {
    this.ID_VOU = ID_VOU;
    this.VOU_NAME = VOU_NAME;
    this.VOU_DESCRIPT = VOU_DESCRIPT;
    this.MAX_DISCOUNT_PRICE = MAX_DISCOUNT_PRICE;
    this.DISCOUNT_PER = DISCOUNT_PER;
    this.DATE_BEGIN = DATE_BEGIN;
    this.DATE_END = DATE_END;
    this.MAX_USE = MAX_USE;
    this.USAGE_COUNT = USAGE_COUNT;
    this.VOU_STATUS = VOU_STATUS;
  }

  public Voucher_Entity() {}

  public String getID_VOU() {
    return ID_VOU;
  }

  public void setID_VOU(String ID_VOU) {
    this.ID_VOU = ID_VOU;
  }

  public String getVOU_NAME() {
    return VOU_NAME;
  }

  public void setVOU_NAME(String VOU_NAME) {
    this.VOU_NAME = VOU_NAME;
  }

  public String getVOU_DESCRIPT() {
    return VOU_DESCRIPT;
  }

  public void setVOU_DESCRIPT(String VOU_DESCRIPT) {
    this.VOU_DESCRIPT = VOU_DESCRIPT;
  }

  public Double getMAX_DISCOUNT_PRICE() {
    return MAX_DISCOUNT_PRICE;
  }

  public void setMAX_DISCOUNT_PRICE(Double MAX_DISCOUNT_PRICE) {
    this.MAX_DISCOUNT_PRICE = MAX_DISCOUNT_PRICE;
  }

  public Double getDISCOUNT_PER() {
    return DISCOUNT_PER;
  }

  public void setDISCOUNT_PER(Double DISCOUNT_PER) {
    this.DISCOUNT_PER = DISCOUNT_PER;
  }

  public Date getDATE_BEGIN() {
    return DATE_BEGIN;
  }

  public void setDATE_BEGIN(Date DATE_BEGIN) {
    this.DATE_BEGIN = DATE_BEGIN;
  }

  public Date getDATE_END() {
    return DATE_END;
  }

  public void setDATE_END(Date DATE_END) {
    this.DATE_END = DATE_END;
  }

  public Integer getMAX_USE() {
    return MAX_USE;
  }

  public void setMAX_USE(Integer MAX_USE) {
    this.MAX_USE = MAX_USE;
  }

  public Integer getUSAGE_COUNT() {
    return USAGE_COUNT;
  }

  public void setUSAGE_COUNT(Integer USAGE_COUNT) {
    this.USAGE_COUNT = USAGE_COUNT;
  }

  public String getVOU_STATUS() {
    return VOU_STATUS;
  }

  public void setVOU_STATUS(String VOU_STATUS) {
    this.VOU_STATUS = VOU_STATUS;
  }
}