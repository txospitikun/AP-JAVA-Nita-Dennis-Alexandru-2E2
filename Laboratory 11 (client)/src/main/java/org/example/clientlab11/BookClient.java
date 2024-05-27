package org.example.clientlab11;

import org.example.clientlab11.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class BookClient {

    private static final String BASE_URL = "http://localhost:8008/books";
    private final RestTemplate restTemplate;

    public BookClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> getAllBooks() {
        ResponseEntity<Book[]> response = restTemplate.getForEntity(BASE_URL, Book[].class);
        return Arrays.asList(response.getBody());
    }

    public Book getBookById(int id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, Book.class);
    }

    public void addBook(Book book) {
        restTemplate.postForEntity(BASE_URL, book, String.class);
    }

    public void updateBookTitle(int id, String newTitle) {
        restTemplate.put(BASE_URL + "/" + id + "?newTitle=" + newTitle, null);
    }

    public void deleteBook(int id) {
        restTemplate.delete(BASE_URL + "/" + id);
    }
}