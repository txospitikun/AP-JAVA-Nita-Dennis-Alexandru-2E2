package org.example;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        try {
            Database.createAuthorsTable();
            Database.createGenresTable();
            Database.createBooksTable();

            AuthorDAO authorDAO = new AuthorDAO();
            authorDAO.create("William Shakespeare");
            authorDAO.create("J.K.Rowling");

            System.out.println(new AuthorDAO().findByName("William Shakespeare"));
            System.out.println(new AuthorDAO().findAll());

            GenreDAO genreDAO = new GenreDAO();
            genreDAO.create("Thriller");
            genreDAO.create("SF");
            System.out.println(new GenreDAO().findAll());


            Book romeoAndJuliet = new Book("Romeo and Juliet", "William Shakespeare", "Tragedy", "eng", "1597-07-01", 399);
            Book hitchhikersGuide = new Book("A Swiftly Tilting Planet", "Madeleine L'Eclair", "Adventure, Comedy", "eng", "1978-05-13", 319);

            BookDAO bookDAO = new BookDAO();
            bookDAO.create(romeoAndJuliet);
            bookDAO.create(hitchhikersGuide);

            List<Book> allBooks = bookDAO.getAllBooks();
            for (Book book : allBooks) {
                System.out.println(book);
            }

            Database.closeConnection();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}