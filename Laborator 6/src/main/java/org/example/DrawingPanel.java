package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.sqrt;
public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    int rows, cols;
    int canvasWidth = 600, canvasHeight = 600;
    int boardWidth, boardHeight;
    int cellWidth, cellHeight;
    int padX, padY;
    int stoneSize = 20;
    boolean someoneWon = false;

    boolean firstInit = true;
    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        init(frame.configPanel.getRows(), frame.configPanel.getCols());
    }
    final void init(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.padX = stoneSize + 10;
        this.padY = stoneSize + 10;
        this.cellWidth = (canvasWidth - 2 * padX) / (cols - 1);
        this.cellHeight = (canvasHeight - 2 * padY) / (rows - 1);
        this.boardWidth = (cols - 1) * cellWidth;
        this.boardHeight = (rows - 1) * cellHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        BooleanObject isFirstPlayer = new BooleanObject();

        isFirstPlayer.value = false;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(someoneWon)
                {
                    return;
                }
                float A = (float) ((e.getX()-padX)*1.0/cellWidth);
                float B = (float) ((e.getY()-padY)*1.0/cellHeight);

                float distance = (float) Math.sqrt((A-Math.round(A))*(A-Math.round(A)) + (B-Math.round(B))*(B-Math.round(B)));
                if(distance*100 < stoneSize) {
                    GameLogic.getInstance().validateMove((int) ((padX + Math.round(A) * cellHeight) - stoneSize / 2), (int)((padY + Math.round(B) * cellWidth) - stoneSize / 2), A, B, false, isFirstPlayer);
                }
                boolean canPlace = false;
                for(int i = 0; i < frame.configPanel.rows; i++)
                {
                    for(int j = 0; j < frame.configPanel.cols; j++)
                    {
                        if(GameLogic.getInstance().validateMove((int) ((padX + Math.round(i) * cellHeight) - stoneSize / 2), (int)((padY + Math.round(j) * cellWidth) - stoneSize / 2), i, j, true, isFirstPlayer))
                        {
                            canPlace = true;
                            break;
                        }
                    }
                }
                if(!canPlace)
                {
                    JOptionPane.showMessageDialog(null, STR."\{isFirstPlayer.value ? "Blue" : "Red"} player wins!");
                    someoneWon = true;
                }
                repaint();
            }
        });

        if(firstInit) {
            GameLogic.getInstance().generateLogicGraph(rows, cols);
            GameLogic.getInstance().generateSticks(rows, cols);
            firstInit = false;
        }

        repaint();
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        paintGrid(g);
        paintSticks(g);
        paintStones(g);
    }
    private void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        for (int row = 0; row < rows; row++) {
            int x1 = padX;
            int y1 = padY + row * cellHeight;
            int x2 = padX + boardWidth;
            int y2 = y1;
            g.drawLine(x1, y1, x2, y2);
        }
        for (int col = 0; col < cols; col++) {
            int x1 = padX + col * cellWidth;
            int y1 = padY;
            int y2 = padX + boardHeight;
            int x2 = x1;
            g.drawLine(x1, y1, x2, y2);
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = padX + col * cellWidth;
                int y = padY + row * cellHeight;
                g.setColor(Color.LIGHT_GRAY);
                g.drawOval(x - stoneSize / 2, y - stoneSize / 2, stoneSize, stoneSize);
            }
        }
    }
    private void paintSticks(Graphics2D g)
    {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        var generatedEdges = GameLogic.getInstance().gameGraph.edgeSet();
        for(var edge : generatedEdges)
        {
            Pattern pattern = Pattern.compile("\\((\\d+)-(\\d+) : (\\d+)-(\\d+)\\)");
            Matcher matcher = pattern.matcher(edge.toString());

            if (matcher.matches()) {
                x1 = Integer.parseInt(matcher.group(1));
                y1 = Integer.parseInt(matcher.group(2));

                x2 = Integer.parseInt(matcher.group(3));
                y2 = Integer.parseInt(matcher.group(4));
            }
            //System.out.println(STR."X1: \{x1} Y1: \{y1} X2: \{x2} Y2: \{y2}");

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(5));
            g.drawLine(padX + (x1) * cellWidth, padY + (y1) * cellHeight, padX + (x2) * cellWidth, padY + (y2) * cellHeight);
        }
    }

    private void paintStones(Graphics2D g)
    {
        float x1 = 0, y1 = 0;
        Pattern pattern = Pattern.compile("(-?\\d*\\.?\\d+)\\s*-\\s*(-?\\d*\\.?\\d+)");
        for(var player : GameLogic.getInstance().playerOneVertices)
        {
            Matcher matcher = pattern.matcher(player);
            if (matcher.matches()) {
                x1 = Float.parseFloat(matcher.group(1));
                y1 = Float.parseFloat(matcher.group(2));

                g.setColor(Color.RED);
                g.fillOval((int) x1, (int) y1, stoneSize, stoneSize);
            }
        }

        for(var player : GameLogic.getInstance().playerTwoVertices)
        {
            Matcher matcher = pattern.matcher(player);
            if (matcher.matches()) {
                x1 = Float.parseFloat(matcher.group(1));
                y1 = Float.parseFloat(matcher.group(2));

                g.setColor(Color.BLUE);
                g.fillOval((int) x1, (int) y1, stoneSize, stoneSize);
            }
        }
    }
}
