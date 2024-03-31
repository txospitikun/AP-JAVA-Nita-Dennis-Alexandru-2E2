package classes;

import records.Document;
import records.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var repository = new Repository(Path.of("C:\\Users\\plays\\OneDrive\\Desktop\\Java\\Programare-Avansata\\Laborator 5\\Laborator5\\RepositoryTest"));
        var service = new RepositoryService(repository);

        var path = service.populateMap();

        for(var file : path)
        {
            List<Path> filesInDirectory = new ArrayList<>();
            try (Stream<Path> walk = Files.walk(file)) {
                filesInDirectory = walk.filter(Files::isRegularFile).toList();
            }
            for(var fileInDirectory : filesInDirectory)
            {
                repository.putDocuments(new Person(file.getFileName().toString()), new Document(fileInDirectory.getFileName().toString()));
            }
        }
        service.showRepository();
    }
}