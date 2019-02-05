package uk.co.tdaly;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        boolean validInputs = false;
        int dimension, randomCells;
        dimension = randomCells = 0;
        do {
            String input = (String)JOptionPane.showInputDialog(null, "Welcome to the Game of Life\nEnter game dimensions (between 8 and 40):");
            if (input != null && input.length() > 0) {
                try {
                    dimension = Integer.parseInt(input);
                    if (!(dimension >= 8 && dimension <= 40)) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,"Dimensions must be an integer between 8 and 40!");
                    continue;
                }
                int maxCells = dimension * dimension;
                input = (String)JOptionPane.showInputDialog(null, "Enter amount of live cells to be randomly placed (between 1 and " + maxCells + "):");
                if (input != null && input.length() > 0) {
                    try {
                        randomCells = Integer.parseInt(input);
                        if (!(randomCells >= 1 && randomCells <= maxCells)) {
                            throw new NumberFormatException();
                        }
                        validInputs = true;
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Live cell amount must be an integer between 1 and " + maxCells + "!");
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
