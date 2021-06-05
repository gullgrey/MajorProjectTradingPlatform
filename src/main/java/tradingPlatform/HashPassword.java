package main.java.tradingPlatform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is responsible for handling the hashing of the users password
 * associated with the database and login.
 */
public class HashPassword {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * This method is used to has the user's password.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return hashed password of the user.
     * @throws NoSuchAlgorithmException thrown when the algorithm does not exist.
     */
    public static String hashedPassword(String username, String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // Encryption method being used
        md.reset();
        md.update(username.getBytes());
        byte[] hash = md.digest(password.getBytes()); // Adding the encrypted char to the password.
        return bytesToStringHex(hash);
    }

    /**
     * This method is used to encode the hash required for the password.
     * @param bytes the encoding length.
     * @return encoding for hashing the password.
     */
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
