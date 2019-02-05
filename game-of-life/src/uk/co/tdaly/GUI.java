package uk.co.tdaly;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * GUI for game, consisting of a frame with four main panels for labels, the game board,
 * play/pause/step buttons and a period scrollbar
 *
 */
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

    /**
     * Constructor
     * Creates JFrame with components and creates a new game iterator
     * @param game
     */
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

    /**
     * Creates all components for frame
     * Creates main panel for game board, labels panel for 'iteration', 'live cells' and 'period' labels,
     * buttons panel for 'play', 'pause' and 'step' buttons, and a scrollbar component to change the iteration period
     */
    private void initialiseComponents() {
        mainPanel = new MainPanel();
        iterationsLabel = new JLabel("Iteration 0");
        cellsLabel = new JLabel("Live cells " + game.getLiveCells());
        periodLabel = new JLabel(game.getIterationPeriod() + "ms");
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

    /**
     * Game board panel to draw game each iteration
     */
    class MainPanel extends JPanel {
        private int cellWidth = 20;
        private int cellHeight = 20;

        /**
         * Constructor
         * Sets size of panel and background colour
         */
        MainPanel() {
            setPreferredSize(new Dimension(game.getRows() * cellWidth, game.getRows() * cellHeight));
            setBackground(Color.WHITE);
        }

        /**
         * Draws live cells on panel each iteration
         * @param g graphics
         */
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

    /**
     * Iterator timer task to increment iterations and update game panel
     */
    class GameIterator extends TimerTask {
        /**
         * Gets period from scrollbar and iterates game, called every period
         */
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

        /**
         * Iterates game once, repaints main board and changes text of labels
         */
        private void iterate() {
            game.next();
            mainPanel.repaint();
            iterationsLabel.setText("Iteration " + game.getNumIterations());
            periodLabel.setText("Period " + game.getIterationPeriod() + "ms");
            cellsLabel.setText("Live cells " + game.getLiveCells());
        }

        /**
         * Sets board into play mode when 'play' button pressed, schedules timer run() function
         * Disables 'step' button during play
         */
        private void play() {
            iterationTimer = new Timer();
            iterator = new GameIterator();
            iterationTimer.scheduleAtFixedRate(iterator, 0, game.getIterationPeriod());
            stepButton.setEnabled(false);
        }

        /**
         * Pauses game by cancelling timer
         */
        private void pause() {
            iterationTimer.cancel();
            stepButton.setEnabled(true);
        }
    }
}
