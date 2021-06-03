package test.java.tradingPlatform;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.*;
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

public class ItAdministrationTestException {

    private static final String propsFile = "src/test/resources/maria.props";
    private static TradingPlatformDataSource dataSource;
    private static ItAdministration adminAccount;
    //private static NetworkDataSource dataSource;
    private static final String aNewUser = "User666";
    private static final String aNewUserInitialPassword = "Password";
    private static final String aNewUserType = "USER";
    private static final String aITUserType = "ADMIN";
    private static final String STANDARD = "STANDARD";
    private static final String organisation1 = "Apple";
    private static final String organisation2 = "NotReal";
    private static final String asset1 = "AppleWatch5.0";
    private static final int asset1Amount = 2;
    private static final int addOrderCreditAmount = 40;
    private static final String falseVariable = "DoesNotExist";
    private static  ItAdministration adminUser;

    /**
     * Initializes the database for testing.
     *
     * @throws IOException
     * @throws SQLException
     */
    @BeforeAll
    static void setupDatabase() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        dataSource = new NetworkDataSource();
        adminAccount = new ItAdministration(dataSource, aNewUser);
        adminAccount.addOrganisation(organisation1, addOrderCreditAmount);
    }


    @AfterEach
    public void clearDatabase() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        dataSource.deleteAll();
        adminAccount.addOrganisation(organisation1, addOrderCreditAmount);

    }
    @AfterAll
    static void clearDatabase2() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        dataSource.deleteAll();
    }
    //Todo
    //add ADMIN to organisations fails
    //delete ADMIN from organisations fails.
    //



    /**
     * Testing if the program throws an exception if the same user is added.
     */
    @Test
    public void addStandardUserThrowsDuplicate() {
        assertThrows(DuplicationException.class , () -> {
            adminUser.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
            adminUser.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
        });
    }

    /**
     * Testing if the program throws an exception if the organisation doesn't exist.
     */
    @Test
    public void addStandardUserThrowsNoOrganisation() {
        assertThrows(NullValueException.class , () -> {
            adminUser.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
        });
    }

    /**
     * Testing if the program throws an exception if the same IT user is added.
     */
    @Test
    public void addItUserThrowsDuplicate() {
        assertThrows(DuplicationException.class , () -> {
            adminUser.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
            adminUser.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
        });
    }

    /**
     * Testing if the program throws an exception if the user doesn't exist
     * when trying to delete them.
     */
    @Test
    public void removeUserThrowsNull() {
        assertThrows(DuplicationException.class , () -> {
            String notAUser = "NoUser";
            adminUser.removeUser(notAUser);
        });
    }

    /**
     * Testing if the program throws an exception if the admin users tries
     * to add and organisation with the reserved ADMIN name.
     */
    @Test
    public void addOrganisationThrowsReserve() {
        assertThrows(InvalidValueException.class , () -> {
            String reserved = "ADMIN";
            adminUser.addOrganisation(reserved, addOrderCreditAmount);
        });
    }

    /**
     * Testing if the program throws an exception when the admin users tries
     * to add the organisation twice.
     */
    @Test
    public void addOrganisationThrowsDuplicate() {
        assertThrows(DuplicationException.class , () -> {
            adminUser.addOrganisation(organisation1, addOrderCreditAmount);
            adminUser.addOrganisation(organisation1, addOrderCreditAmount);
        });
    }

    /**
     * Testing if the program throws an exception if and admin users tries
     * to remove an organisation that doesn't exist.
     */
    @Test
    public void removeOrganisationThrows() {
        assertThrows(NullValueException.class , () -> {
            adminUser.addOrganisation(organisation1, addOrderCreditAmount);
        });
    }
}