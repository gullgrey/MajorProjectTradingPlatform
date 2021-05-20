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
     *
     * @param userName
     * @param password
     * @param organisation
     * @throws SQLException
     */
    public void addStandardUser(String userName, String password, String organisation) throws SQLException {

    }

    /**
     *
     * @param userName
     * @param password
     * @throws SQLException
     */
    public void addItUser(String userName, String password) throws SQLException {

    }

    /**
     *
     * @param userName
     * @throws SQLException
     */
    public void removeUser(String userName) throws SQLException {

    }

    /**
     *
     * @param organisation
     * @throws SQLException
     */
    public void addOrganisation(String organisation) throws SQLException {

    }

    /**
     *
     * @param organisation
     * @throws SQLException
     */
    public void removeOrganisation(String organisation) throws SQLException {

    }

    /**
     *
     * @param organisation
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
