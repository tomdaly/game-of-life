package uk.co.tdaly;

public class Main {

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife(10, 10);
        GUI gui = new GUI(game);
    }
}
