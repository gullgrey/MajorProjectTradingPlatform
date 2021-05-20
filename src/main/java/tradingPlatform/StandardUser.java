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

    /**
     *
     * @param asset
     * @param amount
     * @param credits
     * @throws SQLException
     */
    public void buyAsset(String asset, int amount, int credits) throws SQLException {
        //handle credits not enough here.
        //handle negative credits
    }

    /**
     *
     * @param asset
     * @param amount
     * @param credits
     * @throws SQLException
     */
    public void sellAsset(String asset, int amount, int credits) throws SQLException {
        //handle assets not enough/available here.
        //handle negative credits
    }

    /**
     *
     * @param orderId
     * @throws SQLException
     */
    public void removeOrder(int orderId) throws SQLException {

    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public Set<Asset> getAssets() throws SQLException {
        return null;
    }

    /**
     *
     * @param asset
     * @return
     * @throws SQLException
     */
    public int getAssetAmount(String asset) throws SQLException{
        return 0;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public int getCredits() throws SQLException {
        return 0;
    }
}
