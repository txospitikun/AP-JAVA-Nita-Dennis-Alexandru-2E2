package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 3));
        exitBtn.addActionListener(this::exitGame);
        loadBtn.addActionListener(this::loadGame); // Add ActionListener for saveBtn
        add(exitBtn);
        add(loadBtn);
        add(saveBtn);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

    private void loadGame(ActionEvent e) {
        // Implement load game functionality
    }

    private void saveGame(ActionEvent e) {
        // Implement save game functionality
    }
}