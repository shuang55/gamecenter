package fall2018.csc2017.slidingtiles;

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
        slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
    }

    //Testing begins here
    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwoBoardSize3() {
        setUpCorrect(3);
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
        setUpCorrect(3);
        assertEquals(8, slidingTileBoard.getTile(2, 1).getId());
        assertEquals(25, slidingTileBoard.getTile(2, 2).getId());
        slidingTileBoard.swapTiles(2, 2, 2, 1);
        assertEquals(25, slidingTileBoard.getTile(2, 1).getId());
        assertEquals(8, slidingTileBoard.getTile(2, 2).getId());
    }

    /**
     * Test whether swapping the last two tiles works on a board size 4.
     */
    @Test
    public void testSwapLastTwoBoardSize4() {
        setUpCorrect(4);
        assertEquals(15, slidingTileBoard.getTile(3, 2).getId());
        assertEquals(25, slidingTileBoard.getTile(3, 3).getId());
        slidingTileBoard.swapTiles(3, 3, 3, 2);
        assertEquals(25, slidingTileBoard.getTile(3, 2).getId());
        assertEquals(15, slidingTileBoard.getTile(3, 3).getId());
    }

    /**
     * Test whether swapping the last two tiles works on a board size 4.
     */
    @Test
    public void testSwapLastTwoBoardSize5() {
        setUpCorrect(5);
        assertEquals(24, slidingTileBoard.getTile(4, 3).getId());
        assertEquals(25, slidingTileBoard.getTile(4, 4).getId());
        slidingTileBoard.swapTiles(4, 4, 4, 3);
        assertEquals(25, slidingTileBoard.getTile(4, 3).getId());
        assertEquals(24, slidingTileBoard.getTile(4, 4).getId());
    }

    /**
     * Test whether findTile works.
     */
    @Test
    public void testFindTile(){
        setUpCorrect(4);
        assertEquals(15, slidingTileBoard.findTile(25));
        assertEquals(0, slidingTileBoard.findTile(1));
        assertEquals(11, slidingTileBoard.findTile(12));
    }
    
    @Test
    public void testIterator(){
        setUpCorrect(5);
        Iterator<Tile> iterator = slidingTileBoard.iterator();
        int i = 1;
        for(Tile tile = iterator.next(); iterator.hasNext(); i++){
            assertEquals(i, tile.getId());
            tile = iterator.next();
        }
    }
}

