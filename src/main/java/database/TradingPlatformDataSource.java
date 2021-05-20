package main.java.database;

import main.java.tradingPlatform.TPOrder;
import main.java.tradingPlatform.Transaction;

import java.sql.SQLException;
import java.util.Set;

/**
 * TODO
 */
public interface TradingPlatformDataSource {

    /**
     * Gets the number of credits an organisation has.
     *
     * @param organisation The name of the organisational unit.
     * @return the number of credits an organisation has.
     * @throws
     */
    int getCredits(String organisation) throws SQLException;

    /**
     * Updates the number of credits an organisation has by the inputted amount.
     *
     * @param organisation The name of the organisational unit.
     * @param credits The amount the organisation's credits change by. Can be positive or negative.
     */
    void updateCredits(String organisation, int credits) throws SQLException;

    /**
     * Retrieves a set of asset names for a specific organisation.
     *
     * @param organisation The name of the organisational unit.
     * @return A set of all of the asset names that belong to an organisational unit
     */
    Set<String> getAssets(String organisation) throws SQLException;

    /**
     * Adds a new asset to an organisation and its initial amount.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be added.
     * @param amount The amount of the asset to be added.
     */
    void addAsset(String organisation, String asset, int amount) throws SQLException;

    /**
     * Retrieves the amount of an asset belonging to an organisation.
     *
     * @param organisation The name of the organisational unit.
     * @param Asset  The name of the asset.
     * @return the integer value of the amount of the asset from the organisation.
     */
    int getAssetAmount(String organisation, String Asset) throws SQLException;

    /**
     * Removes an asset from an organisation.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be removed.
     */
    void deleteAsset(String organisation, String asset) throws SQLException;

    /**
     * Updates the amount of an asset an organisation has by the inputted amount.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be updated.
     * @param amount The amount the asset is changed by. Can be positive or negative.
     */
    void updateAssetAmount(String organisation, String asset, int amount) throws SQLException;

    /**
     *  Retrieves the names of all of the organisations in the database
     *
     * @return a set of all the organisation names.
     */
    Set<String> getOrganisations() throws SQLException;

    /**
     * Retrieves the name of the organisational unit for the inputted user.
     *
     * @param username The username of a user.
     * @return the organisational unit of the inputted user.
     */
    String getUserOrganisation(String username) throws SQLException;

    /**
     * Adds a new organisation to the database as well as its starting credits.
     *
     * @param organisation The name of the new organisation.
     * @param credits The amount of credits the new organisation starts with.
     */
    void addOrganisation(String organisation, int credits) throws SQLException;

    /**
     * Deletes an organisation from the database.
     * @param organisation The name of the organisational unit.
     */
    void deleteOrganisation(String organisation) throws SQLException;

    /**
     * Retrieves the set of usernames that belong to the inputted organisation.
     *
     * @param organisation The name of the organisational unit.
     * @return a set of usernames.
     */
    Set<String> getUsers(String organisation) throws SQLException;

    /**
     * Returns the hashed password of the user with the inputted username.
     *
     * @param username The username of a user.
     * @return a hashed password.
     */
    String getUserPassword(String username) throws SQLException;

    /**
     * Adds a user to the database. Can be a standard user or an IT admin. If its a standard user the
     * inputted organisation must be one that already exists.
     *
     * @param username The username of a user.
     * @param password A hashed password.
     * @param type The type of user being created (STANDARD / ADMIN)
     * @param organisation The name of the organisational unit. Null if the new user is IT admin.
     */
    void addUser(String username, String password, String type, String organisation) throws SQLException;

    /**
     * Deletes a user from the database.
     *
     * @param username The username of a user.
     */
    void deleteUser(String username) throws SQLException;

    /**
     * Updates a users hashed password in the database to the inputted hashed password.
     *
     * @param username The username of a user.
     * @param password A hashed password.
     */
    void updatePassword(String username, String password) throws SQLException;

    /**
     * Creates an order object from an order in the database with the inputted idx. The order
     * object contains the id, organisation, asset, amount, credits, date-time and type of order.
     *
     * @param idx the integer id of a buy or sell order
     * @return an order object that contains all of the order information
     */
    TPOrder getOrder(int idx) throws SQLException;

    /**
     * Creates a set of order objects that have values equal to the inputted organisation and
     * assets. The order information is retrieved from the database.
     * If either of organisation or asset is blank then it retrieves orders for all organisations,
     * assets or both.
     *
     * @param organisation The name of the organisational unit. Can be null.
     * @param asset The name of the asset. Can be null.
     * @param isBuyOrder True if the orders are buy orders. False if they're sell orders.
     * @return a set of TPOrder objects with all the order information.
     */
    Set<TPOrder> getOrders(String organisation, String asset,
                           boolean isBuyOrder) throws SQLException;

    /**
     * Adds a new order to the database trading platform. Can be a buy order or sell order.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset.
     * @param amount The amount of the asset.
     * @param credits The number of credits the asset is listed for.
     * @param isBuyOrder True if the order is a buy order. False if its a sell order.
     */
    void addOrder(String organisation, String asset, int amount,
                  int credits, boolean isBuyOrder) throws SQLException;

    /**
     * Removes a buy or sell order from the database that corresponds to the inputted id.
     *
     * @param idx the integer id of a buy or sell order
     */
    void deleteOrder(int idx) throws SQLException;

    /**
     * Adds a new transaction to the database trade_history after a trade is completed.
     *
     * @param buyingOrganisation The name of the organisational unit that bought assets.
     * @param sellingOrganisation The name of the organisational unit that sold assets.
     * @param asset The name of the asset that was sold.
     * @param amount The amount of the asset that was sold.
     * @param credits The number of credits the asset was sold for.
     */
    void addTransaction(String buyingOrganisation, String sellingOrganisation,
                         String asset, int amount, int credits) throws SQLException;

    /**
     * Creates a set of Transaction objects that have values equal to the inputted organisations and
     * assets. The order information is retrieved from the database.
     * If any of buyingOrganisation, sellingOrganisation or asset is blank then it retrieves orders
     * for all organisations, assets or both.
     *
     * @param buyingOrganisation  The name of an organisational unit that bought assets. Can be null.
     * @param sellingOrganisation  The name of an organisational unit that sold assets. Can be null.
     * @param asset The name of an asset that was sold.
     * @return a set of Transaction objects with all the transaction information.
     */
    Set<Transaction> getOrderHistory(String buyingOrganisation,
                                     String sellingOrganisation, String asset) throws SQLException;

    /**
     * Deletes all rows from every table in the database.
     */
    void deleteAll() throws SQLException;

}
