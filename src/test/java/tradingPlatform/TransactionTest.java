package test.java.tradingPlatform;

import main.java.database.JDBCTradingPlatformDataSource;
import main.java.tradingPlatform.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.sql.SQLException;
import java.util.DuplicateFormatFlagsException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static JDBCTradingPlatformDataSource dataSource;


    /**
     * Initializes the database for testing.
     *
     * @throws IOException
     * @throws SQLException
     */
    @BeforeAll
    static void setupDatabase() throws IOException, SQLException {
        dataSource = new JDBCTradingPlatformDataSource(propsFile);
    }

    /**
     * Setup an admin account and add an initial organisation to implement tests.
     *
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     */
    @BeforeAll
    static void setupAdmin() throws DuplicationException, NullValueException, InvalidValueException {

    }


}
