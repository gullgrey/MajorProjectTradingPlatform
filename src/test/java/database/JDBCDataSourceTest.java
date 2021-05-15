package test.java.database;

import main.java.database.JDBCTradingPlatformDataSource;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.xml.namespace.QName;

public class JDBCDataSourceTest {

    private static final String propsFile = "src/test/resources/JDBCDataSourceTest.props";
    private static final JDBCTradingPlatformDataSource dataSource = new JDBCTradingPlatformDataSource(propsFile);
    private static String organisationName;
    private static int amount;
    @BeforeAll
    static void setupDatabase() {
        dataSource.prepareDatabase();
    }


    @Test
    public void addOrganisationData() {
        organisationName = "google";
        amount = 10;
        dataSource.addOrganisation(organisationName, amount);
        assertEquals(organisationName, amount);
    }

    @Test
    public void getOrganisationData() {
        dataSource.getOrganisations();

    }

    @Test
    public void newUser() {

    }





    @AfterAll
    static void resetDatabase() {
        dataSource.deleteAll();
    }
}
