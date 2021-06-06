package test.java.mockups;

import main.java.common.*;
import main.java.server.database.TradingPlatformDataSource;
import main.java.server.network.NewTransaction;

import java.util.*;

/**
 * This class is responsible for mocking the database. It carries out similar functions and
 * stores all the values associated with the database and database queries, except with mock
 * Objects.
 */
public class DataSourceMockup implements TradingPlatformDataSource {

    DatabaseMockup database;

    public DataSourceMockup(DatabaseMockup database) {
        this.database = database; // Build the initial values for the database
    }

    /**
     * @see TradingPlatformDataSource#getCredits(String)
     */
    @Override
    public int getCredits(String organisation) {
        for (Organisation org : database.organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                return org.getCredits();
            }
        }
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#updateCredits(String, int)
     */
    @Override
    public int updateCredits(String organisation, int credits) {

        boolean exists = false;
        //Check to see if the organisation exists (Foreign key constraint)
        for (Organisation org : database.organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }
        //If the organisation exists update credits
        for (Organisation org : database.organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                int orgCredits = org.getCredits();
                orgCredits += credits;
                if (orgCredits <= 0) {
                    orgCredits = 0;
                }
                org.setCredits(orgCredits);
                return 1;
            }
        }
        return PlatformGlobals.getPrimaryKeyFail();
    }

    /**
     * @see TradingPlatformDataSource#getAssets()
     */
    @Override
    public Set<Asset> getAssets() {
        Set<Asset> newSet = new TreeSet<>();
        for (Asset user : database.assetsList) {
            Asset asset = new Asset();
            asset.setAsset(user.getAsset());
            asset.setAmount(user.getAmount());
            newSet.add(asset);
        }
        return newSet;
    }

    /**
     * @see TradingPlatformDataSource#addAsset(String, String, int)
     */
    @Override
    public int addAsset(String organisation, String asset, int amount) {

        boolean exists = false;
        Asset aNewAsset = new Asset(organisation, asset, amount);

        // Check to see if that organisation already has that asset (Primary key constraint)
        for (Asset anAsset : database.assetsList) {
            if (anAsset.getOrganisation().equals(organisation) && anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            return PlatformGlobals.getPrimaryKeyFail();
        }

        // Check to see if that organisation does exist (Foreign key constraint)
        for (Organisation org : database.organisationsList) {
            if (aNewAsset.getOrganisation().equals(org.getOrganisation())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }
        database.assetsList.add(aNewAsset);
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#getAssetAmount(String, String)
     */
    @Override
    public int getAssetAmount(String organisation, String asset) {

        boolean exists = false;

        // Check to see if that organisation exists (Foreign key constraint)
        for (Organisation anOrg : database.organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        exists = false;
        // Check to see if that asset exists (Foreign key constraint)
        for (Asset anAsset : database.assetsList) {
            if (anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        int amount = PlatformGlobals.getGeneralSQLFail();
        for (Asset aAsset : database.assetsList) {
            if (aAsset.getOrganisation().equals(organisation) && aAsset.getAsset().equals(asset)) {
                amount = aAsset.getAmount();
            }
        }
        return amount;
    }

    /**
     * @see TradingPlatformDataSource#deleteAsset(String, String)
     */
    @Override
    public int deleteAsset(String organisation, String asset) {

        boolean exists = false;

        // Check to see if that organisation exists (Foreign key constraint)
        for (Organisation anOrg : database.organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        exists = false;

        // Check to see if that asset exists (Foreign key constraint)
        for (Asset anAsset : database.assetsList) {
            if (anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        // Remove the asset
        for (Asset anAsset : database.assetsList) {
            if (anAsset.getOrganisation().equals(organisation) && anAsset.getAsset().equals(asset)) {
                database.assetsList.remove(anAsset);
                return 1;
            }
        }
        return PlatformGlobals.getGeneralSQLFail();
    }

    /**
     * @see TradingPlatformDataSource#updateAssetAmount(String, String, int)
     */
    @Override
    public int updateAssetAmount(String organisation, String asset, int amount) {

        boolean exists = false;

        // Check to see if that organisation exists (Foreign key constraint)
        for (Organisation anOrg : database.organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        exists = false;
        // Check to see if that asset exists (Foreign key constraint)
        for (Asset anAsset : database.assetsList) {
            if (anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        int increaseAsset;
        for (Asset aAsset : database.assetsList) {
            if (aAsset.getAsset().equals(asset) & aAsset.getOrganisation().equals(organisation)) {

                increaseAsset = aAsset.getAmount();
                increaseAsset += amount;
                if (increaseAsset <= 0) {
                    increaseAsset = 0;
                }
                aAsset.setAmount(increaseAsset);
                return 1;
            }
        }
        return PlatformGlobals.getGeneralSQLFail();
    }

    /**
     * @see TradingPlatformDataSource#getOrganisations()
     */
    @Override
    public Set<Organisation> getOrganisations() {
        return database.organisationsList;
    }

    /**
     * @see TradingPlatformDataSource#getUserOrganisation(String)
     */
    @Override
    public String getUserOrganisation(String username) {
        for (UserOrganisation aUser : database.userOrganisationList) {
            if (aUser.getUser().equals(username)) {
                return aUser.getOrganisation();
            }
        }
        return null;
    }

    /**
     * @see TradingPlatformDataSource#addOrganisation(String, int)
     */
    @Override
    public int addOrganisation(String organisation, int credits) {

        boolean exists = false;
        Organisation anOrganisation = new Organisation(organisation, credits);

        // Check to see if that organisation already exists (Primary key constraint)
        for (Organisation anOrg : database.organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        // Checks to see if the organisation exists (Primary key constraint)
        if (exists) {
            return PlatformGlobals.getPrimaryKeyFail();
        }
        database.organisationsList.add(anOrganisation);
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#deleteOrganisation(String)
     */
    @Override
    public int deleteOrganisation(String organisation) {

        boolean exists = false;

        for (Organisation anOrg : database.organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }
        database.organisationsList.removeIf(aOrg -> aOrg.getOrganisation().equals(organisation));
        database.userOrganisationList.removeIf(aUserOrg -> aUserOrg.getOrganisation().equals(organisation));
        database.assetsList.removeIf(orgAsset -> orgAsset.getOrganisation().equals(organisation));
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#getUsers()
     */
    @Override
    public Set<UserOrganisation> getUsers() {
        return database.userOrganisationList;
    }

    /**
     * @see TradingPlatformDataSource#getUserPassword(String)
     */
    @Override
    public String getUserPassword(String username) {

        for (UserMockup user : database.userMock) {
            if (user.getUsername().equals(username)) return user.getPassword();
        }
        return null;
    }

    /**
     * @see TradingPlatformDataSource#addUser(String, String, String, String)
     */
    @Override
    public int addUser(String username, String password, String type, String organisation) {

        UserMockup aNewUser = new UserMockup(username, password, type, organisation);
        UserOrganisation sameUser = new UserOrganisation(username, organisation);
        boolean exists = false;

        // Check to see if the organisation exists (Foreign key constraint)
        for (Organisation orgs : database.organisationsList) {
            if (orgs.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail(); // Organisation doesn't exist.
        }

        // Primary key failure, user already exists.
        for (UserMockup user : database.userMock) {

            if (user.getUsername().equals(username)) {
                return PlatformGlobals.getPrimaryKeyFail();

            }


        }
        database.userMock.add(aNewUser);
        database.userOrganisationList.add(sameUser);
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#deleteUser(String)
     */
    @Override
    public int deleteUser(String username) {

        for (UserMockup aUser : database.userMock) {
            if (aUser.getUsername().equals(username)) {
                database.userMock.remove(aUser);
                break;
            }
        }

        for (UserOrganisation sameUser : database.userOrganisationList) {
            if (sameUser.getUser().equals(username)) {
                database.userOrganisationList.remove(sameUser);
                return 1;
            }
        }
        return PlatformGlobals.getNoRowsAffected();
    }

    /**
     * @see TradingPlatformDataSource#updatePassword(String, String)
     */
    @Override
    public int updatePassword(String username, String password) {

        boolean exists = false;

        // Checks to see if the user exists (Foreign Key constraint)
        for (UserMockup aUser : database.userMock) {
            if (aUser.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail(); // Organisation doesn't exist.
        }

        for (UserMockup aUser : database.userMock) {
            if (aUser.getUsername().equals(username)) {
                aUser.setPassword(password);
                return 1;
            }
        }
        return PlatformGlobals.getGeneralSQLFail();
    }

    /**
     * @see TradingPlatformDataSource#getOrder(int)
     */
    @Override
    public TPOrder getOrder(int idx) {

        for (TPOrder order : database.orderList) {
            if (order.getId() == idx) {
                return order;
            }
        }
        return null;
    }

    /**
     * @see TradingPlatformDataSource#getOrders(boolean)
     */
    @Override
    public Set<TPOrder> getOrders(boolean isBuyOrder) {
        //add a set
        Set<TPOrder> buyOrders = new HashSet<>();
        Set<TPOrder> sellOrders = new HashSet<>();

        for (TPOrder order : database.orderList) {
            if (order.getType().equals(PlatformGlobals.getBuyOrder())) {
                buyOrders.add(order);
            } else {
                sellOrders.add(order);
            }
        }

        if (isBuyOrder) {
            return buyOrders;
        } else {
            return sellOrders;
        }
    }

    /**
     * @see TradingPlatformDataSource#addOrder(String, String, int, int, boolean)
     */
    @Override
    public int addOrder(String organisation, String asset, int amount, int credits, boolean isBuyOrder) {
        String isType;
        if (isBuyOrder) {
            isType = PlatformGlobals.getBuyOrder();
        } else {
            isType = PlatformGlobals.getSellOrder();
        }
        TPOrder order = new TPOrder();
        order.setOrganisation(organisation);
        order.setAsset(asset);
        order.setAmount(amount);
        order.setCredits(credits);
        order.setType(isType);

        NewTransaction transaction = new NewTransaction(new NewTransactionDataSourceMockup(database));
        if (isBuyOrder) {
            return transaction.addBuyOrder(order);
        } else {
            return transaction.addSellOrder(order);
        }
    }

    /**
     * @see TradingPlatformDataSource#deleteOrder(int)
     */
    @Override
    public int deleteOrder(int idx) {

        NewTransactionDataSourceMockup data = new NewTransactionDataSourceMockup(database);
        NewTransaction transaction = new NewTransaction(data);
        return transaction.removeOrder(idx);
    }

    /**
     * @see TradingPlatformDataSource#addTransaction(String, String, String, int, int)
     */
    @Override
    public int addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount,
                              int credits) {

        Transaction transaction = new Transaction();
        transaction.setId(database.historyCount);
        transaction.setBuyingOrganisation(buyingOrganisation);
        transaction.setSellingOrganisation(sellingOrganisation);
        transaction.setAsset(asset);
        transaction.setAmount(amount);
        transaction.setCredits(credits);
        transaction.setDateTime("");

        database.transactionList.add(transaction);

        database.historyCount++;
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#getOrderHistory()
     */
    @Override
    public Set<Transaction> getOrderHistory() {
        return database.transactionList;
    }

    /**
     * @see TradingPlatformDataSource#deleteAll()
     */
    @Override
    public int deleteAll() {
        database.userMock = new HashSet<>();
        database.organisationsList = new HashSet<>();
        database.assetsList = new HashSet<>();
        database.userOrganisationList = new HashSet<>();
        database = new DatabaseMockup();
        return 1;
    }
}
