package uk.co.tdaly;

import javax.swing.*;

/**
 * Main class for opening application and getting initial input of dimensions and random live cells
 *
 */
public class Main {

    private static final int MIN_DIMENSION = 8;
    private static final int MAX_DIMENSION = 50;
    private static final int MIN_CELLS = 1;

    /**
     * Gets user input and validates against set minimum/maximum dimensions, then gets input again
     * for a count of random live cells to place between 1 and dimension^2
     * If both inputs are valid, creates a GameOfLife and GUI instance and starts game
     * @param args passed arguments
     */
    public static void main(String[] args) {
        boolean validInputs = false;
        int dimension, randomCells;
        dimension = randomCells = 0;
        do {
            String input = (String)JOptionPane.showInputDialog(null, "Welcome to the Game of Life\nEnter game dimensions (between " + MIN_DIMENSION +" and " + MAX_DIMENSION + "):");
            if (input != null && input.length() > 0) {
                try {
                    dimension = Integer.parseInt(input);
                    if (!(dimension >= MIN_DIMENSION && dimension <= MAX_DIMENSION)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"Dimensions must be an integer between " + MIN_DIMENSION + " and " + MAX_DIMENSION + "!");
                    continue;
                }
                int maxCells = dimension * dimension;
                input = (String)JOptionPane.showInputDialog(null, "Enter amount of live cells to be randomly placed (between " + MIN_CELLS + " and " + maxCells + "):");
                if (input != null && input.length() > 0) {
                    try {
                        randomCells = Integer.parseInt(input);
                        if (!(randomCells >= MIN_CELLS && randomCells <= maxCells)) {
                            throw new NumberFormatException();
                        }
                        validInputs = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Live cell amount must be an integer between " + MIN_CELLS + " and " + maxCells + "!");
                    }
                } else {
                    System.exit(1);
                }
            } else {
                System.exit(1);
            }
        } while (!validInputs);

        GameOfLife game = new GameOfLife(dimension, dimension);
        game.addRandomCells(randomCells);
        GUI gui = new GUI(game);
    }
}
