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
     * Abstract method for calculating the score of a game
     * @return the score
     */
    int getScore();

    /**
     * Abstract method for getting the game's name
     * @return name of the game
     */
    String getGameName();

    /**
     * Abstract Method for getting the current time in game
     * @return the time
     */
    String getTime();

    /**
     * Abstract method for getting the game's difficulty
     * @return the game difficulty
     */
    String getGameDifficulty();
}
