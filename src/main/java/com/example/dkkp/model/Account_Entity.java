package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account")
public class Account_Entity {
  @Id
  @Column(name = "\"EMAIL_ACC\"")
  private String EMAIL_ACC;
  @Column(name = "\"PASSWORD_ACC\"")
  private String PASSWORD_ACC;
  @Column(name = "\"ROLE_ACC\"")
  private String ROLE_ACC;
  @Column(name = "\"ID_USER\"")
  private String ID_USER;

  public Account_Entity(String EMAIL_ACC, String PASSWORD_ACC, String ROLE_ACC, String ID_USER) {
    this.EMAIL_ACC = EMAIL_ACC;
    this.PASSWORD_ACC = PASSWORD_ACC;
    this.ROLE_ACC = ROLE_ACC;
    this.ID_USER = ID_USER;
  }

  public Account_Entity() {}

  public String getEMAIL_ACC() {
    return EMAIL_ACC;
  }

  public void setEMAIL_ACC(String EMAIL_ACC) {
    this.EMAIL_ACC = EMAIL_ACC;
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

  public String getID_USER() {
    return ID_USER;
  }

  public void setID_USER(String ID_USER) {
    this.ID_USER = ID_USER;
  }
}