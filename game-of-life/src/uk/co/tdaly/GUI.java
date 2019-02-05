package uk.co.tdaly;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GUI extends JFrame{

    private GameOfLife game;
    private MainPanel mainPanel;
    private Timer iterationTimer;
    private GameIterator iterator;

    public GUI(GameOfLife game) {
        super("Game of Life");
        this.game = game;

        initialiseComponents();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        game.addRandomCells(20);
    }

    public void initialiseComponents() {
        mainPanel = new MainPanel();
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener( e -> {
            game.next();
            mainPanel.repaint();
        });
        JButton playButton = new JButton("Play");
        playButton.addActionListener( e -> {
            iterationTimer = new Timer();
            iterator = new GameIterator();
            iterationTimer.scheduleAtFixedRate(iterator, 0, game.getIterationPeriod());
            stepButton.setEnabled(false);
        });
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener( e -> {
            if (iterationTimer != null) {
                iterationTimer.cancel();
            }
            stepButton.setEnabled(true);
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton, BorderLayout.LINE_START);
        buttonPanel.add(pauseButton, BorderLayout.CENTER);
        buttonPanel.add(stepButton, BorderLayout.LINE_END);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
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

    class GameIterator extends TimerTask {
        @Override
        public void run() {
            game.next();
            mainPanel.repaint();
        }
    }
}
