package org.example;

public class Game {
    private int gameId;
    private Player[] players = new Player[2];
    private Board[] boards = new Board[2];
    private boolean[] ready = new boolean[2];
    private int currentPlayer = 0;

    public Game(int gameId) {
        this.gameId = gameId;
        boards[0] = new Board();
        boards[1] = new Board();
    }

    public int getGameId() {
        return gameId;
    }

    public synchronized void addPlayer(Player player) {
        if (players[0] == null) {
            players[0] = player;
        } else if (players[1] == null) {
            players[1] = player;
        }
    }

    public synchronized Player[] getPlayers() {
        return players;
    }

    public synchronized boolean placeShip(Player player, String coordinate, String direction) {
        int playerIndex = player.equals(players[0]) ? 0 : 1;
        int row = coordinate.charAt(0) - 'A';
        int col = Integer.parseInt(coordinate.substring(1)) - 1;
        return boards[playerIndex].placeShip(row, col, direction);
    }

    public synchronized void setReady(Player player) {
        int playerIndex = player.equals(players[0]) ? 0 : 1;
        ready[playerIndex] = true;
    }

    public synchronized boolean isReadyToStart() {
        return ready[0] && ready[1];
    }

    public synchronized String makeMove(Player player, String coordinate) {
        int playerIndex = player.equals(players[0]) ? 0 : 1;
        if (playerIndex != currentPlayer) {
            return "Not your turn";
        }

        int row = coordinate.charAt(0) - 'A';
        int col = Integer.parseInt(coordinate.substring(1)) - 1;
        String result = boards[1 - playerIndex].attack(row, col);

        if (result.equals("Hit") || result.equals("Miss")) {
            currentPlayer = 1 - currentPlayer; // Switch turns
        }

        return result;
    }

    public synchronized boolean isGameOver() {
        return boards[0].allShipsSunk() || boards[1].allShipsSunk();
    }

    public synchronized String getWinner() {
        if (!isGameOver()) {
            return "Game is not over yet";
        }
        return boards[0].allShipsSunk() ? players[1].getName() : players[0].getName();
    }

    public synchronized String showMap(Player player) {
        int playerIndex = player.equals(players[0]) ? 0 : 1;
        return boards[playerIndex].showMap();
    }

    public synchronized String showAttack(Player player) {
        int playerIndex = player.equals(players[0]) ? 0 : 1;
        return boards[1 - playerIndex].showAttack();
    }
}
