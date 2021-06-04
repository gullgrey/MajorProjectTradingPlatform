package main.java.tradingPlatform;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashPassword {

    public static String hashedPassword(String username, String password) throws NoSuchAlgorithmException {
        String seededpassword = username+password; // Generated see based on the username and password.
        SecureRandom random = new SecureRandom(seededpassword.getBytes(StandardCharsets.UTF_8));
        byte[] salt = new byte[55]; // Length of the Hashed password
        random.nextBytes(salt); // Getting the next character in the password.
        MessageDigest md = MessageDigest.getInstance("SHA-512"); // Encryption method being used
        md.update(salt); // Adding the encrypted char to the password.

        StringBuilder sb = new StringBuilder();
        for (byte b : salt){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
