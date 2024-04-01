package org.classes;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Command
{
    private String command_text;
    public Command(String value)
    {
        this.command_text = value;
    }

    public String getCommand()
    {
        return this.command_text;
    }

    public abstract String CommandImplementation(ArrayList<String> args) throws IOException;

}
