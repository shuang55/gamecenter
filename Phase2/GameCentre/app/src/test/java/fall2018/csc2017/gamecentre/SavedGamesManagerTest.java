package fall2018.csc2017.gamecentre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.cardmatching.CardMatchingBoardManager;

import static org.junit.Assert.*;

/**
 * Test SavedGamesManager.
 */
public class SavedGamesManagerTest {

    /**
     * A SavedGamesManager.
     */
    private SavedGamesManager savedGamesManager = new SavedGamesManager();

    /**
     * A CardMatchingBoardManager.
     */
    private CardMatchingBoardManager cardMatchingBoardManager;

    /**
     * A GameToSave.
     */
    private GameToSave gameToSave;

    /**
     * All saved games for all users of all game type.
     */
    private Map<String, Map<String, ArrayList<GameToSave>>> savedGames = new HashMap<>();

    /**
     * All saved games of all game type for specific user.
     */
    private Map<String, ArrayList<GameToSave>> savedGamesByUser = new HashMap<>();

    /**
     * All saved games of a game type for specific user.
     */
    private ArrayList<GameToSave> savedGamesByGame = new ArrayList<>();

    /**
     * A User
     */
    private User user;

    /**
     * Name of a game.
     */
    private String nameOfGame;

    /**
     * Set Up all variables for testing.
     */
    @Before
    public void setUp() {
        cardMatchingBoardManager = new CardMatchingBoardManager(8, true);
        setUpSavedGamesByGame();
        savedGamesManager = new SavedGamesManager(savedGames);
        user = new User("User1", "1");
        nameOfGame =  getTime() + " (" + cardMatchingBoardManager.getGameDifficulty() + ") ";
    }

    /**
     * Set up savedGamesByGame.
     */
    private void setUpSavedGamesByGame() {
        CardMatchingBoardManager cardMatchingBoardManager1 = new CardMatchingBoardManager(10, true);
        GameToSave gameToSave1 = new GameToSave(cardMatchingBoardManager1);
        CardMatchingBoardManager cardMatchingBoardManager2 = new CardMatchingBoardManager(10, true);
        GameToSave gameToSave2 = new GameToSave(cardMatchingBoardManager2);
        gameToSave = new GameToSave(cardMatchingBoardManager);
        savedGamesByGame.add(gameToSave);
        savedGamesByGame.add(gameToSave1);
        savedGamesByGame.add(gameToSave2);
    }

    /**
     * Get time of the game saved.
     */
    private String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * Makes all variable equals to null.
     */
    @After
    public void tearDown(){
        cardMatchingBoardManager = null;
        gameToSave = null;
        savedGamesManager = null;
        savedGamesByGame = null;
        nameOfGame = null;
        user = null;
    }

    /**
     * Test whether updateSavedGames works with empty savedGames.
     */
    @Test
    public void testUpdateSavedGamesWithEmptyMap(){
        assertEquals(0, savedGames.size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(1, savedGames.size());
        assertEquals(1, savedGames.get("User1").size());
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
    }

    /**
     * Test whether updateSavedGames works with same user but different game type.
     */
    @Test
    public void testUpdateSavedGamesWithSameUserDifferentGameType(){
        savedGamesByUser.put("Sliding Tile", savedGamesByGame);
        savedGames.put("User1", savedGamesByUser);
        assertEquals(1, savedGames.get("User1").size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(2, savedGames.get("User1").size());
        assertEquals(3, savedGames.get("User1").get("Sliding Tile").size());
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
    }

    /**
     * Test whether updateSavedGames works with same user, same game type, different board.
     */
    @Test
    public void testUpdateSavedGamesWithSameUserSameGameTypeDifferentBoard(){
        CardMatchingBoardManager newCardMatchingBoard = new CardMatchingBoardManager(12, true);
        GameToSave newGameToSave = new GameToSave(newCardMatchingBoard);
        assertEquals(0, savedGames.size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(1, savedGames.size());
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
        savedGamesManager.updateSavedGames(newGameToSave, "User1");
        assertEquals(2, savedGames.get("User1").get("Card Matching").size());
    }

    /**
     * Test whether updateSavedGames works with same user, same game type, same board.
     */
    @Test
    public void testUpdateSavedGamesWithSameUserSameGameTypeSameBoard(){
        CardMatchingBoardManager newCardMatchingBoard = cardMatchingBoardManager;
        GameToSave newGameToSave = new GameToSave(newCardMatchingBoard);
        assertEquals(0, savedGames.size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(1, savedGames.size());
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
        savedGamesManager.updateSavedGames(newGameToSave, "User1");
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
    }

    /**
     * Test whether updateSavedGames works with different user.
     */
    @Test
    public void testUpdateSavedGamesWithDifferentUser(){
        assertEquals(0, savedGames.size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(1, savedGames.size());
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
        savedGamesManager.updateSavedGames(gameToSave, "User2");
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
        assertEquals(1, savedGames.get("User2").get("Card Matching").size());
        assertEquals(2, savedGames.size());
    }

    /**
     * Test whether updateSavedGames removes game with same board.
     */
    @Test
    public void testUpdateSavedGameRemoveSameBoard(){
        savedGamesByUser.put("Card Matching", savedGamesByGame);
        savedGames.put("User1", savedGamesByUser);
        assertEquals(3, savedGames.get("User1").get("Card Matching").size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(3, savedGames.get("User1").get("Card Matching").size());
    }

    /**
     * Test whether updateSavedGames removes game when number of games saved goes over 3.
     */
    @Test
    public void testUpdateSavedGameRemoveExtraGame(){
        savedGamesByUser.put("Card Matching", savedGamesByGame);
        savedGames.put("User1", savedGamesByUser);
        assertEquals(3, savedGames.get("User1").get("Card Matching").size());
        CardMatchingBoardManager cardMatchingBoardManager3 = new CardMatchingBoardManager(10, true);
        GameToSave gameToSave3 = new GameToSave(cardMatchingBoardManager3);
        savedGamesManager.updateSavedGames(gameToSave3, "User1");
        assertEquals(3, savedGames.get("User1").get("Card Matching").size());
    }

    /**
     * Test whether constructNameArray works on empty savedGames.
     */
    @Test
    public void testConstructNameArrayOnEmptyMap(){
        ArrayList<String> nameArray = new ArrayList<>();
        savedGames.put("User1", savedGamesByUser);
        assertEquals(0, nameArray.size());
        nameArray = savedGamesManager.constructNameArray("Card Matching", user);
        ArrayList<String> expectedArray = new ArrayList<>();
        expectedArray.add("Select a Saved Game");
        assertEquals(expectedArray, nameArray);
    }

    /**
     * Test whether constructNameArray works.
     */
    @Test
    public void testConstructNameArray(){
        ArrayList<String> nameArray = new ArrayList<>();
        ArrayList<GameToSave> gameArrayList = new ArrayList<>();
        gameArrayList.add(gameToSave);
        savedGamesByUser.put("Card Matching", gameArrayList);
        savedGames.put("User1", savedGamesByUser);
        assertEquals(0, nameArray.size());
        nameArray = savedGamesManager.constructNameArray("Card Matching", user);
        ArrayList<String> expectedArray = new ArrayList<>();
        expectedArray.add("Select a Saved Game");
        expectedArray.add(nameOfGame);
        assertEquals(expectedArray, nameArray);
    }

    /**
     * Test whether getSelectedGameToSave return the right GameManager.
     */
    @Test
    public void testGetSelectedGameToSave(){
        GameManager result = savedGamesManager.getSelectedGameToSave(nameOfGame, savedGamesByGame);
        assertEquals(cardMatchingBoardManager, result);
    }

    /**
     * Test whether getSavedGames works.
     */
    @Test
    public void testGetSavedGames(){
        assertEquals(savedGames, savedGamesManager.getSavedGames());
    }
}
