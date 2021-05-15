package main.java.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

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

//    public static Connection getInstance(String newPropsFile) throws SQLException, IOException{
//        if (connection == null && propsFile == newPropsFile) {
//            new DBConnection(propsFile);
//        }
//        return connection;
//    }

    public Connection getConnection() {
        return connection;
    }

}
