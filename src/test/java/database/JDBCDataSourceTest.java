package test.java.database;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.Asset;
import main.java.tradingPlatform.TPOrder;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.sql.SQLException;
import java.util.DuplicateFormatFlagsException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JDBCDataSourceTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static JDBCTradingPlatformDataSource dataSource;
    private static final String aNewUser = "User666";
    private static final String aNewUserInitialPassword = "Password";
    private static final String aNewUserSecondPassword = "Password123";
    private static final String ADMIN = "ADMIN";
    private static final String STANDARD = "STANDARD";
    private static final String organisationApple = "Apple";
    private static final int organisationAppleCredits = 100;
    private static final String asset1 = "AppleWatch5.0";
    private static final int asset1Amount = 2;
    private static final int asset1AmountChange = 5;
    private static final int asset1AmountChange2 = -5;
    private static final String asset2 = "AppleMacComputer";
    private static final int asset2Amount = 10;
    private static final int  addOrderAssetAmount = 5;
    private static final int addOrderCreditAmount = 40;
    private static final Asset compareAsset = new Asset(organisationApple, asset1, asset1Amount);


    /**
     * Prepare the database
     */
    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
        dataSource = new JDBCTradingPlatformDataSource(propsFile);
    }

    /**
     * Add an initial organisation
     */
    @Test
    public void testAddOrganisationData() throws SQLException {
        dataSource.addOrganisation(organisationApple, organisationAppleCredits);
        assertTrue(dataSource.getOrganisations().contains(organisationApple));
        assertEquals(organisationAppleCredits, dataSource.getCredits(organisationApple));
    }

    /**
     *Create a standard user
     */
    @Test
    public void testAddNewUser() throws SQLException {
        dataSource.addUser(aNewUser, aNewUserInitialPassword, STANDARD, organisationApple);
        assertTrue(dataSource.getUsers().contains(aNewUser));
    }

    /**
     * Update the aNewUsers password
     */
    @Test
    public void testUpdatePassword() throws SQLException {
        dataSource.updatePassword(aNewUser, aNewUserSecondPassword);
        assertEquals(aNewUserSecondPassword, dataSource.getUserPassword(aNewUser));
    }

    /**
     * Delete aNewUser
     */
    @Test
    public void testDeleteUser() throws SQLException {
        dataSource.deleteUser(aNewUser);
        assertFalse(dataSource.getUsers().contains(aNewUser));
    }

    /**
     * Add an asset to the organisation
     */
    @Test
    public void testAddAssetData() throws SQLException {
        dataSource.addAsset(organisationApple,asset1, asset1Amount);

        assertTrue(dataSource.getAssets(organisationApple).contains(compareAsset));
        assertEquals(dataSource.getAssetAmount(organisationApple, asset1), asset1Amount);
        //TODO seperate these two into seperate test
    }

    /**
     * Increase an assets amount
     */
    @Test
    public void testIncrementUpdateAssetAmount() throws SQLException {
        dataSource.updateAssetAmount(organisationApple, asset1, asset1AmountChange);
        assertEquals((asset1Amount + asset1AmountChange), dataSource.getAssetAmount(organisationApple, asset1));
    }

    /**
     * Decrease an assets amount
     */
    @Test
    public void testDecrementUpdateAssetAmount() throws SQLException {
        dataSource.updateAssetAmount(organisationApple, asset1, asset1AmountChange2);
        assertEquals((asset1Amount), dataSource.getAssetAmount(organisationApple, asset1));
    }

    /**
     * Remove an asset
     */
    @Test
    public void testRemoveAsset() throws SQLException {
        dataSource.deleteAsset(organisationApple, asset1);
        Asset assetObject = new Asset(organisationApple, asset1, asset1Amount);
        assertFalse(dataSource.getAssets(organisationApple).contains(assetObject));
    }

    /**
     * Place a buy order
     */
    @Test
    public void testBuyAddOrder() throws SQLException {
        dataSource.addOrder(organisationApple, asset1, addOrderAssetAmount,
                addOrderCreditAmount,true);
        Set<TPOrder> tempOrders = dataSource.getOrders(organisationApple,asset1, true);
        TPOrder tempOrder = tempOrders.iterator().next();
        int idx = tempOrder.getId();
        assertTrue(tempOrder.getOrganisation().equals(organisationApple) && tempOrder.getAsset().equals(asset1)
                && tempOrder.getAmount() == addOrderAssetAmount && tempOrder.getCredits() == addOrderCreditAmount);
    }

    /**
     * Place a sale order
     */
    @Test
    public void testSellAddOrder() throws SQLException {
        dataSource.addOrder(organisationApple,asset1, addOrderAssetAmount,
                addOrderCreditAmount,false);
        Set<TPOrder> tempOrders = dataSource.getOrders(organisationApple,asset1, false);
        TPOrder tempOrder = tempOrders.iterator().next();
        int idx = tempOrder.getId();
        assertTrue(tempOrder.getOrganisation().equals(organisationApple) && tempOrder.getAsset().equals(asset1)
                && tempOrder.getAmount() == addOrderAssetAmount && tempOrder.getCredits() == addOrderCreditAmount);
    }

    /**
     * Delete an order
     */
    @Test
    public void testDeleteOrder() throws SQLException {
        dataSource.addOrder(organisationApple, asset1, addOrderAssetAmount,
                addOrderCreditAmount,true);
//        Set<TPOrder> tempOrders = dataSource.getOrders(organisationApple,asset1, true);
//        TPOrder tempOrder = tempOrders.iterator().next();
        int idx = 1;
        dataSource.deleteOrder(idx);
        assertTrue(dataSource.getOrders(organisationApple,asset1, true).isEmpty());
    }

    //TODO test addTransaction and getOrderHistory.

    @AfterAll
    static void resetDatabase() throws SQLException {
        dataSource.deleteAll();
    }
}


