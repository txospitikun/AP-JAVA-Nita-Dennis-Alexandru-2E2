package org.example.lab11.controllers;

import classes.Author;
import dao.AuthorAO;
import data.lab11.Database;
import data.lab11.Database;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AuthorController {

    @RequestMapping("/authors")
    public List<Author> getAuthors() throws SQLException {
       List<Author> authors = new ArrayList<>();
        Connection con = Database.getConnection();
       for(int i = 1; i < 15; i++)
       {
           Author author = AuthorAO.findById(i,con);
              if(author!=null)
              {
                authors.add(author);
              }
       }
       return authors;
    }
}
