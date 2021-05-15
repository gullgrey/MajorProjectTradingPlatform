package test.java.database;

import main.java.database.JDBCTradingPlatformDataSource;
import org.junit.jupiter.api.*;

public class JDBCDataSourceTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static final JDBCTradingPlatformDataSource dataSource = new JDBCTradingPlatformDataSource(propsFile);

    @BeforeAll
    static void setupDatabase() {
        dataSource.prepareDatabase();
    }

    @Test
    public void newUser() {
        // TODO
    }

    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}
