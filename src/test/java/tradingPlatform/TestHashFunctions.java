package test.java.tradingPlatform;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.database.TradingPlatformDataSource;
import main.java.network.NetworkDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

public class TestHashFunctions {
    @Test
    public void testHash() throws NoSuchAlgorithmException {
        String HashedPassword = HashPassword.hashedPassword("ADMINdasdasdasdasdsadasdasdasdasdasdsadasdasdsadasdsadsadsadsadsadsadsadsdaasdasdasdsad", "ADMINdasdasdasdasdsadasdasdasdasdasdsadasdasdsadasdsadsadsadsadsadsadsadsdaasdasdasdsad");
        String HashedPassword2 = HashPassword.hashedPassword("ADMIN", "ADMIN");
        String HashedPassword3 = HashPassword.hashedPassword("ADMINdasdasdasdasdsadasdasdasdasdasdsadasdasdsadasdsadsadsadsadsadsadsadsdaasdasdasdsad", "ADMINdasdasdasdasdsadasdasdasdasdasdsadasdasdsadasdsadsadsadsadsadsadsadsdaasdasdasdsad");
        assertEquals( "F8ED27280564F0E7F945C463E483B800B1F2C6D44F6E9D400A44B2C9BACF0AF9",HashedPassword3);
    }
}
