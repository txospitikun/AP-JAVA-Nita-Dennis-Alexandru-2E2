package org.classes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;

public class RepositoryService
{
    private Repository currentRepository;

    RepositoryService(Repository repository)
    {
        this.currentRepository = repository;
    }
    public void setRepository(Repository repository)
    {
        this.currentRepository = repository;
    }

    public Repository getRepository()
    {
        return this.currentRepository;
    }

    public List<Path> populateMap() throws IOException {
        List<Path> result;
        if(currentRepository == null)
        {
            return null;
        }
        try(Stream<Path> walk = Files.list(currentRepository.getDirectory()))
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
}
