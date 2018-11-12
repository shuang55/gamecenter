package fall2018.csc2017.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Class that stores information for the score.
 */
public class Score implements Comparable<Score>, Serializable {

    /**
     * The game that score is set in
     */
    private String game;

    /**
     * The user that achieved the score.
     */
    private String user;

    /**
     * The score.
     */
    private int score;

    /**
     * Initialize a new score with no parameters.
     */
    Score() {
        this.game = "";
        this.user = "";
        this.score = 0;
    }

    /**
     * Initialize a new score with name of game, user, and score.
     *
     * @param game  the game that score is set in
     * @param user  the user that achieved the score
     * @param score the score
     */
    Score(String game, String user, int score) {
        this.game = game;
        this.user = user;
        this.score = score;

    }

    /**
     * The compareTo method for sorting.
     *
     * @param s another Score class
     * @return an int depending on whether this score is higher than s.getScore();
     */
    @Override
    public int compareTo(@NonNull Score s) {
        return Integer.compare(this.score, s.getScore());
    }

    /**
     * Getter for game name.
     *
     * @return the game name
     */
    public String getGame() {
        return game;
    }

    /**
     * Getter for user.
     *
     * @return the user name
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }
}
