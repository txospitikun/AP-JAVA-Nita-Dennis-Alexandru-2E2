package classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class BookClient {

    private static final String BASE_URL = "http://localhost:8008/books";

    public static void run() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Pause execution for 5 seconds to ensure the server starts
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // GET request to fetch all books
        ResponseEntity<Book[]> response = restTemplate.getForEntity(BASE_URL, Book[].class);
        List<Book> books = Arrays.asList(response.getBody());
        books.forEach(book -> System.out.println(book.getTitle()));

        // POST request to add a new book
        Book newBook = new Book(15, "New Book", "English", "2020-01-01", 300);
        restTemplate.postForEntity(BASE_URL, newBook, String.class);

        restTemplate.put(BASE_URL + "/15?newTitle=Updated Book Title", null);

        // DELETE request to delete a book
        restTemplate.delete(BASE_URL + "/15");
    }

    public static void main(String[] args) {
        run();
    }
}