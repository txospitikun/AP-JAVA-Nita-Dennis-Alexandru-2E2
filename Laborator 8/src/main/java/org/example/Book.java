package org.example;

import java.util.List;
import java.sql.Date;

public class Book extends BaseModel {
    private String title;
    private String genres;
    private String language;
    private String publicationDate;
    private String authors;
    private int numberOfPages;

    public Book()
    {
        // default constructor
    }

    public Book(String m_title, String m_authors, String m_genres, String m_language, String m_publicationDate, int m_numberOfPages) {
        this.title = m_title;
        this.authors = m_authors;
        this.genres = m_genres;
        this.language = m_language;
        this.publicationDate = m_publicationDate;
        this.numberOfPages = m_numberOfPages;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public void setId(int id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthors()
    {
        return authors;
    }

    public void setAuthors(String authors)
    {
        this.authors = authors;
    }

    public String getTitle()
    {
        return title;
    }


    public String getGenres()
    {
        return genres;
    }

    public void setGenres(String genres)
    {
        this.genres = genres;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getPublicationDate()
    {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate)
    {
        this.publicationDate = publicationDate;
    }

    public int getNumberOfPages()
    {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages)
    {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", genres='" + genres + '\'' +
                ", language='" + language + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", numberOfPages=" + numberOfPages +
                '}';
    }
}
