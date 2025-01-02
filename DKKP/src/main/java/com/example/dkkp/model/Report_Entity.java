package com.example.dkkp.model;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report_Entity {
  @Id
  @Column(name = "\"ID_REPORT\"")
  private String ID_REPORT;
  @Column(name = "\"ID_USER\"")
  private String ID_USER;
  @Column(name = "\"SCRIPT_BUG\"")
  private String SCRIPT_BUG;
  @Column(name = "\"TYPE_BUG\"")
  private String TYPE_BUG;
  @Column(name = "\"DATE_REPORT\"")
  private String DATE_REPORT;

  @Type(ListArrayType.class)
  public String getID_REPORT() {
    return ID_REPORT;
  }

  public String getID_USER() {
    return ID_USER;
  }

  public String getSCRIPT_BUG() {
    return SCRIPT_BUG;
  }

  public String getTYPE_BUG() {
    return TYPE_BUG;
  }

  public String getDATE_REPORT() {
    return DATE_REPORT;
  }

  public Report_Entity(String ID_REPORT, String ID_USER, String SCRIPT_BUG, String TYPE_BUG, String DATE_REPORT) {
    this.ID_REPORT = ID_REPORT;
    this.ID_USER = ID_USER;
    this.SCRIPT_BUG = SCRIPT_BUG;
    this.TYPE_BUG = TYPE_BUG;
    this.DATE_REPORT = DATE_REPORT;
  }

  public Report_Entity() {}
}