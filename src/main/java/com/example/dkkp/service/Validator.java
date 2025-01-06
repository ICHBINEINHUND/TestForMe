package com.example.dkkp.service;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public Validator() {
    }

    public  TextFormatter<Integer> formatterInteger = new TextFormatter<>(new IntegerStringConverter(), null, c -> {
        if (c.getControlNewText().matches("[0-9]*")) {
            return c;
        } else {
            return null;
        }
    });

    public static TextFormatter<Double> formatterDouble = new TextFormatter<>(new DoubleStringConverter(), 0.0, c -> {
        String newText = c.getControlNewText();
        return newText.matches("-?\\d*(\\.\\d*)?") || newText.isEmpty() ? c : null;
    });

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidName(String name) {
        String regex = "^[A-Za-zÀ-ỹ]+(?:[ ][A-Za-zÀ-ỹ]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean isValidAddress(String address) {
        String STREET_ADDRESS_REGEX = "^[0-9]+[\\sA-Za-z\\-]+$";
        Pattern streetPattern = Pattern.compile(STREET_ADDRESS_REGEX);
        Matcher streetMatcher = streetPattern.matcher(address);
        return streetMatcher.matches();
    }


    public static String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty())  throw new IllegalArgumentException("Phone number cannot be null or empty");
        if (phoneNumber.startsWith("0")) return handleLocalPhoneNumber(phoneNumber);
        if (phoneNumber.startsWith("+"))return handleInternationalPhoneNumber(phoneNumber);
        throw new IllegalArgumentException("Invalid phone number format");
    }

    private static String handleLocalPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10 && phoneNumber.matches("^0[3-9]{1}[0-9]{8}$"))return "+84" + phoneNumber.substring(1); // Chuyển thành số quốc tế
        else throw new IllegalArgumentException("Invalid local phone number format");
    }

    private static String handleInternationalPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() >= 10 && phoneNumber.length() <= 15 && phoneNumber.matches("^\\+\\d{1,4}[0-9]{7,13}$"))return phoneNumber;
        else throw new IllegalArgumentException("Invalid international phone number format");
    }
}
