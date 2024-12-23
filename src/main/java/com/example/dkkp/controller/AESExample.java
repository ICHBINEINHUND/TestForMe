package com.example.dkkp.controller;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESExample {

    // Khóa bí mật (Secret Key)
    private static final String SECRET_KEY = "1234567887654321";  // 16 byte (128 bit)

    public static String encrypt(String data) throws Exception {
        // Khởi tạo đối tượng SecretKeySpec với khóa và thuật toán AES
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // Khởi tạo đối tượng Cipher với thuật toán AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);  // Chế độ mã hóa (ENCRYPT_MODE)

        // Mã hóa dữ liệu
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        // Trả về dữ liệu mã hóa đã được chuyển sang Base64
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Giải mã (Decrypt)
    public static String decrypt(String encryptedData) throws Exception {
        // Khởi tạo đối tượng SecretKeySpec với khóa và thuật toán AES
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // Khởi tạo đối tượng Cipher với thuật toán AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);  // Chế độ giải mã (DECRYPT_MODE)

        // Giải mã dữ liệu
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);  // Giải mã Base64 về byte[]
        byte[] decryptedData = cipher.doFinal(decodedData);  // Giải mã dữ liệu

        // Trả về chuỗi đã giải mã
        return new String(decryptedData);
    }

    public static void main(String[] args) {
        try {
            String originalData = "Hello, World!";

            // Mã hóa dữ liệu
            String encryptedData = encrypt(originalData);
            System.out.println("Encrypted: " + encryptedData);

            // Giải mã dữ liệu
            String decryptedData = decrypt(encryptedData);
            System.out.println("Decrypted: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
