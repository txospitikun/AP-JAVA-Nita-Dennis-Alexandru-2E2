package org.classes;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.swing.*;
import java.awt.Desktop;
import java.io.*;
import java.util.*;

public class Report extends Command {

    Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    public Report(String value) throws IOException {
        super(value);

        cfg.setDirectoryForTemplateLoading(new File("C:/ftl"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public String CommandImplementation(ArrayList<String> args) throws IOException {

        if(args.size() > 0)
        {
            return "Invalid arguments.";
        }
        var respositoryService = RepositoryService.getInstance();
        respositoryService.showRepository();

        try {
            //Instantiate Configuration class
            Map<String, Object> map = new HashMap<>();
            map.put("blogTitle", "Report of the current repository");
            map.put("message", "This website is a report of the current repository, outputing the user and its files.");
            List<URL> references = new ArrayList<>();
            var info = respositoryService.getRepositoryInfo();
            String user;
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
            Template template = cfg.getTemplate("blog-template.ftl");
            Writer console = new OutputStreamWriter(System.out);
            template.process(map, console);
            console.flush();
            // File output
            Writer file = new FileWriter(new File("C:/ftl/blog-template-output.html"));
            template.process(map, file);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "OK";

    }
}
