package com.example.dkkp.model;

import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;

@Entity
@Table(name = "import")
public class Import_Entity {
  @Id
  @Column(name = "\"ID_IMP\"")
  private String ID_IMP;
  @Column(name = "\"ID_REPLACE\"")
  private String ID_REPLACE;
  @Column(name = "\"IS_AVAILABLE\"")
  private String IS_AVAILABLE;
  @Column(name = "\"TOTAL_PRICE\"")
  private String TOTAL_PRICE;
  @Column(name = "\"DATE_IMP\"")
  private String DATE_IMP;
  @Column(name = "\"DESCRIPTION\"")
  private String DESCRIPTION;

  @Type(ListArrayType.class)
  public String getID_IMP() {
    return ID_IMP;
  }

  public String getID_REPLACE() {
    return ID_REPLACE;
  }

  public String getIS_AVAILABLE() {
    return IS_AVAILABLE;
  }

  public String getDATE_IMP() {
    return DATE_IMP;
  }

  public String getTOTAL_PRICE() {
    return TOTAL_PRICE;
  }

  public String getDESCRIPTION() {
    return DESCRIPTION;
  }

  public Import_Entity(String ID_IMP, String ID_REPLACE, String IS_AVAILABLE, String DATE_IMP, String TOTAL_PRICE, String DESCRIPTION) {
    this.ID_IMP = ID_IMP;
    this.ID_REPLACE = ID_REPLACE;
    this.IS_AVAILABLE = IS_AVAILABLE;
    this.DATE_IMP = DATE_IMP;
    this.TOTAL_PRICE = TOTAL_PRICE;
    this.DESCRIPTION = DESCRIPTION;
  }

  public Import_Entity() {}
}