package fall2018.csc2017.cardmatching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.Spinner;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;
import fall2018.csc2017.gamecentre.UserManager;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class CardMatchingStartingActivity extends AppCompatActivity {

    /**
     * A temporary save file. (For Loading Games)
     */
    public static final String CARD_MATCHING_START_FILE = GameManager.TEMP_SAVE_START;

    /**
     * Gamecentre for managing files
     */
    private GameCentre gameCentre;
    /**
     * The board manager.
     */
    private CardMatchingBoardManager cardMatchingBoardManager;

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
        gameCentre = new GameCentre(this);
        gameCentre.saveManager(CARD_MATCHING_START_FILE, cardMatchingBoardManager);

        addStartButtonListener();
        addLoadAutoSaveButtonListener();
        addComplexityButton();
        addOneMoveWinButton();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton1);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardMatchingBoardManager = new CardMatchingBoardManager(numCardPairs, false);
                gameCentre.saveManager(GameManager.TEMP_SAVE_START, cardMatchingBoardManager);
                swapToCardMatching();
            }
        });
    }

    /**
     * Activates LoadAutoSaveButton.
     */
    private void addLoadAutoSaveButtonListener() {
        Button slidingTileAutoSave = findViewById(R.id.CardMatchingAutoSave);
        slidingTileAutoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCentre.loadManager(UserManager.USERS);
                CardMatchingBoardManager tempBoard = (CardMatchingBoardManager) gameCentre.getUserManager().
                        getSelectedGame("Card Matching");
                checkValidAutoSavedGame(tempBoard);
            }
        });
    }

    /**
     * Checks if a game has been autosaved, and transition into game if it has.
     *
     * @param tempBoard the CardMatchingBoardManager
     */
    private void checkValidAutoSavedGame(CardMatchingBoardManager tempBoard) {
        if (tempBoard != null) {
            cardMatchingBoardManager = tempBoard;
            gameCentre.saveManager(GameManager.TEMP_SAVE_START, cardMatchingBoardManager);
            swapToCardMatching();
        } else {
            makeToastNoAutoSavedGame();
        }
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastNoAutoSavedGame() {
        Toast.makeText(this, "No AutoSave History", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the CardMatchingGameActivity view to play the game.
     */
    public void swapToCardMatching() {
        Intent tmp = new Intent(this, CardMatchingGameActivity.class);
        startActivity(tmp);
    }

    /**
     * Add more complexities , 8 pairs, 10 pairs, 12 pairs.
     */
    public void addComplexityButton() {
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

    /**
     * Add a button that makes a game one move away from victory.
     */
    public void addOneMoveWinButton() {
        Button oneMoveToVictory = findViewById(R.id.OneMoveWin);
        oneMoveToVictory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardMatchingBoardManager = new CardMatchingBoardManager(numCardPairs, true);
                gameCentre.saveManager(GameManager.TEMP_SAVE_START, cardMatchingBoardManager);
                swapToCardMatching();
            }
        });
    }
}