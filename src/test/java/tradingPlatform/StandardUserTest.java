package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

public class StandardUserTest {

    private static final String propsFile = "src/test/resources/StandardUserTestTest.props";
    private static JDBCTradingPlatformDataSource dataSource;
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

    /**
     * Creates a new database and instantiates two organisations with the second
     * containing an asset.
     * @throws IOException
     * @throws SQLException
     */
    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
        dataSource = new JDBCTradingPlatformDataSource(propsFile);
        dataSource.addOrganisation(aNewUserOrganisation, startingCredits);
        dataSource.addOrganisation(bNewUserOrganisation, startingCredits);
        dataSource.addAsset(bNewUserOrganisation, assetName, fullAssetAmount);
    }

    /**
     * Checks that the number of assets for the second organisation is 1.
     * @throws NullValueException
     */
    public void testGetAssets() throws NullValueException {
        int expectedNumber = 1;
        StandardUser standardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        Set<Asset> allAssets = standardTest.getAssets();
        assertEquals(expectedNumber, allAssets.size());
    }

    /**
     * Checks that the number of assets increases when a new asset is added.
     * @throws NullValueException
     * @throws SQLException
     */
    public void testGetAssetsAfterAddingAsset() throws NullValueException, SQLException {
        int expectedNumber = 2;
        StandardUser standardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        dataSource.addAsset(bNewUserOrganisation, assetName2, fullAssetAmount);
        Set<Asset> allAssets = standardTest.getAssets();
        assertEquals(expectedNumber, allAssets.size());
    }

    /**
     * Checks that when an asset is removed
     * @throws NullValueException
     * @throws SQLException
     */
    public void testGetAssetsAfterRemovingAsset() throws NullValueException, SQLException {
        int expectedNumber = 1;
        StandardUser standardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        dataSource.addAsset(bNewUserOrganisation, assetName2, fullAssetAmount);
        dataSource.deleteAsset(bNewUserOrganisation, assetName);
        Set<Asset> allAssets = standardTest.getAssets();
        assertEquals(expectedNumber, allAssets.size());
    }

    /**
     * Checking that when an buy and sell order match it will update the buying users credits.
     * @throws InvalidValueException
     * @throws NullValueException
     * @throws DuplicationException
     */
    public void testBuyAssetAutoTransaction() throws InvalidValueException, NullValueException, DuplicationException {
        StandardUser standardTest = new StandardUser(aNewUser, aNewUserOrganisation);
        StandardUser otherStandardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        int buyerCredits = standardTest.getCredits(); // Storing current credit amount.
        standardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        otherStandardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        int newBuyerCredits = standardTest.getCredits(); // Storing updated credit amount.
        assertTrue(newBuyerCredits == buyerCredits - (assetAmount * creditPricePerAsset));
    }

    /**
     * Checking that when an buy and sell order match it will update the selling users credits.
     * @throws InvalidValueException
     * @throws NullValueException
     * @throws DuplicationException
     */
    public void testSellAssetAutoTransaction() throws InvalidValueException, NullValueException, DuplicationException {
        StandardUser standardTest = new StandardUser(aNewUser, aNewUserOrganisation);
        StandardUser otherStandardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        int sellerCredits = otherStandardTest.getCredits(); // Storing current credit amount.
        standardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        otherStandardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        int newSellerCredits = otherStandardTest.getCredits(); // Storing updated credit amount.
        assertTrue(newSellerCredits == sellerCredits + (assetAmount * creditPricePerAsset));
    }

//TODO THIS NEEDS TO BE LOOKED INTO
//    public void testRemovingOrder() throws DuplicationException, NullValueException, InvalidValueException, SQLException {
//        StandardUser StandardTest = new StandardUser(bNewUser, bNewUserOrganisation);
//        StandardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
//        Set<TPOrder> organisationsOrders = dataSource.getOrders(bNewUserOrganisation, assetName, false);
////        TPOrder[] index = (TPOrder[]) organisationsOrders.toArray()[0];
////        TPOrder a = index[0][0];
//    }

    /**
     * Checks that the current amount of an asset on startup matches.
     * @throws InvalidValueException
     * @throws NullValueException
     * @throws DuplicationException
     */
    public void getAssetAmount() throws InvalidValueException, NullValueException, DuplicationException {
        StandardUser standardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        int currentAssetAmount = standardTest.getAssetAmount(assetName);
        assertTrue( currentAssetAmount == fullAssetAmount);
    }

    /**
     * Checks that the asset amount is changed when the organisations adds a sell request.
     * @throws InvalidValueException
     * @throws NullValueException
     * @throws DuplicationException
     */
    public void getAssetAmountAfterSellorder() throws InvalidValueException, NullValueException, DuplicationException {
        StandardUser standardTest = new StandardUser(bNewUser, bNewUserOrganisation);
        standardTest.sellAsset(assetName, assetAmount, creditPricePerAsset);
        int newAssetAmount = standardTest.getAssetAmount(assetName);
        assertEquals(fullAssetAmount-assetAmount, newAssetAmount);
    }

    /**
     * Ensures that the starting number of credits matches what is pulled from the database.
     * @throws InvalidValueException
     * @throws NullValueException
     */
    public void testgetCredits() throws InvalidValueException, NullValueException {
        StandardUser standardTest = new StandardUser(aNewUser, aNewUserOrganisation);
        int currentCredits = standardTest.getCredits();
        assertEquals(startingCredits, currentCredits);
    }

    /**
     * Checks that when a buy order is placed on the database that it updates the organisations credits.
     * @throws InvalidValueException
     * @throws NullValueException
     */
    public void testgetCreditsAfterBuyorder() throws InvalidValueException, NullValueException {
        StandardUser standardTest = new StandardUser(aNewUser, aNewUserOrganisation);
        standardTest.buyAsset(assetName, assetAmount, creditPricePerAsset);
        int currentCredits = standardTest.getCredits();
        assertEquals(startingCredits - (assetAmount * creditPricePerAsset), currentCredits);
    }

    /**
     * Destroys the database connection that was made for testing.
     * @throws SQLException
     */
    @AfterAll
    static void resetDatabase() throws SQLException {
        dataSource.deleteAll();
    }
}
