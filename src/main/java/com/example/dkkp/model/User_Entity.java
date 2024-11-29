package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "uzer")
public class User_Entity {
  @Id
  @Column(name = "\"ID_USER\"")
  private String ID_USER;
  @Column(name = "\"NAME_USER\"")
  private String NAME_USER;
  @Column(name = "\"DATE_JOIN\"")
  private LocalDateTime DATE_JOIN;

  public User_Entity(String ID_USER, String NAME_USER, LocalDateTime LocalDateTime_JOIN) {
    this.ID_USER = ID_USER;
    this.NAME_USER = NAME_USER;
    this.DATE_JOIN = LocalDateTime_JOIN;
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

  public LocalDateTime getDATE_JOIN() {
    return DATE_JOIN;
  }

  public void setDATE_JOIN(LocalDateTime LocalDateTime_JOIN) {
    this.DATE_JOIN = LocalDateTime_JOIN;
  }
}