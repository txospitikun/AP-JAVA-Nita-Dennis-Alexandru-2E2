package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        try
        {
            FileInputStream fileInputStream = new FileInputStream("gamelogic_serialized.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            var x = (GameLogic)objectInputStream.readObject();
            GameLogic.setInstance(x);
//            System.out.println("after: playerOneVertices: " + GameLogic.getInstance().playerOneVertices);
//            System.out.println("after: playerTwoVertices: " + GameLogic.getInstance().playerTwoVertices);
//            System.out.println("after: playerOneNodes: " + GameLogic.getInstance().playerOneNodes);
//            System.out.println("after: playerTwoNodes: " + GameLogic.getInstance().playerTwoNodes);
            objectInputStream.close();
            frame.canvas.init(frame.configPanel.rows, frame.configPanel.cols);
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void saveGame(ActionEvent e)
    {
        try {
            FileOutputStream fileOutput = new FileOutputStream("gamelogic_serialized.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutput);
            objectOutputStream.writeObject(GameLogic.getInstance());
//            System.out.println("before: playerOneVertices: " + GameLogic.getInstance().playerOneVertices);
//            System.out.println("before: playerTwoVertices: " + GameLogic.getInstance().playerTwoVertices);
//            System.out.println("before: playerOneNodes: " + GameLogic.getInstance().playerOneNodes);
//            System.out.println("before: playerTwoNodes: " + GameLogic.getInstance().playerTwoNodes);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch(Exception ex)
        {
            ex.printStackTrace();
        }

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
            ImageIO.write(imagebuf,"png", new File("save1.png"));
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
}