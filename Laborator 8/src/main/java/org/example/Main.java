package org.example;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        try
        {
            var authors = new AuthorDAO();
            authors.create("William Shakespeare");

            var genres = new GenreDAO();
            genres.create("Tragedy");
            Database.getConnection().commit();

            var books = new BookDAO();
            books.create(1597, "Romeo and Juliet", "William Shakespeare", "Tragedy");
            books.create(1979, "The Hitchhiker's Guide to the Galaxy");

            Database.getConnection().commit();


        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}