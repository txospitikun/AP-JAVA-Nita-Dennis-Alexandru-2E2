package dao;

import classes.Author;

import java.sql.*;

public class AuthorAO {

    public void create(String name, Connection con) throws SQLException{
        int newAuthorId = 1;
        try (PreparedStatement maxIdStmt = con.prepareStatement("SELECT MAX(author_id) AS max_id FROM authors")) {
            ResultSet rs = maxIdStmt.executeQuery();
            if (rs.next()) {
                int maxId = rs.getInt("max_id");
                newAuthorId = maxId + 1; // Increment the max ID
            }
        }
        Author author = new Author(newAuthorId, name);
        try(PreparedStatement pstmt = con.prepareStatement("INSERT INTO authors(author_id, name) VALUES(?,?)")){
            pstmt.setInt(1,newAuthorId);
            pstmt.setString(2,author.getName());
            pstmt.executeUpdate();
            con.commit();

        }
        catch (SQLException e){
            con.rollback();
            System.err.println(e);
        }
    }

    public Author findByName(String name, Connection con) throws SQLException {

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM authors WHERE name = '" + name + "'")) {
            return rs.next() ? new Author(rs.getInt("author_id"), rs.getString("name")) : null;
        }
    }

    public static Author findById(int id, Connection con) throws SQLException{
        try(Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM authors WHERE author_id = '" + id + "'")
        ){
            return rs.next() ? new Author(rs.getInt("author_id"), rs.getString("name")) : null;
        }
    }
}
