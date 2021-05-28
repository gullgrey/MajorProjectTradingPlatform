package main.java.tradingPlatform;

import java.sql.SQLException;

/**
 * TODO
 */
public abstract class TPUser {

    /**
     * FROM USER PERSPECTIVE: Allows them to change their password.
     * FROM ADMINS PERSPECTIVE : Allows them to update a given users password.
     * @param username the user whose password is to be changed.
     * @param password new password to set for the user
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public void changeUserPassword(String username, String password) throws SQLException {

    }
}
