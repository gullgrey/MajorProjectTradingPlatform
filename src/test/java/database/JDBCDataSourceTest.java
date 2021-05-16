package test.java.database;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.TPOrder;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.namespace.QName;
import java.sql.SQLException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JDBCDataSourceTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static final JDBCTradingPlatformDataSource dataSource = new JDBCTradingPlatformDataSource(propsFile);
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
    private static final String asset2 = "AppleMacComputer";
    private static final int asset2Amount = 10;
    private static final int  addOrderAssetAmount = 5;
    private static final int addOrderCreditAmount = 40;


    /**
     * Prepare the database
     */
    @BeforeAll
    static void setupDatabase() {
        dataSource.prepareDatabase();
    }

    /**
     * Add an initial organisation
     */
    @Test
    public void testAddOrganisationData() {
        int creditAmount = 200;
        dataSource.addOrganisation(organisationApple, creditAmount);
        assertTrue(dataSource.getOrganisations().contains(organisationApple));
        assertEquals(creditAmount, dataSource.getCredits(organisationApple));
    }

    /**
     *Create a standard user
     */
    @Test
    public void testAddNewUser() {
        dataSource.addUser(aNewUser, aNewUserInitialPassword, STANDARD, organisationApple);
        assertTrue(dataSource.getUsers(organisationApple).contains(aNewUser));
    }

    /**
     * Update the aNewUsers password
     */
    @Test
    public void testUpdatePassword() {
        dataSource.updatePassword(aNewUser, aNewUserSecondPassword);
        assertEquals(aNewUserSecondPassword, dataSource.getUserPassword(aNewUser));
    }

    /**
     * Delete aNewUser
     */
    @Test
    public void testDeleteUser() {
        dataSource.deleteUser(aNewUser);
        assertFalse(dataSource.getUsers(organisationApple).contains(aNewUser));
    }

    /**
     * Add an asset to the organisation
     */
    @Test
    public void testAddAssetData() {
        dataSource.addAsset(organisationApple,asset1, asset1Amount);
        assertTrue(dataSource.getAssets(organisationApple).contains(asset1));
        assertEquals(dataSource.getAssetAmount(organisationApple, asset1), asset1Amount);
    }

    /**
     * Increase an assets amount
     */
    @Test
    public void testIncrementUpdateAssetAmount() {
        dataSource.updateAssetAmount(organisationApple, asset1, asset1AmountChange);
        assertEquals((asset1Amount + asset1AmountChange), dataSource.getAssetAmount(organisationApple, asset1));
    }

    /**
     * Decrease an assets amount
     */
    @Test
    public void testDecrementUpdateAssetAmount() {
        dataSource.updateAssetAmount(organisationApple, asset1, asset1AmountChange);
        assertEquals((asset1Amount - asset1AmountChange), dataSource.getAssetAmount(organisationApple, asset1));
    }

    /**
     * Remove an asset
     */
    @Test
    public void testRemoveAsset() {
        dataSource.deleteAsset(organisationApple, asset1);
        assertFalse(dataSource.getAssets(organisationApple).contains(asset1));
    }

    /**
     * Place a buy order
     */
    @Test
    public void testBuyAddOrder() {
        dataSource.addOrder(organisationApple, asset1, addOrderAssetAmount,addOrderCreditAmount,true);
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
    public void testSellAddOrder() {
        dataSource.addOrder(organisationApple,asset1, addOrderAssetAmount,addOrderCreditAmount,false);
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
    public void testDeleteOrder() {

        Set<TPOrder> tempOrders = dataSource.getOrders(organisationApple,asset1, true);
        TPOrder tempOrder = tempOrders.iterator().next();
        int idx = tempOrder.getId();
        dataSource.deleteOrder(idx);
        assertThrows(SQLException.class, () -> dataSource.getOrders(organisationApple,asset1, true));
    }

    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}


