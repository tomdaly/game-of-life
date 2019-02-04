package uk.co.tdaly;

import java.util.concurrent.ThreadLocalRandom;

public class GameOfLife {

    private boolean[][] board;
    private int rows;
    private int columns;
    private int numIterations;
    private float iterationPeriod;

    public GameOfLife(int rows, int columns) {
        board = new boolean[rows][columns]; 
        this.rows = rows;
        this.columns = columns;
    }

    public boolean[][] getBoard() {
        return this.board;
    }

    private int getRows() {
        return this.rows;
    }

    private int getColumns() {
        return this.columns;
    }

    public void setCell(int row, int col, boolean state) {
        this.board[row][col] = state;
    }

    public boolean getCell(int row, int col) {
        return getBoard()[row][col];
    }

    public int getLiveCells() {
        int liveCells = 0;
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                if (getBoard()[i][j]) {
                    liveCells++;
                }
            }
        }
        return liveCells;
    }
   
    public void addRandomCells(int numCells) {
        int rndRow;
        int rndCol;
        while (numCells > 0) {
            rndRow = ThreadLocalRandom.current().nextInt(0, getRows());
            rndCol = ThreadLocalRandom.current().nextInt(0, getColumns());
            if(!getCell(rndRow, rndCol)) { // if cell isn't empty, loop again without setting
                setCell(rndRow, rndCol, true);
                numCells--;
            }
        }
    }

    public void next() {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                int neighbours = getNumNeighbours(row, col);
            }
        }
    }

    public int getNumNeighbours(int row, int col) {
        int minRow, maxRow, minCol, maxCol;
        minRow = row - 1;
        maxRow = row + 1;
        minCol = col - 1;
        maxCol = col + 1;
        if (row - 1 < 0) {
            minRow = 0;
        } else if (row + 1 >= getRows()) {
            maxRow = getRows() - 1;
        }
        if (col - 1 < 0) {
            minCol = 0;
        } else if (col + 1 >= getColumns()) {
            maxCol = getColumns() - 1;
        }

        int neighbours = 0;
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (getBoard()[i][j]) {
                    neighbours++;
                }
            }
        }

        return neighbours;
    }
}
