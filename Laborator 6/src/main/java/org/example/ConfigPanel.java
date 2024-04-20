package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        spinner = new JSpinner(new SpinnerNumberModel(6, 2, 100, 1));
        spinner2 = new JSpinner(new SpinnerNumberModel(6, 2, 100, 1));
        add(label);
        add(spinner);
        add(spinner2);
        add(newGameBtn);
        newGameBtn.addActionListener(this::newGame);
        spinner.addChangeListener(this::onChangeRow);
        spinner2.addChangeListener(this::onChangeCols);
    }

    private void onChangeRow(ChangeEvent changeEvent)
    {
        this.frame.configPanel.rows = (int) spinner.getValue();
    }

    private void onChangeCols(ChangeEvent changeEvent)
    {
        this.frame.configPanel.cols = (int) spinner.getValue();
    }

    public void onChange(ChangeListener c)
    {

    }
    public void newGame(ActionEvent e)
    {
        frame.canvas.firstInit = true;
        GameLogic.setInstance(new GameLogic());
        frame.canvas.someoneWon = false;
        frame.canvas.init(frame.configPanel.rows, frame.configPanel.cols);
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
