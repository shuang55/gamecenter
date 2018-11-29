package fall2018.csc2017.gamecentre;

import java.io.Serializable;

/**
 * Interface for every game added to GameCentre.
 */
public interface GameManager extends Serializable {

    /**
     * Game ending screen file.
     */
    String TEMP_SAVE_WIN = "temp_save_win.ser";

    /**
     * Game for starting from starting activity
     */
    String TEMP_SAVE_START = "temp_save_start.ser";

    /**
     * Checks if the tap made is a valid tap.
     *
     * @param position the position of the tap
     * @return whether or not the tap is valid
     */
    boolean isValidTap(int position);

    /**
     * Return if the board is solved.
     *
     * @return true if board is solved, false other wise
     */
    boolean puzzleSolved();

    /**
     * Makes the move if the tap is valid.
     *
     * @param position the position of the tap
     */
    void touchMove(int position);


    /**
     * Abstract method for calculating the score of a game.
     *
     * @return the score
     */
    int getScore();

    /**
     * Return move ocunt
     *
     * @return move count
     */
    int getMove();

    /**
     * Abstract method for getting the game's name.
     *
     * @return name of the game
     */
    String getGameName();

    /**
     * Abstract Method for getting the current time in game.
     *
     * @return the time
     */
    String getTime() ;

    /**
     * Abstract method for getting the game's difficulty.
     *
     * @return the game difficulty
     */
    String getGameDifficulty();
}
