package test.java.mockups;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.Order;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.util.*;


public class DataSourceMockup implements TradingPlatformDataSource {



    private final Set<DataSourceMockup> UserMock = new HashSet<>();
    private final Set<UserOrganisation> UserOrganisationObjectSet = new HashSet<>();
    //private Set<DataSourceMockup> organisationList = new HashSet<>();
    private final Set<Organisation> organisations = new HashSet<>();
    private final Set<Asset> assetsList = new HashSet<>();
    private final Set<TPOrder> buyOrderList = new HashSet<>();
    private final Set<TPOrder> sellOrderList = new HashSet<>();
    private final Set<Transaction> transationList = new HashSet<>();


    private String organisation;
    private String assetName;
    private String username;
    private String password;
    private int creditamount;
    private int assetAmount;
    private String type;
    private int credits;

    public DataSourceMockup(String organisation, int credits) {

    }

    // User
    public DataSourceMockup(String username, String password, String type, String organisation) {
        this.organisation = username;
        this.organisation = password;
        this.type = type;
        this.organisation = organisation;
    }

    @Override
    public int getCredits(String organisation) {
        for (Organisation org : organisations) {
            if (org.getOrganisation().equals(organisation)) {
                return org.getCredits();
            }
        }
        return 1;
    }

    @Override
    public int updateCredits(String organisation, int credits) {
        for(Organisation org : organisations){
            if(org.getOrganisation().equals(organisation)){
                int orgCredits = org.getCredits();
                orgCredits += credits;
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
        for(Asset anAsset : assetsList){
            if (anAsset.getOrganisation().equals(organisation) & anAsset.getAsset().equals(asset)){
                assetsList.remove(anAsset);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public int updateAssetAmount(String organisation, String asset, int amount) {
        int increaseAsset;
        for(Asset aAsset : assetsList){
            if (aAsset.getAsset().equals(asset) & aAsset.getOrganisation().equals(organisation)){
                increaseAsset = aAsset.getAmount();
                increaseAsset += amount;
                return increaseAsset;
            }
        }
        return -1;
    }

    @Override
    public Set<Organisation> getOrganisations() {
        return organisations;
    }

    @Override
    public String getUserOrganisation(String username) {
        for(UserOrganisation aUser : UserOrganisationObjectSet) {
            if(aUser.getUser().equals(username)){
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
        organisations.add(anOrg);
        return 1;
    }

    @Override
    public int deleteOrganisation(String organisation) {
        for(Organisation aOrg : organisations){
            if(aOrg.getOrganisation().equals(organisation)){
                organisations.remove(aOrg);
                return 1;
            }
        }
        return -2;
    }

    @Override
    public Set<UserOrganisation> getUsers() {
        return UserOrganisationObjectSet;
    }

    @Override
    public String getUserPassword(String username) {
        String password = null;
        for (DataSourceMockup user : UserMock) {
            if (user.username.equals(username)) {
                password = user.password;
            }
        }
        return password;
    }


    @Override
    public int addUser(String username, String password, String type, String organisation) {
        try {
            DataSourceMockup newuser = new DataSourceMockup(username, password, type, organisation);
            boolean exists = false;
            boolean running = true;
            while(running){
                for(Organisation orgs : organisations){
                    if(orgs.getOrganisation().equals(organisation)){
                        running = false;
                        exists = true;
                        break;
                    }
                }
            }
            if(exists){
                return -2; // Organisation doesn't exist.
            }

            for (DataSourceMockup user : UserMock) {
                if (user.username.equals(newuser.username)) {
                    return -1;

                } else {

                    UserOrganisation aNewUser = new UserOrganisation(username, organisation);
                    UserOrganisationObjectSet.add(aNewUser);
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
        for (DataSourceMockup aUser : UserMock){
            if (aUser.username.equals(username)){
                UserMock.remove(aUser);
                return 1;
            }
        }
        return -1;
    }

    @Override
    public int updatePassword(String username, String password) {
        for(DataSourceMockup aUser : UserMock){
            if(aUser.username.equals(username)){
                aUser.password = password;
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
        //TPOrder aTPOrder = new TPOrder(organisation, asset, amount, credits );
       // if (isBuyOrder) {
           // buyOrderList.add(aTPOrder);
        //} else
        //    sellOrderList.add(aTPOrder);
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
        return 0;
    }
}
