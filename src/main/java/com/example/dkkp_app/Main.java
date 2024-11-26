package com.example.dkkp_app;

import com.example.dkkp_app.model.User_Entity;
import com.example.dkkp_app.service.DatabaseService;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        Date ngay = Date.valueOf("2024-11-26");
        User_Entity entity = new User_Entity("id thu nhat","name thu nhat",ngay);
        DatabaseService.addEntity(entity);

        System.out.println("Entities in database:");
        for (User_Entity e : DatabaseService.getAllEntities()) {
            System.out.println("ID: " + e.getId() + ", Name: " + e.getName() + ", Age: " + e.getDate());
        }
    }
}
