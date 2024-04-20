package org.example;

public class Timekeeper extends Thread
{
    int time = 0;
    int maxTime = -1;

    Timekeeper(int maxTime)
    {
        this.maxTime = maxTime;
    }

    @Override
    public void run()
    {
        while(true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            time += 1;
            System.out.println("Current game has been running for: " + time + " seconds.");

            if(time >= maxTime )
            {
                System.out.println("Stopping game as time limit has been exceeded.");
                System.exit(0);
            }
        }

    }

}
