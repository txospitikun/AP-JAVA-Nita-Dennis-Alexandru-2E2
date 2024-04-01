package org.classes;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Export extends Command {

    public Export(String value) {
        super(value);
    }

    public String CommandImplementation(ArrayList<String> args) throws IOException {

        if(args.size() > 1)
        {
            return "Invalid arguments.";
        }
        var repo = RepositoryService.getInstance();
        var info = repo.getRepositoryInfo();

        ObjectMapper objectMapper = new ObjectMapper();
        String user;
        List<URL> references = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for(var pair : info.entrySet())
        {
            ArrayList<String> documents = new ArrayList<>();
            user = pair.getKey().name();
            for(var file : pair.getValue())
            {
                documents.add(file.documentName());
            }
            references.add(new URL(user, documents));
        }
        map.put("references", references);
        objectMapper.writeValue(new File("target/repo.json"), map);
        return "OK";
    }
}
