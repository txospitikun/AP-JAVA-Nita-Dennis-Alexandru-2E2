package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookCSVReader {
    private static final String CSV_FILE_PATH = "books.csv";
    public static void importBooksFromCSV() throws IOException, SQLException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH)))
        {
            br.readLine();

            List<Book> books = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] data = line.split(",");
                Book book = new Book();
                book.setTitle(data[1]);
                book.setAuthors(data[2]);
                book.setGenres(null);
                book.setLanguage(data[6]);
                book.setNumberOfPages(Integer.parseInt(data[7].trim()));
                book.setPublicationDate(data[10]);
                books.add(book);
            }

            System.out.println("Book added to the database.");

            BookDAO bookDAO = new BookDAO();
            bookDAO.createBatch(books);
        }
    }
}