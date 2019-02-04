package uk.co.tdaly;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestGameOfLife {

    private GameOfLife game;

    @Test
    public void testInitialise() {
        game = new GameOfLife(3, 3);
        assertThat(game.getBoard().length, is(equalTo(3)));
        assertThat(game.getBoard()[0].length, is(equalTo(3)));
    }

    @Test
    public void testCountCells() {
        game = new GameOfLife(3, 3);
        game.setCell(1, 1, true);
        assertThat(game.getLiveCells(), is(equalTo(1)));
    }

    @Test
    public void testRandomSeed() {
        game = new GameOfLife(3, 3);
        game.addRandomCells(1);
        assertThat(game.getLiveCells(), is(equalTo(1)));
    }

    // Test Scenarios
    // - = empty cell
    // # = live cell

    //  ---    ---
    //  --- -> ---
    //  ---    ---
    @Test
    public void testNoInteractions() {
        game = new GameOfLife(3, 3);
        assertThat(game.getLiveCells(), is(equalTo(0)));
        game.next();
        assertThat(game.getLiveCells(), is(equalTo(0)));
    }

    //  ---    ---
    //  -#- -> ---
    //  -#-    ---
    @Test
    public void testUnderpopulation() {
        game = new GameOfLife(3, 3);
        game.setCell(1, 1, true);
        assertThat(game.getLiveCells(), is(equalTo(1)));
        game.next();
        assertThat(game.getLiveCells(), is(equalTo(0)));
    }

    //  -#-    ###
    //  ### -> #-#
    //  -#-    ###
    @Test
    public void testOvercrowding() {
        game = new GameOfLife(3, 3);
        game.setCell(0, 1, true);
        game.setCell(1, 0, true);
        game.setCell(1, 1, true);
        game.setCell(1, 2, true);
        game.setCell(2, 1, true);
        game.next();
        assertThat(game.getCell(1, 1), is(false));
    }

    //  ---    -#-
    //  ### -> -#-
    //  ---    -#-
    @Test
    public void testSurvivalOne() {
        game = new GameOfLife(3, 3);
        game.setCell(1, 0, true);
        game.setCell(1, 1, true);
        game.setCell(1, 2, true);
        game.next();
        assertThat(game.getCell(0, 1), is(true));
        assertThat(game.getCell(1, 1), is(true));
        assertThat(game.getCell(2, 1), is(true));
    }

    //  ##--    ##--    ##--
    //  ##-- -> #--- -> ##--
    //  --##    ---#    --##
    //  --##    --##    --##
    @Test
    public void testSurvivalTwo() {
        game = new GameOfLife(4, 4);
        game.setCell(0, 0, true);
        game.setCell(0, 1, true);
        game.setCell(1, 0, true);
        game.setCell(1, 1, true);
        game.setCell(2, 2, true);
        game.setCell(2, 3, true);
        game.setCell(3, 2, true);
        game.setCell(3, 3, true);
        game.next();
        assertThat(game.getCell(1, 1), is(false));
        assertThat(game.getCell(2, 2), is(false));
        game.next();
        assertThat(game.getCell(1, 1), is(true));
        assertThat(game.getCell(2, 2), is(false));
    }
}
