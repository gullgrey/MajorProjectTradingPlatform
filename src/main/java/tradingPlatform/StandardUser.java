package main.java.tradingPlatform;

import java.sql.SQLException;
import java.util.Set;

/**
 * TODO
 */
public class StandardUser extends TPUser {

    private String username;
    private String organisation;

    public StandardUser(String username, String organisation) {
        this.username = username;
        this.organisation = organisation;
    }
    //TODO ########################
    // # Discuss with owen and time about the credits for buyAsset and SellAsset.
    // Do we want to make them set a credit price per asset or set a block price and this will
    // influence how we do out calculations.
    /**
     * Method is used to take a buy request from a used.
     * @param asset asset they would like to buy
     * @param amount amount they would like to get of the asset.
     * @param credits number of credits they would like to pay.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public void buyAsset(String asset, int amount, int credits) throws SQLException {
        //handle credits not enough here.
        //handle negative credits
    }

    /**
     * Method is used to take a sell request from a used.
     * @param asset asset they would like to sell.
     * @param amount amount of the asset they are selling.
     * @param credits amount they are selling it for
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public void sellAsset(String asset, int amount, int credits) throws SQLException {
        //handle assets not enough/available here.
        //handle negative credits
    }

    /**
     * Removed a request buy or sell order of the users organisation.
     * @param orderId id of the order that is being removed
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public void removeOrder(int orderId) throws SQLException {

    }

    /**
     * Method is used to get all the assets of the users organisation.
     * @return a set containing all the assets that relate to the organisation.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public Set<Asset> getAssets() throws SQLException {
        return null;
    }

    /**
     * Given the name of an asset it returns the amount the organisation has.
     * If they don't contact he asset 0 will be returned.
     * @param asset name of the asset.
     * @return the amount of the asset for that organisation.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public int getAssetAmount(String asset) throws SQLException{
        return 0;
    }

    /**
     * Gets the amount of credits the users organisation has.
     * @return value of the organisations credits.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public int getCredits() throws SQLException {
        return 0;
    }
}
