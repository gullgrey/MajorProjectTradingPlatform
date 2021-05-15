package main.java.database;

import main.java.tradingPlatform.Order;
import main.java.tradingPlatform.Transaction;

import java.util.Set;

/**
 *
 */
public interface TradingPlatformDataSource {

    /**
     * Gets the number of credits an organisation has.
     *
     * @param organisation The name of the organisational unit.
     * @return the number of credits an organisation has.
     */
    int getCredits(String organisation);

    /**
     * Updates the number of credits an organisation has by the inputted amount.
     *
     * @param organisation The name of the organisational unit.
     * @param credits The amount the organisation's credits change by. Can be positive or negative.
     */
    void updateCredits(String organisation, int credits);

    /**
     * Retrieves a set of asset names for a specific organisation.
     *
     * @param organisation The name of the organisational unit.
     * @return A set of all of the asset names that belong to an organisational unit
     */
    Set<String> getAssets(String organisation);

    /**
     *
     *
     * @param organisation The name of the organisational unit.
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
