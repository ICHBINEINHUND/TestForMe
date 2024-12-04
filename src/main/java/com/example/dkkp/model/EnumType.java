package com.example.dkkp.model;

public class EnumType {

    public enum Bug_Type{
        UI("UI Issue"),
        Perf("Perfomance Bug"),
        Comp("Comp bug"),
        NET("Net bug"),
        DATA("Data bug"),
        FUNC("Funct bug");
        private final String description; // Trường để lưu mô tả
        // Constructor nhận giá trị mô tả
        Bug_Type(String description) {
            this.description = description;
        }
        // Getter để lấy mô tả
        public String getDescription() {
            return description;
        }
    }
    public enum Status_Bill{
        PEN("pending"),
        CONF("confirmed"),
        SHIP("shipped"),
        DELI("delivered"),
        CANC("cancel");
        private final String description; // Trường để lưu mô tả
        // Constructor nhận giá trị mô tả
        Status_Bill(String description) {
            this.description = description;
        }
        // Getter để lấy mô tả
        public String getDescription() {
            return description;
        }
    }
}
