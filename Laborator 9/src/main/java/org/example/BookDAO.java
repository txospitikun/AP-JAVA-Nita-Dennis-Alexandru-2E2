package org.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public void create(Book book) throws SQLException
    {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO books (title, authors, genres, language, publication_date, number_of_pages) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthors());
            pstmt.setString(3, book.getGenres());
            pstmt.setString(4, book.getLanguage());
            pstmt.setString(5, book.getPublicationDate());
            pstmt.setInt(6, book.getNumberOfPages());
            pstmt.executeUpdate();
        }
    }
    public void createBatch(List<Book> books) throws SQLException
    {
        String sql = "INSERT INTO books (title, authors, language, publication_date, number_of_pages) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (Book book : books) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getAuthors());
                pstmt.setString(3, book.getLanguage());
                pstmt.setString(4, book.getPublicationDate());
                pstmt.setInt(5, book.getNumberOfPages());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public List<Book> getAllBooks() throws SQLException
    {
        Connection con = Database.getConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM books");
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthors(rs.getString("authors"));
                book.setGenres(rs.getString("genres"));
                book.setLanguage(rs.getString("language"));
                book.setPublicationDate(rs.getString("publication_date"));
                book.setNumberOfPages(rs.getInt("number_of_pages"));
                books.add(book);
            }
        }
        return books;
    }

    public Book findById(int id) throws SQLException
    {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM books WHERE id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthors(rs.getString("authors"));
                    book.setGenres(rs.getString("genres"));
                    book.setLanguage(rs.getString("language"));
                    book.setPublicationDate(rs.getString("publication_date"));
                    book.setNumberOfPages(rs.getInt("number_of_pages"));
                    return book;
                } else {
                    return null;
                }
            }
        }
    }

    public Book findByName(String name) throws SQLException
    {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement(
                "SELECT * FROM books WHERE name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthors(rs.getString("authors"));
                    book.setGenres(rs.getString("genres"));
                    book.setLanguage(rs.getString("language"));
                    book.setPublicationDate(rs.getString("publication_date"));
                    book.setNumberOfPages(rs.getInt("number_of_pages"));
                    return book;
                } else {
                    return null;
                }
            }
        }
    }
}
