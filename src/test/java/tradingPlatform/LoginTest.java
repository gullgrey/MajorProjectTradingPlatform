package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.client.tradingPlatform.*;
import org.junit.jupiter.api.*;
import test.java.mockups.DataSourceMockup;
import test.java.mockups.DatabaseMockup;

public class LoginTest {

    private static DataSourceMockup dataSource;
    private static final String adminUser = "ADMIN";
    private static final String adminUser1 = "ADMIN1";
    private static final String adminPassword = "ADMIN";
    private static final String setTestName = "James";
    private static final String setTestPassword = "abc123";
    private static final String setTestOrganisation = "Microsoft";
    private static final int testOrganisationCredits = 1000;


    @BeforeAll
    public static void setupTestEnvironment() throws DuplicationException, UnknownDatabaseException, NullValueException, InvalidValueException {
        dataSource = new DataSourceMockup(new DatabaseMockup());
        ItAdministration administrator = new ItAdministration(dataSource, adminUser);
        administrator.addItUser(adminUser1, adminPassword);
        administrator.addOrganisation(setTestOrganisation, testOrganisationCredits);
        administrator.addStandardUser(setTestName, setTestPassword, setTestOrganisation);
    }

    /**
     * Testing a valid admin user logging in.
     */
    @Test
    public void TestIsAdminLogginIn() throws UnknownDatabaseException, WrongCredentialException, NetworkException {
        Login lg = new Login(adminUser1, adminPassword, dataSource);
        boolean IsLoginValid = lg.checkSuppliedCredentials();
        assertTrue(IsLoginValid);
    }

    /**
     * Testing a valid admin user logging in.
     */
    @Test
    public void testIsUserLoggingIn() throws UnknownDatabaseException, WrongCredentialException, NetworkException {
        Login lg = new Login(setTestName, setTestPassword, dataSource);
        boolean IsLoginValid = lg.checkSuppliedCredentials();
        assertFalse(IsLoginValid);
    }


    /**
     * Destroys the database connection after every test.
     */
    @AfterEach
    public void resetDatabaseAftereach() throws DuplicationException, UnknownDatabaseException, InvalidValueException, NullValueException {
        dataSource.deleteAll();
        ItAdministration administrator = new ItAdministration(dataSource, adminUser);
        administrator.addItUser(adminUser1, adminPassword);
        administrator.addOrganisation(setTestOrganisation, testOrganisationCredits);
        administrator.addStandardUser(setTestName, setTestPassword, setTestOrganisation);
    }
    /**
     * Destroys the database connection that was made for testing.
     */
    @AfterAll
    static void resetDatabase(){
        dataSource.deleteAll();
    }

}
