package fall2018.csc2017.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages saved games.
 */
public class SavedGames implements Serializable {

    /**
     * File that contains all saved games for all users and all games.
     */
    static final String SAVEDGAMES = "savedgames.ser";

    /**
     * Stores a HashMap(User: HashMap(TypeOfGame: ArrayList(game)))].
     */
    public Map<String, Map<String, ArrayList<GameToSave>>> savedGames = new HashMap<>();

    /**
     * Updates the hashmap that stores all saved games with the new game to save.
     *
     * @param gameToSave the new game to be saved
     * @param currentUser the current user
     */
    void updateSavedGames(GameToSave gameToSave, String currentUser) {
        String gameType = gameToSave.getGameName();
        checkNullKey(currentUser, gameType);
        ArrayList<GameToSave> gameToSaves = savedGames.get(currentUser).get(gameType);
        updateSavedGamesArray(gameToSave, gameToSaves);
        if (savedGames.get(currentUser).get(gameType).size() == 4) {
            savedGames.get(currentUser).get(gameType).remove(3);
        }
    }

    /**
     * Updates the array of gameToSaves
     *
     * @param gameToSave the input game to be saved
     * @param gameToSaves the arraylist of previously saved games
     */
    private void updateSavedGamesArray(GameToSave gameToSave, ArrayList<GameToSave> gameToSaves) {
        if (!containsSameBoard(gameToSave, gameToSaves)){
            gameToSaves.add(0, gameToSave);
        }
        else if(containsSameBoard(gameToSave, gameToSaves)){
            int indexOfSameBoard = getIndexSameBoard(gameToSave, gameToSaves);
            gameToSaves.remove(indexOfSameBoard);
            gameToSaves.add(0, gameToSave);
        }
    }

    /**
     * Checks if the input is a valid key in savedGames, and create new key if it is null.
     *
     * @param currentUser input username
     * @param gameType input gametype
     */
    private void checkNullKey(String currentUser, String gameType) {
        if (!savedGames.containsKey(currentUser)) {
            savedGames.put(currentUser, new HashMap<String, ArrayList<GameToSave>>());
        }
        if (!(savedGames.get(currentUser).containsKey(gameType))){
            savedGames.get(currentUser).put(gameType, new ArrayList<GameToSave>());
        }
    }

    /**
     * Checks if the hashmap already contains the exact same game state.
     *
     * @param gameToSave the new game state to be saved
     * @param gameToSaves the list of game states
     * @return the whether or not the same board is found
     */
    private boolean containsSameBoard(GameToSave gameToSave, ArrayList<GameToSave> gameToSaves){
        boolean result = false;
        for (GameToSave game: gameToSaves){
            if (game.getGameManager() == gameToSave.getGameManager()){
                result = true;
            }
        }
        return result;
    }

    /**
     * Creates a String array containing the name of the saved games.
     *
     * @param gameName the game that we are interested in
     * @param currentUser the current User that we are interested in
     * @return a String array containing the name of the saved games
     */
    ArrayList<String> constructNameArray(String gameName, User currentUser){
        ArrayList<String> nameArray = new ArrayList<>();
        String currentUserUserName = currentUser.getUsername();
        nameArray.add("Select a Saved Game");
        Map<String, ArrayList<GameToSave>> savedGamesByGameType = savedGames.get(currentUserUserName);
        if (savedGamesByGameType != null && savedGamesByGameType.get(gameName) != null) {
            ArrayList<GameToSave> listOfSavedGame = savedGamesByGameType.get(gameName);
            for (int i = 0; i < listOfSavedGame.size(); i++) {
                String gameDifficulty = listOfSavedGame.get(i).getGameDifficulty();
                nameArray.add(listOfSavedGame.get(i).getSavedTime() + " (" + gameDifficulty + ") ");
            }
        }
        return nameArray;
    }

    /**
     * Returns the index of the game state that's the same.
     *
     * @param gameToSave the new game to be saved
     * @param gameToSaves the list of game states
     * @return the index of the game states found, return -1 if not found.
     */
    private int getIndexSameBoard(GameToSave gameToSave, ArrayList<GameToSave> gameToSaves){
        for (int i = 0; i < gameToSaves.size(); i++) {
            if (gameToSaves.get(i).getGameManager() == gameToSave.getGameManager()) {
                return i;
            }
        }
        return -1;
    }
}
