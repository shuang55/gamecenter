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

    @Override
    public String toString() {
        return "username= " + username;
    }

    public void addSavedGame(GameManager gameManager) {
        for (GameManager game: savedGames) {
            if (game.getGameName().equals(gameManager.getGameName())) {
                savedGames.remove(game);
            }
        }
        savedGames.add(gameManager);
    }

    public GameManager getSelectedGame(String gameName) {
        for (GameManager game : savedGames) {
            if (game.getGameName().equals(gameName)) {
                return game;
            }
        }
        return null;
    }

    public void removeSavedGame(String gameName) {
        for (GameManager game : savedGames) {
            if (game.getGameName().equals(gameName)) {
                savedGames.remove(game);
            }
        }
    }
}
