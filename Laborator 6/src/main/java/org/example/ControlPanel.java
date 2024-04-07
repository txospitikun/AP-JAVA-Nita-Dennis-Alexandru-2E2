package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class ControlPanel extends JPanel {
    final MainFrame frame;
    JButton exitBtn = new JButton("Exit");
    JButton loadBtn = new JButton("Load");
    JButton saveBtn = new JButton("Save");
    JButton savePhotto = new JButton("PNG");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 4));
        exitBtn.addActionListener(this::exitGame);
        loadBtn.addActionListener(this::loadGame); // Add ActionListener for saveBtn
        saveBtn.addActionListener(this::saveGame);
        savePhotto.addActionListener(this::savePhoto);
        add(exitBtn);
        add(loadBtn);
        add(saveBtn);
        add(savePhotto);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

    private void loadGame(ActionEvent e) {
        // Implement load game functionality
    }

    private void saveGame(ActionEvent e) {
        var gameLogicInstance = GameLogic.getInstance();
    }
    private void savePhoto(ActionEvent e) {

        BufferedImage imagebuf=null;
        try {
            imagebuf = new Robot().createScreenCapture(frame.bounds());
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        Graphics2D graphics2D = imagebuf.createGraphics();
        frame.paint(graphics2D);
        try {
            ImageIO.write(imagebuf,"jpeg", new File("save1.jpeg"));
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
}