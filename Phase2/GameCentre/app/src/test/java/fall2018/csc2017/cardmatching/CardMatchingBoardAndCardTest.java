package fall2018.csc2017.cardmatching;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CardMatchingBoardAndCardTest {

    /** The board manager for testing. */
    private CardMatchingBoardManager CardMatchingBoardManager;

    /**
     * Make a solved Board.
     */
    private void setUpCorrect(int boardSize) {
        CardMatchingBoardManager = new CardMatchingBoardManager(4, true);
    }

//    /**
//     * Shuffle a few tiles.
//     */
//    private void swapFirstTwoTiles() {
//        CardMatchingBoardManager.getCardMatchingBoard().swapTiles(0, 0, 0, 1);
//    }
//
//    /**
//     * Test whether swapping two tiles makes a solved board unsolved.
//     */
//    @Test
//    public void testIsSolved() {
//        setUpCorrect(3);
//        assertTrue(cardMatchingBoardManager.puzzleSolved());
//        swapFirstTwoTiles();
//        assertFalse(cardMatchingBoardManager.puzzleSolved());
//    }
//
//    /**
//     * Test whether swapping the first two tiles works.
//     */
//    @Test
//    public void testSwapFirstTwoBoardSize3() {
//        setUpCorrect(3);
//        assertEquals(1, cardMatchingBoardManager.getSlidingTileBoard().getTile(0, 0).getId());
//        assertEquals(2, cardMatchingBoardManager.getSlidingTileBoard().getTile(0, 1).getId());
//        cardMatchingBoardManager.getSlidingTileBoard().swapTiles(0, 0, 0, 1);
//        assertEquals(2, cardMatchingBoardManager.getSlidingTileBoard().getTile(0, 0).getId());
//        assertEquals(1, cardMatchingBoardManager.getSlidingTileBoard().getTile(0, 1).getId());
//    }
//
//    /**
//     * Test whether swapping the last two tiles works on a board size 3.
//     */
//    @Test
//    public void testSwapLastTwoBoardSize3() {
//        setUpCorrect(3);
//        assertEquals(8, cardMatchingBoardManager.getSlidingTileBoard().getTile(2, 1).getId());
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(2, 2).getId());
//        cardMatchingBoardManager.getSlidingTileBoard().swapTiles(2, 2, 2, 1);
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(2, 1).getId());
//        assertEquals(8, cardMatchingBoardManager.getSlidingTileBoard().getTile(2, 2).getId());
//    }
//
//    /**
//     * Test whether swapping the last two tiles works on a board size 4.
//     */
//    @Test
//    public void testSwapLastTwoBoardSize4() {
//        setUpCorrect(4);
//        assertEquals(15, cardMatchingBoardManager.getSlidingTileBoard().getTile(3, 2).getId());
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(3, 3).getId());
//        cardMatchingBoardManager.getSlidingTileBoard().swapTiles(3, 3, 3, 2);
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(3, 2).getId());
//        assertEquals(15, cardMatchingBoardManager.getSlidingTileBoard().getTile(3, 3).getId());
//    }
//
//    /**
//     * Test whether swapping the last two tiles works on a board size 4.
//     */
//    @Test
//    public void testSwapLastTwoBoardSize5() {
//        setUpCorrect(5);
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        cardMatchingBoardManager.getSlidingTileBoard().swapTiles(4, 4, 4, 3);
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//    }
//
//    /**
//     * Test whether isValidHelp works.
//     */
//    @Test
//    public void testIsValidTap() {
//        setUpCorrect(4);
//        assertTrue(cardMatchingBoardManager.isValidTap(11));
//        assertTrue(cardMatchingBoardManager.isValidTap(14));
//        assertFalse(cardMatchingBoardManager.isValidTap(10));
//    }
//
//    /**
//     * Test whether findTile works.
//     */
//    @Test
//    public void testFindTile(){
//        setUpCorrect(4);
//        assertEquals(15, cardMatchingBoardManager.getSlidingTileBoard().findTile(25));
//        assertEquals(0, cardMatchingBoardManager.getSlidingTileBoard().findTile(1));
//        assertEquals(11, cardMatchingBoardManager.getSlidingTileBoard().findTile(12));
//    }
//
//    /**
//     * Test whether TouchMove works.
//     */
//    @Test
//    public void testTouchMove(){
//        setUpCorrect(5);
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        cardMatchingBoardManager.touchMove(23);
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        cardMatchingBoardManager.touchMove(22);
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 2).getId());
//        assertEquals(23, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//    }
//
//    /**
//     * Test whether UndoMove works.
//     */
//    @Test
//    public void testUndoMove(){
//        setUpCorrect(5);
//        assertFalse(cardMatchingBoardManager.undoMove());
//        cardMatchingBoardManager.touchMove(23);
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        assertTrue(cardMatchingBoardManager.undoMove());
//        assertEquals(25, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        assertEquals(24, cardMatchingBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//    }
//
//
//    /**
//     * Test whether board is solvable for board size 3.
//     */
//    @Test
//    public void testSolvabilityBoardSize3(){
//        cardMatchingBoardManager = new CardMatchingBoardManager(3);
//        Card[][] tiles = cardMatchingBoardManager.getSlidingTileBoard().getTiles();
//        List<Card> tilesList = new ArrayList<>();
//        for(int i = 0; i != 3; i++){
//            for(int j = 0; j != 3; j++){
//                tilesList.add(tiles[i][j]);
//            }
//        }
//        assertTrue(checkSolvability(tilesList, 3));
//    }
//
//    /**
//     * Test whether board is solvable for board size 4.
//     */
//    @Test
//    public void testSolvabilityBoardSize4(){
//        cardMatchingBoardManager = new CardMatchingBoardManager(4);
//        Card[][] tiles = cardMatchingBoardManager.getSlidingTileBoard().getTiles();
//        List<Card> tilesList = new ArrayList<>();
//        for(int i = 0; i != 4; i++){
//            for(int j = 0; j != 4; j++){
//                tilesList.add(tiles[i][j]);
//            }
//        }
//        assertTrue(checkSolvability(tilesList, 4));
//    }
//
//    /**
//     * Test whether board is solvable for board size 5.
//     */
//    @Test
//    public void testSolvabilityBoardSize5(){
//        cardMatchingBoardManager = new CardMatchingBoardManager(5);
//        Card[][] tiles = cardMatchingBoardManager.getSlidingTileBoard().getTiles();
//        List<Card> tilesList = new ArrayList<>();
//        for(int i = 0; i != 5; i++){
//            for(int j = 0; j != 5; j++){
//                tilesList.add(tiles[i][j]);
//            }
//        }
//        assertTrue(checkSolvability(tilesList, 5));
//    }
}

