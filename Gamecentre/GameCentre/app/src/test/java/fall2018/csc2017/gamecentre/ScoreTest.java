package fall2018.csc2017.gamecentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreTest {

    /**
     * Create random scores to test.
     */
    private Score score1 = new Score();
    private Score score2 = new Score("game1", "user1", 2);
    private Score score3 = new Score("game2", "user2", 2);

    /**
     * test the difference between score 1 and score 2, score 2 and score 1, soore 2 and score 3
     * to see if it behaves properly
     */
    @Test
    public void compareTo() {
        assertEquals(score1.compareTo(score2), -1);
        assertEquals(score2.compareTo(score1), 1);
        assertEquals(score2.compareTo(score3), 0);
    }

    /**
     * test whether getGame works on score 2
     */
    @Test
    public void getGame() {
        assertEquals(score2.getGame(), "game1");
    }

    /**
     * test whether getUser works on score 3
     */
    @Test
    public void getUser() {
        assertEquals(score3.getUser(), "user2");
    }

    /**
     * test whether getScore works on score 1
     */
    @Test
    public void getScore() {
        assertEquals(score1.getScore(), 0);
    }
}