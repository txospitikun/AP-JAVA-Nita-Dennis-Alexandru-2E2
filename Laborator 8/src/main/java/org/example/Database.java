package org.example;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Database
{
    private static final String URL = "jdbc:postgresql://localhost:5432/books";
    private static final String USER = "postgres";
    private static final String PASSWORD = "afds7412";
    private static Connection connection = null;

    private Database() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("-> " + connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void createConnection()
    {
        try
        {
            connection.setAutoCommit(false);
        } catch (SQLException e)
        {
            System.err.println(e);
        }
    }
}
