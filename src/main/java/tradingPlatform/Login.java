package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Set;

public class Login {

    private TradingPlatformDataSource dataSource;

    private String username;
    private String password;
    private String organisationalUnit;
    private Boolean isAdmin;


    /**
     * The constructor sets the Username and Password that the current user has provided.
     * Runs methods checkSuppliedCredentials and getUserFromDB.
     * @param userName
     * @param password
     */
    public Login(String  userName, String password, TradingPlatformDataSource dataSource){
        this.username = userName;
        this.password = password;
        this.dataSource = dataSource;
    }

    /**
     * Based off the current values of username, Password. This look get the remaining data about
     * the user from the database.
     *
     * @return All the information about the It Admin in the database (e.g. Username, Password, Organisation).
     */
    public ItAdministration getItAdministration(){
        return new ItAdministration(dataSource, username);
    }

    /**
     * Based off the current values of username, Password. This look get the remaining data about
     * the user from the database.
     *
     * @return All the information about the Standard User in the database (e.g. Username, Password, Organisation).
     */
    public StandardUser getStandardUser() {
        return new StandardUser(dataSource, username, organisationalUnit);
    }

    /**
     * Function is used to check if the user exists in the DB.
     * @return boolean of whether or not the user is an IT Administrator.
     */
    public boolean checkSuppliedCredentials() throws NullValueException, UnknownDatabaseException {
        try {
            String hashedPassword = HashPassword.hashedPassword(username, password);
            String checkPassword = dataSource.getUserPassword(username);
            String message = "Invalid Username or Password";
            if (checkPassword == null) {

                throw new NullValueException(message);
            }
            if (hashedPassword.equals(checkPassword)) {
                getUserFromDB();
                return isAdmin;
            } else {
                throw new NullValueException(message);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
    }

    /**
     * Grabs all the information for a specific user from the database.
     *  specific to the logging in user.
     */
    private void getUserFromDB() throws UnknownDatabaseException {
        Set<UserOrganisation> users = dataSource.getUsers();
        if (users == null) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
        for (UserOrganisation user : users) {
            if (user.getUser().equals(username)) {
                if (user.getOrganisation().equals(PlatformGlobals.getAdminOrganisation())) {
                    isAdmin = true;
                } else {
                    isAdmin = false;
                    organisationalUnit = user.getOrganisation();
                }
                break;
            }
        }
    }
}
