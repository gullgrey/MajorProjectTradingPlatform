package main.java.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

    private DBConnection(String propsFile) throws SQLException, IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(propsFile);
        System.out.println("Hi");
        props.load(in);
        in.close();

        // specify the data source, username and password
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        String schema = props.getProperty("jdbc.schema");

        connection = DriverManager.getConnection(url + "/" + schema, username,
                password);

    }

    public static Connection getInstance(String propsFile) throws SQLException, IOException{
        if (connection == null) {
            new DBConnection(propsFile);
        }
        return connection;
    }
}
