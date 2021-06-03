package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import java.sql.SQLException;
import java.util.Set;

/**
 * TODO
 */
public class StandardUser extends TPUser {

    private final String username;
    private final String organisation;

    private static final String amountMessage = "Asset amount cannot be less then 1.";
    private static final String priceMessage = "Asset price cannot be less then 1.";

    public StandardUser(TradingPlatformDataSource dataSource, String username, String organisation) {
        super(dataSource);
        this.username = username;
        this.organisation = organisation;
    }

    public String getOrganisation() {
        return organisation;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Method is used to take a buy request from a used.
     * @param asset asset they would like to buy
     * @param amount amount they would like to get of the asset.
     * @param credits number of credits per asset they would like to pay.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     * @return number of assets immediately bought
     */
    public int buyAsset(String asset, int amount, int credits) throws InvalidValueException,
            UnknownDatabaseException {
        if(amount <= 0){
            throw new InvalidValueException(amountMessage);
        }
        if(credits <= 0) {
            throw new InvalidValueException(priceMessage);
        }
        int buyRequest = dataSource.addOrder(organisation, asset, amount, credits, true);
        if (buyRequest == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "Your organisation does not have enough credits to complete this order.";
            throw new InvalidValueException(message);
        } else if (buyRequest == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
        return buyRequest;
    }

    /**
     * Method is used to take a sell request from a used.
     * @param asset asset they would like to sell.
     * @param amount amount of the asset they are selling.
     * @param credits amount per assert.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     * @return number of assets immediately sold
     */
    public int sellAsset(String asset, int amount, int credits) throws InvalidValueException,
            UnknownDatabaseException {
        if(amount <= 0){
            throw new InvalidValueException(amountMessage);
        }
        if(credits <= 0) {
            throw new InvalidValueException(priceMessage);
        }
        int sellRequest = dataSource.addOrder(organisation, asset, amount, credits, false);
        if (sellRequest == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "Your organisation does not have enough assets to complete this order.";
            throw new InvalidValueException(message);
        } else if (sellRequest == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
        return sellRequest;
    }

    /**
     * Removed a request buy or sell order of the users organisation.
     * @param orderId id of the order that is being removed
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public void removeOrder(int orderId) throws InvalidValueException, NullValueException, UnknownDatabaseException {
        TPOrder order = dataSource.getOrder(orderId);
        if (order == null) {
            String message = "Order with that ID does not exist.";
            throw new NullValueException(message);
        } else if (!order.getOrganisation().equals(this.organisation)) {
            String message = "Order number is not from your organisation.";
            throw new InvalidValueException(message);
        }
        int rowsAffected = dataSource.deleteOrder(orderId);
        if (rowsAffected == PlatformGlobals.getNoRowsAffected()) {
            String message = "An order with that ID does not exist.";
            throw new NullValueException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
    }

    /**
     * Method is used to get all the assets of the users organisation.
     * @return a set containing all the assets that relate to the organisation.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public Set<Asset> getAssets() throws UnknownDatabaseException {
        Set<Asset> Asset = dataSource.getAssets();
        if (Asset == null) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
        return Asset;
    }

    /**
     * Given the name of an asset it returns the amount the organisation has.
     * If they don't contact the asset 0 will be returned.
     * @param asset name of the asset.
     * @return the amount of the asset for that organisation.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public int getAssetAmount(String asset) throws UnknownDatabaseException {
        int amount = dataSource.getAssetAmount(organisation, asset);
        if (amount == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
        return amount;
    }

    /**
     * Gets the amount of credits the users organisation has.
     * @return value of the organisations credits.
     * @throws SQLException catches any SQL exceptions that are thrown if there are any issues.
     */
    public int getCredits() throws UnknownDatabaseException {
        int credits = dataSource.getCredits(organisation);
        if (credits == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(PlatformGlobals.getUnknownSQLMessage());
        }
        return credits;
    }


}
