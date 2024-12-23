package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "product_options")
public class Product_Option_Entity {
  @Id
  @Column(name = "\"ID_OPTION\"")
  private Integer ID_OPTION;
  @Column(name = "\"NAME_OPTION\"")
  private String NAME_OPTION;

  public Integer getID_OPTION() {
    return ID_OPTION;
  }

  public void setID_OPTION(Integer ID_OPTION) {
    this.ID_OPTION = ID_OPTION;
  }

  public String getNAME_OPTION() {
    return NAME_OPTION;
  }

  public void setNAME_OPTION(String NAME_OPTION) {
    this.NAME_OPTION = NAME_OPTION;
  }

  public Product_Option_Entity(Integer ID_OPTION, String NAME_OPTION) {
    this.ID_OPTION = ID_OPTION;
    this.NAME_OPTION = NAME_OPTION;
  }

  public Product_Option_Entity() {
  }
}