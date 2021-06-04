package main.java.tradingPlatform;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashPassword {

    public static String hashedPassword(String username, String password) throws NoSuchAlgorithmException {

        return password;
//        SecureRandom random = new SecureRandom(username.getBytes(StandardCharsets.UTF_8));
//        byte[] salt = new byte[16];
//        random.nextBytes(salt);
//        MessageDigest md = MessageDigest.getInstance("SHA-512");
//        md.update(salt);
//
//        StringBuilder sb = new StringBuilder();
//        for (byte b : salt){
//            sb.append(String.format("%02x", b));
//        }
//        return sb.toString();
    }
}
