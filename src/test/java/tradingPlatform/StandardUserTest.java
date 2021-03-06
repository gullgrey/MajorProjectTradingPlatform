package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;

import main.java.common.Asset;
import main.java.common.TPOrder;
import main.java.client.tradingPlatform.*;
import org.junit.jupiter.api.*;
import test.java.mockups.DataSourceMockup;
import test.java.mockups.DatabaseMockup;

import java.util.Set;

/**
 * This class is responsible for testing the logic in the StandardUser class.
 */
public class StandardUserTest {

    private static DataSourceMockup dataSource;
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

    /**
     * Creates a new database and instantiates two organisations with the second
     * containing an asset.
     */
    @BeforeEach
    public void setupDatabase() {
        dataSource = new DataSourceMockup(new DatabaseMockup());
        dataSource.addOrganisation(aNewUserOrganisation, startingCredits);
        dataSource.addOrganisation(bNewUserOrganisation, startingCredits);
        dataSource.addAsset(bNewUserOrganisation, assetName, fullAssetAmount);
    }

    /**
     * Checks that the number of assets for the second organisation is 1.
     * @throws UnknownDatabaseException specified field does not exist in the database.
     */
    @Test
    public void testGetAssets() throws UnknownDatabaseException {
        int expectedNumber = 1;
        StandardUser standardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        Set<Asset> allAssets = standardTest.getAssets();
        assertEquals(expectedNumber, allAssets.size());
    }

    /**
     * Checks that the number of assets increases when a new asset is added.
     * @throws UnknownDatabaseException specified field does not exist in the database.
     */
    @Test
    public void testGetAssetsAfterAddingAsset() throws UnknownDatabaseException {
        int expectedNumber = 2;
        StandardUser standardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        dataSource.addAsset(bNewUserOrganisation, assetName2, fullAssetAmount);
        Set<Asset> allAssets = standardTest.getAssets();
        assertEquals(expectedNumber, allAssets.size());
    }

    /**
     * Checks that when an asset is removed the number of assets is
     * @throws UnknownDatabaseException issue with the database.
     */
    @Test
    public void testGetAssetsAfterRemovingAsset() throws UnknownDatabaseException {
        int expectedNumber = 1;
        StandardUser standardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        dataSource.addAsset(bNewUserOrganisation, assetName2, fullAssetAmount);
        dataSource.deleteAsset(bNewUserOrganisation, assetName);
        Set<Asset> allAssets = standardTest.getAssets();
        assertEquals(expectedNumber, allAssets.size());
    }

    /**
     * Checking that when a buy and sell order match it will update the buying users credits.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws UnknownDatabaseException issue with the database.
     */
    @Test
    public void testBuyAssetAutoTransaction() throws InvalidValueException, UnknownDatabaseException {
        StandardUser standardTest = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
        StandardUser otherStandardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        int buyerCredits = standardTest.getCredits(); // Storing current credit amount.
        standardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        otherStandardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        int newBuyerCredits = standardTest.getCredits(); // Storing updated credit amount.
        assertEquals(newBuyerCredits, buyerCredits - (assetAmount * creditPricePerAsset));
    }

    /**
     * Checking that when a buy and sell order match it will update the selling users credits.
     */
    @Test
    public void testSellAssetAutoTransaction() throws InvalidValueException, UnknownDatabaseException {
        StandardUser standardTest = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
        StandardUser otherStandardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        int sellerCredits = otherStandardTest.getCredits(); // Storing current credit amount.
        standardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        otherStandardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        int newSellerCredits = otherStandardTest.getCredits(); // Storing updated credit amount.
        assertEquals(newSellerCredits, sellerCredits + (assetAmount * creditPricePerAsset));
    }

    /**
     * Test removing a known ID of a Sell order is removed from the system.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws UnknownDatabaseException value entered is incorrect type.
     */
    @Test
    public void testRemovingSellOrder() throws  NullValueException, InvalidValueException, UnknownDatabaseException {
        StandardUser StandardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        StandardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        Set<TPOrder> organisationsOrders = dataSource.getOrders(false);
        TPOrder order = organisationsOrders.iterator().next();
        int Id = order.getId();
        StandardTest.removeOrder(Id);
        assertEquals(0, dataSource.getOrders(false).size());
    }

    /**
     * Test that entering in the known ID of a Buy order on the system is removed.
     */
    @Test
    public void testRemovingBuyOrder() throws  NullValueException, InvalidValueException, UnknownDatabaseException {
        StandardUser StandardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        StandardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        Set<TPOrder> organisationsOrders = dataSource.getOrders(true);
        TPOrder order = organisationsOrders.iterator().next();
        int Id = order.getId();
        StandardTest.removeOrder(Id);
        assertEquals(0, dataSource.getOrders(false).size());
    }

    /**
     * Checks that the current amount of an asset on startup matches.
     */
    @Test
    public void getAssetAmount() throws NullValueException, UnknownDatabaseException {
        StandardUser standardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        int currentAssetAmount = standardTest.getAssetAmount(assetName);
        assertEquals(fullAssetAmount, currentAssetAmount);
    }

    /**
     * Checks that the asset amount is changed when the organisations adds a sell request.
     */
    @Test
    public void getAssetAmountAfterSellorder() throws InvalidValueException, UnknownDatabaseException {
        StandardUser standardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
        standardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        int newAssetAmount = standardTest.getAssetAmount(assetName);
        assertEquals(fullAssetAmount-assetAmount, newAssetAmount);
    }

    /**
     * Ensures that the starting number of credits matches what is pulled from the database.
     */
    @Test
    public void testgetCredits() throws UnknownDatabaseException {
        StandardUser standardTest = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
        int currentCredits = standardTest.getCredits();
        assertEquals(startingCredits, currentCredits);
    }

    /**
     * Checks that when a buy order is placed on the database that it updates the organisations credits.
     */
    @Test
    public void testgetCreditsAfterBuyorder() throws InvalidValueException, UnknownDatabaseException {
        StandardUser standardTest = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
        standardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        int currentCredits = standardTest.getCredits();
        assertEquals(startingCredits - (assetAmount * creditPricePerAsset), currentCredits);
    }

    /**
     * Destroys the database connection after every test.
     */
    @AfterEach
    public void resetDatabaseAftereach(){
        dataSource.deleteAll();
    }
    /**
     * Destroys the database connection that was made for testing.
     */
    @AfterAll
    static void resetDatabase(){
        dataSource.deleteAll();
    }
}
