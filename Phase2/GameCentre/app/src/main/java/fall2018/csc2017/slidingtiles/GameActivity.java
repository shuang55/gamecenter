package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.CustomAdapter;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;
import fall2018.csc2017.gamecentre.GameToSave;
import fall2018.csc2017.gamecentre.GestureDetectGridView;
import fall2018.csc2017.gamecentre.SavedGames;
import fall2018.csc2017.gamecentre.UserManager;
import fall2018.csc2017.gamecentre.YouWinActivity;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * Gamecentre for managing files
     */
    private GameCentre gameCentre;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
        setMoveCountText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tile);
        gameCentre = new GameCentre(this);
        gameCentre.loadManager(GameManager.TEMP_SAVE_START);
        boardManager = (BoardManager) gameCentre.getGameManager();
        createTileButtons(this);

        //Activate undo button
        addUndoButtonListener();
        addSaveButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().boardSize);
        gridView.setGameManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                         columnWidth = displayWidth / boardManager.getBoard().boardSize;
                        columnHeight = displayHeight / boardManager.getBoard().boardSize;

                        display();
                    }
                });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedGames savedGames = gameCentre.getSavedGames();
                UserManager userManager = gameCentre.getUserManager();
                String userName = userManager.getCurrentUser().getUsername();
                String timeSaved = boardManager.getTime();
                String gameDifficulty = boardManager.getGameDifficulty();
                GameToSave gameToSave = new GameToSave(timeSaved, "Sliding Tile", gameDifficulty, boardManager);
                savedGames.updateSavedGames(gameToSave, userName);
                gameCentre.saveManager(SavedGames.SAVEDGAMES, savedGames);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.boardSize; row++) {
            for (int col = 0; col != board.boardSize; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().boardSize;
            int col = nextPos % boardManager.getBoard().boardSize;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        autoSave();
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
     * updates the screen
     * @param o observable
     * @param arg object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!boardManager.puzzleSolved()) {
            display();
        }
        else {
            saveToFile(GameManager.TEMP_SAVE_WIN);
            switchToWinActivity();
        }
    }

    /**
     * swaps activity to you win activity
     */
    private void switchToWinActivity() {
        Intent win = new Intent(this, YouWinActivity.class);
        startActivity(win);
    }

    /**
     * adds an undo button and undoes the previous move when clicked.
     */
    private void addUndoButtonListener() {
        Button undo = findViewById(R.id.Undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean undo = boardManager.undoMove();
                autoSave();
                if (!undo) {
                    makeToastNoMoreUndo();
                }
            }
        });
    }

    /**
     * Autosaves the board
     */
    private void autoSave() {
        UserManager userManager = gameCentre.getUserManager();
        userManager.autoSaveGame(boardManager);
        gameCentre.saveManager(UserManager.USERS, userManager);
    }

    /**
     * give the text no more moves are available
     */
    private void makeToastNoMoreUndo() {
        Toast.makeText(this, "No more moves are available", Toast.LENGTH_SHORT).show();
    }

    /**
     * sets the move count on screen
     */
    private void setMoveCountText() {
        TextView moves = findViewById(R.id.MoveCount);
        moves.setText(String.format("%s", boardManager.getMove()));
    }
}
