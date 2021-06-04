package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.tradingPlatform.Login;
import main.java.tradingPlatform.NullValueException;
import main.java.tradingPlatform.StandardUser;
import main.java.tradingPlatform.UnknownDatabaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginTest {
    private static final String setTestName = "James";
    private static final String setTestPassword = "abc123";

    private static final String passingTestName = "James";
    private static final String passingTestPassword = "abc123";
    private static final String wrongTestName = "Tim";
    private static final String wrongTestPassword = "zyx987";

//    @Test
//    public void testLoginUsernameIsString(){
//        Login lg = new Login(testName, testPassword);
//        assertTrue(lg.);
//    }
//
//    @Test
//    public void testLoginPasswordIsString(){
//        Login lg = new Login(testName, testPassword);
//        assertTrue(lg.getUsername().equals(testName));
//    }

    //TODO Check of we want to supply checkSuppliedCredentials witha username
    // and a password or if the method is just going to look at the private
    // vales in the class.
//    @Test
//    public void testcheckSuppliedCredentialsTrue() throws UnknownDatabaseException, NullValueException {
//        Login testLI = new Login(passingTestName, passingTestPassword);
//        assertTrue(testLI.checkSuppliedCredentials());
//    }

//
//    @Test
//    public void hashtest() throws NoSuchAlgorithmException {
//        StandardUser standardTest = new StandardUser(dataSource, aNewUser, aNewUserOrganisation);
//        String salt = standardTest.hashedPassword("aaaaaa");
//        String expectedHasedpassword = "1f04146469fa1ead4dd7e590d53471ac";
//        assertEquals(expectedHasedpassword, salt);
//
//    }
//    @Test
//    public void hashtest2() throws NoSuchAlgorithmException {
//        StandardUser standardTest = new StandardUser(dataSource, bNewUser, bNewUserOrganisation);
//        String salt = standardTest.hashedPassword("aaaaaa");
//        String expectedHasedpassword = "6305e39aeecdd330ab6ea760e083da29";
//        assertEquals(expectedHasedpassword, salt);
//
//    }
}
