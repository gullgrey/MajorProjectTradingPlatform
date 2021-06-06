package test.java.tradingPlatform;

import main.java.client.tradingPlatform.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.java.mockups.DataSourceMockup;
import test.java.mockups.DatabaseMockup;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class is responsible for testing the logic in the Login class regarding exception calls.
 */
public class LoginExceptionsTest {
    private static DataSourceMockup dataSource;
    private static final String adminUser = "ADMIN";
    private static final String adminUser1 = "ADMIN1";
    private static final String adminPassword = "ADMIN";
    private static final String setTestName = "James";
    private static final String setTestPassword = "abc123";
    private static final String setTestOrganisation = "Microsoft";
    private static final int testOrganisationCredits = 1000;
    private static final String wrongTestName = "Tim";
    private static final String wrongTestPassword = "zyx987";
    private static final String blankString = "";

    @BeforeAll
    public static void setupTestEnvironment() throws DuplicationException, UnknownDatabaseException, NullValueException, InvalidValueException {
        dataSource = new DataSourceMockup(new DatabaseMockup());
        ItAdministration administrator = new ItAdministration(dataSource, adminUser);
        administrator.addItUser(adminUser1, adminPassword);
        administrator.addOrganisation(setTestOrganisation, testOrganisationCredits);
        administrator.addStandardUser(setTestName, setTestPassword, setTestOrganisation);
    }

    /**
     * Testing Username and password that is not on the database.
     */
    @Test
    public void testInvalidUser() {
        assertThrows(WrongCredentialException.class, () -> {
            Login lg = new Login(wrongTestName, wrongTestPassword, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Testing a blank Username.
     */
    @Test
    public void testBlankUsernameOnLoggingIn() {
        assertThrows(WrongCredentialException.class, () -> {
            Login lg = new Login(blankString, setTestPassword, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Testing a blank password.
     */
    @Test
    public void testBlankPasswordOnLoggingIn() {
        assertThrows(WrongCredentialException.class, () -> {
            Login lg = new Login(setTestName, blankString, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Testing both blank username and password.
     */
    @Test
    public void testBlankUsernameAndPasswordOnLoggingIn() {
        assertThrows(WrongCredentialException.class, () -> {
            Login lg = new Login(blankString, blankString, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Destroys the database connection after every test.
     */
    @AfterEach
    public void resetDatabaseAfterEach(){
        dataSource.deleteAll();
    }
    /**
     * Destroys the database connection that was made for testing.
     */
    @AfterAll
    static void resetDatabase(){
        dataSource.deleteAll();
    }
}
