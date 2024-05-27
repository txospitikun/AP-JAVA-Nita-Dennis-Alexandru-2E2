package classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author{
    private int id;
    private String name;

    public Author(int id, String name){
        this.id = id;
        this.name = name;
    }
}
