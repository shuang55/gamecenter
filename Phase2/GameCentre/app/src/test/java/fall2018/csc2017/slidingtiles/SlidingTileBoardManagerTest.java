package fall2018.csc2017.slidingtiles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTileBoardManagerTest {

    /** The board manager for testing. */
    private SlidingTileBoardManager slidingTileBoardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 25;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        return tiles;
    }

    /**
     * Make a solved Board.
     */
    @Before
    public void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        SlidingTileBoard slidingTileBoard = new SlidingTileBoard(tiles, 5);
        slidingTileBoardManager = new SlidingTileBoardManager(slidingTileBoard);
    }

    /**
     * Removed slidingTileBoardManager after use.
     */
    @After
    public void tearDown() {
        slidingTileBoardManager = null;
    }

    /**
     * Checks if the current sliding tile board is solvable. If it is not solvable, make it solvable
     *
     * @param boardSize size of the board
     * @param tiles a list containing tiles for sliding tile game
     * reference: https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
     */
    private boolean checkSolvability(List<Tile> tiles, int boardSize) {
        int blankId = 25;
        int blankRow = slidingTileBoardManager.getSlidingTileBoard().findTile(blankId) / boardSize;
        int inversionCount = getInversionCount(tiles);
        return (boardSize % 2 == 1 && inversionCount % 2 == 0 ||
                boardSize % 2 == 0 && (inversionCount + boardSize - 1 - blankRow) % 2 == 0);
    }

    /**
     * Calculates the number of inversions in sliding tile board.
     *
     * @param tiles a list containing tiles for sliding tile game
     * @return number of inversions
     */
    private int getInversionCount(List<Tile> tiles) {
        int inversionCount = 0;
        for (int i = 0; i < tiles.size() - 1; i++)
            for (int j = i + 1; j < tiles.size(); j++)
                if (tiles.get(i).getId() != 25 && tiles.get(j).getId() != 25)
                    if (tiles.get(i).getId() > tiles.get(j).getId())
                        inversionCount++;
        return inversionCount;
    }

    /**
     * Test whether isValidTap works.
     */
    @Test
    public void testIsValidTap() {
        assertTrue(slidingTileBoardManager.isValidTap(19));
        assertTrue(slidingTileBoardManager.isValidTap(23));
        assertFalse(slidingTileBoardManager.isValidTap(10));
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        assertTrue(slidingTileBoardManager.puzzleSolved());
        slidingTileBoardManager.getSlidingTileBoard().swapTiles(0, 0, 0, 1);
        assertFalse(slidingTileBoardManager.puzzleSolved());
    }

    /**
     * Test whether TouchMove works.
     */
    @Test
    public void testTouchMove(){
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
        assertFalse(slidingTileBoardManager.undoMove());
        slidingTileBoardManager.touchMove(23);
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        assertTrue(slidingTileBoardManager.undoMove());
        assertEquals(25, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 4).getId());
        assertEquals(24, slidingTileBoardManager.getSlidingTileBoard().getTile(4, 3).getId());
    }

    /**
     * Test whether board is solvable for board size 3.
     */
    @Test
    public void testSolveAbilityBoardSize3(){
        slidingTileBoardManager = new SlidingTileBoardManager(3);
        Tile[][] tiles = slidingTileBoardManager.getSlidingTileBoard().getTiles();
        List<Tile> tilesList = new ArrayList<>();
        for(int i = 0; i != 3; i++){
            for(int j = 0; j != 3; j++){
                tilesList.add(tiles[i][j]);
            }
        }
        assertTrue(checkSolvability(tilesList, 3));
    }

    /**
     * Test whether board is solvable for board size 4.
     */
    @Test
    public void testSolveAbilityBoardSize4(){
        slidingTileBoardManager = new SlidingTileBoardManager(4);
        Tile[][] tiles = slidingTileBoardManager.getSlidingTileBoard().getTiles();
        List<Tile> tilesList = new ArrayList<>();
        for(int i = 0; i != 4; i++){
            for(int j = 0; j != 4; j++){
                tilesList.add(tiles[i][j]);
            }
        }
        assertTrue(checkSolvability(tilesList, 4));
    }

    /**
     * Test whether board is solvable for board size 5.
     */
    @Test
    public void testSolveAbilityBoardSize5(){
        slidingTileBoardManager = new SlidingTileBoardManager(5);
        Tile[][] tiles = slidingTileBoardManager.getSlidingTileBoard().getTiles();
        List<Tile> tilesList = new ArrayList<>();
        for(int i = 0; i != 5; i++){
            for(int j = 0; j != 5; j++){
                tilesList.add(tiles[i][j]);
            }
        }
        assertTrue(checkSolvability(tilesList, 5));
    }

    /**
     * Test whether One move away board is actually one move away for board size 3.
     */
    @Test
    public void testEasyWinBoardSize3(){
        slidingTileBoardManager = new SlidingTileBoardManager(3);
        slidingTileBoardManager.setBoardOneMoveWin();
        assertFalse(slidingTileBoardManager.puzzleSolved());
        slidingTileBoardManager.touchMove(8);
        assertTrue(slidingTileBoardManager.puzzleSolved());
    }

    /**
     * Test whether One move away board is actually one move away for board size 4.
     */
    @Test
    public void testEasyWinBoardSize4(){
        slidingTileBoardManager = new SlidingTileBoardManager(4);
        slidingTileBoardManager.setBoardOneMoveWin();
        assertFalse(slidingTileBoardManager.puzzleSolved());
        slidingTileBoardManager.touchMove(15);
        assertTrue(slidingTileBoardManager.puzzleSolved());
    }

    /**
     * Test whether One move away board is actually one move away for board size 5.
     */
    @Test
    public void testEasyWinBoardSize5(){
        slidingTileBoardManager.setBoardOneMoveWin();
        assertFalse(slidingTileBoardManager.puzzleSolved());
        slidingTileBoardManager.touchMove(24);
        assertTrue(slidingTileBoardManager.puzzleSolved());
    }

    /**
     * Test whether getMove increments after player makes a valid move.
     */
    @Test
    public void testGetMove(){
        assertEquals(0, slidingTileBoardManager.getMove());
        slidingTileBoardManager.touchMove(10);
        assertEquals(0, slidingTileBoardManager.getMove());
        slidingTileBoardManager.touchMove(23);
        assertEquals(1, slidingTileBoardManager.getMove());
    }

    /**
     * Test whether getScore changes after player makes a valid move.
     */
    @Test
    public void testGetScore(){
        assertEquals(1000, slidingTileBoardManager.getScore());
        slidingTileBoardManager.touchMove(10);
        assertEquals(1000, slidingTileBoardManager.getScore());
        slidingTileBoardManager.touchMove(23);
        assertEquals(997, slidingTileBoardManager.getScore());
    }

    /**
     * Test whether score becomes negative or becomes 0, if too many moves were made.
     */
    @Test
    public void testGetScoreWhenScoreBecomesNegative(){
        assertEquals(1000, slidingTileBoardManager.getScore());
        for (int i = 350; i != 0; i--){
            slidingTileBoardManager.touchMove(19);
            slidingTileBoardManager.touchMove(24);
        }
        assertEquals(0, slidingTileBoardManager.getScore());
    }

    /**
     * Test whether getName returns the right name.
     */
    @Test
    public void testGetName(){
        assertEquals("Sliding Tile", slidingTileBoardManager.getGameName());
    }

    /**
     * Test whether getTime returns the right time.
     */
    @Test
    public void testGetTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        assertEquals(dateTimeFormatter.format(now), slidingTileBoardManager.getTime());
    }

    /**
     * Test whether getGameDifficulty returns the right string.
     */
    @Test
    public void testGetGameDifficulty(){
        assertEquals("5 by 5", slidingTileBoardManager.getGameDifficulty());
    }
}
