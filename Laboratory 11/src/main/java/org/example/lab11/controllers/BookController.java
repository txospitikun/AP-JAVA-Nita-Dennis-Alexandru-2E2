package org.example.lab11.controllers;

import classes.Book;
import dao.BooksAO;
import data.lab11.Database;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Connection con = Database.getConnection();
        for (int i = 1; i < 15; i++) {
            Book book = BooksAO.findById(i, con);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) throws SQLException {
        Connection con = Database.getConnection();
        return BooksAO.findById(id, con);
    }

    @PostMapping
    public String addBook(@RequestBody Book book) throws SQLException {
        Connection con = Database.getConnection();
        BooksAO booksAO = new BooksAO();
        booksAO.create(book.getId(), book.getTitle(), "Author1,Author2", "Genre1,Genre2", book.getPublicationDate(), con);
        return "{\"message\": \"Book added successfully!\"}";
    }

    @PutMapping("/{id}")
    public String updateBookTitle(@PathVariable int id, @RequestParam String newTitle) throws SQLException {
        Connection con = Database.getConnection();
        BooksAO.updateTitle(id, newTitle, con);
        return "{\"message\": \"Book title updated successfully!\"}";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) throws SQLException {
        Connection con = Database.getConnection();
        BooksAO.delete(id, con);
        return "{\"message\": \"Book deleted successfully!\"}";
    }
}