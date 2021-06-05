package test.java.tradingPlatform;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.*;
import test.java.mockups.DataSourceMockup;


public class ItAdministrationTest {

    private static final String propsFile = "src/test/resources/maria.props";
    //private static TradingPlatformDataSource dataSource;
    private static final String adminUserName = "Admin";
    private static final int adminUserIncorrect = 1234;
    private static final String userNameCorrect = "User1";
    private static final int userNameIncorrect = 1234;
    private static final String correctPassword = "12345";
    private static ItAdministration adminAccount;
    private static final String standardOrganisation = "Microsoft";
    private static final String standardOrganisation2 = "Samsung";
    private static final int standardOrganisationCredits = 40;
    private static final String standardAsset = "Computer";
    private static final String aNewAsset = "ThisAsset";
    private static final int aNewAssetAmount = 100;
    private static final int aNewAssetAmountReduce = -30;
    private static final String organisation = "Apple";
    private static final ConfigReader fileReader = new ConfigReader();
    private static DataSourceMockup dataSource;
    private static final int usersExpected1 = 1;
    private static final int usersExpected2 = 2;


    /**
     * Initializes the database for testing.
     *
     * @throws DuplicationException
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    @BeforeAll
      static void setupDatabase() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        ///////dataSource = new NetworkDataSource();
        dataSource = new DataSourceMockup();
        adminAccount = new ItAdministration(dataSource, adminUserName);
        //dataSource.getUsers();
        //dataSource.getAssets();
        //dataSource.getOrganisations();
        //Set<TPOrder> order1 = dataSource.getOrders(true);
        //Set<TPOrder> order2 = dataSource.getOrders(false);
        //dataSource.getOrderHistory();
        adminAccount.addOrganisation(standardOrganisation, standardOrganisationCredits);
    }

    /**
     * Clears the database after each test to prevent discrepancies.
     *
     * @throws DuplicationException
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    @AfterEach
    public void clearDatabase() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        dataSource.deleteAll();
        adminAccount.addOrganisation(standardOrganisation, standardOrganisationCredits);

    }

    /**
     * Clears the database after each test to prevent discrepancies.
     */
    @AfterAll
    static void clearDatabase2()  {
        dataSource.deleteAll();
    }

    /**
     * Test to add a new standard user to the system.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException value entered is incorrect type.
     */
    @Test
    public void testAddStandardUser() throws DuplicationException, NullValueException,  UnknownDatabaseException {
        adminAccount.addStandardUser(userNameCorrect,correctPassword, standardOrganisation);
        Set<UserOrganisation> userCheck = dataSource.getUsers();
        assertEquals(usersExpected2, userCheck.size());
    }

    /**
     * Test to confirm configuration file formatting.
     * @throws IOException
     */
    @Test
    public void testReadFileClient() throws IOException {
        Set<String> newSet = fileReader.readClientFile();
        String array[] = new String[5];
        array = newSet.toArray(array);
    }

    /**
     * Test to confirm configuration file formatting.
     * @throws IOException
     */
    @Test
    public void testReadFileServer() throws IOException {
        Set<String> newSet = fileReader.readServerFile();
        String array[] = new String[5];
        array = newSet.toArray(array);
    }


    /**
     * Test to add a new IT admin user to the system.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws UnknownDatabaseException value entered is incorrect type.
     */
    @Test
    public void testAddItUser() throws DuplicationException, UnknownDatabaseException {
        adminAccount.addItUser(userNameCorrect,correctPassword);
        Set<UserOrganisation> userCheck = dataSource.getUsers();
        assertEquals(userCheck.size(), usersExpected2);
    }

    /**
     * Test to remove a user from the system.
     *
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException
     * @throws DuplicationException the specified field already exists.
     */
    @Test
    public void testRemoveUser() throws NullValueException, UnknownDatabaseException, DuplicationException {
        adminAccount.addStandardUser(userNameCorrect, "password", standardOrganisation);
        adminAccount.removeUser(userNameCorrect);
        Set<UserOrganisation> userCheck = dataSource.getUsers();
        assertEquals(userCheck.size(), usersExpected1);
    }

    /**
     * Test to add a new organisation to the system.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws UnknownDatabaseException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     */
    @Test
    public void testAddOrganisation() throws DuplicationException, InvalidValueException,  UnknownDatabaseException {
        adminAccount.addOrganisation(organisation,standardOrganisationCredits);
        Set<Organisation> organisationCheck = dataSource.getOrganisations();
        assertEquals(3, organisationCheck.size());
    }

    /**
     * Test to remove an organisation if it exists.
     *
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    @Test
    public void testRemoveOrganisation() throws NullValueException, UnknownDatabaseException, InvalidValueException {
        adminAccount.removeOrganisation(standardOrganisation);
        Set<Organisation> organisationCheck = dataSource.getOrganisations();
        assertEquals(1, organisationCheck.size());
    }

    /**
     * Test to increase the credits of an existing organisation.
     *
     * @throws InvalidValueException value entered is incorrect type.
     * @throws UnknownDatabaseException
     */
    @Test
    public void testIncreaseCredits() throws InvalidValueException, UnknownDatabaseException {
        int increasedAmount = 100;
        adminAccount.updateCreditAmount(standardOrganisation, increasedAmount);
        int newAmount = dataSource.getCredits(standardOrganisation);
        assertEquals(standardOrganisationCredits + increasedAmount, newAmount);
    }

    /**
     * Test to reduce the credits of an existing organisation.
     *
     * @throws InvalidValueException value entered is incorrect type.
     * @throws UnknownDatabaseException
     */
    @Test
    public void testReduceCredits() throws InvalidValueException, UnknownDatabaseException {
        int decreasedAmount = -20;
        adminAccount.updateCreditAmount(standardOrganisation, decreasedAmount);
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
    public void testAddAsset() throws DuplicationException, UnknownDatabaseException, NullValueException, InvalidValueException {
        adminAccount.addOrganisation(organisation,standardOrganisationCredits);
        adminAccount.addAsset(organisation, standardAsset, 10);
        Set<Asset> allAssets = dataSource.getAssets();
        assertEquals(allAssets.size(), 1);
    }

    /**
     * Test to add increase an available asset amount by a specific amount.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException value entered is incorrect type.
     * @throws InvalidValueException
     */
    @Test
    public void testAddAssetAmount() throws DuplicationException, NullValueException, UnknownDatabaseException, InvalidValueException {
        adminAccount.addOrganisation(organisation,standardOrganisationCredits);
        adminAccount.addAsset(organisation, aNewAsset, aNewAssetAmount);
        Set<Asset> allAssets = dataSource.getAssets();
        assertEquals(allAssets.size(), 1);
    }

    /**
     * Test to remove and asset from a specified organisation.
     *
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    @Test
    public void testRemoveAsset() throws DuplicationException, NullValueException, InvalidValueException,
            UnknownDatabaseException {
        adminAccount.addAsset(standardOrganisation, aNewAsset, aNewAssetAmount);
        Set<Asset> allAssets = dataSource.getAssets();
        assertEquals(allAssets.size(), 1);
        adminAccount.removeAsset(standardOrganisation, aNewAsset);
        Set<Asset> allAssets2 = dataSource.getAssets();
        assertEquals(allAssets2.size(), 0);
    }

    /**
     * Test to increase an asset by a specified amount.
     *
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    @Test
    public void testUpdateAssetIncrease() throws DuplicationException, NullValueException, InvalidValueException,
            UnknownDatabaseException {
        adminAccount.addAsset(standardOrganisation, standardAsset, aNewAssetAmount);
        adminAccount.updateAssetAmount(standardOrganisation, standardAsset, aNewAssetAmount);
        int assetAmount = dataSource.getAssetAmount(standardOrganisation, standardAsset);
        assertEquals(aNewAssetAmount + aNewAssetAmount, assetAmount);
    }

    /**
     * Test to decrese and asset by as specified amount.
     *
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    @Test
    public void testUpdateAssetDecrease() throws DuplicationException, NullValueException, InvalidValueException,
            UnknownDatabaseException {
        adminAccount.addAsset(standardOrganisation, standardAsset, aNewAssetAmount);
        adminAccount.updateAssetAmount(standardOrganisation, standardAsset, aNewAssetAmountReduce);
        int assetAmount = dataSource.getAssetAmount(standardOrganisation, standardAsset);
        assertEquals(aNewAssetAmount + aNewAssetAmountReduce, assetAmount);
    }
}
