package main.java.database;

import main.java.tradingPlatform.Order;
import main.java.tradingPlatform.Transaction;

import java.util.Set;

/**
 *
 */
public interface TradingPlatformDataSource {

    /**
     *
     * @param organisation
     * @return
     */
    int getCredits(String organisation);

    /**
     *
     * @param organisation
     * @param credits
     */
    void updateCredits(String organisation, int credits);

    /**
     *
     * @param organisation
     * @return
     */
    Set<String> getAssets(String organisation);

    /**
     *
     * @param organisation
     * @param amount
     */
    void addAsset(String organisation, int amount);

    /**
     *
     * @param organisation
     * @param asset
     */
    void deleteAsset(String organisation, String asset);

    /**
     *
     * @param organisation
     * @param asset
     * @param amount
     */
    void updateAssetAmount(String organisation, String asset, int amount);

    /**
     *
     * @return
     */
    Set<String> getOrganisations();

    /**
     *
     * @param username
     * @return
     */
    String getUserOrganisation(String username);

    /**
     *
     * @param organisation
     */
    void deleteOrganisation(String organisation);

    /**
     *
     * @param organisation
     * @return
     */
    Set<String> getUsers(String organisation);

    /**
     *
     * @param username
     * @return
     */
    String getUserPassword(String username);

    /**
     *
     * @param username
     * @param password
     * @param type
     * @param organisation
     */
    void addUser(String username, String password, String type, String organisation);

    /**
     *
     * @param username
     */
    void deleteUser(String username);

    /**
     *
     * @param username
     * @param password
     */
    void updatePassword(String username, String password);

    /**
     *
     * @param idx
     * @return
     */
    Order getOrder(int idx);

    /**
     *
     * @param organisation
     * @param Asset
     * @param amount
     * @param credits
     * @param isBuyOrder
     * @return
     */
    Set<Order> getOrders(String organisation, String Asset, int amount,
                         int credits, boolean isBuyOrder);

    /**
     *
     * @param isBuyOrder
     * @return
     */
    Set<Order> getAllOrders(boolean isBuyOrder);

    /**
     *
     * @param organisation
     * @param Asset
     * @param amount
     * @param credits
     * @param isBuyOrder
     */
    void addOrder(String organisation, String Asset, int amount,
                  int credits, boolean isBuyOrder);

    /**
     *
     * @param idx
     */
    void deleteOrder(int idx);

    /**
     *
     * @param buyingOrganisation
     * @param sellingOrganisation
     * @param asset
     * @param amount
     */
    void addTransaction(String buyingOrganisation, String sellingOrganisation,
                         String asset, int amount);

    /**
     *
     * @param buyingOrganisation
     * @param sellingOrganisation
     * @param asset
     * @return
     */
    Set<Transaction> getOrderHistory(String buyingOrganisation,
                                     String sellingOrganisation, String asset);

    /**
     *
     */
    void deleteAll();

}
