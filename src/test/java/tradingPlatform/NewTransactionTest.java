package test.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.NewTransaction;
import main.java.tradingPlatform.TPOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

public class NewTransactionTest {

    private static TradingPlatformDataSource dataSource;
    private static NewTransaction newTransaction;
    private static final String buyOrganisation = "Microsoft";
    private static final int standardOrganisationCredits = 40;
    private static final int assetAmount = 10;
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
    }

    @AfterEach
    static void resetAssets() {
        dataSource.deleteAsset(buyOrganisation, standardAsset);
        dataSource.deleteAsset(sellOrganisation, standardAsset);
        dataSource.addAsset(buyOrganisation, standardAsset, assetAmount);
        dataSource.addAsset(sellOrganisation, standardAsset, assetAmount);
    }

    @Test
    public void testAddOrder() {
//        newTransaction.addBuyOrder()
    }

    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}
