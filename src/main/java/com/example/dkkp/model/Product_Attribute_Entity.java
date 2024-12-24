package com.example.dkkp.model;

import jakarta.persistence.*;


@Entity
@Table(name = "product_attribute")
public class Product_Attribute_Entity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "\"ID_ATTRIBUTE\"")
  private Integer ID_ATTRIBUTE;
  @Column(name = "\"NAME_ATTRIBUTE\"")
  private String NAME_ATTRIBUTE;
  @Column(name = "\"ID_CATEGORY\"")
  private Integer ID_CATEGORY;

  public Integer getID_ATTRIBUTE() {
    return ID_ATTRIBUTE;
  }

  public void setID_ATTRIBUTE(Integer ID_ATTRIBUTE) {
    this.ID_ATTRIBUTE = ID_ATTRIBUTE;
  }

  public String getNAME_ATTRIBUTE() {
    return NAME_ATTRIBUTE;
  }

  public void setNAME_ATTRIBUTE(String NAME_ATTRIBUTE) {
    this.NAME_ATTRIBUTE = NAME_ATTRIBUTE;
  }

  public Integer getID_CATEGORY() {
    return ID_CATEGORY;
  }

  public void setID_CATEGORY(Integer ID_CATEGORY) {
    this.ID_CATEGORY = ID_CATEGORY;
  }

  public Product_Attribute_Entity() {
  }

  public Product_Attribute_Entity(Integer ID_ATTRIBUTE, String NAME_ATTRIBUTE, Integer ID_CATEGORY) {
    this.ID_ATTRIBUTE = ID_ATTRIBUTE;
    this.NAME_ATTRIBUTE = NAME_ATTRIBUTE;
    this.ID_CATEGORY = ID_CATEGORY;
  }
}