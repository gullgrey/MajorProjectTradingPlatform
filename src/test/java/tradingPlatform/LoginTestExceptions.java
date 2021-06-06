package test.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.java.mockups.DataSourceMockup;
import test.java.mockups.DatabaseMockup;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginTestExceptions {
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
     * @throws UnknownDatabaseException
     * @throws NullValueException
     */
    @Test
    public void testInvalidUser() throws UnknownDatabaseException, NullValueException {
        assertThrows(NullValueException.class, () -> {
            Login lg = new Login(wrongTestName, wrongTestPassword, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Testing a blank Username.
     * @throws UnknownDatabaseException
     * @throws NullValueException
     */
    @Test
    public void testblankUsernameOnLoggingIn() throws UnknownDatabaseException, NullValueException {
        assertThrows(NullValueException.class, () -> {
            Login lg = new Login(blankString, setTestPassword, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Testing a blank password.
     * @throws UnknownDatabaseException
     * @throws NullValueException
     */
    @Test
    public void testblankPasswordOnLoggingIn() throws UnknownDatabaseException, NullValueException {
        assertThrows(NullValueException.class, () -> {
            Login lg = new Login(setTestName, blankString, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Testing both blank username and password.
     * @throws UnknownDatabaseException
     * @throws NullValueException
     */
    @Test
    public void testblankUsernameAndPasswordOnLoggingIn() throws UnknownDatabaseException, NullValueException {
        assertThrows(NullValueException.class, () -> {
            Login lg = new Login(blankString, blankString, dataSource);
            lg.checkSuppliedCredentials();
        });
    }

    /**
     * Destroys the database connection after every test.
     * @throws SQLException
     */
    @AfterEach
    public void resetDatabaseAftereach(){
        dataSource.deleteAll();
    }
    /**
     * Destroys the database connection that was made for testing.
     * @throws SQLException
     */
    @AfterAll
    static void resetDatabase(){
        dataSource.deleteAll();
    }
}
