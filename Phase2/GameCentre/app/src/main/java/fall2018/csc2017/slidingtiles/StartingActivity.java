package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;
import fall2018.csc2017.gamecentre.UserManager;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class StartingActivity extends AppCompatActivity {
// https://www.vectorstock.com/royalty-free-vector/pink-star-with-a-smile-logo-vector-21368236
// star photo

    /**
     * Temp save file for starting sudoku game
     */
    public static final String SLIDING_TILE_START_FILE = GameManager.TEMP_SAVE_START;

    /**
     * Gamecentre for managing files
     */
    private GameCentre gameCentre;

    /**
     * Sliding Tile BoardManager to be used in game
     */
    private BoardManager boardManager;

    /**
     * Level of difficulty chosen.
     */
    private String complexitySelected;

    /**
     * Size of the board chosen.
     */
    public int boardSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_sliding_tile);
        gameCentre = new GameCentre(this);
        gameCentre.saveManager(SLIDING_TILE_START_FILE, boardManager);

        addStartButtonListener();
        addLoadAutoSaveButtonListener();
        addEzWinButtonListener();
        addComplexityButton();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager(boardSize);
                gameCentre.saveManager(GameManager.TEMP_SAVE_START, boardManager);
                swapToSlidingTileGame();
            }
        });
    }

    /**
     * Activates LoadAutoSaveButton
     */
    private void addLoadAutoSaveButtonListener() {
        Button slidingTileAutoSave = findViewById(R.id.SlidingTileAutoSave);
        slidingTileAutoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCentre.loadManager(UserManager.USERS);
                BoardManager tempBoard = (BoardManager) gameCentre.getUserManager().
                        getSelectedGame("Sliding Tile");
                checkValidAutoSavedGame(tempBoard);
            }
        });
    }

    /**
     * Checks if a game has been autosaved, and transition into game if it has.
     * @param tempBoard the sudokuBoardManager
     */
    private void checkValidAutoSavedGame(BoardManager tempBoard) {
        if (tempBoard != null) {
            boardManager = tempBoard;
            gameCentre.saveManager(GameManager.TEMP_SAVE_START, boardManager);
            swapToSlidingTileGame();
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
     * Activate the ez win button.
     */
    private void addEzWinButtonListener() {
        Button saveButton = findViewById(R.id.preset);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEzWin();
                gameCentre.saveManager(GameManager.TEMP_SAVE_START, boardManager);
                swapToSlidingTileGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    public void swapToSlidingTileGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

    /**
     * Set the board to be one move away from winning.
     */
    public void setEzWin() {
        List<Tile> tiles = new ArrayList<>();
        Log.v(String.valueOf(boardSize), "bleh");
        final int numTiles = boardSize * boardSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }
        tiles.remove(tiles.size() - 1);
        tiles.add(new Tile(24));
        Board board = new Board(tiles, boardSize);
        board.swapTiles(board.boardSize - 1, board.boardSize - 1, board.boardSize - 1, board.boardSize - 2);
        boardManager = new BoardManager(board);
    }

    /**
     * Add more complexities , 3 by 3, 4 by 4, 5 by 5.
     */
    public void addComplexityButton() {
        final Spinner complexity = findViewById(R.id.complexity);
        complexity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                complexitySelected = complexity.getItemAtPosition(i).toString();
                switch (complexitySelected) {
                    case "3 by 3":
                        boardSize = 3;
                        break;
                    case "4 by 4":
                        boardSize = 4;
                        break;
                    default:
                        boardSize = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}