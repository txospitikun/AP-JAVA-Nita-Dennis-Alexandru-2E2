package org.example;

public class Board {
    private static final int SIZE = 10;
    private static final int EMPTY = 0;
    private static final int SHIP = 1;
    private static final int HIT = 2;
    private int[][] grid;
    private int ships;

    public Board() {
        grid = new int[SIZE][SIZE];
        ships = 0;
    }

    public boolean placeShip(int row, int col, String direction) {
        if (ships >= 3) return false;

        int[] dRow = {0, 0, -1, 1}; // left, right, up, down
        int[] dCol = {-1, 1, 0, 0};
        int dirIndex;

        switch (direction.toLowerCase()) {
            case "left": dirIndex = 0; break;
            case "right": dirIndex = 1; break;
            case "up": dirIndex = 2; break;
            case "down": dirIndex = 3; break;
            default: return false;
        }

        for (int i = 0; i < 3; i++) {
            int newRow = row + i * dRow[dirIndex];
            int newCol = col + i * dCol[dirIndex];
            if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE || grid[newRow][newCol] != EMPTY) {
                return false;
            }
        }

        for (int i = 0; i < 3; i++) {
            int newRow = row + i * dRow[dirIndex];
            int newCol = col + i * dCol[dirIndex];
            grid[newRow][newCol] = SHIP;
        }

        ships++;
        return true;
    }

    public String attack(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return "Invalid move";
        }

        if (grid[row][col] == SHIP) {
            grid[row][col] = HIT;
            return "Hit";
        } else if (grid[row][col] == EMPTY) {
            grid[row][col] = HIT;
            return "Miss";
        } else {
            return "Already targeted";
        }
    }

    public boolean allShipsPlaced() {
        return ships == 3;
    }

    public boolean allShipsSunk() {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == SHIP) {
                    return false;
                }
            }
        }
        return true;
    }

    public String showMap() {
        StringBuilder map = new StringBuilder();
        map.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            map.append((char) ('A' + i)).append(" ");
            for (int j = 0; j < SIZE; j++) {
                map.append(grid[i][j] == SHIP ? "1 " : "0 ");
            }
            map.append("\n");
        }
        return map.toString();
    }

    public String showAttack() {
        StringBuilder map = new StringBuilder();
        map.append("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < SIZE; i++) {
            map.append((char) ('A' + i)).append(" ");
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == HIT) {
                    map.append("2 ");
                } else {
                    map.append("0 ");
                }
            }
            map.append("\n");
        }
        return map.toString();
    }
}
