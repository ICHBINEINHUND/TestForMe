package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_check")
public class Email_Check_Entity {
  @Id
  @Column(name = "\"ID_CHECK\"")
  private String ID_CHECK;
  @Column(name = "\"EMAIL\"")
  private String EMAIL;
  @Column(name = "\"TOKEN\"")
  private String TOKEN;
  @Column(name = "\"DATE_ẸND\"")
  private LocalDateTime DATE_ẸND;

  public String getID_CHECK() {
    return ID_CHECK;
  }

  public void setID_CHECK(String ID_CHECK) {
    this.ID_CHECK = ID_CHECK;
  }

  public String getEMAIL() {
    return EMAIL;
  }

  public void setEMAIL(String EMAIL) {
    this.EMAIL = EMAIL;
  }

  public String getTOKEN() {
    return TOKEN;
  }

  public void setTOKEN(String TOKEN) {
    this.TOKEN = TOKEN;
  }

  public LocalDateTime getDate_End() {
    return DATE_ẸND;
  }

  public void setDate_End(LocalDateTime DATE_ẸND) {
    this.DATE_ẸND = DATE_ẸND;
  }

  public Email_Check_Entity() {
  }

  public Email_Check_Entity(String ID_CHECK, String EMAIL, String TOKEN, LocalDateTime DATE_ẸND) {
    this.ID_CHECK = ID_CHECK;
    this.EMAIL = EMAIL;
    this.TOKEN = TOKEN;
    this.DATE_ẸND = DATE_ẸND;
  }
}