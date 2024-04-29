package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAO extends GenericDAO<Author>
{
    public AuthorDAO() {
        super("authors");
    }
    @Override
    protected Author createEntity(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setName(rs.getString("name"));
        return author;
    }
}