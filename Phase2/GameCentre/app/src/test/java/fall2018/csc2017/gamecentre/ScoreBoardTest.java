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

    /**
     * Test createDisplayString for displaying the top ten games of a user.
     */
    @Test
    public void createDisplayStringTopTenUser() {
        board.updateScore(score1);
        board.updateScore(score2);
        board.updateScore(score3);

        String expectedTopTenUser = "1. game1 \n2. game1 \n3.  \n4.  \n5.  \n6.  \n7.  \n8.  \n9.  \n10.  \n";
        String topTenUser = board.createDisplayString("User", "user1", false);
        assertEquals(expectedTopTenUser, topTenUser);
    }

    /**
     * Test createDisplayString for displaying top ten scores of a user.
     */
    @Test
    public void createDisplayStringTopTenScores() {
        board.updateScore(score1);
        board.updateScore(score2);
        board.updateScore(score3);

        String expectedTopTenScores = " 8 \n 5 \n 0 \n 0 \n 0 \n 0 \n 0 \n 0 \n 0 \n 0 \n";
        String topTenScores = board.createDisplayString("game1", "user1", true);
        assertEquals(expectedTopTenScores, topTenScores);
    }

    /**
     * Test createDisplayString for displaying top ten users of a game.
     */
    @Test
    public void createDisplayStringTopTenUsers(){
        board.updateScore(score1);
        board.updateScore(score2);
        board.updateScore(score3);

        String expectedTopTenAll = "1. user2 \n2. user1 \n3. user1 \n4.  \n5.  \n6.  \n7.  \n8.  \n9.  \n10.  \n";
        String topTenAll = board.createDisplayString("game1(All)", "user1", false);
        assertEquals(expectedTopTenAll, topTenAll);
    }
}