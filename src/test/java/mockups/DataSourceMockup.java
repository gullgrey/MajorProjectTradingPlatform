package test.java.mockups;

import main.java.database.TradingPlatformDataSource;
import main.java.tradingPlatform.*;

import java.util.Set;

public class DataSourceMockup implements TradingPlatformDataSource {
    @Override
    public int getCredits(String organisation) {
        return 0;
    }

    @Override
    public int updateCredits(String organisation, int credits) {
        return 0;
    }

    @Override
    public Set<Asset> getAssets() {
        return null;
    }

    @Override
    public int addAsset(String organisation, String asset, int amount) {
        return 0;
    }

    @Override
    public int getAssetAmount(String organisation, String asset) {
        return 0;
    }

    @Override
    public int deleteAsset(String organisation, String asset) {
        return 0;
    }

    @Override
    public int updateAssetAmount(String organisation, String asset, int amount) {
        return 0;
    }

    @Override
    public Set<Organisation> getOrganisations() {
        return null;
    }

    @Override
    public String getUserOrganisation(String username) {
        return null;
    }

    @Override
    public int addOrganisation(String organisation, int credits) {
        return 0;
    }

    @Override
    public int deleteOrganisation(String organisation) {
        return 0;
    }

    @Override
    public Set<UserOrganisation> getUsers() {
        return null;
    }

    @Override
    public String getUserPassword(String username) {
        return null;
    }

    @Override
    public int addUser(String username, String password, String type, String organisation) {
        return 0;
    }

    @Override
    public int deleteUser(String username) {
        return 0;
    }

    @Override
    public int updatePassword(String username, String password) {
        return 0;
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
        return 0;
    }

    @Override
    public int deleteOrder(int idx) {
        return 0;
    }

    @Override
    public int addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount, int credits) {
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
