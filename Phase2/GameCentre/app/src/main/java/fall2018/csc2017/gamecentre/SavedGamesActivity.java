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
//Excluded from test because this is a view class.
public class SavedGamesActivity extends AppCompatActivity {

    /**
     * The current user that we are interested in.
     */
    private User currentUser;

    /**
     * GameCentre for managing files.
     */
    private GameCentre gameCentre;

    /**
     * Manager for saved games.
     */
    private SavedGamesManager savedGamesManager;

    /**
     * The hashMap that contains all saved games for all users and all games.
     */
    private Map<String, Map<String, ArrayList<GameToSave>>> savedGamesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);
    }

    /**
     * Updates the screen on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        gameCentre = new GameCentre(this);
        currentUser = gameCentre.getUserManager().getCurrentUser();
        savedGamesManager = gameCentre.getSavedGamesManager();
        savedGamesMap = savedGamesManager.getSavedGames();
        addLoadGameSpinnerListener("Sliding Tile", R.id.SlidingTileSpinner);
        addLoadGameSpinnerListener("Card Matching", R.id.cardMatchingSpinner);
        addLoadGameSpinnerListener("Sudoku", R.id.sudokuSpinner);
        addBackButtonListener();
    }

    /**
     * Activates the load game spinner.
     *
     * @param gameName the name of the game
     * @param IdOfSpinner the id of the spinner
     */
    private void addLoadGameSpinnerListener(String gameName, int IdOfSpinner){
        final String GAMENAME = gameName;
        Spinner Spinner = findViewById(IdOfSpinner);
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
        createArrayAdapter(GAMENAME, Spinner);
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
     * Create and set the array adapter for the given spinner.
     *
     * @param gameName name of the game of the spinner
     * @param spinner Spinner of the given game
     */
    private void createArrayAdapter(String gameName, Spinner spinner) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SavedGamesActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                savedGamesManager.constructNameArray(gameName, currentUser));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    /**
     * Switch to the game user selected.
     *
     * @param selected the game user choose to load
     * @param gameName the name of the game
     */
    private void switchToLoadGame(String selected, String gameName) {
        String currentUserUsername = currentUser.getUsername();
        ArrayList<GameToSave> savedGames = savedGamesMap.get(currentUserUsername).get(gameName);
        GameManager gameManager = savedGamesManager.getSelectedGameToSave(selected, savedGames);
        startActivity(gameName, gameManager);
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
     * Switch the view to GameCentre Activity.
     */
    private void switchToGameCentre() {
        Intent gameCentre = new Intent(this, GameCentreActivity.class);
        startActivity(gameCentre);
    }

    /**
     * Switch to view of Sliding tile game.
     */
    public void switchToSlidingTile() {
        Intent slidingTile = new Intent(this, SlidingTileGameActivity.class);
        startActivity(slidingTile);
    }

    /**
     * Switch to view of Card Matching game.
     */
    public void switchToCardMatching() {
        Intent cardMatching = new Intent(this, CardMatchingGameActivity.class);
        startActivity(cardMatching);
    }

    /**
     * Switch to view of Sudoku game.
     */
    public void switchToSudoku() {
        Intent sudoku = new Intent(this, SudokuGameActivity.class);
        startActivity(sudoku);
    }
}
