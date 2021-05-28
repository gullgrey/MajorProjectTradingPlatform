package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.tradingPlatform.Login;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    @Test
    public void testcheckSuppliedCredentialsTrue(){
        Login testLI = new Login(passingTestName, passingTestPassword);
        assertTrue(testLI.checkSuppliedCredentials());
    }

}
