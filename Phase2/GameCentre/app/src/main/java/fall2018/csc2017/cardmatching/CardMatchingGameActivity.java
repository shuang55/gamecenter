package fall2018.csc2017.cardmatching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class CardMatchingGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private CardMatchingBoardManager cardMatchingBoardManager;

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
        setMoveCountText();
        autoSave();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_matching);
        loadManagers();

        // activate button
        addSaveButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.CardMatchingGrid);
        gridView.setNumColumns(cardMatchingBoardManager.getCardMatchingBoard().numCardPerCol);
        gridView.setGameManager(cardMatchingBoardManager);
        cardMatchingBoardManager.getCardMatchingBoard().addObserver(this);

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / cardMatchingBoardManager.getCardMatchingBoard().numCardPerCol;
                        columnHeight = displayHeight / cardMatchingBoardManager.getCardMatchingBoard().numCardPerRow;

                        display();
                    }
                });
    }

    /**
     * Load necessary managers
     */
    private void loadManagers() {
        gameCentre = new GameCentre(this);
        gameCentre.loadManager(GameManager.TEMP_SAVE_START);
        cardMatchingBoardManager = (CardMatchingBoardManager) gameCentre.getGameManager();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.CardMatchingSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManager userManager = gameCentre.getUserManager();
                SavedGames savedGames = gameCentre.getSavedGames();
                String userName = userManager.getCurrentUser().getUsername();
                GameToSave gameToSave = new GameToSave(cardMatchingBoardManager);
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
        CardMatchingBoard cardMatchingBoard = cardMatchingBoardManager.getCardMatchingBoard();
        cardButtons = new ArrayList<>();
        for (int row = 0; row != cardMatchingBoard.numCardPerRow; row++) {
            for (int col = 0; col != cardMatchingBoard.numCardPerCol; col++) {
                Button button = new Button(context);
                Card currentCard = cardMatchingBoardManager.getCardMatchingBoard().getCard(row, col);
                int mode = currentCard.isPaired() ? 1 : 0;
                setCardBackGround(currentCard, mode, button);
                cardMatchingBoardManager.setOpenPairExistsToFalse();
                this.cardButtons.add(button);
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
        CardMatchingBoard cardMatchingBoard = cardMatchingBoardManager.getCardMatchingBoard();
        int row = operation[0];
        int col = operation[1];
        int mode = operation[2];
        int position = row * 4 + col;
        Button button = this.cardButtons.get(position);
        Card card = cardMatchingBoard.getCard(row, col);
        setCardBackGround(card, mode, button);
        if (operation[3] == 1){
            gameCentre.saveManager(GameManager.TEMP_SAVE_WIN, cardMatchingBoardManager);
            switchToWinActivity();
        }
    }

    private void setCardBackGround(Card card, int mode, Button button){
        if (mode == 0){
            button.setBackgroundResource(card.getCardBackId());
        }
        else{
            button.setBackgroundResource(card.getCardFaceId());
        }
        card.setOpened(mode);
    }

    /**
     * updates the screen
     * @param o observable
     * @param arg object
     */
    @Override
    public void update(Observable o, Object arg) {
            changeCardDisplay((int[]) arg);
            display();
            if (cardMatchingBoardManager.puzzleSolved()) {
                gameCentre.saveManager(GameManager.TEMP_SAVE_WIN, cardMatchingBoardManager);
                switchToWinActivity();
            }
        }

    /**
     * Autosaves the board
     */
    private void autoSave() {
        UserManager userManager = gameCentre.getUserManager();
        userManager.autoSaveGame(cardMatchingBoardManager);
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
        moves.setText(String.format("%s", cardMatchingBoardManager.getMove()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        createCardButtons(this);
    }
}

