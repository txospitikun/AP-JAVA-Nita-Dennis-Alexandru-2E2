package dao;

import classes.Author;
import classes.Book;
import classes.Genre;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BooksAO {

    // Method to parse the date
    private static String getParsableDate(String publicationDate) {
        String[] dateParts = publicationDate.split(", ");
        if (dateParts.length < 2) {
            throw new IllegalArgumentException("Invalid publication date format: " + publicationDate);
        }

        String day = dateParts[0].trim();
        String monthYear = dateParts[1].trim();
        String[] monthYearParts = monthYear.split(" ");
        if (monthYearParts.length < 2) {
            throw new IllegalArgumentException("Invalid publication date format: " + publicationDate);
        }

        String month = monthYearParts[0];
        String year = monthYearParts[1];

        switch (month) {
            case "January": month = "01"; break;
            case "February": month = "02"; break;
            case "March": month = "03"; break;
            case "April": month = "04"; break;
            case "May": month = "05"; break;
            case "June": month = "06"; break;
            case "July": month = "07"; break;
            case "August": month = "08"; break;
            case "September": month = "09"; break;
            case "October": month = "10"; break;
            case "November": month = "11"; break;
            case "December": month = "12"; break;
            default: throw new IllegalArgumentException("Invalid month: " + month);
        }

        return month + "/" + day + "/" + year;
    }

    // Method to create a new book
    public void create(int bookId, String title, String authors, String genres, String publicationDate, Connection con) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        publicationDate = getParsableDate(publicationDate);
        String[] authorsList = authors.split(",");
        String[] genresList = genres.split(",");
        Book existingBook = BooksAO.findById(bookId, con);
        if (existingBook != null) {
            System.out.println("Book with title " + title + " already exists");
            return;
        }
        try {
            java.util.Date utilDate = dateFormat.parse(publicationDate);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            con.setAutoCommit(false);

            // Insert into books table first
            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO books(book_id, title, language, publication_date, number_pages) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setInt(1, bookId);
                stmt.setString(2, title);
                stmt.setString(3, "English");
                stmt.setDate(4, sqlDate);
                stmt.setInt(5, (int) (Math.random() * 1000 + 1));
                stmt.executeUpdate();
            }

            // Then insert into book_authors and book_genres
            for (String author : authorsList) {
                Author authorObj = new AuthorAO().findByName(author.trim(), con);
                if (authorObj == null) {
                    new AuthorAO().create(author.trim(), con);
                    authorObj = new AuthorAO().findByName(author.trim(), con);
                }
                try (PreparedStatement stmt = con.prepareStatement("INSERT INTO book_authors(book_id, author_id) VALUES (?, ?)")) {
                    stmt.setInt(1, bookId);
                    stmt.setInt(2, authorObj.getId());
                    stmt.executeUpdate();
                }
            }
            for (String genre : genresList) {
                Genre genreObj = new GenreAO().findByName(genre.trim(), con);
                if (genreObj == null) {
                    new GenreAO().create(genre.trim(), con);
                    genreObj = new GenreAO().findByName(genre.trim(), con);
                }
                if (genreObj != null) {
                    try (PreparedStatement stmt = con.prepareStatement("INSERT INTO book_genres(book_id, genre_id) VALUES (?, ?)")) {
                        stmt.setInt(1, bookId);
                        stmt.setInt(2, genreObj.getId());
                        stmt.executeUpdate();
                    }
                } else {
                    throw new SQLException("Error finding or creating genre: " + genre.trim());
                }
            }
            con.commit();
        } catch (ParseException e) {
            con.rollback();
            System.err.println("Error parsing publication date: " + e.getMessage());
        } catch (Exception e) {
            con.rollback();
            System.err.println("Error creating book record: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
        }
    }

    // Method to find a book by title
    public static Book findByTitle(String title, Connection con) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM books WHERE title = ?")) {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("language"), rs.getDate("publication_date").toString(), rs.getInt("number_pages")) : null;
            }
        } catch (Exception e) {
            System.err.println("Error finding book by title: " + e.getMessage());
            return null;
        }
    }

    // Method to find a book by id
    public static Book findById(int id, Connection con) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM books WHERE book_id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? new Book(rs.getInt("book_id"), rs.getString("title"), rs.getString("language"), rs.getDate("publication_date").toString(), rs.getInt("number_pages")) : null;
            }
        } catch (Exception e) {
            System.err.println("Error finding book by id: " + e.getMessage());
            return null;
        }
    }

    // Method to update the title of a book
    public static void updateTitle(int bookId, String newTitle, Connection con) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement("UPDATE books SET title = ? WHERE book_id = ?")) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating book title: " + e.getMessage());
            throw e;
        }
    }

    // Method to delete references to a book in book_authors and book_genres
    private static void deleteBookReferences(int bookId, Connection con) throws SQLException {
        try (PreparedStatement pstmt = con.prepareStatement("DELETE FROM book_authors WHERE book_id = ?")) {
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book authors: " + e.getMessage());
            throw e;
        }

        try (PreparedStatement pstmt = con.prepareStatement("DELETE FROM book_genres WHERE book_id = ?")) {
            pstmt.setInt(1, bookId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting book genres: " + e.getMessage());
            throw e;
        }
    }

    // Method to delete a book
    public static void delete(int bookId, Connection con) throws SQLException {
        con.setAutoCommit(false);
        try {
            // First delete references in book_authors and book_genres
            deleteBookReferences(bookId, con);

            // Then delete the book itself
            try (PreparedStatement pstmt = con.prepareStatement("DELETE FROM books WHERE book_id = ?")) {
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.err.println("Error deleting book: " + e.getMessage());
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }
}
