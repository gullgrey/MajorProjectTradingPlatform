package test.java.database;

import main.java.database.JDBCTradingPlatformDataSource;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.namespace.QName;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JDBCDataSourceTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static final JDBCTradingPlatformDataSource dataSource = new JDBCTradingPlatformDataSource(propsFile);
    private static String organisationName;
    private static int amount;
    private static String aNewUser = "User666";
    private static String aNewUserInitialPassword = "Password";
    private static String aNewUserSecondPassword = "Password123";
    private static final String ADMIN = "ADMIN";
    private static final String STANDARD = "STANDARD";
    private static final String organisationApple = "Apple";
    private static final String asset1 = "AppleWatch5.0";

    //Create an organisation --
    //Create a user --
    //Update their password --
    //Delete a user
    //Create an admin
    //Delete an admin
    //Add asset to organisation
    //Update an asset amount
    //Remove an asset from an organisation
    //Add an order
    //delete an order

    /**
     Prepare the database
     */
    @BeforeAll
    static void setupDatabase() {
        dataSource.prepareDatabase();
    }

    /**
     Add an initial organisation
     */
    @Test
    public void testAddOrganisationData() {
        int creditAmount = 200;
        dataSource.addOrganisation(organisationApple, creditAmount);
        assertTrue(dataSource.getOrganisations().contains(organisationApple));
        assertEquals(creditAmount, dataSource.getCredits(organisationApple));
    }

    /**
     Create a standard user
     */
    @Test
    public void testAddNewUser() {
        dataSource.addUser(aNewUser, aNewUserInitialPassword, STANDARD, organisationApple);
        assertTrue(dataSource.getUsers(organisationApple).contains(aNewUser));
    }

    /**
     Update the aNewUsers password
     */
    @Test
    public void testUpdatePassword() {
        dataSource.updatePassword(aNewUser, aNewUserSecondPassword);
        assertEquals(aNewUserSecondPassword, dataSource.getUserPassword(aNewUser));
    }

    /**
     Delete aNewUser
     */
    @Test
    public void testDeleteUser() {

    }

    /**
     Add an asset to the organisation
     */
    @Test
    public void testAddAssetData() {
        int assetAmount = 5;
        dataSource.addAsset(organisationApple, asset1, assetAmount);
        assertTrue(dataSource.getAssets(organisationApple).contains(asset1));
        assertEquals(assetAmount, dataSource.getAssetAmount(organisationApple, asset1));
    }



    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}


