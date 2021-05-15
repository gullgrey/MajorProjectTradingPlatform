package main.java.database;

import main.java.tradingPlatform.Order;
import main.java.tradingPlatform.Transaction;

import java.util.Set;

public class JDBCTradingPlatformDataSource implements TradingPlatformDataSource{

    public JDBCTradingPlatformDataSource(String propsFile) {

    }

    /**
     *
     */
    public void prepareDatabase(){

    }

    /**
     * @see TradingPlatformDataSource#getCredits(String)
     */
    @Override
    public int getCredits(String organisation) {
        return 0;
    }

    /**
     * @see TradingPlatformDataSource#updateCredits(String, int)
     */
    @Override
    public void updateCredits(String organisation, int credits) {

    }

    /**
     * @see TradingPlatformDataSource#getAssets(String) 
     */
    @Override
    public Set<String> getAssets(String organisation) {
        return null;
    }

    @Override
    public void addAsset(String organisation, int amount) {

    }

    @Override
    public void deleteAsset(String organisation, String asset) {

    }

    @Override
    public void updateAssetAmount(String organisation, String asset, int amount) {

    }

    @Override
    public Set<String> getOrganisations() {
        return null;
    }

    @Override
    public String getUserOrganisation(String username) {
        return null;
    }

    @Override
    public void deleteOrganisation(String organisation) {

    }

    @Override
    public Set<String> getUsers(String organisation) {
        return null;
    }

    @Override
    public String getUserPassword(String username) {
        return null;
    }

    @Override
    public void addUser(String username, String password, String type, String organisation) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void updatePassword(String username, String password) {

    }

    @Override
    public Order getOrder(int idx) {
        return null;
    }

    @Override
    public Set<Order> getOrders(String organisation, String Asset, int amount, int credits, boolean isBuyOrder) {
        return null;
    }

    @Override
    public Set<Order> getAllOrders(boolean isBuyOrder) {
        return null;
    }

    @Override
    public void addOrder(String organisation, String Asset, int amount, int credits, boolean isBuyOrder) {

    }

    @Override
    public void deleteOrder(int idx) {

    }

    @Override
    public void addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount) {

    }

    @Override
    public Set<Transaction> getOrderHistory(String buyingOrganisation, String sellingOrganisation, String asset) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
    
    private String hashPassword(String password) {
        return null;
    }
}
