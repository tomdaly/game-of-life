package uk.co.tdaly;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    private GameOfLife game;
    private MainPanel mainPanel;

    public GUI(GameOfLife game) {
        super("Game of Life");
        this.game = game;

        setSize(25 * game.getRows(), 25 * game.getColumns());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        add(mainPanel);
        setVisible(true);
    }

    class MainPanel extends JPanel {
        MainPanel() {
            setPreferredSize(new Dimension(getWidth() - 10, getHeight() - 10));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.BLACK);
        }
    }
}
