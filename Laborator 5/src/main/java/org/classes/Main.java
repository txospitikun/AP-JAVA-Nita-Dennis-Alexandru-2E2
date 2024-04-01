package org.classes;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
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

class NoValidCommandException extends Exception
{
    NoValidCommandException(String s)
    {
        super(s);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        RepositoryService repository = RepositoryService.getInstance();
        repository.getRepository().setDirectory(Path.of("C:\\Users\\plays\\OneDrive\\Desktop\\Java\\Programare-Avansata\\Laborator 5\\RepositoryTest"));

        var path = repository.populateMap();

        for(var file : path)
        {
            List<Path> filesInDirectory = new ArrayList<>();
            try (Stream<Path> walk = Files.walk(file)) {
                filesInDirectory = walk.filter(Files::isRegularFile).toList();
            }
            for(var fileInDirectory : filesInDirectory)
            {
                repository.getRepository().putDocuments(new Person(file.getFileName().toString()), new Document(fileInDirectory.getFileName().toString()));
            }
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputCommand;

        ArrayList<Command> commands = new ArrayList<>();

        commands.add(new View("view"));
        commands.add(new Report("report"));
        commands.add(new Export("export"));
        commands.add(new Excel("excel"));

        while(true)
        {
            System.out.print("> ");
            inputCommand = reader.readLine();
            if(inputCommand.equals("exit"))
            {
                break;
            }
            ArrayList arguments = new ArrayList<>(List.of(inputCommand.split(" ")));

            try {
                boolean commandFound = false;
                for (var command : commands) {
                    if (arguments.get(0).equals(command.getCommand())) {
                        String res = command.CommandImplementation((ArrayList<String>) arguments.stream().skip(1).collect(Collectors.toCollection(ArrayList::new)));
                        if (res != "OK") {
                            System.out.println(res);
                        }
                        commandFound = true;
                        break;
                    }

                }
                if(!commandFound)
                {
                    throw new NoValidCommandException("No valid command found!");
                }
            }
            catch(Exception e)
            {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}