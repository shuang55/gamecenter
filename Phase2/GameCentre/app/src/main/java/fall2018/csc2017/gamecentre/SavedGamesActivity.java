package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.R;
import fall2018.csc2017.cardmatching.CardGameActivity;
import fall2018.csc2017.cardmatching.CardStartingActivity;
import fall2018.csc2017.slidingtiles.GameActivity;
import fall2018.csc2017.slidingtiles.StartingActivity;

/**
 * Activity for Saved Games.
 */
public class SavedGamesActivity extends AppCompatActivity {

    /**
     * The userManager that stores all users.
     */
    private UserManager userManager;

    /**
     * The hashmap that contains all saved games for all users and all games.
     */
    private HashMap<String, HashMap<String, ArrayList<GameToSave>>> savedGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_games);
    }

    /**
     * Loads the userManager and savedGames.
     */
    private void loadManager(){
        Context savedGame = SavedGamesActivity.this;
        GameCentre gameCentre = new GameCentre(savedGame);
        gameCentre.loadManager(UserManager.USERS);
        userManager = gameCentre.userManager;
        gameCentre.loadManager(SavedGames.SAVEDGAMES);
        savedGames = gameCentre.savedGames.savedGames;
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
                android.R.layout.simple_spinner_dropdown_item, constructNameArray(GAMENAME));
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
     * Creates a String array containing the name of the saved games.
     *
     * @param gameName the game that we are interested in
     * @return a String array containing the name of the saved games
     */
    private ArrayList<String> constructNameArray(String gameName){
        ArrayList<String> nameArray = new ArrayList<>();
        String currentUser = userManager.getCurrentUser().getUsername();
        nameArray.add("Select a Saved Game");
        HashMap<String, ArrayList<GameToSave>> savedGamesByGameType = savedGames.get(currentUser);
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
     * Set up which game to be loaded, and switch to that view.
     *
     * @param selected the game user choose to load
     * @param gameName the name of the game
     */
    private void switchToLoadGame(String selected, String gameName) {
        int indexOfSelectedGame=0;
        String currentUser = userManager.getCurrentUser().getUsername();
        ArrayList<GameToSave> listOfSavedGame = savedGames.get(currentUser).get(gameName);
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
        if (gameName.equals("Sliding Tile")){
            saveToFile(StartingActivity.TEMP_SAVE_FILENAME,gameManager);
            switchToSlidingTile();
        }
        else if (gameName.equals("Card Matching")){
            saveToFile(CardStartingActivity.TEMP_SAVE_FILENAME,gameManager);
            switchToCardMatching();
        }
    }

    /**
     * Save gameManager to fileName.
     *
     * @param fileName the destination to save to
     * @param gameManager the gameManager to be saved
     */
    public void saveToFile(String fileName, GameManager gameManager) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(gameManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Switch to view of Sliding tile game.
     */
    public void switchToSlidingTile() {
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

    /**
     * Switch to view of Card Matching game.
     */
    public void switchToCardMatching() {
        Intent tmp = new Intent(this, CardGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Updates the screen on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadManager();
        int slidingTileSpinnerID = R.id.SlidingTileSpinner;
        int cardMatchingSpinnerID = R.id.cardMatchingSpinner;
        addLoadGameSpinnerListener("Sliding Tile", slidingTileSpinnerID);
        addLoadGameSpinnerListener("Card Matching", cardMatchingSpinnerID);
        addBackButtonListener();
    }
}
