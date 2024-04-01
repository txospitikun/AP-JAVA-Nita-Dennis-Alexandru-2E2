package org.classes;

import org.records.Document;
import org.records.Person;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;

public class RepositoryService
{
    private static RepositoryService instance = null;
    private Repository repository;


    RepositoryService()
    {
        this.repository = new Repository();
    }
    public void setRepository(Repository repository)
    {
        this.repository = repository;
    }

    public static RepositoryService getInstance()
    {
        if(instance == null)
        {
            instance = new RepositoryService();
        }
        return instance;
    }
    public Repository getRepository()
    {
        return this.repository;
    }

    public List<Path> populateMap() throws IOException {
        List<Path> result;
        if(instance == null)
        {
            return null;
        }
        try(Stream<Path> walk = Files.list(repository.getDirectory()))
        {
            result = walk.filter(Files::isDirectory).collect(Collectors.toList());
        }
        return result;
    }

    public void showRepository()
    {
        for(var pair : getRepository().getDocuments().entrySet())
        {
            System.out.println("Files for user: " + pair.getKey() + ":");
            for(var file : pair.getValue())
            {
                System.out.println("File: " + file.documentName());
            }
        }
    }

    public Map<Person, List<Document>> getRepositoryInfo()
    {
        return getRepository().getDocuments();
    }
}
