package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "option_values")
public class Option_Values_Entity {
  @Id
  @Column(name = "\"ID_VALUE\"")
  private String ID_VALUE;
  @Column(name = "\"VALUE\"")
  private String VALUE;
  @Column(name = "\"ID_PARENT\"")
  private String ID_PARENT;
  @Column(name = "\"ID_OPTION\"")
  private String  ID_OPTION;

  public String getID_VALUE() {
    return ID_VALUE;
  }

  public void setID_VALUE(String ID_VALUE) {
    this.ID_VALUE = ID_VALUE;
  }

  public String getVALUE() {
    return VALUE;
  }

  public void setVALUE(String VALUE) {
    this.VALUE = VALUE;
  }

  public String getID_PARENT() {
    return ID_PARENT;
  }

  public void setID_PARENT(String ID_PARENT) {
    this.ID_PARENT = ID_PARENT;
  }

  public String getID_OPTION() {
    return ID_OPTION;
  }

  public void setID_OPTION(String ID_OPTION) {
    this.ID_OPTION = ID_OPTION;
  }

  public Option_Values_Entity(String ID_VALUE, String VALUE, String ID_PARENT, String ID_OPTION) {
    this.ID_VALUE = ID_VALUE;
    this.VALUE = VALUE;
    this.ID_PARENT = ID_PARENT;
    this.ID_OPTION = ID_OPTION;
  }

  public Option_Values_Entity() {
  }
}