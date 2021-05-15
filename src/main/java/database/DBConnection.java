package main.java.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private DBConnection(String propsFile) throws SQLException, IOException {

    }

    public static Connection getInstance(String propsFile) throws SQLException, IOException{
        return null;
    }
}
