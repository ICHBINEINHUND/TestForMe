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
  @Column(name = "\"EMAIL_ACC\"")
  private String EMAIL_ACC;
  @Column(name = "\"PHONE_ACC\"")
  private String PHONE_ACC;
  @Column(name = "\"PASSWORD_ACC\"")
  private String PASSWORD_ACC;
  @Column(name = "\"ROLE_ACC\"")
  private String ROLE_ACC;
  @Column(name = "\"NAME_USER\"")
  private String NAME_USER;
  @Column(name = "\"DATE_JOIN\"")
  private LocalDateTime DATE_JOIN;

  public String getID_USER() {
    return ID_USER;
  }

  public void setID_USER(String ID_USER) {
    this.ID_USER = ID_USER;
  }

  public String getEMAIL_ACC() {
    return EMAIL_ACC;
  }

  public void setEMAIL_ACC(String EMAIL_ACC) {
    this.EMAIL_ACC = EMAIL_ACC;
  }

  public String getPHONE_ACC() {
    return PHONE_ACC;
  }

  public void setPHONE_ACC(String PHONE_ACC) {
    this.PHONE_ACC = PHONE_ACC;
  }

  public String getPASSWORD_ACC() {
    return PASSWORD_ACC;
  }

  public void setPASSWORD_ACC(String PASSWORD_ACC) {
    this.PASSWORD_ACC = PASSWORD_ACC;
  }

  public String getROLE_ACC() {
    return ROLE_ACC;
  }

  public void setROLE_ACC(String ROLE_ACC) {
    this.ROLE_ACC = ROLE_ACC;
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

  public void setDATE_JOIN(LocalDateTime DATE_JOIN) {
    this.DATE_JOIN = DATE_JOIN;
  }

  public User_Entity(String ID_USER, String EMAIL_ACC, String PHONE_ACC, String PASSWORD_ACC, String ROLE_ACC, String NAME_USER, LocalDateTime DATE_JOIN) {
    this.ID_USER = ID_USER;
    this.EMAIL_ACC = EMAIL_ACC;
    this.PHONE_ACC = PHONE_ACC;
    this.PASSWORD_ACC = PASSWORD_ACC;
    this.ROLE_ACC = ROLE_ACC;
    this.NAME_USER = NAME_USER;
    this.DATE_JOIN = DATE_JOIN;
  }

  public User_Entity() {}
}