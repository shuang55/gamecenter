package fall2018.csc2017.cardmatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CardMatchingBoardManagerTest {

    //Set up for testings.
    /**
     * The board manager for testing.
     */
    private CardMatchingBoardManager cardMatchingBoardManager;
    @Before
    public void setUp(){cardMatchingBoardManager = new CardMatchingBoardManager(8, true);}
    @After
    public void tearDown() {
        cardMatchingBoardManager = null;
    }
    @Test
    public void testGetGameName(){
        assertEquals("Card Matching", cardMatchingBoardManager.getGameName());
    }
    @Test
    public void testGetGameDifficulty(){
        assertEquals("4 by 4", cardMatchingBoardManager.getGameDifficulty());
    }
    @Test
    public void testPuzzleSolvedFalse(){
        assertFalse(cardMatchingBoardManager.puzzleSolved());
    }
    @Test
    public void testPuzzleSolvedHalfFinished(){
        for (int i = 0; i <= 7; i++){
            cardMatchingBoardManager.touchMove(i);
        }
        assertFalse(cardMatchingBoardManager.puzzleSolved());
    }
    @Test
    public void testIsValidTap(){
        cardMatchingBoardManager.touchMove(2);
        assertFalse(cardMatchingBoardManager.isValidTap(2));
        assertTrue(cardMatchingBoardManager.isValidTap(3));
    }
    @Test
    public void testGetScore(){
        for (int i = 0; i <= 15; i++){
            cardMatchingBoardManager.touchMove(i);
        }
        assertEquals(840, cardMatchingBoardManager.getScore());
    }
    @Test
    public void testGetMove(){
        cardMatchingBoardManager.touchMove(1);
        assertEquals(1, cardMatchingBoardManager.getMove());
    }


}

//    /**
//     * Make a set of tiles that are in order.
//     * @return a set of tiles that are in order
//     */
//    private List<Card> makeCards(int numCardPair, boolean easyBoard ) {
//        List<Card> tiles = new ArrayList<>();
//        final int numCards = boardSize * boardSize;
//        for (int tileNum = 0; tileNum != numCards; tileNum++) {
//            tiles.add(new Card(tileNum));
//        }
//        if (boardSize == 3) {
//            tiles.remove(8);
//            Card blankCard = new Card(24);
//            tiles.add(blankCard);
//        }
//        else if (boardSize == 4) {
//            tiles.remove(15);
//            Card blankCard = new Card(24);
//            tiles.add(blankCard);
//        }
//        return tiles;
//    }
//
//    /**
//     * Make a solved Board.
//     */
//    private void setUpCorrect(int boardSize) {
//        List<Card> tiles = makeCards(boardSize);
//        SlidingTileBoard slidingTileBoard = new SlidingTileBoard(tiles, boardSize);
//        slidingTileBoardManager = new SlidingTileBoardManager(slidingTileBoard);
//    }
//
//    /**
//     * Checks if the current sliding tile board is solvable. If it is not solvable, make it solvable
//     *
//     * @param boardSize size of the board
//     * @param tiles a list containing tiles for sliding tile game
//     * reference: https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
//     */
//    private boolean checkSolvability(List<Tile> tiles, int boardSize) {
//        int blankId = 25;
//        int blankRow = slidingTileBoardManager.getSlidingTileBoard().findTile(blankId) / boardSize;
//        int inversionCount = getInversionCount(tiles);
//        return (boardSize % 2 == 1 && inversionCount % 2 == 0 ||
//                boardSize % 2 == 0 && (inversionCount + boardSize - 1 - blankRow) % 2 == 0);
//    }
//
//    /**
//     * Calculates the number of inversions in sliding tile board.
//     *
//     * @param tiles a list containing tiles for sliding tile game
//     * @return number of inversions
//     */
//    private int getInversionCount(List<Tile> tiles) {
//        int inversionCount = 0;
//        for (int i = 0; i < tiles.size() - 1; i++)
//            for (int j = i + 1; j < tiles.size(); j++)
//                if (tiles.get(i).getId() != 25 && tiles.get(j).getId() != 25)
//                    if (tiles.get(i).getId() > tiles.get(j).getId())
//                        inversionCount++;
//        return inversionCount;
//    }
//
//    // Testing begins here
//    /**
//     * Test whether isValidHelp works.
//     */
//    @Test
//    public void testIsValidTap() {
//        setUpCorrect(4);
//        assertTrue(slidingTileBoardManager.isValidTap(11));
//        assertTrue(slidingTileBoardManager.isValidTap(14));
//        assertFalse(slidingTileBoardManager.isValidTap(10));
//    }
//
//    /**
//     * Test whether swapping two tiles makes a solved board unsolved.
//     */
//    @Test
//    public void testIsSolved() {
//        setUpCorrect(3);
//        assertTrue(slidingTileBoardManager.puzzleSolved());
//        slidingTileBoardManager.getSlidingTileBoard().swapTiles(0, 0, 0, 1);
//        assertFalse(slidingTileBoardManager.puzzleSolved());
//    }
//
//    /**
//     * Test whether TouchMove works.
//     */
//    @Test
//    public void testTouchMove(){
//        setUpCorrect(5);
//        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        slidingTileBoardManager.touchMove(23);
//        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        slidingTileBoardManager.touchMove(22);
//        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 2).getId());
//        assertEquals(23, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//    }
//
//    /**
//     * Test whether UndoMove works.
//     */
//    @Test
//    public void testUndoMove(){
//        setUpCorrect(5);
//        assertFalse(slidingTileBoardManager.undoMove());
//        slidingTileBoardManager.touchMove(23);
//        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        assertTrue(slidingTileBoardManager.undoMove());
//        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
//        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
//    }
//
//    /**
//     * Test whether board is solvable for board size 3.
//     */
//    @Test
//    public void testSolvabilityBoardSize3(){
//        slidingTileBoardManager = new SlidingTileBoardManager(3);
//        Tile[][] tiles = slidingTileBoardManager.getSlidingTileBoard().getTiles();
//        List<Tile> tilesList = new ArrayList<>();
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
//        slidingTileBoardManager = new SlidingTileBoardManager(4);
//        Tile[][] tiles = slidingTileBoardManager.getSlidingTileBoard().getTiles();
//        List<Tile> tilesList = new ArrayList<>();
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
//        slidingTileBoardManager = new SlidingTileBoardManager(5);
//        Tile[][] tiles = slidingTileBoardManager.getSlidingTileBoard().getTiles();
//        List<Tile> tilesList = new ArrayList<>();
//        for(int i = 0; i != 5; i++){
//            for(int j = 0; j != 5; j++){
//                tilesList.add(tiles[i][j]);
//            }
//        }
//        assertTrue(checkSolvability(tilesList, 5));
//    }
//
//    /**
//     * Test whether One move away board is actually one move away for board size 3.
//     */
//    @Test
//    public void testEasyWinBoardSize3(){
//        slidingTileBoardManager = new SlidingTileBoardManager(3);
//        slidingTileBoardManager.setBoardOneMoveWin();
//        assertFalse(slidingTileBoardManager.puzzleSolved());
//        slidingTileBoardManager.touchMove(8);
//        assertTrue(slidingTileBoardManager.puzzleSolved());
//    }
//
//    /**
//     * Test whether One move away board is actually one move away for board size 3.
//     */
//    @Test
//    public void testEasyWinBoardSize4(){
//        slidingTileBoardManager = new SlidingTileBoardManager(4);
//        slidingTileBoardManager.setBoardOneMoveWin();
//        assertFalse(slidingTileBoardManager.puzzleSolved());
//        slidingTileBoardManager.touchMove(15);
//        assertTrue(slidingTileBoardManager.puzzleSolved());
//    }
//
//    /**
//     * Test whether One move away board is actually one move away for board size 3.
//     */
//    @Test
//    public void testEasyWinBoardSize5(){
//        slidingTileBoardManager = new SlidingTileBoardManager(5);
//        slidingTileBoardManager.setBoardOneMoveWin();
//        assertFalse(slidingTileBoardManager.puzzleSolved());
//        slidingTileBoardManager.touchMove(24);
//        assertTrue(slidingTileBoardManager.puzzleSolved());
//    }
//}
