package org.example;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel
{
    int rows, cols;
    final MainFrame frame;
    JLabel label;

    JSpinner spinner;
    JSpinner spinner2;

    JButton newGameBtn = new JButton("New game");

    public ConfigPanel(MainFrame frame, int rows, int cols)
    {
        this.frame = frame;
        this.rows = rows;
        this.cols = cols;
        init();
    }

    private void init()
    {
        setLayout(new GridLayout(1, 2));
        label = new JLabel("Grid size:");
        spinner = new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        spinner2= new JSpinner(new SpinnerNumberModel(10, 2, 100, 1));
        add(label);
        add(spinner);
        add(spinner2);
        add(newGameBtn);
    }

    public int getRows()
    {
        return this.rows;
    }

    public int getCols()
    {
        return this.cols;
    }
}
