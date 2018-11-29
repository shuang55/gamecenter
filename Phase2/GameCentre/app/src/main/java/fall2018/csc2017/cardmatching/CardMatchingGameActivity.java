package fall2018.csc2017.cardmatching;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.CustomAdapter;
import fall2018.csc2017.gamecentre.GameActivity;
import fall2018.csc2017.gamecentre.GestureDetectGridView;

// Excluded from tests because it's a view class, there is no logic in this class
public class CardMatchingGameActivity extends GameActivity implements Observer {

    /**
     * The board manager.
     */
    private CardMatchingBoardManager cardMatchingBoardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> cardButtons;

    /**
     * The Grid View
     */
    private GestureDetectGridView gridView;
    /**
     * Calculated column height and width based on device size
     */
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        gridView.setAdapter(new CustomAdapter(cardButtons, columnWidth, columnHeight));
        setMoveCountText(R.id.CardMatchingMoveCount);
        gameCentre.autoSave(cardMatchingBoardManager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_matching);
        loadManagers(this);
        cardMatchingBoardManager = (CardMatchingBoardManager) gameManager;

        // activate button
        addSaveButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.CardMatchingGrid);
        gridView.setNumColumns(cardMatchingBoardManager.getCardMatchingBoard().getNumCardPerCol());
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
                        columnWidth = displayWidth / cardMatchingBoardManager.getCardMatchingBoard().getNumCardPerCol();
                        columnHeight = displayHeight / cardMatchingBoardManager.getCardMatchingBoard().getNumCardPerRow();
                        display();
                    }
                });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.CardMatchingSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCentre.saveGame(cardMatchingBoardManager);
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
        for (int row = 0; row != cardMatchingBoard.getNumCardPerRow(); row++) {
            for (int col = 0; col != cardMatchingBoard.getNumCardPerCol(); col++) {
                Button button = new Button(context);
                Card currentCard = cardMatchingBoardManager.getCardMatchingBoard().getCard(row, col);
                int mode = currentCard.getIsPaired() ? 1 : 0;
                setCardBackGround(currentCard, mode, button);
                this.cardButtons.add(button);
            }
        }
        cardMatchingBoardManager.setOpenPairExistsToFalse();
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     *
     * @param operation an array with 3 integers. Index 0 is row, 1 is col, and 2 is the mode.
     *                  mode 0 is to close the card, mode 1 is to open the card.
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
    }

    private void setCardBackGround(Card card, int mode, Button button) {
        if (mode == 0) {
            button.setBackgroundResource(card.getCardBackId());
        } else {
            button.setBackgroundResource(card.getCardFaceId());
        }
        card.setOpened(mode);
    }

    /**
     * updates the screen
     *
     * @param o   observable
     * @param arg object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            changeCardDisplay((int[]) arg);
        }
        display();
        if (cardMatchingBoardManager.puzzleSolved()) {
//            gameCentre.gameManagerWin(cardMatchingBoardManager);
            switchToWinActivity(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        createCardButtons(this);
    }
}

