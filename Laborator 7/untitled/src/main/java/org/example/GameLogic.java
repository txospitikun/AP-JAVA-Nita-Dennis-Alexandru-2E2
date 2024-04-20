package org.example;

import java.util.*;

public class GameLogic
{
    public final Object monitor = new Object();
    public int currentTurn = 0;
    public boolean isFirstThreadEntered = false;
    List<Thread> playerThreads = new ArrayList<>();
    Random rand = new Random();
    int tokenCount;
    ArrayList<Pair> tokens = new ArrayList<>();

    public boolean isInUse = false;
    public void generateTokens(int tokenCount)
    {
        this.tokenCount = tokenCount;
        Set<Integer> randomNumbers = new HashSet<>();

        int i1 = rand.nextInt(100);
        int i1copy = i1;
        int ik = -1;

        for(int i = 1; i < tokenCount; i++)
        {
            ik = rand.nextInt(100);
            tokens.add(new Pair(i1, ik));
            i1 = ik;
        }
        tokens.add(new Pair(ik, i1copy));
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
            extractedTokens.add(tokens.get(0));
            tokens.remove(0);
        }
        return extractedTokens;
    }
    public void addPlayer(Player player)
    {
        player.game = this;
        playerThreads.add(new Thread(player));
    }

    public void startThreads()
    {
        for(var thread : playerThreads)
        {
            thread.start();
        }
    }
}
