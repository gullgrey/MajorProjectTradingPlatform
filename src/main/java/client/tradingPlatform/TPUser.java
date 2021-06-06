package main.java.client.tradingPlatform;

import main.java.common.*;
import main.java.server.database.TradingPlatformDataSource;
import javax.swing.table.DefaultTableModel;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * This class is responsible for handling the integration of the users buy
 * and sell orders. It also maintains the updating of the GUI marketplace layout.
 */
public abstract class TPUser {

    TradingPlatformDataSource dataSource;
    String username;
    String organisation;

    static final String generalMessage = "Update did not go through. Please refresh page.";

    DefaultTableModel organisationList;
    DefaultTableModel userList;
    DefaultTableModel assetList;
    DefaultTableModel buyOrderList;
    DefaultTableModel sellOrderList;
    DefaultTableModel transactionList;

    public TPUser(TradingPlatformDataSource dataSource, String username, String organisation) {
        this.dataSource = dataSource;
        this.username = username;
        this.organisation = organisation;
        createTableModels();
    }

    public DefaultTableModel getOrganisationList() {
        return organisationList;
    }

    public DefaultTableModel getUserList() {
        return userList;
    }

    public DefaultTableModel getAssetList() {
        return assetList;
    }

    public DefaultTableModel getBuyOrderList() {
        return buyOrderList;
    }

    public DefaultTableModel getSellOrderList() {
        return sellOrderList;
    }

    public DefaultTableModel getTransactionList() {
        return transactionList;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getUsername() {
        return username;
    }

    /**
     * FROM USER PERSPECTIVE: Allows them to change their password.
     * FROM ADMINS PERSPECTIVE : Allows them to update a given users password.
     * @param userName the user whose password is to be changed.
     * @param password new password to set for the user
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void changeUserPassword(String userName, String password) throws UnknownDatabaseException,
            NullValueException {
        String hashedPassword;
        try {
            hashedPassword = HashPassword.hashedPassword(userName, password);
        } catch (NoSuchAlgorithmException e) {
            throw new UnknownDatabaseException(generalMessage);
        }
        int rowsAffected = dataSource.updatePassword(userName, hashedPassword);
        if (rowsAffected == PlatformGlobals.getNoRowsAffected()) {
            String message = "User does not exist.";
            throw new NullValueException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }
    }

    /**
     * This method creates the GUI marketplace buy and sell order displays.
     */
    private void createTableModels() {

        organisationList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        organisationList.addColumn("Organisation");
        organisationList.addColumn("Credits");

        userList = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userList.addColumn("Username");
        userList.addColumn("Organisation");

        assetList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        assetList.addColumn("Organisation");
        assetList.addColumn("Asset");
        assetList.addColumn("Amount");

        buyOrderList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        buyOrderList.addColumn("ID");
        buyOrderList.addColumn("Organisation");
        buyOrderList.addColumn("Asset");
        buyOrderList.addColumn("Amount");
        buyOrderList.addColumn("Credits");
        buyOrderList.addColumn("Date and Time");

        sellOrderList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sellOrderList.addColumn("ID");
        sellOrderList.addColumn("Organisation");
        sellOrderList.addColumn("Asset");
        sellOrderList.addColumn("Amount");
        sellOrderList.addColumn("Credits");
        sellOrderList.addColumn("Date and Time");

        transactionList  = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionList.addColumn("ID");
        transactionList.addColumn("Buying Organisation");
        transactionList.addColumn("Selling Organisation");
        transactionList.addColumn("Asset");
        transactionList.addColumn("Amount");
        transactionList.addColumn("Credits");
        transactionList.addColumn("Date and Time");

        refreshAll();
    }

    /**
     * This method refreshes the organisation's in the GUI layout.
     */
    public void refreshOrganisations() {
        organisationList.setRowCount(0);
        for (Organisation organisation : this.dataSource.getOrganisations()) {
            String credits = Integer.toString(organisation.getCredits());
            organisationList.addRow(new String[]{organisation.getOrganisation(), credits});
        }
    }

    /**
     * This method refreshes the User's in the GUI layout.
     */
    public void refreshUsers() {
        try {
            userList.setRowCount(0);

            Set<UserOrganisation> actualUsers = dataSource.getUsers();
            for (UserOrganisation user : actualUsers) {
                userList.addRow(new String[]{user.getUser(), user.getOrganisation()});
            }
        } catch (ArrayIndexOutOfBoundsException ignore) {

        }
    }

    /**
     * This method refreshes the Asset's in the GUI layout.
     */
    public void refreshAssets() {
        assetList.setRowCount(0);
        for (Asset asset : this.dataSource.getAssets()) {
            String amount = Integer.toString(asset.getAmount());
            assetList.addRow(new String[]{asset.getOrganisation(), asset.getAsset(), amount});
        }
    }

    /**
     * This method refreshes the Buy Order's in the GUI layout.
     */
    public void refreshBuyOrders() {

        buyOrderList.setRowCount(0);
        for (TPOrder order : dataSource.getOrders(true)) {
            String id = Integer.toString(order.getId());
            String amount = Integer.toString(order.getAmount());
            String credits = Integer.toString(order.getCredits());
            buyOrderList.addRow(new String[]{id, order.getOrganisation(), order.getAsset(),
                    amount, credits, order.getDateTime()});
        }
    }

    /**
     * This method refreshes the Sell Order's in the GUI layout.
     */
    public void refreshSellOrders() {

        sellOrderList.setRowCount(0);
        for (TPOrder order : dataSource.getOrders(false)) {
            String id = Integer.toString(order.getId());
            String amount = Integer.toString(order.getAmount());
            String credits = Integer.toString(order.getCredits());
            sellOrderList.addRow(new String[]{id, order.getOrganisation(), order.getAsset(),
                    amount, credits, order.getDateTime()});
        }
    }

    /**
     * This method refreshes the Transactions in the GUI layout.
     */
    public void refreshTransactions() {
        transactionList.setRowCount(0);
        for (Transaction transaction : dataSource.getOrderHistory()) {
            String id = Integer.toString(transaction.getId());
            String amount = Integer.toString(transaction.getAmount());
            String credits = Integer.toString(transaction.getCredits());
            transactionList.addRow(new String[]{id, transaction.getBuyingOrganisation(),
                    transaction.getSellingOrganisation(), transaction.getAsset(),
                    amount, credits, transaction.getDateTime()});
        }
    }

    /**
     * This method refresh all the corresponding methods at once.
     */
    public void refreshAll() {

        refreshOrganisations();
        refreshUsers();
        refreshAssets();
        refreshBuyOrders();
        refreshSellOrders();
        refreshTransactions();
    }

    /**
     * This method refreshes the Order's in the GUI layout.
     */
    public void refreshOrders() {
        refreshBuyOrders();
        refreshSellOrders();
    }
}
