package org.example;

import java.beans.IntrospectionException;
import java.util.Scanner;

public class Player extends Thread {
    public GameLogic game;
    public String name;
    public int turn;
    public boolean running = true;

    Player(String name, int turn) {
        this.name = name;
        this.turn = turn;
    }

    @Override
    public void run() {
        // de facut metode separate syncronized (run nu este niciodata functie syncronzied)
        while(turn != game.currentTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(name + " -> Enter the number of tokens you want to extract: ");
        int input = scanner.nextInt();
        var tokens = game.extractTokens(input);
        System.out.print("Player: " + this.name + " extracted: ");
        for (var token : tokens) {
            System.out.print("(" + token.first + " " + token.second + ") ");
        }
        System.out.println();
        game.currentTurn = (game.currentTurn + 1) % game.playerThreads.size();
        notifyAll();
    }
//   public void run1() {
//        while(running) {
//            synchronized (game) {
//                while (turn != game.currentTurn) {
//                    try {
//                        game.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Scanner scanner = new Scanner(System.in);
//                System.out.println(name + " -> Enter the number of tokens you want to extract: ");
//                int input = scanner.nextInt();
//                var tokens = game.extractTokens(input);
//                System.out.print("Player: " + this.name + " extracted: ");
//                for (var token : tokens) {
//                    System.out.print("(" + token.first + " " + token.second + ") ");
//                }
//                System.out.println();
//                game.currentTurn = (game.currentTurn + 1) % game.playerThreads.size();
//                game.notifyAll();
//            }
//        }
//   }


}