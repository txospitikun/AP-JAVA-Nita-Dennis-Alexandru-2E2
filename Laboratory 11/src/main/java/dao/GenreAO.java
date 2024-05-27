package dao;

import classes.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreAO {

    // Method to find a genre by name
    public Genre findByName(String name, Connection con) throws SQLException {
        String query = "SELECT genre_id, genre_name FROM genres WHERE genre_name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error finding genre by name: " + e.getMessage());
        }
        return null;
    }

    // Method to create a new genre
    public void create(String name, Connection con) throws SQLException {
        String query = "INSERT INTO genres (genre_name) VALUES (?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Error creating genre: " + e.getMessage());
        }
    }
}
