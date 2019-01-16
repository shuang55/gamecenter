package fall2018.csc2017.gamecentre;


import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Password of the user.
     */
    private String password;
    /**
     * list of auto-saved games for the user
     */
    private ArrayList<GameManager> savedGames = new ArrayList<>();

    /**
     * Initializes new instance of user.
     *
     * @param user username of the user
     * @param pass password of the user
     */
    User(String user, String pass) {
        this.username = user;
        this.password = pass;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Checks if input password is the correct password.
     *
     * @param password the input password
     * @return boolean whether the password is correct
     */
    boolean authenticate(String password) {
        return password.equals(this.password);
    }

    /**
     * Getter for username
     * @return the username
     */
    @Override
    public String toString() {
        return "username= " + username;
    }

    /**
     * Adds a gameManager to the list of temporary autosaves
     * @param gameManager the gameManager to be added
     */
    void addSavedGame(GameManager gameManager) {
        GameManager gameToRemove = null;
        for (GameManager game: savedGames) {
            if (game.getGameName().equals(gameManager.getGameName())) {
                gameToRemove = game;
            }
        }
        if (gameToRemove != null){
            savedGames.remove(gameToRemove);
        }
        savedGames.add(gameManager);
    }

    /**
     * Returns the gameManager with gameName
     * @param gameName the gameName of gameManager to be returned
     * @return a gameManager
     */
    GameManager getSelectedGame(String gameName) {
        for (GameManager game : savedGames) {
            if (game.getGameName().equals(gameName)) {
                return game;
            }
        }
        return null;
    }

    /**
     * Removes a savedGame from the list of temporary savedGames
     * @param gameName gameName of gameManager to be removed
     */
    void removeSavedGame(String gameName) {
        ArrayList<GameManager> copy = new ArrayList<>();
        for (GameManager game : savedGames) {
            if (!game.getGameName().equals(gameName)) {
                copy.add(game);
            }
        }
        savedGames = copy;
    }
}
