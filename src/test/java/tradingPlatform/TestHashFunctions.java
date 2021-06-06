package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.common.HashPassword;
import org.junit.jupiter.api.*;
import java.security.NoSuchAlgorithmException;

public class TestHashFunctions {

    static final String UsernameAndPassword = "ADMIN";

    /**
     * Testing Hash is a 64 Char String.
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testHash() throws NoSuchAlgorithmException {
        String HashedPassword = HashPassword.hashedPassword("UsernameAndPassword", "UsernameAndPassword");
        assertEquals( "3183140D24B23B1DDC8C2E3718E4E4BA365C2FD3E0B8877ECC75A6CCF68DBFEC",HashedPassword);
    }

    /**
     * Testing Hash is the same after 2 iterations for the same username and password.
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testHashIsTheSameTwoItterationsTime() throws NoSuchAlgorithmException {
        String HashedPassword = HashPassword.hashedPassword("UsernameAndPassword", "UsernameAndPassword");
        String HashedPassword2 = HashPassword.hashedPassword("UsernameAndPassword", "UsernameAndPassword");
        assertEquals(HashedPassword, HashedPassword2);
    }

    /**
     * Testing Hash is the same after 11 iterations for the same username and password.
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testHashIsTheSameTenItterationsTime() throws NoSuchAlgorithmException {
        String FirstHashedPassword = HashPassword.hashedPassword("UsernameAndPassword", "UsernameAndPassword");
        String HashedPassword = "";
        for(int i = 0; i < 10; i++){
            HashedPassword = HashPassword.hashedPassword("UsernameAndPassword", "UsernameAndPassword");
        }
        assertEquals(FirstHashedPassword, HashedPassword);
    }

    /**
     * Testing Hash is different for two users with almost identical credentials.
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testHashUsersWithAlmostIdenticalCredentials() throws NoSuchAlgorithmException {
        String HashedPassword = HashPassword.hashedPassword("UsernameAndPassword", "UsernameAndPassword");
        String HashedPassword2 = HashPassword.hashedPassword("ADMIN2", "UsernameAndPassword");
        assertTrue(HashedPassword != HashedPassword2);
    }

}
