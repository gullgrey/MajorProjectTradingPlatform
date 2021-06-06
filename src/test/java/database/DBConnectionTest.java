package test.java.database;

import main.java.server.database.DBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionTest {

    private Connection connection;
    private DBConnection dbConnection;

    @Test
    public void newDatabaseInstance() throws SQLException, IOException {
        String propsFile = "src/test/resources/JDBCDataSourceTest.props";
//        dbConnection = new DBConnection(propsFile);
        connection = DBConnection.getInstance(propsFile);
        Assertions.assertNotNull(connection);
        Assertions.assertFalse(connection.isClosed());
    }

//    @Test
//    public void wrongConnectionPath() throws IOException, SQLException {
//        String wrongPath = "NOT A PATH";
//        Assertions.assertThrows(IOException.class, () -> {
//            dbConnection = new DBConnection(wrongPath);
//            connection = dbConnection.getConnection();
//        });
//    }
//
//    @Test
//    public void wrongDatabaseURL() {
//        String wrongURL = "src/test/resources/WrongDataSource.props";
//        Assertions.assertThrows(SQLException.class, () -> {
//            dbConnection = new DBConnection(wrongURL);
//            connection = dbConnection.getConnection();
//        });
//    }
}
