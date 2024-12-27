package com.example.dkkp.service;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;




public class reflectField {

    public static List<String> getAllNameColumn(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object should not be null");
        }
        Class<?> clazz = object.getClass();
        List<String> columnName = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            columnName.add(field.getName());
        }
        return columnName;
    }

    public static boolean isPropertyNameMatched(Class<?> clazz, String nameToCheck) {
        if(nameToCheck == null) {
            return true;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                String columnName = columnAnnotation.name();
                if (columnName.equalsIgnoreCase(nameToCheck.toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

}
