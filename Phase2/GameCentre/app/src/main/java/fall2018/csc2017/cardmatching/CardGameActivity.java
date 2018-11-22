package fall2018.csc2017.cardmatching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
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


public class CardGameActivity extends AppCompatActivity implements Observer {

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
    private ArrayList<Button> cardButtons;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
        autoSave();
        setMoveCountText();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_matching);
        gameCentre = new GameCentre(this);
        gameCentre.loadManager(GameManager.TEMP_SAVE_START);
        boardManager = (BoardManager) gameCentre.getGameManager();
        createCardButtons(this);
        addSaveButtonListener();
        // Add View to activity
        gridView = findViewById(R.id.grid1);
        gridView.setNumColumns(boardManager.getBoard().numCardPerCol);
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

                        columnWidth = displayWidth / boardManager.getBoard().numCardPerCol;
                        columnHeight = displayHeight / boardManager.getBoard().numCardPerRow;

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
                UserManager userManager = gameCentre.getUserManager();
                SavedGames savedGames = gameCentre.getSavedGames();
                String userName = userManager.getCurrentUser().getUsername();
                GameToSave gameToSave = new GameToSave(boardManager);
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
    private void createCardButtons(Context context) {
        Board board = boardManager.getBoard();
        cardButtons = new ArrayList<>();
        int cardBackId = board.getCard(0,0).getCardBackId();
        for (int row = 0; row != board.numCardPerRow; row++) {
            for (int col = 0; col != board.numCardPerCol; col++) {
                Button tmp = new Button(context);
                Card currentCard = boardManager.getBoard().getCard(row, col);
                if (currentCard.isPaired()){
                    tmp.setBackgroundResource(currentCard.getCardFaceId());
                }
                else{
                    currentCard.setOpened(0);
                    tmp.setBackgroundResource(cardBackId);
                }
                boardManager.setOpenPairExists(false);
                this.cardButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     *
     * * @param operation an array with 3 integers. Index 0 is row, 1 is col, and 2 is the mode.
     *                    mode 0 is to close the card, mode 1 is to open the card.
     */
    private void changeCardDisplay(int[] operation) {
        Board board = boardManager.getBoard();
        int row = operation[0];
        int col = operation[1];
        int mode = operation[2];
        int position = row * 4 + col;
        Button b = this.cardButtons.get(position);
        if (mode == 0){
            b.setBackgroundResource(board.getCard(row, col).getCardBackId());
        }
        else{
            b.setBackgroundResource(board.getCard(row, col).getCardFaceId());
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
     * updates the screen
     * @param o observable
     * @param arg object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!boardManager.puzzleSolved()) {
            changeCardDisplay((int[]) arg);
            display();
        }
        else {
            saveToFile(GameManager.TEMP_SAVE_WIN);
            switchToWinActivity();
        }
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
     * swaps activity to you win activity
     */
    private void switchToWinActivity() {
        Intent win = new Intent(this, YouWinActivity.class);
        startActivity(win);
    }

    /**
     * sets the move count on screen
     */
    private void setMoveCountText() {
        TextView moves = findViewById(R.id.MoveCount);
        moves.setText(String.format("%s", boardManager.getMove()));
    }
}

