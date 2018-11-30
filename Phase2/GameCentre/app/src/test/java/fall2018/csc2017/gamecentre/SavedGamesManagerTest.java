package fall2018.csc2017.gamecentre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.cardmatching.Card;
import fall2018.csc2017.cardmatching.CardMatchingBoard;
import fall2018.csc2017.cardmatching.CardMatchingBoardManager;

import static org.junit.Assert.*;


/**
 * Test SavedGamesManager
 */
public class SavedGamesManagerTest {

    private SavedGamesManager savedGamesManager = new SavedGamesManager();

    private CardMatchingBoardManager cardMatchingBoardManager = new CardMatchingBoardManager(8, true);

    private GameToSave gameToSave;

    private Map<String, Map<String, ArrayList<GameToSave>>> savedGames = new HashMap<>();

    private Map<String, ArrayList<GameToSave>> savedGamesByUser = new HashMap<>();

    private ArrayList<GameToSave> savedGamesByGame = new ArrayList<>();

    private User user;

    private String nameOfGame;

    @Before
    public void setUp() {
        CardMatchingBoardManager cardMatchingBoardManager1 = new CardMatchingBoardManager(10, true);
        GameToSave gameToSave1 = new GameToSave(cardMatchingBoardManager1);
        CardMatchingBoardManager cardMatchingBoardManager2 = new CardMatchingBoardManager(10, true);
        GameToSave gameToSave2 = new GameToSave(cardMatchingBoardManager2);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        gameToSave = new GameToSave(cardMatchingBoardManager);
        savedGamesManager = new SavedGamesManager(savedGames);
        savedGamesByGame.add(gameToSave);
        savedGamesByGame.add(gameToSave1);
        savedGamesByGame.add(gameToSave2);
        user = new User("User1", "1");
        nameOfGame = dateTimeFormatter.format(now) + " (" + cardMatchingBoardManager.getGameDifficulty() + ") ";
    }

    @After
    public void tearDown(){
        gameToSave = null;
        savedGamesManager = null;
        user = null;
    }

    @Test
    public void testUpdateSavedGamesWithEmptyStart(){
        assertEquals(0, savedGames.size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(1, savedGames.size());
        assertEquals(1, savedGames.get("User1").size());
        assertEquals(1, savedGames.get("User1").get("Card Matching").size());
    }

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

    @Test
    public void testUpdateSavedGameRemoveSameGame(){
        savedGamesByUser.put("Card Matching", savedGamesByGame);
        savedGames.put("User1", savedGamesByUser);
        assertEquals(3, savedGames.get("User1").get("Card Matching").size());
        savedGamesManager.updateSavedGames(gameToSave, "User1");
        assertEquals(3, savedGames.get("User1").get("Card Matching").size());
    }

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

    @Test
    public void testConstructNameArrayOnEmptyMap(){
        ArrayList<String> nameArray = new ArrayList<>();
        savedGames.put("User1", savedGamesByUser);
        assertEquals(0, nameArray.size());
        nameArray = savedGamesManager.constructNameArray("Card Matching", user);
        assertEquals(1, nameArray.size());
    }

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

    @Test
    public void testGetSelectedGameToSave(){
        GameManager result = savedGamesManager.getSelectedGameToSave(nameOfGame, savedGamesByGame);
        assertEquals(cardMatchingBoardManager, result);
    }

    @Test
    public void testGetSavedGames(){
        assertEquals(savedGames, savedGamesManager.getSavedGames());
    }
}
