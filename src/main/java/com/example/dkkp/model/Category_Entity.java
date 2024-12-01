package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "category")
public class Category_Entity {
  @Id
  @Column(name = "\"ID_CATEGORY\"")
  private String ID_CATEGORY;
  @Column(name = "\"NAME_CATEGORY\"")
  private String NAME_CATEGORY;
  @Column(name = "\"ID_PARENT\"")
  private String ID_PARENT;
  @Column(name = "\"ID_BRAND\"")
  private String ID_BRAND;

  public String getID_CATEGORY() {
    return ID_CATEGORY;
  }

  public void setID_CATEGORY(String ID_CATEGORY) {
    this.ID_CATEGORY = ID_CATEGORY;
  }

  public String getNAME_CATEGORY() {
    return NAME_CATEGORY;
  }

  public void setNAME_CATEGORY(String NAME_CATEGORY) {
    this.NAME_CATEGORY = NAME_CATEGORY;
  }

  public String getID_PARENT() {
    return ID_PARENT;
  }

  public void setID_PARENT(String ID_PARENT) {
    this.ID_PARENT = ID_PARENT;
  }

  public String getID_BRAND() {
    return ID_BRAND;
  }

  public void setID_BRAND(String ID_BRAND) {
    this.ID_BRAND = ID_BRAND;
  }

  public Category_Entity(String ID_CATEGORY, String NAME_CATEGORY, String ID_PARENT, String ID_BRAND) {
    this.ID_CATEGORY = ID_CATEGORY;
    this.NAME_CATEGORY = NAME_CATEGORY;
    this.ID_PARENT = ID_PARENT;
    this.ID_BRAND = ID_BRAND;
  }

  public Category_Entity() {
  }
}