package fall2018.csc2017.gamecentre;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScoreBoardTest {

    /**
     * Create Scores and Scoreboard to test.
     */
    private Score score1 = new Score("game1", "user1", 5);
    private Score score2 = new Score("game1", "user1", 8);
    private Score score3 = new Score("game1", "user2", 90);
    private ScoreBoard board = new ScoreBoard();

    @Test
    public void createDisplayString() {
        board.updateScore(score1);
        board.updateScore(score2);
        board.updateScore(score3);

        String expectedTopTenUser = "1. game1 \n2. game1 \n3.  \n4.  \n5.  \n6.  \n7.  \n8.  \n9.  \n10.  \n";
        String topTenUser = board.createDisplayString("User", "user1", false);
        assertEquals(expectedTopTenUser, topTenUser);

        String expectedTopTenScores = " 8 \n 5 \n 0 \n 0 \n 0 \n 0 \n 0 \n 0 \n 0 \n 0 \n";
        String topTenScores = board.createDisplayString("game1", "user1", true);
        assertEquals(expectedTopTenScores, topTenScores);

        String expectedTopTenAll = "1.  \n2.  \n3.  \n4.  \n5.  \n6.  \n7.  \n8.  \n9.  \n10.  \n";
        String topTenAll = board.createDisplayString("(All)", "user1", false);
        assertEquals(expectedTopTenAll, topTenAll);
    }
}