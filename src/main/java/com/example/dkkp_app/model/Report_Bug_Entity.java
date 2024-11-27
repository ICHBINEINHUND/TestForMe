package com.example.dkkp_app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "report_bug")
public class Report_Bug_Entity {

    @Id
    @Column(name = "\"ID_REPORT\"")
    private String ID_REPORT;
    @Column(name = "\"TYPE_BUG\"")
    private int TYPE_BUG;
    @Column(name = "\"SCRIPT_BUG\"")
    private String SCRIPT_BUG;
    @Column(name = "\"DATE_REPORT\"")
    private Date DATE_REPORT;
    @Column(name = "\"ID_USER\"")
    private String ID_USER;

    public String getID_REPORT() {
        return ID_REPORT;
    }

    public void setID_REPORT(String ID_REPORT) {
        this.ID_REPORT = ID_REPORT;
    }

    public int getTYPE_BUG() {
        return TYPE_BUG;
    }

    public void setTYPE_BUG(int TYPE_BUG) {
        this.TYPE_BUG = TYPE_BUG;
    }

    public String getSCRIPT_BUG() {
        return SCRIPT_BUG;
    }

    public void setSCRIPT_BUG(String SCRIPT_BUG) {
        this.SCRIPT_BUG = SCRIPT_BUG;
    }

    public Date getDATE_REPORT() {
        return DATE_REPORT;
    }

    public void setDATE_REPORT(Date DATE_REPORT) {
        this.DATE_REPORT = DATE_REPORT;
    }

    public String getID_USER() {
        return ID_USER;
    }

    public void setID_USER(String ID_USER) {
        this.ID_USER = ID_USER;
    }

    public Report_Bug_Entity() {
    }

    public Report_Bug_Entity(String ID_REPORT, int TYPE_BUG, String SCRIPT_BUG, Date DATE_REPORT, String ID_USER) {
        this.ID_REPORT = ID_REPORT;
        this.TYPE_BUG = TYPE_BUG;
        this.SCRIPT_BUG = SCRIPT_BUG;
        this.DATE_REPORT = DATE_REPORT;
        this.ID_USER = ID_USER;
    }
}
