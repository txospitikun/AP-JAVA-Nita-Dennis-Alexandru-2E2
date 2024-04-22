package org.example;

import java.sql.*;

public class BookDAO
{
    public void create(Integer year, String ... names) throws SQLException
    {
        Connection connection = Database.getConnection();
        for(var name : names) {
            try (PreparedStatement pstmt = connection.prepareStatement("insert into authors (year, name) values (?)")) {
                pstmt.setString(1, name);
                pstmt.executeUpdate();
            }
        }

    }

    public Integer findByName(String name) throws SQLException
    {
        Connection connection = Database.getConnection();
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select id from authors where name = '" + name + "'"))
        {
            return rs.next() ? rs.getInt(1) : null;
        }

    }

    public Integer findById(int id) throws SQLException
    {
        Connection connection = Database.getConnection();
        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select id from authors where id = '" + id + "'"))
        {
            return rs.next() ? rs.getInt(1) : null;
        }
    }
}
