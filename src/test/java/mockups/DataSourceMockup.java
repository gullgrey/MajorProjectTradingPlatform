package test.java.mockups;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.util.*;


public class DataSourceMockup implements TradingPlatformDataSource {


    private Set<UserMockup> userMock = new HashSet<>();
    private Set<UserOrganisation> userOrganisationObjectSet = new HashSet<>();
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
        initiateDatabase();

    }

    public void initiateDatabase() {
        userMock.add(aUser);
        userOrganisationObjectSet.add(initialUser);
        organisationsList.add(org);
    }

    @Override
    public int getCredits(String organisation) {
        for (Organisation org : organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                return org.getCredits();
            }
        }
        return 1;
    }

    @Override
    public int updateCredits(String organisation, int credits) {
        //Throws
        for (Organisation org : organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                int orgCredits = org.getCredits();
                orgCredits += credits;
                if (orgCredits <= 0){
                    orgCredits = 0;
                }
                org.setCredits(orgCredits);
                return 1;
            }
        }
        for (Organisation org : organisationsList) {
            if (org.getOrganisation().equals(organisation)) {
                int orgCredits = org.getCredits();
                orgCredits += credits;
                if (orgCredits <= 0){
                    orgCredits = 0;
                }
                org.setCredits(orgCredits);
                return 1;
            }
        }
        return -1;
    }

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

    @Override
    public int addAsset(String organisation, String asset, int amount) {
        Asset aNewAsset = new Asset(organisation, asset, amount);
        assetsList.add(aNewAsset);
        return 1;
    }

    @Override
    public int getAssetAmount(String organisation, String asset) {
        int amount = -1;
        for (Asset aAsset : assetsList) {
            if (aAsset.getOrganisation().equals(organisation) && aAsset.getAsset().equals(asset)) {
                amount = aAsset.getAmount();
            }
        }
        return amount;
    }

    @Override
    public int deleteAsset(String organisation, String asset) {
        for (Asset anAsset : assetsList) {
            if (anAsset.getOrganisation().equals(organisation) & anAsset.getAsset().equals(asset)) {
                assetsList.remove(anAsset);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public int updateAssetAmount(String organisation, String asset, int amount) {
        int increaseAsset;
        for (Asset aAsset : assetsList) {
            if (aAsset.getAsset().equals(asset) & aAsset.getOrganisation().equals(organisation)) {

                increaseAsset = aAsset.getAmount();
                increaseAsset += amount;
                if (increaseAsset <= 0){
                    increaseAsset = 0;
                }
                aAsset.setAmount(increaseAsset);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public Set<Organisation> getOrganisations() {
        return organisationsList;
    }

    @Override
    public String getUserOrganisation(String username) {
        for (UserOrganisation aUser : userOrganisationObjectSet) {
            if (aUser.getUser().equals(username)) {
                return aUser.getOrganisation();
            }
        }
        return "";
    }

    @Override
    public int addOrganisation(String organisation, int credits) {
        Organisation anOrg = new Organisation();
        anOrg.setOrganisation(organisation);
        anOrg.setCredits(credits);
        organisationsList.add(anOrg);
        return 1;
    }

    @Override
    public int deleteOrganisation(String organisation) {
        for (Organisation aOrg : organisationsList) {
            if (aOrg.getOrganisation().equals(organisation)) {
                organisationsList.remove(aOrg);
                return 1;
            }
        }
        return -2;
    }

    @Override
    public Set<UserOrganisation> getUsers() {
        return userOrganisationObjectSet;
    }

    @Override
    public String getUserPassword(String username) {
        String password = null;
        for (UserMockup user : userMock) {
            if (user.getUsername().equals(username)) {
                password = user.getPassword();
            }
        }
        return password;
    }


    @Override
    public int addUser(String username, String password, String type, String organisation) {
        try {
            UserMockup aNewUser = new UserMockup(username, password, type, organisation);
            UserOrganisation sameUser = new UserOrganisation(username, organisation);
            boolean exists = false;

            //Foreign Key failure, organisation doesn't exist
            for (Organisation orgs : organisationsList) {
                if (orgs.getOrganisation().equals(organisation)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                return -2; // Organisation doesn't exist.
            }

            // Primary key failure, user already exists.
            for (UserMockup user : userMock) {

                if (user.getUsername().equals(username)) {
                    return -1;

                } else {
                    userMock.add(aNewUser);
                    userOrganisationObjectSet.add(sameUser);
                    return 1;
                }
            }

        } catch (Exception e) {
            return -3;
        }
        return -3;
    }

    @Override
    public int deleteUser(String username) {
        for (UserMockup aUser : userMock) {
            if (aUser.getUsername().equals(username)) {
                userMock.remove(aUser);
                return 1;
            }
        }
        for(UserOrganisation sameUser : userOrganisationObjectSet){
            if(sameUser.getUser().equals(username)){
                userOrganisationObjectSet.remove(sameUser);
            }
        }
        return -1;
    }

    @Override
    public int updatePassword(String username, String password) {
        for (UserMockup aUser : userMock) {
            if (aUser.getUsername().equals(username)) {
                aUser.setPassword(password);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public TPOrder getOrder(int idx) {
        return null;
    }

    @Override
    public Set<TPOrder> getOrders(boolean isBuyOrder) {
        return null;
    }

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


    @Override
    public int deleteOrder(int idx) {
        return 0;
    }

    @Override
    public int addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount,
                              int credits) {
        return 0;
    }

    @Override
    public Set<Transaction> getOrderHistory() {
        return null;
    }

    @Override
    public int deleteAll() {
        userMock = new HashSet<>();
        organisationsList = new HashSet<>();
        assetsList = new HashSet<>();
        userOrganisationObjectSet = new HashSet<>();
        initiateDatabase();
        return 1;
    }
}
