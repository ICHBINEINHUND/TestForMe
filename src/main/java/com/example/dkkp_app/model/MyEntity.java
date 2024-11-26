package com.example.dkkp_app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class MyEntity {

    @Id
    private String id;

    private String name;
    private Date date;

    // Constructors, getters, setters
    public MyEntity() {}

    public MyEntity(String id,String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
