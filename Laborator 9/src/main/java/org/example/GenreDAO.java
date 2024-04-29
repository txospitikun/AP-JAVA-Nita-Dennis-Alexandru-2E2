package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreDAO extends GenericDAO<Genre> {
    public GenreDAO()
    {
        super("genres");
    }

    @Override
    protected Genre createEntity(ResultSet rs) throws SQLException
    {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}
