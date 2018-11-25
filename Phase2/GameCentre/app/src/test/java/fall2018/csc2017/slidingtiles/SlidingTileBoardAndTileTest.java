package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileBoardAndTileTest {

    /** The board manager for testing. */
    private SlidingTileBoardManager slidingTileBoardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int boardSize) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        if (boardSize == 3) {
            tiles.remove(8);
            Tile blankTile = new Tile(24);
            tiles.add(blankTile);
        }
        else if (boardSize == 4) {
            tiles.remove(15);
            Tile blankTile = new Tile(24);
            tiles.add(blankTile);
        }
        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect(int boardSize) {
        List<Tile> tiles = makeTiles(boardSize);
        SlidingTileBoard slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
        slidingTileBoardManager = new SlidingTileBoardManager(slidingTileBoard);
    }

    /**
     * Make a shuffled Board.
     */
    private void setUpShuffled(int boardSize) {
        List<Tile> tiles = makeTiles(boardSize);
        Collections.shuffle(tiles);
        SlidingTileBoard slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
        slidingTileBoardManager = new SlidingTileBoardManager(slidingTileBoard);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        slidingTileBoardManager.getSlidingTileBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect(3);
        assertTrue(slidingTileBoardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(slidingTileBoardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwoBoardSize3() {
        setUpCorrect(3);
        assertEquals(1, slidingTileBoardManager.getSlidingTileBoard().getTile(0, 0).getId());
        assertEquals(2, slidingTileBoardManager.getSlidingTileBoard().getTile(0, 1).getId());
        slidingTileBoardManager.getSlidingTileBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTileBoardManager.getSlidingTileBoard().getTile(0, 0).getId());
        assertEquals(1, slidingTileBoardManager.getSlidingTileBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works on a board size 3.
     */
    @Test
    public void testSwapLastTwoBoardSize3() {
        setUpCorrect(3);
        assertEquals(8, slidingTileBoardManager.getSlidingTileBoard().getTile(2, 1).getId());
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(2, 2).getId());
        slidingTileBoardManager.getSlidingTileBoard().swapTiles(2, 2, 2, 1);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(2, 1).getId());
        assertEquals(8, slidingTileBoardManager.getSlidingTileBoard().getTile(2, 2).getId());
    }

    /**
     * Test whether swapping the last two tiles works on a board size 4.
     */
    @Test
    public void testSwapLastTwoBoardSize4() {
        setUpCorrect(4);
        assertEquals(15, slidingTileBoardManager.getSlidingTileBoard().getTile(3, 2).getId());
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(3, 3).getId());
        slidingTileBoardManager.getSlidingTileBoard().swapTiles(3, 3, 3, 2);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(3, 2).getId());
        assertEquals(15, slidingTileBoardManager.getSlidingTileBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether swapping the last two tiles works on a board size 4.
     */
    @Test
    public void testSwapLastTwoBoardSize5() {
        setUpCorrect(5);
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        slidingTileBoardManager.getSlidingTileBoard().swapTiles(4, 4, 4, 3);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect(4);
        assertTrue(slidingTileBoardManager.isValidTap(11));
        assertTrue(slidingTileBoardManager.isValidTap(14));
        assertFalse(slidingTileBoardManager.isValidTap(10));
    }

    /**
     * Test whether findTile works.
     */
    @Test
    public void testFindTile(){
        setUpCorrect(4);
        assertEquals(15, slidingTileBoardManager.getSlidingTileBoard().findTile(25));
        assertEquals(0, slidingTileBoardManager.getSlidingTileBoard().findTile(1));
        assertEquals(11, slidingTileBoardManager.getSlidingTileBoard().findTile(12));
    }

    /**
     * Test whether TouchMove works.
     */
    @Test
    public void testTouchMove(){
        setUpCorrect(5);
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        slidingTileBoardManager.touchMove(23);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        slidingTileBoardManager.touchMove(22);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 2).getId());
        assertEquals(23, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
    }

    /**
     * Test whether UndoMove works.
     */
    @Test
    public void testUndoMove(){
        setUpCorrect(5);
        assertFalse(slidingTileBoardManager.undoMove());
        slidingTileBoardManager.touchMove(23);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        assertTrue(slidingTileBoardManager.undoMove());
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
    }
}

