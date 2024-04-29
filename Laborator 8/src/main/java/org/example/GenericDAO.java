package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T> {
    private final String tableName;
    public GenericDAO(String tableName) {
        this.tableName = tableName;
    }
    public void create(String name) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO " + tableName + " (name) VALUES (?)")) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public List<T> findAll() throws SQLException
    {
        Connection con = Database.getConnection();
        List<T> entities = new ArrayList<>();
        Statement stmt = con.createStatement();
        try(ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName))
        {
            while (rs.next()) {
                T entity = createEntity(rs);
                entities.add(entity);
            }
        }
        return entities;
    }

    public List<T> findByName(String name) throws SQLException
    {
        Connection con = Database.getConnection();
        List<T> entities = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM " + tableName + " WHERE name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    T entity = createEntity(rs);
                    entities.add(entity);
                }
            }
        }
        return entities;
    }

    public T findById(int id) throws SQLException
    {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM " + tableName + " WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return createEntity(rs);
                }
            }
        }
        return null;
    }


    protected T createEntity(ResultSet rs) throws SQLException
    {

        return null;
    }
}