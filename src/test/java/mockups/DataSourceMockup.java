package test.java.mockups;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.util.*;

/**
 * This class is responsible for mocking the database. It carries out similar functions and
 * stores all the values associated with the database and database queries, except with mock
 * Objects.
 */
public class DataSourceMockup implements TradingPlatformDataSource {


    private Set<UserMockup> userMock = new HashSet<>();
    private Set<UserOrganisation> userOrganisationList = new HashSet<>();
    private Set<Organisation> organisationsList = new HashSet<>();
    private Set<Asset> assetsList = new HashSet<>();
    private final Set<UserMockup> orderList = new HashSet<>();
    private final Set<UserMockup> sellOrderList = new HashSet<>();
    private final Set<Transaction> transationList = new HashSet<>();
    private static DataSourceMockup dataSource;

    private static ItAdministration adminAccount;
    private String organisationMember;
    private static final String adminUserName = "Admin";
    private int count = 0;
    private String admin = PlatformGlobals.getAdminOrganisation();
    private Organisation org = new Organisation(admin, 0);
    private UserMockup aUser = new UserMockup(admin, admin, admin, admin);
    private UserOrganisation initialUser = new UserOrganisation(admin, admin);


    public DataSourceMockup() {
        initiateDatabase(); // Build the initial values for the database
    }

    /**
     * This method initializes the database and adds the default values into the data sets.
     */
    public void initiateDatabase() {
        userMock.add(aUser);
        userOrganisationList.add(initialUser);
        organisationsList.add(org);
    }

    /**
     * @see TradingPlatformDataSource#getCredits(String)
     */
    @Override
    public int getCredits(String organisation) {
        for (Organisation org : organisationsList) {
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
        for (Organisation org : organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }
        //If the organisation exists update credits
        for (Organisation org : organisationsList) {
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
        for (Asset user : assetsList) {
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
        for (Asset anAsset : assetsList) {
            if (anAsset.getOrganisation().equals(organisation) && anAsset.getAsset().equals(asset)) {
                exists = true;
            }
        }
        if (exists) {
            return PlatformGlobals.getPrimaryKeyFail();
        }

        // Check to see if that organisation does exist (Foreign key constraint)
        for (Organisation org : organisationsList) {
            if (aNewAsset.getOrganisation().equals(org.getOrganisation())) {
                exists = true;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }
        assetsList.add(aNewAsset);
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#getAssetAmount(String, String)
     */
    @Override
    public int getAssetAmount(String organisation, String asset) {

        boolean exists = false;

        // Check to see if that organisation exists (Foreign key constraint)
        for (Organisation anOrg : organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        // Check to see if that asset exists (Foreign key constraint)
        for (Asset anAsset : assetsList) {
            if (anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        int amount = PlatformGlobals.getGeneralSQLFail();
        for (Asset aAsset : assetsList) {
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
        for (Organisation anOrg : organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        // Check to see if that asset exists (Foreign key constraint)
        for (Asset anAsset : assetsList) {
            if (anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        // Remove the asset
        for (Asset anAsset : assetsList) {
            if (anAsset.getOrganisation().equals(organisation) && anAsset.getAsset().equals(asset)) {
                assetsList.remove(anAsset);
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
        for (Organisation anOrg : organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        // Check to see if that asset exists (Foreign key constraint)
        for (Asset anAsset : assetsList) {
            if (anAsset.getAsset().equals(asset)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }

        int increaseAsset;
        for (Asset aAsset : assetsList) {
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
        return organisationsList;
    }

    /**
     * @see TradingPlatformDataSource#getUserOrganisation(String)
     */
    @Override
    public String getUserOrganisation(String username) {
        for (UserOrganisation aUser : userOrganisationList) {
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
        for (Organisation anOrg : organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        // Checks to see if the organisation exists (Primary key constraint)
        if (exists) {
            return PlatformGlobals.getPrimaryKeyFail();
        }
        organisationsList.add(anOrganisation);
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#deleteOrganisation(String)
     */
    @Override
    public int deleteOrganisation(String organisation) {

        boolean exists = false;

        for (Organisation anOrg : organisationsList) {
            if (anOrg.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail();
        }
        organisationsList.removeIf(aOrg -> aOrg.getOrganisation().equals(organisation));
        userOrganisationList.removeIf(aUserOrg -> aUserOrg.getOrganisation().equals(organisation));
        assetsList.removeIf(orgAsset -> orgAsset.getOrganisation().equals(organisation));
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#getUsers()
     */
    @Override
    public Set<UserOrganisation> getUsers() {
        return userOrganisationList;
    }

    /**
     * @see TradingPlatformDataSource#getUserPassword(String)
     */
    @Override
    public String getUserPassword(String username) {

        for (UserMockup user : userMock) {
            if (user.getUsername().equals(username)) {
                String password = user.getPassword();
                return password;
            }
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
        //TODO Sort the Type

        // Check to see if the organisation exists (Foreign key constraint)
        for (Organisation orgs : organisationsList) {
            if (orgs.getOrganisation().equals(organisation)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail(); // Organisation doesn't exist.
        }

        // Primary key failure, user already exists.
        for (UserMockup user : userMock) {

            if (user.getUsername().equals(username)) {
                return PlatformGlobals.getPrimaryKeyFail();

            } else {
                userMock.add(aNewUser);
                userOrganisationList.add(sameUser);
                return 1;
            }
        }

        return PlatformGlobals.getGeneralSQLFail();
    }

    /**
     * @see TradingPlatformDataSource#deleteUser(String)
     */
    @Override
    public int deleteUser(String username) {

        for (UserMockup aUser : userMock) {
            if (aUser.getUsername().equals(username)) {
                userMock.remove(aUser);
                break;
            }
        }

        for (UserOrganisation sameUser : userOrganisationList) {
            if (sameUser.getUser().equals(username)) {
                userOrganisationList.remove(sameUser);
                return 1;
            }
        }
        return PlatformGlobals.getForeignKeyFail();
    }

    /**
     * @see TradingPlatformDataSource#updatePassword(String, String)
     */
    @Override
    public int updatePassword(String username, String password) {

        boolean exists = false;

        // Checks to see if the user exists (Foreign Key constraint)
        for (UserMockup aUser : userMock) {
            if (aUser.getUsername().equals(username)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return PlatformGlobals.getForeignKeyFail(); // Organisation doesn't exist.
        }

        for (UserMockup aUser : userMock) {
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
        return new TPOrder();
    }

    /**
     * @see TradingPlatformDataSource#getOrders(boolean)
     */
    @Override
    public Set<TPOrder> getOrders(boolean isBuyOrder) {
        //add a set
        return new TreeSet<TPOrder>();
    }

    /**
     * @see TradingPlatformDataSource#addOrder(String, String, int, int, boolean)
     */
    @Override
    public int addOrder(String organisation, String asset, int amount, int credits, boolean isBuyOrder) {
        String isType;
        if (isBuyOrder) {
            isType = "BUY";
        } else {
            isType = "SELL";
        }
        UserMockup aOrder = new UserMockup(organisation, asset, amount, credits, isType);
        orderList.add(aOrder);
        return 1;
    }

    /**
     * @see TradingPlatformDataSource#deleteOrder(int)
     * @return
     */
    @Override
    public int deleteOrder(int idx) {
        return 0;
    }

    /**
     * @see TradingPlatformDataSource#addTransaction(String, String, String, int, int)
     */
    @Override
    public int addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount,
                              int credits) {
        return 0;
    }

    /**
     * @see TradingPlatformDataSource#getOrderHistory()
     */
    @Override
    public Set<Transaction> getOrderHistory() {
        return new TreeSet<>();
    }

    /**
     * @see TradingPlatformDataSource#deleteAll()
     */
    @Override
    public int deleteAll() {
        userMock = new HashSet<>();
        organisationsList = new HashSet<>();
        assetsList = new HashSet<>();
        userOrganisationList = new HashSet<>();
        initiateDatabase();
        return 1;
    }
}
