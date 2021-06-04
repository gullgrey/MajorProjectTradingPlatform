package main.java.tradingPlatform;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashPassword {

    public static String hashedPassword(String username, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Encryption method being used
        md.reset();
        md.update(username.getBytes());
        byte[] hash = md.digest(password.getBytes()); // Adding the encrypted char to the password.
        return bytesToStringHex(hash);
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToStringHex(byte[] bytes){
        char[] hexChars = new char[bytes.length * 2];
        for (int j=0; j < bytes.length; j++){
            int v = bytes[j] & 0xFF;
            hexChars[j *2 ] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v &0x0F];
        }
        return new String(hexChars);
    }

}
