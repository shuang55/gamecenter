package fall2018.csc2017.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Map;

import fall2018.csc2017.R;
import fall2018.csc2017.cardmatching.CardMatchingGameActivity;
import fall2018.csc2017.cardmatching.CardMatchingStartingActivity;
import fall2018.csc2017.slidingtiles.SlidingTileGameActivity;
import fall2018.csc2017.slidingtiles.SlidingTileStartingActivity;
import fall2018.csc2017.sudoku.SudokuGameActivity;
import fall2018.csc2017.sudoku.SudokuStartingActivity;

/**
 * Activity for Saved Games.
 */
public class SavedGamesActivity extends AppCompatActivity {

    /**
     * The current user that we are interested in.
     */
    private User currentUser;

    /**
     * Gamecentre for managing files
     */
    private GameCentre gameCentre;

    private SavedGames savedGames;

    /**
     * The hashmap that contains all saved games for all users and all games.
     */
    private Map<String, Map<String, ArrayList<GameToSave>>> savedGamesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);
    }

    /**
     * Activates the load game spinner.
     *
     * @param gameName the name of the game
     */
    private void addLoadGameSpinnerListener(String gameName, int IDofSpinner){
        final String GAMENAME = gameName;
        Spinner Spinner = findViewById(IDofSpinner);
        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                switch (position){
                    case 0:
                        break;
                    case 1:
                        switchToLoadGame(selected, GAMENAME);
                        break;
                    case 2:
                        switchToLoadGame(selected, GAMENAME);
                        break;
                    case 3:
                        switchToLoadGame(selected, GAMENAME);
                        break;
                    }
                }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SavedGamesActivity.this,
                android.R.layout.simple_spinner_dropdown_item, savedGames.constructNameArray(GAMENAME, currentUser));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(arrayAdapter);
    }

    /**
     * Activates the back button.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentre();
            }
        });
    }

    /**
     * Switch the view to GameCentre Activity.
     */
    private void switchToGameCentre() {
        Intent gameCentre = new Intent(this, GameCentreActivity.class);
        startActivity(gameCentre);
    }

    /**
     * Set up which game to be loaded, and switch to that view.
     *
     * @param selected the game user choose to load
     * @param gameName the name of the game
     */
    private void switchToLoadGame(String selected, String gameName) {
        int indexOfSelectedGame=0;
        String currentUserUsername = currentUser.getUsername();
        ArrayList<GameToSave> listOfSavedGame = savedGamesMap.get(currentUserUsername).get(gameName);
        for (int i = 0; i < listOfSavedGame.size(); i++) {
            String gameDifficulty = listOfSavedGame.get(i).getGameDifficulty();
            String nameOfSelectedGame = listOfSavedGame.get(i).getSavedTime() + " (" + gameDifficulty + ") ";
            if (nameOfSelectedGame.equals(selected)){
                indexOfSelectedGame = i;
            }
        }
        GameToSave gameToSave = listOfSavedGame.get(indexOfSelectedGame);
        startActivity(gameName, gameToSave.getGameManager());
    }

    /**
     * Switch the view to the game loaded.
     *
     * @param gameName the name of the game
     * @param gameManager the game state to be loaded
     */
    private void startActivity(String gameName, GameManager gameManager){
        switch(gameName){
            case "Sliding Tile":
                gameCentre.saveManager(SlidingTileStartingActivity.SLIDING_TILE_START_FILE, gameManager);
                switchToSlidingTile();
                break;
            case "Card Matching":
                gameCentre.saveManager(CardMatchingStartingActivity.CARD_MATCHING_START_FILE, gameManager);
                switchToCardMatching();
                break;
            case "Sudoku":
                gameCentre.saveManager(SudokuStartingActivity.SUDOKU_START_FILE, gameManager);
                switchToSudoku();
                break;
        }
    }

    /**
     * Switch to view of Sliding tile game.
     */
    public void switchToSlidingTile() {
        Intent tmp = new Intent(this, SlidingTileGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to view of Card Matching game.
     */
    public void switchToCardMatching() {
        Intent tmp = new Intent(this, CardMatchingGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to view of Sudoku game.
     */
    public void switchToSudoku() {
        Intent tmp = new Intent(this, SudokuGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Updates the screen on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameCentre = new GameCentre(this);
        currentUser = gameCentre.getUserManager().getCurrentUser();
        savedGames = gameCentre.getSavedGames();
        savedGamesMap = savedGames.savedGames;
        int slidingTileSpinnerID = R.id.SlidingTileSpinner;
        int cardMatchingSpinnerID = R.id.cardMatchingSpinner;
        int sudokuSpinnerID = R.id.sudokuSpinner;
        addLoadGameSpinnerListener("Sliding Tile", slidingTileSpinnerID);
        addLoadGameSpinnerListener("Card Matching", cardMatchingSpinnerID);
        addLoadGameSpinnerListener("Sudoku", sudokuSpinnerID);
        addBackButtonListener();
    }
}
