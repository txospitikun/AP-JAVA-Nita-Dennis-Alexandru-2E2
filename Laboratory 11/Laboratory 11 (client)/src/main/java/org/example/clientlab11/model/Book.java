package org.example.clientlab11.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String title;
    private String language;
    private String publicationDate;
    private int nrPages;
}