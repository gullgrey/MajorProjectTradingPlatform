package test.java.database;

import main.java.database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    private Connection connection;

    @AfterEach
    public void resetConnection() throws SQLException {
        connection.close();
        connection = null;
        System.out.println(connection);
    }

    @Test
    public void newDatabaseInstance() throws SQLException, IOException {
        String propsFile = "src/test/resources/JDBCDataSourceTest.props";
        System.out.println(connection);
        connection = DBConnection.getInstance(propsFile);
        Assertions.assertNotNull(connection);
        Assertions.assertFalse(connection.isClosed());
    }

    @Test
    public void wrongConnectionPath() throws IOException, SQLException {
        String wrongPath = "NOT A PATH";
        Assertions.assertThrows(IOException.class, () -> connection = DBConnection.getInstance(wrongPath));
    }

    @Test
    public void wrongDatabaseURL() {
        String wrongURL = "src/test/resources/WrongDataSource.props";
        Assertions.assertThrows(SQLException.class, () -> connection = DBConnection.getInstance(wrongURL));
    }
}
