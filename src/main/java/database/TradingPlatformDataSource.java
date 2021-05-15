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
     * Adds a new asset to an organisation and its initial amount.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be added.
     * @param amount The amount of the asset to be added.
     */
    void addAsset(String organisation, String asset, int amount);

    /**
     * Removes an asset from an organisation.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be removed.
     */
    void deleteAsset(String organisation, String asset);

    /**
     * Updates the amount of an asset an organisation has by the inputted amount.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be updated.
     * @param amount The amount the asset is changed by. Can be positive or negative.
     */
    void updateAssetAmount(String organisation, String asset, int amount);

    /**
     *  Retrieves the names of all of the organisations in the database
     *
     * @return a set of all the organisation names.
     */
    Set<String> getOrganisations();

    /**
     * Retrieves the name of the organisational unit for the inputted user.
     *
     * @param username The username of a user.
     * @return the organisational unit of the inputted user.
     */
    String getUserOrganisation(String username);

    /**
     *
     * @param organisation
     * @param credits
     */
    void addOrganisation(String organisation, int credits);

    /**
     * Deletes an organisation from the database.
     * @param organisation The name of the organisational unit.
     */
    void deleteOrganisation(String organisation);

    /**
     * Retrieves the set of usernames that belong to the inputted organisation.
     *
     * @param organisation The name of the organisational unit.
     * @return a set of usernames.
     */
    Set<String> getUsers(String organisation);

    /**
     * Returns the hashed password of the user with the inputted username.
     *
     * @param username The username of a user.
     * @return a hashed password.
     */
    String getUserPassword(String username);

    /**
     * Adds a user to the database. Can be a standard user or an IT admin. If its a standard user the
     * inputted organisation must be one that already exists.
     *
     * @param username The username of a user.
     * @param password A hashed password.
     * @param type The type of user being created (STANDARD / ADMIN)
     * @param organisation The name of the organisational unit. Null if the new user is IT admin.
     */
    void addUser(String username, String password, String type, String organisation);

    /**
     * Deletes a user from the database.
     *
     * @param username The username of a user.
     */
    void deleteUser(String username);

    /**
     * Updates a users hashed password in the database to the inputted hashed password.
     *
     * @param username The username of a user.
     * @param password A hashed password.
     */
    void updatePassword(String username, String password);

    /**
     * Creates an order object from an order in the database with the inputted idx. The order
     * object contains the id, organisation, asset, amount, credits, date-time and type of order.
     *
     * @param idx the integer id of a buy or sell order
     * @return an order object that contains all of the order information
     */
    Order getOrder(int idx);

    /**
     * Creates a set of order objects that have values equal to the inputted organisation and
     * assets.
     * If either of these
     *
     * @param organisation The name of the organisational unit. Can be null.
     * @param asset The name of the asset. Can be null.
     * @param isBuyOrder True if the orders are buy orders. False if they're sell orders.
     * @return
     */
    Set<Order> getOrders(String organisation, String asset,
                         boolean isBuyOrder);

    /**
     *
     * @param organisation The name of the organisational unit.
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
