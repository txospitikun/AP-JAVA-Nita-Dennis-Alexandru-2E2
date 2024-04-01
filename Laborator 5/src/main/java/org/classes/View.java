package org.classes;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class InvalidArgs extends Exception
{
    public InvalidArgs(String s)
    {
        super(s);
    }
}

class NoFileFound extends Exception
{
    public NoFileFound(String s)
    {
        super(s);
    }
}

public class View extends Command {

    public View(String value) {
        super(value);
    }

    public String CommandImplementation(ArrayList<String> args) throws IOException {

        try {


            if (args.size() > 1 || args.size() == 0) {
                throw new InvalidArgs("Invalid arguments");
            }
            Desktop desktop = Desktop.getDesktop();
            File file = new File(args.get(0));

            if (!file.exists()) {
                throw new NoFileFound("No file found!");
            }
            desktop.open(file);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return "OK";
    }
}
