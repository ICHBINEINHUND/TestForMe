package com.example.dkkp_app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "UZER")
public class User_Entity {

    @Id
    @Column(name = "\"ID_USER\"")
    private String ID_USER;
    @Column(name = "\"NAME_USER\"")
    private String NAME_USER;
    @Column(name = "\"DATE_JOIN\"")
    private Date DATE_JOIN;

    // Constructors, getters, setters
    public User_Entity() {}

    public User_Entity(String id, String name, Date date) {
        this.ID_USER = id;
        this.NAME_USER = name;
        this.DATE_JOIN = date;
    }

    public String getId() {
        return ID_USER;
    }

    public void setId(String id) {
        this.ID_USER = id;
    }

    public String getName() {
        return NAME_USER;
    }

    public void setName(String name) {
        this.NAME_USER = name;
    }

    public Date getDate() {
        return DATE_JOIN;
    }

    public void setDate(Date date) {
        this.DATE_JOIN = date;
    }
}
