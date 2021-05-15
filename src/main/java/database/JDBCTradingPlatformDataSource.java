package main.java.database;

import main.java.tradingPlatform.Order;
import main.java.tradingPlatform.Transaction;

import java.util.Set;

/**
 * TODO
 */
public class JDBCTradingPlatformDataSource implements TradingPlatformDataSource{

    public JDBCTradingPlatformDataSource(String propsFile) {

    }

    /**
     * TODO
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

    /**
     * @see TradingPlatformDataSource#addAsset(String, String, int)
     */
    @Override
    public void addAsset(String organisation, String asset, int amount) {

    }

    /**
     * @see TradingPlatformDataSource#deleteAsset(String, String)
     */
    @Override
    public void deleteAsset(String organisation, String asset) {

    }

    /**
     * @see TradingPlatformDataSource#updateAssetAmount(String, String, int)
     */
    @Override
    public void updateAssetAmount(String organisation, String asset, int amount) {

    }

    /**
     * @see TradingPlatformDataSource#getOrganisations()
     */
    @Override
    public Set<String> getOrganisations() {
        return null;
    }

    /**
     * @see TradingPlatformDataSource#getUserOrganisation(String)
     */
    @Override
    public String getUserOrganisation(String username) {
        return null;
    }

    /**
     * @see TradingPlatformDataSource#addOrganisation(String, int) 
     */
    @Override
    public void addOrganisation(String organisation, int credits) {
        
    }

    /**
     * @see TradingPlatformDataSource#deleteOrganisation(String)
     */
    @Override
    public void deleteOrganisation(String organisation) {

    }

    /**
     * @see TradingPlatformDataSource#getUsers(String)
     */
    @Override
    public Set<String> getUsers(String organisation) {
        return null;
    }

    /**
     * @see TradingPlatformDataSource#getUserPassword(String)
     */
    @Override
    public String getUserPassword(String username) {
        return null;
    }

    /**
     * @see TradingPlatformDataSource#addUser(String, String, String, String)
     */
    @Override
    public void addUser(String username, String password, String type, String organisation) {

    }

    /**
     * @see TradingPlatformDataSource#deleteUser(String)
     */
    @Override
    public void deleteUser(String username) {

    }

    /**
     * @see TradingPlatformDataSource#updatePassword(String, String)
     */
    @Override
    public void updatePassword(String username, String password) {

    }

    /**
     * @see TradingPlatformDataSource#getOrder(int)
     */
    @Override
    public Order getOrder(int idx) {
        return null;
    }

    /**
     * @see #getOrders(String, String, boolean)
     */
    @Override
    public Set<Order> getOrders(String organisation, String asset, boolean isBuyOrder) {
        return null;
    }

    /**
     * @see TradingPlatformDataSource#addOrder(String, String, int, int, boolean)
     */
    @Override
    public void addOrder(String organisation, String Asset, int amount, int credits, boolean isBuyOrder) {

    }

    /**
     * @see TradingPlatformDataSource#deleteOrder(int)
     */
    @Override
    public void deleteOrder(int idx) {

    }

    /**
     * @see TradingPlatformDataSource#addTransaction(String, String, String, int)
     */
    @Override
    public void addTransaction(String buyingOrganisation, String sellingOrganisation, String asset, int amount) {

    }

    /**
     * @see TradingPlatformDataSource#getOrderHistory(String, String, String)
     */
    @Override
    public Set<Transaction> getOrderHistory(String buyingOrganisation, String sellingOrganisation, String asset) {
        return null;
    }

    /**
     * @see TradingPlatformDataSource#deleteAll()
     */
    @Override
    public void deleteAll() {

    }

    /**
     * 
     * @param password
     * @return
     */
    private String hashPassword(String password) {
        return null;
    }
}
