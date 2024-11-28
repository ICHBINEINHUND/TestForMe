package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "uzer")
public class User_Entity {
  @Id
  @Column(name = "ID_USER")
  private String ID_USER;
  @Column(name = "NAME_USER")
  private String NAME_USER;
  @Column(name = "DATE_JOIN")
  private Date DATE_JOIN;

  public User_Entity(String ID_USER, String NAME_USER, Date DATE_JOIN) {
    this.ID_USER = ID_USER;
    this.NAME_USER = NAME_USER;
    this.DATE_JOIN = DATE_JOIN;
  }

  public User_Entity() {}

  public String getID_USER() {
    return ID_USER;
  }

  public void setID_USER(String ID_USER) {
    this.ID_USER = ID_USER;
  }

  public String getNAME_USER() {
    return NAME_USER;
  }

  public void setNAME_USER(String NAME_USER) {
    this.NAME_USER = NAME_USER;
  }

  public Date getDATE_JOIN() {
    return DATE_JOIN;
  }

  public void setDATE_JOIN(Date DATE_JOIN) {
    this.DATE_JOIN = DATE_JOIN;
  }
}