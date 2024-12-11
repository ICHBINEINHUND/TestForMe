package com.example.dkkp.service;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class reflectField {

    public static List<String> getAllNameColumn(Object object){
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

    public static boolean isPropertyNameMatched(Object object, String nameToCheck) {
        List<String> propertyNames = getAllNameColumn(object);
        for (String propertyName : propertyNames) {
            if (propertyName.equals(nameToCheck)) {
                return true;
            }
        }
        return false;
    }
}
