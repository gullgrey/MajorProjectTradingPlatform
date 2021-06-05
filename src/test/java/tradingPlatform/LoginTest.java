package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginTest {

    private static final String propsFile = "src/test/resources/maria.props";
    private static TradingPlatformDataSource dataSource;
    private static final String adminUser = "ADMIN";
    private static final String adminUser1 = "ADMIN1";
    private static final String adminPassword = "ADMIN";
    private static final String setTestName = "James";
    private static final String setTestPassword = "abc123";
    private static final String setTestOrganisation = "Microsoft";
    private static final int testOrganisationCredits = 1000;


    @BeforeAll
    public static void setupTestEnvironment() throws DuplicationException, UnknownDatabaseException, NullValueException, InvalidValueException {
        dataSource = new NetworkDataSource();
        ItAdministration administrator = new ItAdministration(dataSource, adminUser);
        administrator.addItUser(adminUser1, adminPassword);
        administrator.addOrganisation(setTestOrganisation, testOrganisationCredits);
        administrator.addStandardUser(setTestName, setTestPassword, setTestOrganisation);
    }

    /**
     * Testing a valid admin user logging in.
     * @throws UnknownDatabaseException
     * @throws NullValueException
     */
    @Test
    public void TestIsAdminLogginIn() throws UnknownDatabaseException, WrongCredentialException, NetworkException {
        Login lg = new Login(adminUser, adminPassword, dataSource);
        boolean IsLoginValid = lg.checkSuppliedCredentials();
        assertTrue(IsLoginValid);
    }

    /**
     * Testing a valid admin user logging in.
     * @throws UnknownDatabaseException
     * @throws NullValueException
     */
    @Test
    public void testIsUserLoggingIn() throws UnknownDatabaseException, WrongCredentialException, NetworkException {
        Login lg = new Login(setTestName, setTestPassword, dataSource);
        boolean IsLoginValid = lg.checkSuppliedCredentials();
        assertFalse(IsLoginValid);
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
