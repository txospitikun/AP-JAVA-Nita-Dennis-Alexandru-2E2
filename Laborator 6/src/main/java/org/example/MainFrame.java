package org.example;

import javax.swing.*;

import java.awt.*;

import static javax.swing.SwingConstants.*;

public class MainFrame extends JFrame
{
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel canvas;

    public MainFrame(int rows, int cols)
    {
        super("Game");
        init(rows, cols);
    }

    private void init(int rows, int cols)
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        configPanel = new ConfigPanel(this, rows, cols);
        controlPanel = new ControlPanel(this);
        canvas = new DrawingPanel(this);
        add(canvas, CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(configPanel, BorderLayout.NORTH);
        pack();
    }
}
