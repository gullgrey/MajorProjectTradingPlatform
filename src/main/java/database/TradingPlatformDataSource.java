package main.java.database;

import main.java.tradingPlatform.*;

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
     */
    int getCredits(String organisation);

    /**
     * Updates the number of credits an organisation has by the inputted amount.
     *
     * @param organisation The name of the organisational unit.
     * @param credits The amount the organisation's credits change by. Can be positive or negative.
     * @return
     */
    int updateCredits(String organisation, int credits);

    /**
     * Retrieves a set of assets and their organisations and amounts.
     *
     * @return A set of all of the assets that belong to an organisational unit
     */
    Set<Asset> getAssets();

    /**
     * Adds a new asset to an organisation and its initial amount.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be added.
     * @param amount The amount of the asset to be added.
     * @returns
     */
    int addAsset(String organisation, String asset, int amount);

    /**
     * Retrieves the amount of an asset belonging to an organisation.
     *
     * @param organisation The name of the organisational unit.
     * @param asset  The name of the asset.
     * @return the integer value of the amount of the asset from the organisation.
     */
    int getAssetAmount(String organisation, String asset);

    /**
     * Removes an asset from an organisation.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be removed.
     * @return
     */
    int deleteAsset(String organisation, String asset);

    /**
     * Updates the amount of an asset an organisation has by the inputted amount.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset to be updated.
     * @param amount The amount the asset is changed by. Can be positive or negative.
     * @return
     */
    int updateAssetAmount(String organisation, String asset, int amount);

    /**
     *  Retrieves the names of all of the organisations in the database
     *
     * @return a set of all the organisation names.
     */
    Set<Organisation> getOrganisations();

    /**
     * Retrieves the name of the organisational unit for the inputted user.
     *
     * @param username The username of a user.
     * @return the organisational unit of the inputted user.
     */
    String getUserOrganisation(String username);

    /**
     * Adds a new organisation to the database as well as its starting credits.
     *
     * @param organisation The name of the new organisation.
     * @param credits The amount of credits the new organisation starts with.
     * @returns
     */
    int addOrganisation(String organisation, int credits);

    /**
     * Deletes an organisation from the database.
     * @param organisation The name of the organisational unit.
     * @return
     */
    int deleteOrganisation(String organisation);

    /**
     * Retrieves the set of usernames with access to the trading platform.
     *
     * @return a set of usernames.
     */
    Set<UserOrganisation> getUsers();

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
     * @returns
     */
    int addUser(String username, String password, String type, String organisation);

    /**
     * Deletes a user from the database.
     *
     * @param username The username of a user.
     * @return
     */
    int deleteUser(String username);

    /**
     * Updates a users hashed password in the database to the inputted hashed password.
     *
     * @param username The username of a user.
     * @param password A hashed password.
     * @return
     */
    int updatePassword(String username, String password);

    /**
     * Creates an order object from an order in the database with the inputted idx. The order
     * object contains the id, organisation, asset, amount, credits, date-time and type of order.
     *
     * @param idx the integer id of a buy or sell order
     * @return an order object that contains all of the order information
     */
    TPOrder getOrder(int idx);

    /**
     * Creates a set of order objects, either buy or sell orders.
     * The order information is retrieved from the database.
     *
     * @param isBuyOrder True if the orders are buy orders. False if they're sell orders.
     * @return a set of TPOrder objects with all the order information.
     */
    Set<TPOrder> getOrders(boolean isBuyOrder);

    /**
     * Adds a new order to the database trading platform. Can be a buy order or sell order.
     *
     * @param organisation The name of the organisational unit.
     * @param asset The name of the asset.
     * @param amount The amount of the asset.
     * @param credits The number of credits the asset is listed for.
     * @param isBuyOrder True if the order is a buy order. False if its a sell order.
     * @returns
     */
    int addOrder(String organisation, String asset, int amount,
                  int credits, boolean isBuyOrder);

    /**
     * Removes a buy or sell order from the database that corresponds to the inputted id.
     *
     * @param idx the integer id of a buy or sell order.
     * @return
     */
    int deleteOrder(int idx);

    /**
     * Adds a new transaction to the database trade_history after a trade is completed.
     *
     * @param buyingOrganisation The name of the organisational unit that bought assets.
     * @param sellingOrganisation The name of the organisational unit that sold assets.
     * @param asset The name of the asset that was sold.
     * @param amount The amount of the asset that was sold.
     * @param credits The number of credits the asset was sold for.
     * @returns
     */
    int addTransaction(String buyingOrganisation, String sellingOrganisation,
                         String asset, int amount, int credits);

    /**
     * Creates a set of Transaction objects. The order information is retrieved from the database.
     *
     * @return a set of Transaction objects with all the transaction information.
     */
    Set<Transaction> getOrderHistory();

    /**
     * Deletes all rows from every table in the database, except for the organisation
     * with the name "ADMIN".
     * @returns
     */
    int deleteAll();

}
