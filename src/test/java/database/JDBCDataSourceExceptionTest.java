package test.java.database;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.TPOrder;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JDBCDataSourceExceptionTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static JDBCTradingPlatformDataSource dataSource;
    private static final String aNewUser = "User666";
    private static final String aNewUserInitialPassword = "Password";
    private static final String STANDARD = "STANDARD";
    private static final String organisationApple = "Apple";
    private static final String asset1 = "AppleWatch5.0";
    private static final int asset1Amount = 2;
    private static final int addOrderCreditAmount = 40;
    private static final String falseVariable = "DoesNotExist";

    /**
     * Prepare the database
     */
    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
         dataSource = new JDBCTradingPlatformDataSource(propsFile);
    }

    /**
     * Testing if the program throws an exception if the same organisation is added
     */
    @Test
    public void testAddDuplicateOrganisation() {
        assertThrows(SQLException.class , () -> {
            int creditAmount = 200;
            dataSource.addOrganisation(organisationApple, creditAmount);
            dataSource.addOrganisation(organisationApple, creditAmount);
        });
    }

    /**
     * Testing if the program throws an exception if the same user is added
     */
    @Test
    public void testAddNewUserThrows() {
        assertThrows(SQLException.class, () -> {
            dataSource.addUser(aNewUser, aNewUserInitialPassword, STANDARD, organisationApple);
            dataSource.addUser(aNewUser, aNewUserInitialPassword, STANDARD, organisationApple);
        });
    }

    /**
     * Testing if the program throws an exception if the user doesn't exist when trying to update
     * their password
     */
    @Test
    public void testUpdatePasswordThrows() {
        assertThrows(SQLException.class, () -> {
            dataSource.updatePassword(falseVariable, aNewUserInitialPassword);
        });
    }

    /**
     * Testing if the program throws an exception if the user doesn't exist when trying to delete them
     */
    @Test
    public void testDeleteUserThrows() {
        assertThrows(SQLException.class, () -> {
            dataSource.deleteUser(falseVariable);
        });
    }

    /**
     * Testing if the program throws an exception if the user tries to add an asset to
     * an organisation that doesn't exist
     */
    @Test
    public void testAddAssetThrowsOrganisation() {
        assertThrows(SQLException.class, () -> {
            dataSource.addAsset(falseVariable, asset1 , asset1Amount);
        });
    }

    /**
     * Testing if the program throws an exception if the user tries to add the same asset
     */
    @Test
    public void testAddAssetThrowsAsset() {
        assertThrows(SQLException.class, () -> {
            dataSource.addAsset(organisationApple, asset1 , asset1Amount);
            dataSource.addAsset(organisationApple, asset1 , asset1Amount);
        });
    }

    /**
     * Testing if the program throws an exception if the user if the organisation doesn't exist
     */
    @Test
    public void testUpdateAssetAmountThrowsOrganisation() {
        assertThrows(SQLException.class, () -> {
            dataSource.updateAssetAmount(falseVariable, asset1 , asset1Amount);
        });
    }

    /**
     * Testing if the program throws an exception if the user if the organisation doesn't exist
     */
    @Test
    public void testUpdateAssetAmountThrowsAsset() {
        assertThrows(SQLException.class, () -> {
            dataSource.updateAssetAmount(organisationApple, falseVariable , asset1Amount);
        });
    }

    /**
     * Testing if the program throws an exception if the organisation doesn't exist already
     */
    @Test
    public void testRemoveAssetThrowsOrganisation() {
        assertThrows(SQLException.class, () -> {
            dataSource.deleteAsset(falseVariable, asset1 );
        });
    }

    /**
     * Testing if the program throws an exception if the asset doesn't exist already
     */
    @Test
    public void testRemoveAssetThrowsAsset() {
        assertThrows(SQLException.class, () -> {
            dataSource.deleteAsset(organisationApple, falseVariable );
        });
    }

    /**
     * Testing if the program throws an exception if the order exists already
     */
    @Test
    public void testBuyOrderThrowsDuplicate() {
        assertThrows(SQLException.class, () -> {
            dataSource.addOrder(organisationApple, asset1, asset1Amount, addOrderCreditAmount,true);
            dataSource.addOrder(organisationApple, asset1, asset1Amount, addOrderCreditAmount,true);
        });
    }

    /**
     * Testing if the program throws an exception if the organisation doesn't exist
     */
    @Test
    public void testBuyOrderThrowsOrganisation() {
        assertThrows(SQLException.class, () -> {
            dataSource.addOrder(falseVariable, asset1, asset1Amount, addOrderCreditAmount,true);
        });
    }

    /**
     * Testing if the program throws an exception if the asset doesn't exist
     */
    @Test
    public void testBuyOrderThrowsAsset() {
        assertThrows(SQLException.class, () -> {
            dataSource.addOrder(organisationApple, falseVariable, asset1Amount, addOrderCreditAmount,true);
        });
    }

    /**
     * Testing if the program throws an exception if the organisation doesn't exist
     */
    @Test
    public void testSellOrderThrowsOrganisation() {
        assertThrows(SQLException.class, () -> {
            dataSource.addOrder(falseVariable, asset1, asset1Amount, addOrderCreditAmount,false);
        });
    }

    /**
     * Testing if the program throws an exception if the asset doesn't exist
     */
    @Test
    public void testSellOrderThrowsAsset() {
        assertThrows(SQLException.class, () -> {
            dataSource.addOrder(organisationApple, falseVariable, asset1Amount, addOrderCreditAmount,false);
        });
    }

    /**
     * Testing if the program throws an exception if the order doesn't exist
     */
    @Test
    public void testDeleteOrderThrowsOrganisation() {
        assertThrows(SQLException.class, () -> {
            dataSource.deleteOrder(999123912);
        });
    }

    @AfterAll
    static void resetDatabase() throws SQLException {
        dataSource.deleteAll();
    }

}
