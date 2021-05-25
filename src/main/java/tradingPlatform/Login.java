package main.java.tradingPlatform;

public class Login {

    private String username;
    private String password;
    private String organisationalUnit;
    private Boolean isAdmin;
    private TPUser userInformation;

    /**
     * The constructor sets the Username and Password that the current user has provided.
     * Runs methods checkSuppliedCreds and getUserFromDB
     * @param userName
     * @param password
     */
    public Login(String  userName, String password){
        this.username = userName;
        this.password = password;
        checkSuppliedCredentials(); // Check the DB for the username and password
        // if the user is found then get the user from the DB
            getUserFromDB();
            // Set the organisationalUnit
            // Set the isAdmin.
    }

    /**
     * Based off the current values of usename, Password. This look get the remaining data about
     * the user from the database
     *
     * @return All the information about the user in the database (e.g. Username, Password, Organisation and
     * if they are an admin.
     */
    public TPUser getUserInfo(){
        return userInformation;
    }

    /**
     * Functions is used to check if the user exists in the DB
     * @return boolean of whether or not the user existed.
     */
    private boolean checkSuppliedCredentials(){
        // Hash the password here.
        // Checked the DB.
        // TODO Change this boolean to be the actual value;
        return true;
    }

    /**
     *  Provided that checkSuppliedCreds is True this will grab all the information
     *  specific to the logging in user.
     */
    private void getUserFromDB(){

    }

}
