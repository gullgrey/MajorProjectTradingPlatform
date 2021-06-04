package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Set;

/**
 * TODO
 */
public abstract class TPUser {

    TradingPlatformDataSource dataSource;
    String username;

    static final String generalMessage = "Update did not go through. Please refresh page.";

    DefaultListModel<Organisation> organisationList;
    DefaultListModel<UserOrganisation> userList;
    DefaultListModel<Asset> assetList;
    DefaultListModel<TPOrder> buyOrderList;
    DefaultListModel<TPOrder> sellOrderList;
    DefaultListModel<Transaction> transactionList;


    public TPUser(TradingPlatformDataSource dataSource, String username) {
        this.dataSource = dataSource;
        this.username = username;

        organisationList = new DefaultListModel<>();
        userList = new DefaultListModel<>();
        assetList = new DefaultListModel<>();
        buyOrderList = new DefaultListModel<>();
        sellOrderList = new DefaultListModel<>();
        transactionList  = new DefaultListModel<>();
        refreshAll();
    }

    public String getUsername() {
        return username;
    }

    /**
     * FROM USER PERSPECTIVE: Allows them to change their password.
     * FROM ADMINS PERSPECTIVE : Allows them to update a given users password.
     * @param username the user whose password is to be changed.
     * @param password new password to set for the user
     */
    public void changeUserPassword(String username, String password) {

    }

    public void refreshOrganisations() {
        organisationList.removeAllElements();
        for (Organisation organisation : this.dataSource.getOrganisations()) {
            organisationList.addElement(organisation);
        }
    }

    public void refreshUsers() {
        userList.removeAllElements();
        for (UserOrganisation user : this.dataSource.getUsers()) {
            userList.addElement(user);
        }
    }

    public void refreshAssets() {
        assetList.removeAllElements();
        for (Asset asset : this.dataSource.getAssets()) {
            assetList.addElement(asset);
        }
    }

    public void refreshBuyOrders() {

        buyOrderList.removeAllElements();
        Set<TPOrder> orders = dataSource.getOrders(true);
        if (orders != null) {
            for (TPOrder order : orders) {
                buyOrderList.addElement(order);
            }
        }
        dataSource = new NetworkDataSource();
    }

    public void refreshSellOrders() {
        dataSource = new NetworkDataSource();
        sellOrderList.removeAllElements();
        Set<TPOrder> orders = dataSource.getOrders(false);
        if (orders != null) {
            for (TPOrder order : orders) {
                sellOrderList.addElement(order);
            }
        }
        dataSource = new NetworkDataSource();

    }

    public void refreshTransactions() {
        transactionList.removeAllElements();
        Set<Transaction> transactions = dataSource.getOrderHistory();
        if (transactions != null) {
            for (Transaction transaction : transactions) {
                transactionList.addElement(transaction);
            }
        }
    }

    public void refreshAll() {

        refreshOrganisations();
        refreshUsers();
        refreshAssets();
        refreshBuyOrders();
        refreshSellOrders();
        refreshTransactions();
    }

    public void refreshOrders() {
        refreshBuyOrders();
        refreshSellOrders();
    }
}
