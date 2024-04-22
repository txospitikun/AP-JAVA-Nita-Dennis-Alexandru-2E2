package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.alg.cycle.PatonCycleBase;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class GameLogic
{
    public final Object monitor = new Object();
    public int currentTurn = 0;
    public boolean isFirstThreadEntered = false;
    List<Player> playerThreads = new ArrayList<>();
    Timekeeper timeKeeper = new Timekeeper(600, this);

    Random rand = new Random();
    int tokenCount;
    ArrayList<Pair> tokens = new ArrayList<>();

    boolean isBagEmpty = false;

    boolean exit = false;

    public boolean isInUse = false;

    public GameLogic()
    {
        timeKeeper.start();
    }
    public void generateTokens(int tokenCount)
    {
        this.tokenCount = tokenCount;
        Set<Integer> randomNumbers = new HashSet<>();

        int i1 = rand.nextInt(100);
        int i1copy = i1;
        int ik = -1;

        for(int i = 1; i < tokenCount; i++)
        {
            while(ik == i1)
            {
                ik = rand.nextInt(100);
            }
            tokens.add(new Pair(i1, ik));
            i1 = ik;
        }
        tokens.add(new Pair(ik, i1copy));
//         int j = 0;
//         while(j < 99)
//         {
//             tokens.add(new Pair(j, j+1));
//             j++;
//         }
//         tokens.add(new Pair(j, 0));
    }

    public synchronized List<Pair> extractTokens(int count)
    {
        ArrayList<Pair> extractedTokens = new ArrayList<>();
        for(int i = 0; i < count; i++)
        {
            if(tokens.isEmpty())
            {
                break;
            }
            int randomToken = rand.nextInt(tokens.size());
            extractedTokens.add(tokens.get(randomToken));
            tokens.remove(randomToken);
        }
        if(tokens.isEmpty())
        {
            this.isBagEmpty = true;
        }
        return extractedTokens;
    }

    public void findWinner()
    {
        Player winnerPlayer = null;
        int maxCycle = -1;
        for(var playerThread : playerThreads)
        {
            Graph<Integer, DefaultEdge> undirectedGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
            for(var pair : playerThread.playerTokens)
            {
                if(!undirectedGraph.containsVertex(pair.first))
                    undirectedGraph.addVertex(pair.first);

                if(!undirectedGraph.containsVertex(pair.second))
                    undirectedGraph.addVertex(pair.second);
            }

            for(var pair : playerThread.playerTokens)
            {
                undirectedGraph.addEdge(pair.first, pair.second);
            }

            PatonCycleBase<Integer, DefaultEdge> cycleDetector = new PatonCycleBase<>(undirectedGraph);

            System.out.println("For player: " + playerThread.name + " found: " + cycleDetector.getCycleBasis().getCycles().size() + " cycles.");
            System.out.println(cycleDetector.getCycleBasis().getCycles());

            int currentMaxCycle = 0;
            for(var cycle : cycleDetector.getCycleBasis().getCycles())
            {
                if(cycle.size() > currentMaxCycle)
                {
                    currentMaxCycle = cycle.size();
                }
            }

            System.out.println(playerThread.name +"'s amount of points won. " + currentMaxCycle);

            if(currentMaxCycle >= maxCycle)
            {
                maxCycle = cycleDetector.getCycleBasis().getCycles().size();
                winnerPlayer = playerThread;
            }

        }

        if(maxCycle == 0)
        {
            System.out.println("No winner!");
        }
        else
            System.out.println("The player: " + winnerPlayer.name + " has won the game!");

        exit = true;
    }
    public void addPlayer(Player player)
    {
        player.game = this;
        playerThreads.add(player);
    }

    public void startThreads()
    {
        for(var thread : playerThreads)
        {
            thread.start();
        }
    }
}
