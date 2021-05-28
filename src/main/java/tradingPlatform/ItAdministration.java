package main.java.tradingPlatform;

import java.sql.SQLException;

/**
 * TODO
 */
public class ItAdministration extends TPUser {

    String adminName;

    public ItAdministration(String adminName){
        this.adminName = adminName;
    }

    /**
     * Creates a new standard user.
     *
     * @param userName the name of the username of the user
     * @param password the password the of the user
     * @param organisation the organisation the user belongs too
     * @throws SQLException
     */
    public void addStandardUser(String userName, String password, String organisation) throws SQLException {

    }

    /**
     * Creates a new staff admin account.
     *
     * @param userName the username of the admin
     * @param password the password of the admin
     * @throws SQLException
     */
    public void addItUser(String userName, String password) throws SQLException {

    }

    /**
     * Removes the user from the system.
     *
     * @param userName the name of the user being removed
     * @throws SQLException
     */
    public void removeUser(String userName) throws SQLException {

    }

    /**
     * Creates a new organisation.
     *
     * @param organisation the name of the organisation being created
     * @throws SQLException
     */
    public void addOrganisation(String organisation) throws SQLException {

    }

    /**
     * Remove an organisation from the system.
     *
     * @param organisation the organisation to be removed
     * @throws SQLException
     */
    public void removeOrganisation(String organisation) throws SQLException {

    }

    /**
     * Adds credits to an organisation.
     *
     * @param organisation the name of the organisation.
     * @param credits
     * @throws SQLException
     */
    public void addCredits(String organisation, int credits) throws SQLException {

    }

    /**
     *
     * @param organisation
     * @param credits
     * @throws SQLException
     */
    public void removeCredits(String organisation, int credits) throws SQLException{
        // throws credits to big exception
    }

    /**
     *
     * @param organisation
     * @param asset
     * @param amount
     * @throws SQLException
     */
    public void addAsset(String organisation, String asset, int amount) throws SQLException {

    }

    /**
     *
     * @param organisation
     * @param asset
     * @param amount
     * @throws SQLException
     */
    public void addAssetAmount(String organisation, String asset, int amount) throws SQLException {
        //excepts negative amount? If so remove asset from organisation.
    }

    /**
     *
     * @param organisation
     * @param asset
     * @throws SQLException
     */
    public void removeAsset(String organisation, String asset) throws SQLException {

    }
}
