package main.java.tradingPlatform;

import java.sql.SQLException;

public class StandardUser extends TPUser {

    private String username;
    private String organisation;

    public StandardUser(String username, String organisation) {
        this.username = username;
        this.organisation = organisation;
    }

    public void buyAsset(String asset, int amount, int credits) throws SQLException {
        //handle credits not enough here.
        //handle negative credits
    }

    public void sellAsset(String asset, int amount, int credits) throws SQLException {
        //handle assets not enough/available here.
        //handle negative credits
    }

    public void removeOrder(int orderId) {

    }

//    public Set<>
}
