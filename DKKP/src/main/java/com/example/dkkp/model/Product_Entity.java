package com.example.dkkp.model;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product_Entity {
  @Id
  @Column(name = "\"ID_SP\"")
  private String ID_SP;
  @Column(name = "\"NAME_SP\"")
  private String NAME_SP;
  @Column(name = "\"PRICE_SP\"")
  private double PRICE_SP;

  @Type(ListArrayType.class)
  public String getID_SP() {
    return ID_SP;
  }

  public String getNAME_SP() {
    return NAME_SP;
  }

  public double getPRICE_SP() {
    return PRICE_SP;
  }

  public Product_Entity(String ID_SP, String NAME_SP, Double PRICE_SP) {
    this.ID_SP = ID_SP;
    this.NAME_SP = NAME_SP;
    this.PRICE_SP = PRICE_SP;
  }

  public Product_Entity() {}
}