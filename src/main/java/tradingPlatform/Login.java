package main.java.tradingPlatform;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Login {

    private String username;
    private String password;
    private String organisationalUnit;
    private Boolean isAdmin;
    private TPUser userInformation;

    /**
     * The constructor sets the Username and Password that the current user has provided.
     * Runs methods checkSuppliedCredentials and getUserFromDB.
     * @param userName
     * @param password
     */
    public Login(String  userName, String password){
        this.username = userName;
        this.password = password;
        checkSuppliedCredentials(); // Check the DB for the username and passwor
        // if the user is found then get the user from the DB
            getUserFromDB();
            // Set the organisationalUnit
            // Set the isAdmin.
    }

    /**
     * Based off the current values of usename, Password. This look get the remaining data about
     * the user from the database.
     *
     * @return All the information about the user in the database (e.g. Username, Password, Organisation and
     * if they are an admin.
     */
    public TPUser getUserInfo(){
        return userInformation;
    }

    /**
     * Function is used to check if the user exists in the DB.
     * @return boolean of whether or not the user existed.
     */
    public boolean checkSuppliedCredentials(){
        // Hash the password here.
        // Checked the DB.
        // TODO Change this boolean to be the actual value;
        return true;
    }

    /**
     * Grabs all the information for a specific user from the database.
     *  specific to the logging in user.
     */
    private void getUserFromDB(){
        //TODO Speak with Tom and owen about this class and see if it needs take any variables as
        // i am unsure how this knows what user information to get.
    }

    public String hashedPassword(String password) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom(username.getBytes(StandardCharsets.UTF_8));
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
