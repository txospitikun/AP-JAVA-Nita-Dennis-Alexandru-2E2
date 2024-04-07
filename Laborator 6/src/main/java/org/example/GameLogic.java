package org.example;

import java.lang.reflect.Array;
import java.util.*;

import org.apache.commons.math3.geometry.spherical.twod.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.alg.*;

public class GameLogic
{
    private static GameLogic instance = null;

    Graph<String, DefaultEdge> gameGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    Set<String> vertices = gameGraph.vertexSet();

    Set<String> playerOneVertices = new HashSet<>();
    Set<String> playerOneNodes = new HashSet<>();
    Set<String> playerTwoVertices = new HashSet<>();
    Set<String> playerTwoNodes = new HashSet<>();
    private final Random rand = new Random();

    int moveCounter = 0;

    public boolean validateMove(float mouseX, float mouseY, float A, float B, boolean isFirstPlayer)
    {

        int currentX = Math.round(A);
        int currentY = Math.round(B);

        System.out.println(currentX + " " + currentY);

        if(moveCounter >= 1) {
            if (playerOneVertices.contains(STR."\{mouseX}-\{mouseY}") || playerTwoVertices.contains(STR."\{mouseX}-\{mouseY}")) {
                return false;
            }

            boolean up1 = playerTwoNodes.contains(STR."\{currentX - 1}-\{currentY}");
            boolean down1 = playerTwoNodes.contains(STR."\{currentX + 1}-\{currentY}");
            boolean left1 = playerTwoNodes.contains(STR."\{currentX}-\{currentY - 1}");
            boolean right1 = playerTwoNodes.contains(STR."\{currentX}-\{currentY + 1}");

            boolean up2 = playerOneNodes.contains(STR."\{currentX - 1}-\{currentY}");
            boolean down2 = playerOneNodes.contains(STR."\{currentX + 1}-\{currentY}");
            boolean left2 = playerOneNodes.contains(STR."\{currentX}-\{currentY - 1}");
            boolean right2 = playerOneNodes.contains(STR."\{currentX}-\{currentY + 1}");

            boolean hasEdge = false;

            System.out.println(gameGraph.edgeSet());

            if((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX - 1}-\{currentY}")
                    || (gameGraph.containsEdge(STR."\{currentX - 1}-\{currentY}", STR."\{currentX}-\{currentY}"))))
            {
                System.out.println(currentX + " " + currentY + "->" + (currentX - 1) + " " + currentY);
                hasEdge = true;
            }

            if((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX + 1}-\{currentY}")
            || (gameGraph.containsEdge(STR."\{currentX + 1}-\{currentY}", STR."\{currentX}-\{currentY}"))))
            {
                System.out.println(currentX + " " + currentY + "->" + (currentX + 1) + " " + currentY);
                hasEdge = true;
            }

            if((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY-1}")
                    || (gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY-1}"))))
            {
                System.out.println(currentX + " " + currentY + "->" + (currentX) + " " + (currentY-1));
                hasEdge = true;
            }

            if((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY+1}")
                    || (gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY+1}"))))
            {
                System.out.println(currentX + " " + currentY + "->" + (currentX) + " " + (currentY+1));
                hasEdge = true;
            }


            if (isFirstPlayer) {
                if (!(up1 || down1 || left1 || right1) && hasEdge)
                    return false;
            }
            else if(!(up2 || down2 || left2 || right2) && hasEdge)
                return false;

        }

        if(isFirstPlayer) {
            playerOneVertices.add(STR."\{mouseX}-\{mouseY}");
            playerOneNodes.add(STR."\{currentX}-\{currentY}");
        }
        else {
            playerTwoVertices.add(STR."\{mouseX}-\{mouseY}");
            playerTwoNodes.add(STR."\{currentX}-\{currentY}");
        }
        moveCounter += 1;
        return true;
    }

    public void generateLogicGraph(int rows, int columns)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                gameGraph.addVertex("" + i + "-" + j);
            }
        }
    }

    public void generateSticks(int rows, int columns)
    {
        var allVertices = gameGraph.vertexSet();
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (rand.nextInt(2) == 0) // top
                {
                    if (i + 1 < rows) {
                        gameGraph.addEdge("" + i + "-" + j, "" + (i + 1) + "-" + j);
                    }
                }
                if (rand.nextInt(2) == 0) //below
                {
                    if (i - 1 > 1) {
                        gameGraph.addEdge("" + i + "-" + j, "" + (i - 1) + "-" + j);

                    }
                }
                if (rand.nextInt(2) == 0) //right
                {
                    if (j + 1 < columns) {
                        gameGraph.addEdge("" + i + "-" + j, "" + i + "-" + (j + 1));

                    }
                }
                if (rand.nextInt(2) == 0) //left
                {
                    if (j - 1 > 1) {
                        gameGraph.addEdge("" + i + "-" + j, "" + i + "-" + (j - 1));
                    }
                }
            }
        }
    }

    public static synchronized GameLogic getInstance()
    {
        if(instance == null)
        {
            instance = new GameLogic();
        }
        return instance;
    }


}
