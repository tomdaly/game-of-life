package uk.co.tdaly;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{

    private GameOfLife game;
    private MainPanel mainPanel;

    public GUI(GameOfLife game) {
        super("Game of Life");
        this.game = game;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = new MainPanel();
        add(mainPanel, BorderLayout.CENTER);
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.next();
                mainPanel.repaint();
            }
        });
        add(stepButton, BorderLayout.PAGE_END);
        pack();
        setVisible(true);

        game.addRandomCells(20);
    }

    class MainPanel extends JPanel {

        private int cellWidth = 25;
        private int cellHeight = 25;

        MainPanel() {
            setPreferredSize(new Dimension(game.getRows() * 25, game.getRows() * 25));
            setBackground(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.BLACK);
            for (int row = 0; row < game.getRows(); row++) {
                for (int col = 0; col < game.getColumns(); col++) {
                    if (game.getCell(row, col)) {
                        g.fillRect(row * cellWidth, col * cellHeight, cellWidth, cellHeight);
                    }
                }
            }
        }
    }
}
