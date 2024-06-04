package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private GameServer server;
    private Player player;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String command;
            while ((command = input.readLine()) != null) {
                System.out.println("Message Received: " + command);
                String[] tokens = command.split(" ");
                String response = "";

                switch (tokens[0].toLowerCase()) {
                    case "stop":
                        response = "Server stopped";
                        server.stopServer();
                        break;
                    case "create":
                        int newGameId = server.createGame();
                        response = "Game created with ID: " + newGameId;
                        break;
                    case "join":
                        if (tokens.length < 3) {
                            response = "Usage: join <gameId> <playerName>";
                        } else {
                            int joinGameId = Integer.parseInt(tokens[1]);
                            String playerName = tokens[2];
                            Game game = server.getGame(joinGameId);
                            if (game != null) {
                                player = new Player(playerName, socket);
                                game.addPlayer(player);
                                response = "Joined game with ID: " + joinGameId;
                            } else {
                                response = "Game not found with ID: " + joinGameId;
                            }
                        }
                        break;
                    case "placeship":
                        if (tokens.length < 4 || player == null) {
                            response = "Usage: placeship <gameId> <coordinate> <direction>";
                        } else {
                            int placeGameId = Integer.parseInt(tokens[1]);
                            String coordinate = tokens[2];
                            String direction = tokens[3];
                            Game game = server.getGame(placeGameId);
                            if (game != null && !game.isReadyToStart()) {
                                if (game.placeShip(player, coordinate, direction)) {
                                    response = "Ship placed at: " + coordinate + " " + direction;
                                } else {
                                    response = "Failed to place ship at: " + coordinate + " " + direction;
                                }
                            } else {
                                response = "Game not found or already started";
                            }
                        }
                        break;
                    case "ready":
                        if (tokens.length < 2 || player == null) {
                            response = "Usage: ready <gameId>";
                        } else {
                            int readyGameId = Integer.parseInt(tokens[1]);
                            Game game = server.getGame(readyGameId);
                            if (game != null) {
                                game.setReady(player);
                                response = "Player is ready for game ID: " + readyGameId;
                            } else {
                                response = "Game not found";
                            }
                        }
                        break;
                    case "start":
                        if (tokens.length < 2 || player == null) {
                            response = "Usage: start <gameId>";
                        } else {
                            int startGameId = Integer.parseInt(tokens[1]);
                            Game game = server.getGame(startGameId);
                            if (game != null && game.isReadyToStart()) {
                                response = "Game started";
                            } else {
                                response = "Game not ready to start";
                            }
                        }
                        break;
                    case "move":
                        if (tokens.length < 3 || player == null) {
                            response = "Usage: move <gameId> <coordinate>";
                        } else {
                            int moveGameId = Integer.parseInt(tokens[1]);
                            String coordinate = tokens[2];
                            Game game = server.getGame(moveGameId);
                            if (game != null && game.isReadyToStart()) {
                                response = game.makeMove(player, coordinate);
                                if (game.isGameOver()) {
                                    String winner = game.getWinner();
                                    response += "\nGame over! Winner: " + winner;
                                    notifyPlayers(game, "Game over! Winner: " + winner);
                                }
                            } else {
                                response = "Game not found or not started";
                            }
                        }
                        break;
                    case "showmap":
                        if (tokens.length < 2 || player == null) {
                            response = "Usage: showmap <gameId>";
                        } else {
                            int mapGameId = Integer.parseInt(tokens[1]);
                            Game game = server.getGame(mapGameId);
                            if (game != null) {
                                response = game.showMap(player);
                            } else {
                                response = "Game not found";
                            }
                        }
                        break;
                    case "showattack":
                        if (tokens.length < 2 || player == null) {
                            response = "Usage: showattack <gameId>";
                        } else {
                            int attackGameId = Integer.parseInt(tokens[1]);
                            Game game = server.getGame(attackGameId);
                            if (game != null) {
                                response = game.showAttack(player);
                            } else {
                                response = "Game not found";
                            }
                        }
                        break;
                    default:
                        response = "Unknown command: " + command;
                        break;
                }

                output.println(response);
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Error in client thread: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void notifyPlayers(Game game, String message) {
        for (Player player : game.getPlayers()) {
            try {
                PrintWriter output = new PrintWriter(player.getSocket().getOutputStream(), true);
                output.println(message);
            } catch (IOException e) {
                System.out.println("Error notifying player: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
