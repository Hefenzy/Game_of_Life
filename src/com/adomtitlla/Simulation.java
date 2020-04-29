package com.adomtitlla;

public class Simulation {
    public int WIDTH;
    public int HEIGHT;
    public boolean[][] BOARD;

    public Simulation(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.BOARD = new boolean[width][height];
    }

    public void setAlive(int x, int y) {
        this.BOARD[x][y] = true;
    }

    public void setDead(int x, int y) {
        this.BOARD[x][y] = false;
    }

    public boolean getState(int x, int y) {
        return this.BOARD[x][y];
    }

    public int countAliveNeighbours(int x, int y) {
        int cnt = 0;

        if (x > 0 && x < this.WIDTH - 1 && y > 0 && y < this.HEIGHT - 1) {   // Not angle and not border
            if (this.BOARD[x - 1][y]) cnt++;    // Left
            if (this.BOARD[x + 1][y]) cnt++;    // Right
            if (this.BOARD[x][y - 1]) cnt++;    // Top
            if (this.BOARD[x][y + 1]) cnt++;    // Bottom

            if (this.BOARD[x - 1][y - 1]) cnt++;    // Left top
            if (this.BOARD[x - 1][y + 1]) cnt++;    // Left bottom
            if (this.BOARD[x + 1][y + 1]) cnt++;    // Right bottom
            if (this.BOARD[x + 1][y - 1]) cnt++;    // Right top
        }

        if (x == 0 && y == 0) {    // Left top angle
            if (this.BOARD[x + 1][y]) cnt++;        // Right
            if (this.BOARD[x + 1][y + 1]) cnt++;    // Right bottom
            if (this.BOARD[x][y + 1]) cnt++;        // Bottom
        }

        if (x == 0 && y == this.HEIGHT - 1) {    // Left bottom
            if (this.BOARD[x + 1][y]) cnt++;        // Right
            if (this.BOARD[x + 1][y - 1]) cnt++;    // Right top
            if (this.BOARD[x][y - 1]) cnt++;        // Top
        }

        if (x == this.WIDTH - 1 && y == this.HEIGHT - 1) {    // Right bottom angle
            if (this.BOARD[x - 1][y]) cnt++;        // Left
            if (this.BOARD[x - 1][y - 1]) cnt++;    // Left top
            if (this.BOARD[x][y - 1]) cnt++;        // Top
        }

        if (x == this.WIDTH - 1 && y == 0) {    // Right top angle
            if (this.BOARD[x - 1][y]) cnt++;        // Left
            if (this.BOARD[x - 1][y + 1]) cnt++;    // Left bottom
            if (this.BOARD[x][y + 1]) cnt++;        // Bottom
        }

        if (x != 0 && x != this.WIDTH - 1 && y == 0) {    // Top border
            if (this.BOARD[x - 1][y]) cnt++;        // Left
            if (this.BOARD[x - 1][y + 1]) cnt++;    // Left bottom
            if (this.BOARD[x][y + 1]) cnt++;        // Bottom
            if (this.BOARD[x + 1][y + 1]) cnt++;    // Right bottom
            if (this.BOARD[x + 1][y]) cnt++;        // Right
        }

        if (x == this.WIDTH - 1 && y != 0 && y != this.HEIGHT - 1) {    // Right border
            if (this.BOARD[x][y - 1]) cnt++;        // Top
            if (this.BOARD[x - 1][y - 1]) cnt++;    // Left top
            if (this.BOARD[x - 1][y]) cnt++;        // Left
            if (this.BOARD[x - 1][y + 1]) cnt++;    // Left Bottom
            if (this.BOARD[x][y + 1]) cnt++;        // Bottom
        }

        if (x != 0 && x != this.WIDTH - 1 && y == this.HEIGHT - 1) {    // Bottom border
            if (this.BOARD[x - 1][y]) cnt++;        // Left
            if (this.BOARD[x - 1][y - 1]) cnt++;    // Left top
            if (this.BOARD[x][y - 1]) cnt++;        // Top
            if (this.BOARD[x + 1][y - 1]) cnt++;    // Right top
            if (this.BOARD[x + 1][y]) cnt++;        // Right
        }

        if (x == 0 && y != 0 && y != this.HEIGHT - 1) {    // Left border
            if (this.BOARD[x][y + 1]) cnt++;        // Bottom
            if (this.BOARD[x + 1][y + 1]) cnt++;    // Right bottom
            if (this.BOARD[x + 1][y]) cnt++;        // Right
            if (this.BOARD[x + 1][y - 1]) cnt++;    // Right top
            if (this.BOARD[x][y - 1]) cnt++;        // Top
        }
        return cnt;
    }

    public void step() {    // Step method with birth, live and death of cells
        boolean[][] newBOARD = new boolean[this.WIDTH][this.HEIGHT]; // Every step make new board and replace old with it

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int aliveNeighbours = this.countAliveNeighbours(x,y); // Count alive neighbours with thay biggest method

                if (this.BOARD[x][y]) { // If cell is alive and it has 2 or 3 neighbours, it stays alive
                    if (aliveNeighbours == 2 || aliveNeighbours == 3) newBOARD[x][y] = true;
                    else newBOARD[x][y] = false;    // Else it dies
                }
                else {  // And if cell is not alive and has 3 neighbours, it comes to live
                    if (aliveNeighbours == 3) newBOARD[x][y] = true;
                }
            }
        }
        this.BOARD = newBOARD;  // Replace old board with new
    }
}