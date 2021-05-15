package test.java.database;

import main.java.database.DBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    private Connection connection;

    @Test
    public void newDatabaseInstance() throws SQLException, IOException {
        String propsFile = "src/test/resources/JDBCDataSourceTest.props";
        connection = DBConnection.getInstance(propsFile);
        Assertions.assertNotNull(connection);
        Assertions.assertFalse(connection.isClosed());
    }

    @Test
    public void wrongConnectionPath(){
        String wrongPath = "NOT A PATH";
        Assertions.assertThrows(IOException.class, () -> connection = DBConnection.getInstance(wrongPath));
    }

    @Test
    public void wrongDatabaseURL() {
        String wrongURL = "src/test/resources/WrongDataSource.props";
        Assertions.assertThrows(SQLException.class, () -> connection = DBConnection.getInstance(wrongURL));
    }
}
