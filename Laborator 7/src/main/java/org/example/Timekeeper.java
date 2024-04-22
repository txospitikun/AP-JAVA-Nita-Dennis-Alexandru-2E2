package org.example;

public class Timekeeper extends Thread
{
    int time = 0;
    int maxTime = -1;

    GameLogic gameLogic;

    Timekeeper(int maxTime, GameLogic gameLogic)
    {
        this.maxTime = maxTime;
        this.gameLogic = gameLogic;
    }

    @Override
    public void run()
    {
        while(true && !gameLogic.exit) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            time += 1;
            if(time % 10 == 0)
                System.out.println("Current game has been running for: " + time + " seconds.");

            if(time >= maxTime)
            {
                System.out.println("Stopping game as time limit has been exceeded.");
                gameLogic.findWinner();
            }
        }

    }

}
