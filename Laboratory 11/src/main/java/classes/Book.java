package classes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class Book {
    private int id;
    private String title;
    private String language;
    private String publicationDate;
    private int nrPages;

    public Book(int id, String title, String language, String publicationDate, int nrPages){
        this.id = id;
        this.title = title;
        this.language = language;
        this.publicationDate = publicationDate;
        this.nrPages = nrPages;
    }
}
