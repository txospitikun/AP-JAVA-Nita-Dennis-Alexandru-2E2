package org.example;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import org.apache.commons.math3.geometry.spherical.twod.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.alg.*;

public class GameLogic implements Serializable
{
    @Serial
    private static final long serialVersionUID = -1273203341611544224L;
    private static GameLogic instance = null;
    public Graph<String, DefaultEdge> gameGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    public Set<String> playerOneVertices = new HashSet<>();
    public Set<String> playerOneNodes = new HashSet<>();
    public Set<String> playerTwoVertices = new HashSet<>();
    public Set<String> playerTwoNodes = new HashSet<>();
    boolean isFirstPlayer = true;
    int moveCounter = 0;

    public boolean validateMove(float mouseX, float mouseY, float A, float B, boolean justValidation, BooleanObject isFirstPlayerClone)
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

            if(isFirstPlayer) {
                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX - 1}-\{currentY}")) && up1) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX - 1) + " " + currentY);
                    hasEdge = true;
                }

                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX + 1}-\{currentY}")) && down1) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX + 1) + " " + currentY);
                    hasEdge = true;
                }

                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY - 1}")) && left1) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX) + " " + (currentY - 1));
                    hasEdge = true;
                }

                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY + 1}")) && right1) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX) + " " + (currentY + 1));
                    hasEdge = true;
                }
            }
            else
            {
                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX - 1}-\{currentY}")) && up2) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX - 1) + " " + currentY);
                    hasEdge = true;
                }

                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX + 1}-\{currentY}")) && down2) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX + 1) + " " + currentY);
                    hasEdge = true;
                }

                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY - 1}")) && left2) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX) + " " + (currentY - 1));
                    hasEdge = true;
                }

                if ((gameGraph.containsEdge(STR."\{currentX}-\{currentY}", STR."\{currentX}-\{currentY + 1}")) && right2) {
                    System.out.println(currentX + " " + currentY + "->" + (currentX) + " " + (currentY + 1));
                    hasEdge = true;
                }
            }


            if (isFirstPlayer) {
                if (!(up1 || down1 || left1 || right1) || !hasEdge)
                    return false;
            }
            else if(!(up2 || down2 || left2 || right2) || !hasEdge)
                return false;

        }

        if(!justValidation) {
            if (isFirstPlayer) {
                playerOneVertices.add(STR."\{mouseX}-\{mouseY}");
                playerOneNodes.add(STR."\{currentX}-\{currentY}");
            } else {
                playerTwoVertices.add(STR."\{mouseX}-\{mouseY}");
                playerTwoNodes.add(STR."\{currentX}-\{currentY}");
            }
            moveCounter += 1;
            isFirstPlayer = !isFirstPlayer;
        }
        isFirstPlayerClone.value = isFirstPlayer;

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
        Random rand = new Random();
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

    public static synchronized GameLogic restartInstance()
    {
        if(instance != null)
        {
            instance = null;
        }
        instance = new GameLogic();

        return instance;
    }

    public static synchronized GameLogic setInstance(GameLogic givenInstance)
    {
        instance = null;
        instance = givenInstance;
        return instance;
    }


}
