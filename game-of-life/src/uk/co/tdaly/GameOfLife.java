package uk.co.tdaly;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main Game class
 * Contains board, algorithms and helper functions for complete game
 *
 */
public class GameOfLife {
    private boolean[][] board;
    private int rows;
    private int columns;
    private int numIterations;
    private int iterationPeriod;

    /**
     * Constructor
     * Creates game board and initialises row and column values
     * @param rows amount of rows on game board
     * @param columns amount of columns on game board
     */
    public GameOfLife(int rows, int columns) {
        board = new boolean[rows][columns]; 
        this.rows = rows;
        this.columns = columns;
        iterationPeriod = 1000; // default 1000ms
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

    /**
     * Sets state of cell
     * @param row row of cell to set
     * @param col column of cell to set
     * @param state true is live cell, false is dead cell
     */
    public void setCell(int row, int col, boolean state) {
        this.board[row][col] = state;
    }

    public boolean getCell(int row, int col) {
        return getBoard()[row][col];
    }

    /**
     * Gets number of live cells on the board
     * @return amount of live cells on board
     */
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

    /**
     * Adds a number of cells to the board in random locations
     * @param numCells number of cells to place
     */
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

    /**
     * Iterates board once
     * Creates a shallow copy of the board to be used to check neighbouring cells
     * between state changes of original board, and sets cell state based on neighbours
     * and set scenarios
     */
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

    /**
     * Gets number of neighbours in 3x3 grid for each cell, excluding self
     * @param row row of cell to check
     * @param col column of cell to check
     * @param iterBoard current iteration's board state
     * @return number of neighbours for given cell
     */
    public int getNumNeighbours(int row, int col, boolean[][] iterBoard) {
        int neighbours = 0;
        for (int i = Math.max(0, row - 1); i <= Math.min(getRows() - 1, row + 1); i++) {
            for (int j = Math.max(0, col - 1); j <= Math.min(getColumns() - 1, col + 1); j++) {
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
