package com.example.dkkp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "import")
public class Import_Entity {
    @Id
    @Column(name = "\"ID_IMP\"")
    private Integer ID_IMP;
    @Column(name = "\"DATE_IMP\"")
    private LocalDateTime DATE_IMP;
    @Column(name = "\"DESCRIPTION\"")
    private String DESCRIPTION;
    @Column(name = "\"IS_AVAILABLE\"")
    private Boolean IS_AVAILABLE;
    @Column(name = "\"ID_REPLACE\"")
    private Integer ID_REPLACE;
    @Column(name = "\"TOTAL_PRICE\"")
    private Double TOTAL_PRICE;


    public Integer getID_IMP() {
        return ID_IMP;
    }

    public void setID_IMP(Integer ID_IMP) {
        this.ID_IMP = ID_IMP;
    }

    public LocalDateTime getDATE_IMP() {
        return DATE_IMP;
    }

    public void setDATE_IMP(LocalDateTime DATE_IMP) {
        this.DATE_IMP = DATE_IMP;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public Boolean getIS_AVAILABLE() {
        return IS_AVAILABLE;
    }

    public void setIS_AVAILABLE(Boolean IS_AVAILABLE) {
        this.IS_AVAILABLE = IS_AVAILABLE;
    }

    public Integer getID_REPLACE() {
        return ID_REPLACE;
    }

    public void setID_REPLACE(Integer ID_REPLACE) {
        this.ID_REPLACE = ID_REPLACE;
    }

    public Double getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public void setTOTAL_PRICE(Double TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }


    public Import_Entity(Integer ID_IMP, LocalDateTime DATE_IMP, String DESCRIPTION, Boolean IS_AVAILABLE, Integer ID_REPLACE, Double TOTAL_PRICE) {
        this.ID_IMP = ID_IMP;
        this.DATE_IMP = DATE_IMP;
        this.DESCRIPTION = DESCRIPTION;
        this.IS_AVAILABLE = IS_AVAILABLE;
        this.ID_REPLACE = ID_REPLACE;
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public Import_Entity() {
    }
}