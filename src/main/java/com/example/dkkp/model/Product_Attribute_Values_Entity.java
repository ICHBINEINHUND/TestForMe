package com.example.dkkp.model;

import jakarta.persistence.*;


@Entity
@Table(name = "product_attribute_values")
public class Product_Attribute_Values_Entity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"ID\"")
  private Integer ID;
  @Column(name = "\"ID_BASE_PRODUCT\"")
  private Integer ID_BASE_PRODUCT;
  @Column(name = "\"ID_ATTRIBUTE\"")
  private Integer ID_ATTRIBUTE;
  @Column(name = "\"VALUE\"")
  private String VALUE;

  public Integer getID() {
    return ID;
  }

  public void setID(Integer ID) {
    this.ID = ID;
  }

  public Integer getID_BASE_PRODUCT() {
    return ID_BASE_PRODUCT;
  }

  public void setID_BASE_PRODUCT(Integer ID_BASE_PRODUCT) {
    this.ID_BASE_PRODUCT = ID_BASE_PRODUCT;
  }

  public Integer getID_ATTRIBUTE() {
    return ID_ATTRIBUTE;
  }

  public void setID_ATTRIBUTE(Integer ID_ATTRIBUTE) {
    this.ID_ATTRIBUTE = ID_ATTRIBUTE;
  }

  public String getVALUE() {
    return VALUE;
  }

  public void setVALUE(String VALUE) {
    this.VALUE = VALUE;
  }

  public Product_Attribute_Values_Entity(Integer ID, Integer ID_BASE_PRODUCT, Integer ID_ATTRIBUTE, String VALUE) {
    this.ID = ID;
    this.ID_BASE_PRODUCT = ID_BASE_PRODUCT;
    this.ID_ATTRIBUTE = ID_ATTRIBUTE;
    this.VALUE = VALUE;
  }

  public Product_Attribute_Values_Entity() {
  }
}