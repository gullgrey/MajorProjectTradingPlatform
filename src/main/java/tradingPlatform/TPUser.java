package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import javax.swing.*;
import java.sql.SQLException;

/**
 * TODO
 */
public abstract class TPUser {

    TradingPlatformDataSource dataSource;

    static final int primaryKeyFail = -1;
    static final int foreignKeyFail = -2;
    static final int generalSQLFail = -3;
    static final String generalMessage = "Update did not go through. Please refresh page.";

    DefaultListModel<Organisation> organisationList;
    DefaultListModel<UserOrganisation> users;
    DefaultListModel<Asset> assetList;
    DefaultListModel<TPOrder> buyOrders;
    DefaultListModel<TPOrder> sellOrders;
    DefaultListModel<Transaction> Transaction;


    public TPUser(TradingPlatformDataSource dataSource) {
        this.dataSource = dataSource;

        organisationList = new DefaultListModel<>();

        for (Organisation organisation : this.dataSource.getOrganisations()) {
            organisationList.addElement(organisation);
        }

        assetList = new DefaultListModel<>();

        for (Asset asset : this.dataSource.getAssets()) {
            assetList.addElement(asset);
        }
    }

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
