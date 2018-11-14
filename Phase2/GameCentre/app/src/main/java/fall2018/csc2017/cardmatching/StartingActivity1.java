package fall2018.csc2017.cardmatching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.UserManager;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity1 extends AppCompatActivity {
// https://www.vectorstock.com/royalty-free-vector/pink-star-with-a-smile-logo-vector-21368236
// star photo

    /**
     * A temporary save file. (For Loading Games)
     */
    public static final String TEMP_SAVE_FILENAME = "tmp_save_file.ser";

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The User Manager.
     */
    private UserManager userManager;

    /**
     * Level of difficulty chosen.
     */
    private String complexitySelected1;

    /**
     * Number of card pairs chosen.
     */
    public int numCardPairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_card_matching);
        loadManager();
        userManager.setCurrentUserFile();
        addStartButtonListener();
        addLoadButtonListener();
        addComplexityButton1();
    }


    /**
     * Loads userManager
     */
    private void loadManager() {
        Context context = StartingActivity1.this;
        GameCentre gameCentre = new GameCentre(context);
        gameCentre.loadManager(UserManager.USERS);
        userManager = gameCentre.getUserManager();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(numCardPairs);
                switchToGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton1);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUserFile = userManager.getCurrentUserFile();
                loadFromFile(currentUserFile);
                if (boardManager != null){
                    saveToFile(TEMP_SAVE_FILENAME);
                    makeToastLoadedText();
                    switchToGame();
                }
                else {
                    makeToastNoPreviousGameText();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastNoPreviousGameText() {
        Toast.makeText(this, "No AutoSave History", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    /**
     * Add more complexities , 8 pairs, 10 pairs, 12 pairs.
     */
    public void addComplexityButton1() {
        final Spinner complexity1 = findViewById(R.id.complexity1);
        complexity1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                complexitySelected1 = complexity1.getItemAtPosition(i).toString();
                switch (complexitySelected1) {
                    case "10 pairs":
                        numCardPairs = 10;
                        break;
                    case "12 pairs":
                        numCardPairs = 12;
                        break;
                    default:
                        numCardPairs = 8;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}