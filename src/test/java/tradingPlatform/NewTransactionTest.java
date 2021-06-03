package test.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.NewTransaction;
import main.java.tradingPlatform.PlatformGlobals;
import main.java.tradingPlatform.TPOrder;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewTransactionTest {

    private static TradingPlatformDataSource dataSource;
    private static NewTransaction newTransaction;
    private static final String buyOrganisation = "Microsoft";
    private static final int standardOrganisationCredits = 40;
    private static final int assetAmount = 10;
    private static final int assetPriceLow = 2;
    private static final int assetPriceHigh = 3;
    private static final String standardAsset = "Computer";
    private static final String sellOrganisation = "Apple";
    private static TPOrder buyOrder;
    private static TPOrder sellOrder;
    /**
     * Initializes the database for testing.
     */
    @BeforeAll
    static void setupDatabase() {
        dataSource = new NetworkDataSource();
        newTransaction = new NewTransaction(dataSource);
        dataSource.addOrganisation(buyOrganisation, standardOrganisationCredits);
        dataSource.addOrganisation(sellOrganisation, standardOrganisationCredits);
        dataSource.addAsset(buyOrganisation, standardAsset, assetAmount);
        dataSource.addAsset(sellOrganisation, standardAsset, assetAmount);

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
        dataSource.addAsset(buyOrganisation, standardAsset, assetAmount);
        dataSource.addAsset(sellOrganisation, standardAsset, assetAmount);
        buyOrder.setCredits(assetPriceLow);
        sellOrder.setCredits(assetPriceLow);
    }

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

    @Test
    public void testBuyOrderCredits() {
        newTransaction.addBuyOrder(buyOrder);
        int finalCredits = standardOrganisationCredits - (assetAmount * assetPriceLow);
        assertEquals(finalCredits, dataSource.getCredits(buyOrganisation));
    }

    @Test
    public void testSellOrderAssets() {
        newTransaction.addSellOrder(sellOrder);
        int finalAmount = 0;
        assertEquals(finalAmount, dataSource.getAssetAmount(sellOrganisation, standardAsset));
    }

//    @Test
//    public void

    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}
