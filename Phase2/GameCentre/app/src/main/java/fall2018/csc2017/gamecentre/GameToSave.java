package fall2018.csc2017.gamecentre;

import java.io.Serializable;

/**
 * A class that contains information of a game to save.
 */
public class GameToSave implements Serializable{

    /**
     * The time when the game is saved.
     */
    private String savedTime;

    /**
     * The name of the game.
     */
    private String gameName;

    /**
     * The difficulty level of the game.
     */
    private String gameDifficulty;

    /**
     * The game's state
     */
    private GameManager gameManager;


    /**
     * Initializes the GameToSave.
     *
     * @param savedTime The time when the game is saved
     * @param gameName The name of the game
     * @param gameDifficulty The difficulty level of the game
     * @param gameManager The game's state
     */
    public GameToSave(String savedTime, String gameName, String gameDifficulty, GameManager gameManager){
        this.savedTime = savedTime;
        this.gameName = gameName;
        this.gameDifficulty = gameDifficulty;
        this.gameManager = gameManager;
    }

    /**
     * Get the savedTime.
     *
     * @return savedTime
     */
    String getSavedTime() {
        return savedTime;
    }

    /**
     * Get the gameDifficulty.
     *
     * @return gameDifficulty
     */
    String getGameDifficulty() {
        return gameDifficulty;
    }

    /**
     * Get the gameManager.
     *
     * @return gameManager
     */
    GameManager getGameManager() {
        return gameManager;
    }

    /**
     * Get the gameName.
     *
     * @return gameName
     */
    String getGameName() {
        return gameName;
    }

}
