package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import javax.swing.*;
import java.sql.SQLException;

/**
 * TODO
 */
public abstract class TPUser {

    TradingPlatformDataSource dataSource;


    static final String generalMessage = "Update did not go through. Please refresh page.";

    DefaultListModel<Organisation> organisationList;
    DefaultListModel<UserOrganisation> userList;
    DefaultListModel<Asset> assetList;
    DefaultListModel<TPOrder> buyOrderList;
    DefaultListModel<TPOrder> sellOrderList;
    DefaultListModel<Transaction> transactionList;


    public TPUser(TradingPlatformDataSource dataSource) {
        this.dataSource = dataSource;

        organisationList = new DefaultListModel<>();
        userList = new DefaultListModel<>();
        assetList = new DefaultListModel<>();
        buyOrderList = new DefaultListModel<>();
        sellOrderList = new DefaultListModel<>();
        transactionList  = new DefaultListModel<>();
        refreshAll();
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
        for (TPOrder order : this.dataSource.getOrders(true)) {
            buyOrderList.addElement(order);
        }
    }

    public void refreshSellOrders() {
        sellOrderList.removeAllElements();
        for (TPOrder order : this.dataSource.getOrders(false)) {
            sellOrderList.addElement(order);
        }
    }

    public void refreshTransactions() {
        transactionList.removeAllElements();
        for (Transaction transaction : this.dataSource.getOrderHistory()) {
            transactionList.addElement(transaction);
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
