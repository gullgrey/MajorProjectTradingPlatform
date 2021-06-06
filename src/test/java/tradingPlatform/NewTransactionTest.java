package test.java.tradingPlatform;

import main.java.server.network.NewTransaction;
import main.java.common.PlatformGlobals;
import main.java.common.TPOrder;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.java.mockups.DataSourceMockup;
import test.java.mockups.DatabaseMockup;
import test.java.mockups.NewTransactionDataSourceMockup;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewTransactionTest {

    private static DataSourceMockup dataSource;
    private static NewTransaction newTransaction;
    private static final String buyOrganisation = "Microsoft";
    private static final int standardOrganisationCredits = 1000;
    private static final int startingAssets = 100;
    private static final int assetAmount = 10;
    private static final int assetPriceLow = 2;
    private static final String standardAsset = "Computer";
    private static final String sellOrganisation = "Apple";
    private static TPOrder buyOrder;
    private static TPOrder sellOrder;
    /**
     * Initializes the database for testing.
     */
    @BeforeAll
    static void setupDatabase() {
        dataSource = new NewTransactionDataSourceMockup(new DatabaseMockup());
        newTransaction = new NewTransaction(dataSource);
        dataSource.addOrganisation(buyOrganisation, standardOrganisationCredits);
        dataSource.addOrganisation(sellOrganisation, standardOrganisationCredits);
        dataSource.addAsset(buyOrganisation, standardAsset, startingAssets);
        dataSource.addAsset(sellOrganisation, standardAsset, startingAssets);

        buyOrder = new TPOrder();
        buyOrder.setOrganisation(buyOrganisation);
        buyOrder.setAsset(standardAsset);
        buyOrder.setAmount(assetAmount);
        buyOrder.setCredits(assetPriceLow);
        buyOrder.setType(PlatformGlobals.getBuyOrder());

        sellOrder = new TPOrder();
        sellOrder.setOrganisation(sellOrganisation);
        sellOrder.setAsset(standardAsset);
        sellOrder.setAmount(assetAmount);
        sellOrder.setCredits(assetPriceLow);
        sellOrder.setType(PlatformGlobals.getSellOrder());
    }

    @AfterEach
    public void resetAssets() {
        dataSource.deleteAll();
        dataSource.addOrganisation(buyOrganisation, standardOrganisationCredits);
        dataSource.addOrganisation(sellOrganisation, standardOrganisationCredits);
        dataSource.addAsset(buyOrganisation, standardAsset, startingAssets);
        dataSource.addAsset(sellOrganisation, standardAsset, startingAssets);
        buyOrder.setCredits(assetPriceLow);
        sellOrder.setCredits(assetPriceLow);
    }

    /**
     * Test adding a buy order sets the Organisation, Asset name, Amount and price per each when a Buy order
     * is added
     */
    @Test
    public void testAddBuyOrder() {
        newTransaction.addBuyOrder(buyOrder);
        Set<TPOrder> tempOrders = dataSource.getOrders(true);
        TPOrder tempOrder = tempOrders.iterator().next();
        assertTrue(tempOrder.getOrganisation().equals(buyOrganisation)
                && tempOrder.getAsset().equals(standardAsset)
                && tempOrder.getAmount() == assetAmount
                && tempOrder.getCredits() == assetPriceLow
        );
    }

    /**
     * Test adding a buy order sets the Organisation, Asset name, Amount and price per each when a sell order
     * is added
     */
    @Test
    public void testAddSellOrder() {
        newTransaction.addSellOrder(sellOrder);
        Set<TPOrder> tempOrders = dataSource.getOrders(false);
        TPOrder tempOrder = tempOrders.iterator().next();
        assertTrue(tempOrder.getOrganisation().equals(sellOrganisation)
                && tempOrder.getAsset().equals(standardAsset)
                && tempOrder.getAmount() == assetAmount
                && tempOrder.getCredits() == assetPriceLow
        );
    }

    /**
     * Test when adding a buy order the credits for organisation making the request drop by this amount.
     */
    @Test
    public void testBuyOrderCredits() {
        newTransaction.addBuyOrder(buyOrder);
        int finalCredits = standardOrganisationCredits - (assetAmount * assetPriceLow);
        assertEquals(finalCredits, dataSource.getCredits(buyOrganisation));
    }

    /**
     * Test when adding a sell order the Asset Amount for organisation making the request drop by the number
     * of assets they are selling.
     */
    @Test
    public void testSellOrderAssets() {
        newTransaction.addSellOrder(sellOrder);
        int finalAmount = startingAssets - assetAmount;
        assertEquals(finalAmount, dataSource.getAssetAmount(sellOrganisation, standardAsset));
    }

    /**
     * Test when a sell order matches a buy order it will automatically complete the transaction and update
     * the buying organisations assets amount.
     */
    @Test
    public void testTransactionGainedAsset() {
        newTransaction.addSellOrder(sellOrder);
        newTransaction.addBuyOrder(buyOrder);
        assertEquals(startingAssets + assetAmount,
                dataSource.getAssetAmount(buyOrganisation, standardAsset));
    }

    /**
     * Test when a sell order matches a buy order it will automatically complete the transaction and update
     * the selling organisations credits.
     */
    @Test
    public void testTransactionGainedCredits() {
        newTransaction.addSellOrder(sellOrder);
        newTransaction.addBuyOrder(buyOrder);
        int profit = assetAmount * assetPriceLow;
        assertEquals(standardOrganisationCredits + profit,
                dataSource.getCredits(sellOrganisation));
    }

    /**
     * Test when a buy order can be removed off the trading platform.
     */
    @Test
    public void testRemoveOrder() {
        newTransaction.addBuyOrder(buyOrder);
        Set<TPOrder> orders = dataSource.getOrders(true);
        TPOrder order = orders.iterator().next();
        int orderID = order.getId();
        newTransaction.removeOrder(orderID);
        Set<TPOrder> newOrders = dataSource.getOrders(true);
        assertTrue(newOrders.isEmpty());
    }


    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}
