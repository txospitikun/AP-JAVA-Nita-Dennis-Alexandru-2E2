package org.classes;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class View extends Command {

    public View(String value) {
        super(value);
    }

    public String CommandImplementation(ArrayList<String> args) throws IOException {

        if(args.size() > 1)
        {
            return "Invalid arguments.";
        }
        Desktop desktop = Desktop.getDesktop();
        File file = new File(args.get(0));

        if(!file.exists())
        {
            return "File doesn't exist!";
        }
        desktop.open(file);
        return "OK";
    }
}
