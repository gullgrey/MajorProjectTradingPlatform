package test.java.tradingPlatform;

import main.java.client.tradingPlatform.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.java.mockups.DataSourceMockup;
import test.java.mockups.DatabaseMockup;

/**
 * This class is responsible for testing the exceptions of the ItAdministration class.
 */
public class ItAdministrationExceptionTest {

    private static ItAdministration adminAccount;
    private static final String aNewUser = "User666";
    private static final String aNewUserInitialPassword = "Password";
    private static final String organisation1 = "Apple";
    private static final String organisation2 = "NotReal";
    private static final int addOrderCreditAmount = 40;
    private static final String reserved = "ADMIN";
    private static DataSourceMockup dataSource; //This is for Mockup

    /**
     * Initializes the database for testing.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws InvalidValueException the field doesn't exist.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    @BeforeAll
    static void setupDatabase() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        dataSource = new DataSourceMockup(new DatabaseMockup());
        adminAccount = new ItAdministration(dataSource, aNewUser);
        adminAccount.addOrganisation(organisation1, addOrderCreditAmount);
    }

    /**
     * After each test clear the database and add the standard organisation.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws InvalidValueException the field doesn't exist.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    @AfterEach
    public void clearDatabase() throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        dataSource.deleteAll();
        adminAccount.addOrganisation(organisation1, addOrderCreditAmount);

    }

    /**
     * Clear the database after tests.
     */
    @AfterAll
    static void clearDatabase2() {
        dataSource.deleteAll();
    }

    /**
     * Test to see that when admin is added as an organisation it throws an exception as this is reserved.
     *
     */
    @Test
    public void testAddingAdminOrganisationFails()  {
        assertThrows(InvalidValueException.class , () -> adminAccount.addOrganisation(reserved, addOrderCreditAmount));
    }

    /**
     * Test to see that when admin is added as an organisation it throws an exception as this is reserved.
     *
     */
    @Test
    public void testRemoveAdminOrganisationFails()  {
        assertThrows(InvalidValueException.class , () -> adminAccount.removeOrganisation(reserved));
    }

    /**
     * Testing if the program throws an exception if the same user is added.
     */
    @Test
    public void addStandardUserThrowsDuplicate() {
        assertThrows(DuplicationException.class , () -> {
            adminAccount.addOrganisation(organisation1,0);
            adminAccount.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
            adminAccount.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
        });
    }

    /**
     * Testing if the program throws an exception if the organisation doesn't exist.
     */
    @Test
    public void addStandardUserThrowsNoOrganisation() {
        assertThrows(NullValueException.class , () -> adminAccount.addStandardUser(aNewUser,aNewUserInitialPassword, organisation2));
    }

    /**
     * Testing if the program throws an exception if the same IT user is added.
     */
    @Test
    public void addItUserThrowsDuplicate() {
        assertThrows(DuplicationException.class , () -> {
            adminAccount.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
            adminAccount.addStandardUser(aNewUser,aNewUserInitialPassword, organisation1);
        });
    }

    /**
     * Testing if the program throws an exception if the user doesn't exist
     * when trying to delete them.
     */
    @Test
    public void removeUserThrowsNull() {
        assertThrows(NullValueException.class , () -> {
            String notAUser = "NoUser";
            adminAccount.removeUser(notAUser);
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
            adminAccount.addOrganisation(reserved, addOrderCreditAmount);
        });
    }

    /**
     * Testing if the program throws an exception when the admin users tries
     * to add the organisation twice.
     */
    @Test
    public void addOrganisationThrowsDuplicate() {
        assertThrows(DuplicationException.class , () -> {
            adminAccount.addOrganisation(organisation1, addOrderCreditAmount);
            adminAccount.addOrganisation(organisation1, addOrderCreditAmount);
        });
    }

    /**
     * Testing if the program throws an exception if and admin users tries
     * to remove an organisation that doesn't exist.
     */
    @Test
    public void removeOrganisationThrows() {
        assertThrows(NullValueException.class , () -> adminAccount.removeOrganisation(organisation2));
    }
}