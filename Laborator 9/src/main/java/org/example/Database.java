package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "afds7412";
    private static final HikariDataSource dataSource;

    static
    {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(300000);
        config.setConnectionTimeout(120000);
        config.setLeakDetectionThreshold(300000);
        dataSource = new HikariDataSource(config);
    }

    private Database()
    {

    }

    public static Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    public static void deleteAll(String tableName) throws SQLException
    {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            String query = "DELETE FROM " + tableName;
            stmt.executeUpdate(query);
            con.commit();
        }
    }

    public static void createAuthorsTable()
    {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            String createTableQuery = "DROP TABLE IF EXISTS authors CASCADE ;"
                    + "CREATE TABLE IF NOT EXISTS authors ("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(100) NOT NULL"
                    + ")";
            stmt.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            System.err.println("Error creating authors table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createGenresTable()
    {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            String createTableQuery = "DROP TABLE IF EXISTS genres;"
                    + "CREATE TABLE IF NOT EXISTS genres ("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL"
                    + ")";
            stmt.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            System.err.println("Error creating genres table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createBooksTable()
    {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            String createTableQuery = "DROP TABLE IF EXISTS books CASCADE;"
                    + "CREATE TABLE IF NOT EXISTS books ("
                    + "id SERIAL PRIMARY KEY,"
                    + "title VARCHAR(255) NOT NULL,"
                    + "language VARCHAR(50) NOT NULL,"
                    + "publication_date VARCHAR(25) NOT NULL,"
                    + "number_of_pages INT NOT NULL,"
                    + "authors VARCHAR(255),"
                    + "genres VARCHAR(255)"
                    + ")";
            stmt.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            System.err.println("Error creating books table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeConnection()
    {
        dataSource.close();
    }
}