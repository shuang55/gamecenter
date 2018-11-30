package fall2018.csc2017.cardmatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    /**
     * make a CardMatchingBoardManager with 8 pairs and an easy-win state
     */
    @Before
    public void setUp() {
        cardMatchingBoardManager = new CardMatchingBoardManager(8, true);
    }

    /**
     * Make CardMatchingBoardManager equal null after the test is done
     */
    @After
    public void tearDown() {
        cardMatchingBoardManager = null;
    }

    /**
     * test whether the correct game name is returned
     */
    @Test
    public void testGetGameName() {
        assertEquals("Card Matching", cardMatchingBoardManager.getGameName());
    }

    /**
     * test whether the correct game difficulty is returned
     */
    @Test
    public void testGetGameDifficulty() {
        assertEquals("4 by 4", cardMatchingBoardManager.getGameDifficulty());
    }

    /**
     * test whether puzzleSolved() correctly identifies an untouched board
     */
    @Test
    public void testPuzzleSolvedFalse() {
        assertFalse(cardMatchingBoardManager.puzzleSolved());
    }

    /**
     * test whether puzzleSolved() correctly identifies a half-finished board
     */
    @Test
    public void testPuzzleSolvedHalfFinished() {
        for (int i = 0; i <= 7; i++) {
            cardMatchingBoardManager.touchMove(i);
        }
        assertFalse(cardMatchingBoardManager.puzzleSolved());
    }

    /**
     * test whether isValidTap() correctly identifies if a card is a valid tap ornot
     */
    @Test
    public void testIsValidTap() {
        cardMatchingBoardManager.touchMove(2);
        assertFalse(cardMatchingBoardManager.isValidTap(2));
        assertTrue(cardMatchingBoardManager.isValidTap(3));
    }

    /**
     * test whether the scorekeeping is as expected
     */
    @Test
    public void testGetScore() {
        for (int i = 0; i <= 15; i++) {
            cardMatchingBoardManager.touchMove(i);
        }
        assertEquals(840, cardMatchingBoardManager.getScore());
    }

    /**
     * test whether the move counter is as expected
     */
    @Test
    public void testGetMove() {
        cardMatchingBoardManager.touchMove(1);
        assertEquals(1, cardMatchingBoardManager.getMove());
    }
}