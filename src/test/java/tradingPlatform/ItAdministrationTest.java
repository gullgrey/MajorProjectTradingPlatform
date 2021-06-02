package test.java.tradingPlatform;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.*;
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


public class ItAdministrationTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static JDBCTradingPlatformDataSource dataSource;
    private static final String adminUserName = "Admin";
    private static final int adminUserIncorrect = 1234;
    private static final String userNameCorrect = "User1";
    private static final int userNameIncorrect = 1234;
    private static final String correctPassword = "12345";
    private static ItAdministration adminAccount;
    private static final String standardOrganisation = "Microsoft";
    private static final int standardOrganisationCredits = 40;
    private static final String standardAsset = "Computer";
    private static final String organisation = "Apple";

    /**
     * Initializes the database for testing.
     *
     * @throws IOException
     * @throws SQLException
     */
    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
        dataSource = new JDBCTradingPlatformDataSource(propsFile);
    }

    //Todo
    //add ADMIN to organisations fails
    //delete ADMIN from organisations fails.
    //

    /**
     * Setup an admin account and add an initial organisation to implement tests.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     */
    @BeforeAll
    static void setupAdmin() throws DuplicationException, NullValueException, InvalidValueException, UnknownDatabaseException {
        adminAccount = new ItAdministration(dataSource, adminUserName);
        adminAccount.addOrganisation(standardOrganisation, standardOrganisationCredits);
        adminAccount.increaseCredits(standardOrganisation, standardOrganisationCredits);
    }

    /**
     * Test to add a new standard user to the system.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     */
    @Test
    public void testAddStandardUser() throws DuplicationException, NullValueException, InvalidValueException, SQLException {
        adminAccount.addStandardUser(userNameCorrect,correctPassword, organisation);
        Set<UserOrganisation> userCheck = dataSource.getUsers();
        assertTrue(userCheck.contains(userNameCorrect));
    }

    /**
     * Test to add a new IT admin user to the system.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     * @throws WrongCredentialException
     */
    @Test
    public void testAddItUser() throws DuplicationException, InvalidValueException, SQLException, WrongCredentialException {
        adminAccount.addItUser(userNameCorrect,correctPassword);
        Set<UserOrganisation> userCheck = dataSource.getUsers();
        assertTrue(userCheck.contains(userNameCorrect));
    }

    /**
     * Test to remove a user from the system.
     *
     * @throws NullValueException specified field does not exist in the database.
     * @throws SQLException
     * @throws UnknownDatabaseException
     * @throws DuplicationException the specified field already exists.
     * @throws InvalidValueException value entered is incorrect type.
     */
    @Test
    public void testRemoveUser() throws NullValueException, SQLException, UnknownDatabaseException, DuplicationException, InvalidValueException {
        adminAccount.addStandardUser(userNameCorrect,correctPassword, organisation);
        adminAccount.removeUser(userNameCorrect);
        Set<UserOrganisation> userCheck = dataSource.getUsers();
        assertFalse(userCheck.contains(userNameCorrect));
    }

    /**
     * Test to add a new organisation to the system.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     */
    @Test
    public void testAddOrganisation() throws DuplicationException, NullValueException, InvalidValueException, SQLException, UnknownDatabaseException {
        adminAccount.addOrganisation(organisation, standardOrganisationCredits);
        Set<Organisation> organisationCheck = dataSource.getOrganisations();
        assertFalse(organisationCheck.contains(userNameCorrect));
    }

    /**
     * Test to remove an organisation if it exists.
     *
     * @throws NullValueException specified field does not exist in the database.
     * @throws SQLException
     * @throws UnknownDatabaseException
     */
    @Test
    public void testRemoveOrganisation() throws  NullValueException, SQLException, UnknownDatabaseException {
        adminAccount.removeOrganisation(standardOrganisation);
        Set<Organisation> organisationCheck = dataSource.getOrganisations();
        assertFalse(organisationCheck.contains(userNameCorrect));
    }

    /**
     * Test to increase the credits of an existing organisation.
     *
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     */
    @Test
    public void testIncreaseCredits() throws NullValueException, InvalidValueException, SQLException {
        int increasedAmount = 100;
        adminAccount.increaseCredits(standardOrganisation, increasedAmount);
        int newAmount = dataSource.getCredits(standardOrganisation);
        assertEquals(standardOrganisationCredits + increasedAmount, newAmount);
    }

    /**
     * Test to reduce the credits of an existing organisation.
     *
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     */
    @Test
    public void testReduceCredits() throws NullValueException, InvalidValueException, SQLException {
        int decreasedAmount = 20;
        adminAccount.reduceCredits(standardOrganisation, decreasedAmount);
        int newAmount = dataSource.getCredits(standardOrganisation);
        assertEquals(standardOrganisationCredits + decreasedAmount, newAmount);
    }

    /**
     * Test to add an asset to an existing organisation.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     */
    @Test
    public void testAddAsset() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        String assetName = "NewAsset";
        int size = 1;
        adminAccount.addAsset(organisation, assetName, 10);
        Set<Asset> allAssets = dataSource.getAssets();
        assertEquals(allAssets.size(), size);
    }

    /**
     * Test to add increase an available asset amount by a specific amount.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     * @throws SQLException
     */
    @Test
    public void testAddAssetAmount() throws DuplicationException, NullValueException, InvalidValueException, SQLException {
        //TODO
    }

    @Test
    public void testRemoveAsset() throws DuplicationException, NullValueException, InvalidValueException, SQLException {
        //TODO
    }
}
