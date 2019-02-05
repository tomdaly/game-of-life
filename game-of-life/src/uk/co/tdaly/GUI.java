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
    private JScrollBar timerScrollbar;
    private JButton stepButton;
    private JLabel iterationsLabel;
    private JLabel periodLabel;
    private JLabel cellsLabel;

    public GUI(GameOfLife game) {
        super("Game of Life");
        this.game = game;

        initialiseComponents();
        pack();
        validate();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        iterator = new GameIterator();
    }

    private void initialiseComponents() {
        mainPanel = new MainPanel();
        iterationsLabel = new JLabel("Iteration 0");
        periodLabel = new JLabel(game.getIterationPeriod() + "ms");
        cellsLabel = new JLabel("Live cells " + game.getLiveCells());
        JPanel labelPanel = new JPanel();
        labelPanel.add(iterationsLabel, BorderLayout.LINE_START);
        labelPanel.add(cellsLabel, BorderLayout.CENTER);
        labelPanel.add(periodLabel, BorderLayout.LINE_END);

        stepButton = new JButton("Step");
        stepButton.addActionListener( e -> {
            iterator.iterate();
        });
        JButton playButton = new JButton("Play");
        playButton.addActionListener( e -> {
            iterator.play();
        });
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener( e -> {
            iterator.pause();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton, BorderLayout.LINE_START);
        buttonPanel.add(pauseButton, BorderLayout.CENTER);
        buttonPanel.add(stepButton, BorderLayout.LINE_END);

        timerScrollbar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, 10);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(labelPanel);
        add(mainPanel);
        add(buttonPanel);
        add(timerScrollbar);
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
            int period = 1000 - timerScrollbar.getValue() * 100;
            if (period != game.getIterationPeriod()) {
                game.setIterationPeriod(period);
                pause();
                play();
            }
            iterate();
        }

        private void iterate() {
            game.next();
            mainPanel.repaint();
            iterationsLabel.setText("Iteration " + game.getNumIterations());
            periodLabel.setText("Period " + game.getIterationPeriod() + "ms");
            cellsLabel.setText("Live cells " + game.getLiveCells());
        }

        private void play() {
            iterationTimer = new Timer();
            iterator = new GameIterator();
            iterationTimer.scheduleAtFixedRate(iterator, 0, game.getIterationPeriod());
            stepButton.setEnabled(false);
        }

        private void pause() {
            iterationTimer.cancel();
            stepButton.setEnabled(true);
        }
    }
}
