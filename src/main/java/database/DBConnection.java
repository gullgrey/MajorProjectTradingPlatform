package main.java.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class is responsible for handling the connection to the database.
 */
public class DBConnection {

    private static Connection connection = null;

    /**
     * Constructor initializes the connection to a database.
     *
     * @param propsFile A props file that contains a database: url, username, password and schema.
     * @throws SQLException when the supplied props file contains props that are not able to access the database.
     * @throws IOException when the propsFile string is not a path to a propsFile.
     */
    public DBConnection(String propsFile) throws SQLException, IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(propsFile);
        props.load(in);
        in.close();

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        String schema = props.getProperty("jdbc.schema");

        connection = DriverManager.getConnection(url + "/" + schema, username,
                password);

    }

    /**
     * Initializes the singleton connection to the database.
     * @param propsFile the credentials for the connection to the database.
     * @return connection object.
     * @throws SQLException when the supplied props file contains props that are not able to access the database.
     * @throws IOException when the propsFile string is not a path to a propsFile.
     */
    public static Connection getInstance(String propsFile) throws SQLException, IOException{
        if (connection == null) {
            new DBConnection(propsFile);
        }
        return connection;
    }

}
