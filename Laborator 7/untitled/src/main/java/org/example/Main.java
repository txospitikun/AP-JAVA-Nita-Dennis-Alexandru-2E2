package org.example;

public class Main {
    public static void main(String[] args)
    {
        GameLogic logic = new GameLogic();
        logic.generateTokens(100);

        Player player1 = new Player("Dennis", 0);
        Player player2 = new Player("Bombica", 1);
        Player player3 = new Player("Cazan", 2);

        logic.addPlayer(player1);
        logic.addPlayer(player2);
        logic.addPlayer(player3);

        logic.startThreads();



    }
}