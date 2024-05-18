package com.example.hackathon2024.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;

public class Hs256KeyGenerator {
    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger(Hs256KeyGenerator.class.getName());
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HMACSHA256");
            SecretKey secretKey = keyGenerator.generateKey();

            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            byte[] keyBytes = Base64.getDecoder().decode(base64Key);

            String hexKey = bytesToHex(keyBytes);
            logger.info("HS256 Key (Base64):");
            logger.info(base64Key);

            logger.info("HS256 Key (Hex):");
            logger.info(hexKey);
        } catch (NoSuchAlgorithmException e) {
            e.fillInStackTrace();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}