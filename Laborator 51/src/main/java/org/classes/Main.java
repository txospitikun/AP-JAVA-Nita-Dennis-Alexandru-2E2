package org.classes;

import org.records.Document;
import org.records.Person;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        var repository = new Repository(Path.of("C:\\Users\\chinez\\Desktop\\Java Lab\\Programare-Avansata\\Laborator 51\\RepositoryTest"));
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

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputCommand;

        ArrayList<Command> commands = new ArrayList<>();

        commands.add(new View("view"));

        while(true)
        {
            System.out.print("> ");
            inputCommand = reader.readLine();
            if(inputCommand.equals("exit"))
            {
                break;
            }
            ArrayList arguments = new ArrayList<>(List.of(inputCommand.split(" ")));


            for(var command : commands)
            {
                if(arguments.get(0).equals(command.getCommand()))
                {
                    String res = command.CommandImplementation((ArrayList<String>) arguments.stream().skip(1).collect(Collectors.toCollection(ArrayList::new)));
                    if(res != "OK")
                    {
                        System.out.println(res);
                    }
                    break;
                }
            }
        }
    }
}