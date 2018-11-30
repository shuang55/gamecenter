package fall2018.csc2017.slidingtiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileBoardTest {

    //Set up for testings.
    /** The board manager for testing. */
    private SlidingTileBoard slidingTileBoard;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 9;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.remove(8);
        Tile blankTile = new Tile(24);
        tiles.add(blankTile);
        return tiles;
    }

    /**
     * Make a solved Board.
     */
    @Before
    public void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        slidingTileBoard = new SlidingTileBoard(tiles, 3);
    }

    /**
     * Make the slidingTileBoard = null after finishing testing.
     */
    @After
    public void tearDown(){
        slidingTileBoard = null;
    }

    //Testing begins here
    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwoBoardSize3() {
        assertEquals(1, slidingTileBoard.getTile(0, 0).getId());
        assertEquals(2, slidingTileBoard.getTile(0, 1).getId());
        slidingTileBoard.swapTiles(0, 0, 0, 1);
        assertEquals(2, slidingTileBoard.getTile(0, 0).getId());
        assertEquals(1, slidingTileBoard.getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works on a board size 3.
     */
    @Test
    public void testSwapLastTwoBoardSize3() {
        assertEquals(8, slidingTileBoard.getTile(2, 1).getId());
        assertEquals(25, slidingTileBoard.getTile(2, 2).getId());
        slidingTileBoard.swapTiles(2, 2, 2, 1);
        assertEquals(25, slidingTileBoard.getTile(2, 1).getId());
        assertEquals(8, slidingTileBoard.getTile(2, 2).getId());
    }

    /**
     * Test whether findTile works.
     */
    @Test
    public void testFindTile(){
        assertEquals(8, slidingTileBoard.findTile(25));
        assertEquals(0, slidingTileBoard.findTile(1));
        assertEquals(5, slidingTileBoard.findTile(6));
    }

    /**
     * Test whether toString works.
     */
    @Test
    public void testToString(){
        assertEquals("SlidingTileBoard{tiles=[Tile(1), Tile(2), Tile(3), " +
                "Tile(4), Tile(5), Tile(6), Tile(7), Tile(8), Tile(25)]}",
                slidingTileBoard.toString());
    }

    /**
     * Test whether getBoardSize() work on board size 3.
     */
    @Test
    public void testGetBoardSize3(){
        assertEquals(3, slidingTileBoard.getBoardSize());
    }

    /**
     * Test whether getBoardSize() work on board size 4.
     */
    public void testGetBoardSize4() { assertEquals(4, slidingTileBoard.getBoardSize()); }

    /**
     * Test whether getBoardSize work on board size 5.
     */
    public void testGetBoardSize5() { assertEquals(5, slidingTileBoard.getBoardSize()); }

    /**
     * Test whether getTile returns the right tile.
     */
    @Test
    public void testGetTile(){
        assertEquals(25, slidingTileBoard.getTile(2, 2).getId());
        slidingTileBoard.swapTiles(2, 2, 2, 1);
        assertEquals(8, slidingTileBoard.getTile(2, 2).getId());
    }

    /**
     * Test whether Iterator works.
     */
    @Test
    public void testIterator(){
        Iterator<Tile> iterator = slidingTileBoard.iterator();
        int i = 1;
        for(Tile tile = iterator.next(); iterator.hasNext(); i++){
            assertEquals(i, tile.getId());
            tile = iterator.next();
        }
    }
}

