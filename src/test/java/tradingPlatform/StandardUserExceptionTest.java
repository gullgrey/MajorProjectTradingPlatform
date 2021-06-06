package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.java.mockups.DataSourceMockup;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class StandardUserExceptionTest {

    private static DataSourceMockup dataSource;
    private static final String AdminName = "Jen";
    private static final String aNewUser = "James";
    private static final String aNewUserOrganisation = "QUT";
    private static final String bNewUser = "Fred";
    private static final String bNewUserOrganisation = "UQ";
    private static final String assetName = "20% GPU";
    private static final String assetName2 = "Web Server";
    private static final int startingCredits = 1000;
    private static final int fullAssetAmount = 100;
    private static final int assetAmount = 36;
    private static final int creditPricePerAsset = 6;
    private static final int invalidInt = -9;
    private static final int validID = 27;

    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
        dataSource = new DataSourceMockup();
        dataSource.addOrganisation(aNewUserOrganisation, startingCredits);
        dataSource.addOrganisation(bNewUserOrganisation, startingCredits);
        dataSource.addAsset(bNewUserOrganisation, assetName, fullAssetAmount);
    }

    /**
     * Testing that when an invalid asset amount for a Buy Asset is attempted an
     * InvalidValueException is thrown.
     */
    @Test
    public void testbuyAssetInvalidAssetValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.buyAsset(assetName, invalidInt, creditPricePerAsset);
        } );
    }

    /**
     * Testing that when an invalid PricePer Asset is attempted for a Buy Asset
     * request an invalid an InvalidValueException is thrown.
     */
    @Test
    public void testbuyAssetInvalidCreditPriceValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.buyAsset(assetName, assetAmount, invalidInt);
        } );
    }

    /**
     * Testing that when both the PricePer Asset and Asset Amount for a Buy Asset
     * request are invalid an InvalidValueException is thrown.
     */
    @Test
    public void testbuyAssetBothAssetAmountCreditPriceValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.buyAsset(assetName, invalidInt, invalidInt);
        } );
    }
    /**
     * Testing that when an invalid asset Amount is attempted for a sell order an
     * InvalidValueException is thrown.
     */
    @Test
    public void testSellAssetInvalidAssetValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.sellAsset(assetName, invalidInt, creditPricePerAsset);
        } );
    }

    /**
     * Testing that when an invalid PricePer Asset is attempted for a sell Asset
     * request an invalid an InvalidValueException is thrown.
     */
    @Test
    public void testSellAssetInvalidCreditPriceValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.sellAsset(assetName, assetAmount, invalidInt);
        } );
    }
    /**
     * Testing that when both the PricePer Asset and Asset Amount for a sell Asset
     * request are invalid an InvalidValueException is thrown.
     */
    @Test
    public void testSellAssetBothAssetAmountCreditPriceValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.sellAsset(assetName, invalidInt, invalidInt);
        } );
    }

    /**
     * Test removing an order when there are no orders on the trading platform throws
     * a NullValueException.
     */
    @Test
    public void testRemoveOrderNullValueException() {
        assertThrows(NullValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.removeOrder(validID);
        } );
    }

    /**
     * Test removing an ID that will never be valid throws an InvalidValueException
     */
    @Test
    public void testRemoveOrderInvalidValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
            standardUser.buyAsset(assetName,assetAmount,creditPricePerAsset);
            standardUser.removeOrder(invalidInt);
        } );
    }

    @Test
    public void testRemoveOrderForSameOrderInvalidValueException() {
        assertThrows(InvalidValueException.class, () -> {
            StandardUser standardUser = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
            standardUser.buyAsset(assetName, assetAmount, creditPricePerAsset);
            standardUser.buyAsset(assetName2, assetAmount, creditPricePerAsset);
            Set<TPOrder> organisationsOrders = dataSource.getOrders(false);
            TPOrder order = organisationsOrders.iterator().next();
            int Id = order.getId();
            standardUser.removeOrder(Id);
            standardUser.removeOrder(Id);
        } );
    }


    @AfterEach
    public void resetDatabaseAftereach(){
        dataSource.deleteAll();
    }
    /**
     * Destroys the database connection that was made for testing.
     * @throws SQLException
     */
    @AfterAll
    static void resetDatabase(){
        dataSource.deleteAll();
    }



}
