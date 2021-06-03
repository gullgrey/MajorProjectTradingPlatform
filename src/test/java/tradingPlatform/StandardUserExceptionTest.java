package test.java.tradingPlatform;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.sql.SQLException;

public class StandardUserExceptionTest {
    private static final String propsFile = "src/test/resources/StandardUserExceptionTest.props";
    private static JDBCTradingPlatformDataSource dataSource;

    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
        dataSource = new JDBCTradingPlatformDataSource(propsFile);
//        dataSource.addOrganisation(aNewUserOrganisation, startingCredits);
//        dataSource.addOrganisation(bNewUserOrganisation, startingCredits);
//        dataSource.addAsset(bNewUserOrganisation, assetName, fullAssetAmount);
    }

    public void testGetAssetsNullValueException() {

        assertThrows(NullValueException.class, () -> {

        } );
    }




}
