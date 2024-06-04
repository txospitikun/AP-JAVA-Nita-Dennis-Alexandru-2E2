package org.example.clientlab11;

import org.example.clientlab11.BookClient;
import org.example.clientlab11.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientApplication implements CommandLineRunner {

    private final BookClient bookClient;

    public ClientApplication(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        // Get all books
        System.out.println("Getting all books:");
        bookClient.getAllBooks().forEach(System.out::println);

        // Add a new book
        Book newBook = new Book(100, "New Book Title", "English", "1, January 2020", 300);
        bookClient.addBook(newBook);
        System.out.println("Added new book");

        // Get book by ID
        System.out.println("Getting book by ID 100:");
        Book book = bookClient.getBookById(100);
        System.out.println(book);

        // Update book title
        bookClient.updateBookTitle(100, "Updated Book Title");
        System.out.println("Updated book title");

        // Get book by ID after update
        System.out.println("Getting book by ID 100 after update:");
        book = bookClient.getBookById(100);
        System.out.println(book);

        // Delete book
        bookClient.deleteBook(100);
        System.out.println("Deleted book with ID 100");
    }
}
