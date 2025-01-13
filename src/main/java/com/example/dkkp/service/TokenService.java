//package com.example.dkkp.service;
//
//import io.jsonwebtoken.*;
//import java.util.Date;
//import java.util.Map;
//import java.util.HashMap;
//import javax.crypto.spec.SecretKeySpec;
//import java.security.Key;
//
//public class TokenService {
//    private static final String SECRET_KEY = "1234567887654321"; // Key của bạn
//    private static final long EXPIRATION_TIME = 86400000; // Thời gian sống của token (1 ngày = 86400000ms)
//
//    // Tạo token JWT
//    public String generateToken(String userId, String role) {
//        // Tạo payload (thông tin) cho token
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", userId);
//        claims.put("role", role);
//
//        // Thời gian hết hạn
//        long expirationTimeMillis = System.currentTimeMillis() + EXPIRATION_TIME;
//        Date expirationDate = new Date(expirationTimeMillis);
//
//        // Tạo token
//        return Jwts.builder()
//                .setClaims(claims) // Payload (Dữ liệu người dùng)
//                .setSubject(userId) // ID người dùng
//                .setIssuedAt(new Date()) // Thời gian tạo token
//                .setExpiration(expirationDate) // Thời gian hết hạn
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Chữ ký với key
//                .compact();
//    }
//
//    // Lấy key dùng để mã hóa và giải mã JWT
//    private Key getSigningKey() {
//        return new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//    }
//
//    // Kiểm tra token JWT
//    public Claims validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException {
//        // Giải mã token và kiểm tra chữ ký
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(getSigningKey()) // Sử dụng key để giải mã
//                    .build()
//                    .parseClaimsJws(token) // Giải mã và trả về Claims (payload)
//                    .getBody();
//        } catch (ExpiredJwtException e) {
//            throw new ExpiredJwtException(null, null, "Token has expired.");
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid token.");
//        }
//    }
//
//    // Kiểm tra nếu token có hết hạn hay không
//    public boolean isTokenExpired(String token) {
//        try {
//            Claims claims = validateToken(token);
//            Date expirationDate = claims.getExpiration();
//            return expirationDate.before(new Date()); // Kiểm tra thời gian hết hạn
//        } catch (Exception e) {
//            return true; // Nếu có lỗi, coi như token đã hết hạn
//        }
//    }
//
//    // Lấy thông tin User từ token
//    public String getUserIdFromToken(String token) {
//        Claims claims = validateToken(token);
//        return claims.getSubject(); // ID người dùng
//    }
//}
