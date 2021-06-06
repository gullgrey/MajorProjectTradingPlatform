package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * This class is responsible is responsible for handling the hashing of the
 * users login password and for determining if the user is allowed to access
 * the system.
 */
public class Login {

    private final TradingPlatformDataSource dataSource;
    private final String username;
    private final String password;
    private String organisationalUnit;
    private Boolean isAdmin;

    /**
     * The constructor sets the Username and Password that the current user has provided.
     * Runs methods checkSuppliedCredentials and getUserFromDB.
     * @param userName the username of the user logging in.
     * @param password the password of the user logging in.
     * @param dataSource the database object needed to manage querying the database.
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
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     * @throws WrongCredentialException when a user provides the wrong inputs.
     * @throws NetworkException when client cannot connect to the network.
     */
    public boolean checkSuppliedCredentials() throws UnknownDatabaseException, WrongCredentialException, NetworkException {
        try {
            String hashedPassword = HashPassword.hashedPassword(username, password);
            String checkPassword = dataSource.getUserPassword(username);
            String message = "Invalid Username or Password";
            if (checkPassword == null) {

                throw new WrongCredentialException(message);
            }
            if (hashedPassword.equals(checkPassword)) {
                getUserFromDB();
                return isAdmin;
            } else {
                throw new WrongCredentialException(message);
            }

        } catch (NoSuchAlgorithmException e) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        } catch (NullPointerException e) {
            String message = "Failed to connect to Server.";
            throw new NetworkException(message);
        }
    }

    /**
     * Grabs all the information for a specific user from the database.
     * specific to the logging in user.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
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
