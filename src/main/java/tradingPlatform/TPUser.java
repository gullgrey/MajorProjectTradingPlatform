package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * TODO
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

    public void refreshOrganisations() {
        organisationList.setRowCount(0);
        for (Organisation organisation : this.dataSource.getOrganisations()) {
            String credits = Integer.toString(organisation.getCredits());
            organisationList.addRow(new String[]{organisation.getOrganisation(), credits});
        }
    }

    public void refreshUsers() {
        try {
            userList.setRowCount(0);

            Set<UserOrganisation> actualUsers = dataSource.getUsers();
//            ArrayList<String> actualNames = new ArrayList<>();
//            for (UserOrganisation user : actualUsers) {
//                actualNames.add(user.getUser());
//            }
//
//            ArrayList<Integer> deleteValues = new ArrayList<>();
//            ArrayList<String> checkUsernames = new ArrayList<>();
//            for (int row = 0; row < userList.getRowCount(); row++) {
//                String username = userList.getValueAt(row, 0).toString();
//                checkUsernames.add(username);
//                if (!actualNames.contains(username)) {
//                    deleteValues.add(row);
//
//                }
//            }
//
//            int indexShift = 0;
//            for (int deleteIndex : deleteValues) {
//
//                userList.removeRow(deleteIndex - indexShift);
//                indexShift--;
//            }

            for (UserOrganisation user : actualUsers) {
//                if (!checkUsernames.contains(user.getUser())){
//                SwingUtilities.invokeLater(() -> userList.addRow(new String[]{user.getUser(), user.getOrganisation()}));
                    userList.addRow(new String[]{user.getUser(), user.getOrganisation()});
//                }

            }
        } catch (ArrayIndexOutOfBoundsException ignore) {

        }
    }

    public void refreshAssets() {
        assetList.setRowCount(0);
        for (Asset asset : this.dataSource.getAssets()) {
            String amount = Integer.toString(asset.getAmount());
            assetList.addRow(new String[]{asset.getOrganisation(), asset.getAsset(), amount});
        }
    }

    public void refreshBuyOrders() {

        buyOrderList.setRowCount(0);
//        Set<TPOrder> orders = dataSource.getOrders(true);
//        if (orders != null) {
        for (TPOrder order : dataSource.getOrders(true)) {
            String id = Integer.toString(order.getId());
            String amount = Integer.toString(order.getAmount());
            String credits = Integer.toString(order.getCredits());
            buyOrderList.addRow(new String[]{id, order.getOrganisation(), order.getAsset(),
                    amount, credits, order.getDateTime()});
        }
//        }
//        dataSource = new NetworkDataSource();
    }

    public void refreshSellOrders() {

        sellOrderList.setRowCount(0);
        for (TPOrder order : dataSource.getOrders(false)) {
            String id = Integer.toString(order.getId());
            String amount = Integer.toString(order.getAmount());
            String credits = Integer.toString(order.getCredits());
            sellOrderList.addRow(new String[]{id, order.getOrganisation(), order.getAsset(),
                    amount, credits, order.getDateTime()});
        }
//        dataSource = new NetworkDataSource();

    }

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
