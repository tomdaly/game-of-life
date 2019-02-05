package uk.co.tdaly;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GameOfLife {

    private boolean[][] board;
    private int rows;
    private int columns;
    private int numIterations;
    private int iterationPeriod;

    public GameOfLife(int rows, int columns) {
        board = new boolean[rows][columns]; 
        this.rows = rows;
        this.columns = columns;
        iterationPeriod = 500; // default 500ms
    }

    public boolean[][] getBoard() {
        return this.board;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getNumIterations() {
        return this.numIterations;
    }

    public int getIterationPeriod() {
        return iterationPeriod;
    }

    public void setIterationPeriod(int period) {
        this.iterationPeriod = period;
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
                if (getCell(i, j)) {
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
        boolean[][] boardCopy = Arrays.stream(getBoard())
                                      .map(boolean[]::clone)
                                      .toArray(boolean[][]::new);
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                int neighbours = getNumNeighbours(row, col, boardCopy);
                if (neighbours < 2) {  // underpopulation
                    setCell(row, col, false);
                } else if (neighbours > 3) { // overcrowding
                    setCell(row, col, false);
                } else if (neighbours == 3) { // creation
                    setCell(row, col, true);
                } // survival leaves cells in same state
            }
        }
        numIterations++;
    }

    public int getNumNeighbours(int row, int col, boolean[][] iterBoard) {
        int minRow, maxRow, minCol, maxCol;
        minRow = row - 1;
        maxRow = row + 1;
        minCol = col - 1;
        maxCol = col + 1;
        if (minRow < 0) {
            minRow = 0;
        } else if (maxRow >= getRows()) {
            maxRow = getRows() - 1;
        }
        if (minCol < 0) {
            minCol = 0;
        } else if (maxCol >= getColumns()) {
            maxCol = getColumns() - 1;
        }

        int neighbours = 0;
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (iterBoard[i][j]) {
                    neighbours++;
                }
                if (neighbours > 3) {
                    return neighbours;
                }
            }
        }

        return neighbours;
    }
}
