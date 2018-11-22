package fall2018.csc2017.cardmatching;

import android.os.Handler;
import android.os.Looper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.gamecentre.GameManager;

/**
 * Manage a cardMatchingBoard, including swapping tiles, checking for a win, and managing taps.
 */
public class CardMatchingBoardManager implements Serializable, GameManager {
//TODO: make boardmanager an interface
    /**
     * The cardMatchingBoard being managed.
     */
    private CardMatchingBoard cardMatchingBoard;

    /**
     * The current number of moves that the player has made.
     */
    private int move = 0;

    private int[] lastMove = new int[2];

    public void setOpenPairExists(boolean openPairExists) {
        this.openPairExists = openPairExists;
    }

    private boolean openPairExists;

    /**
     * Return the current cardMatchingBoard.
     */
    CardMatchingBoard getCardMatchingBoard() {
        return cardMatchingBoard;
    }

    /**
     * Manage a cardMatchingBoard with varies complexities.
     *
     * @param numCardPair the size of the cardMatchingBoard.
     */
    CardMatchingBoardManager(int numCardPair) {
        List<Card> cards = new ArrayList<>();
        final int NUMCARDS = numCardPair * 2;
        for (int cardNum = 0; cardNum != NUMCARDS; cardNum++) {
            cards.add(new Card(cardNum));
        }
        Collections.shuffle(cards);
        this.cardMatchingBoard = new CardMatchingBoard(cards, numCardPair);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        for (Card card : cardMatchingBoard) {
            if (!(card.isPaired())) {
                solved = false;
            }
        }
        return solved;
    }

    /**
     * Return whether the card is a valid tap. True if card isn't flipped, false if already flipped.
     *
     * @param position the Card to check
     * @return whether the Card is not paired or paired.
     */
    public boolean isValidTap(int position) {
        int row = position / cardMatchingBoard.numCardPerCol;
        int col = position % cardMatchingBoard.numCardPerCol;
        Card cardTapped = cardMatchingBoard.getCard(row, col);
        return !(cardTapped.isOpen() == 1 || openPairExists) ;
    }

    /**
     * Process a touch at position in the cardMatchingBoard, swapping tiles as appropriate.
     * <p>
     * Mode 0 is to cover the card, Mode 1 is to open the card.
     *
     * @param position the position of the tile that is tapped
     */
    public void touchMove(int position) {
        final int row = position / cardMatchingBoard.numCardPerCol;
        final int col = position % cardMatchingBoard.numCardPerCol;
        move++;
        cardMatchingBoard.flipCard(row, col, 1);
        if (move % 2 == 0) {
            openPairExists = true;
            Card card1 = cardMatchingBoard.getCard(lastMove[0], lastMove[1]);
            Card card2 = cardMatchingBoard.getCard(row, col);
            if (checkMatch(card1, card2)){
                openPairExists = false;
            }
            else{
                coverCardAfterFixedDelay(row, col);
            }
        }
        else{
            lastMove[0] = row;
            lastMove[1] = col;
        }
    }

    /**
     * Return the player's score.
     */
    public int getScore() {
        int score = 1000 - move * 2 * (13 - cardMatchingBoard.numCardPair);
        if (score < 0){
            return 0;
        }
        return score;
    }

    private boolean checkMatch(Card card1, Card card2){
        if (card1.getCardFaceId() == card2.getCardFaceId()){
            card1.setPaired(true);
            card2.setPaired(true);
            return true;
        }
        return false;
    }

    private void coverCardAfterFixedDelay(int row, int col){
        final int rowOfCard = row;
        final int colOfCard = col;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                cardMatchingBoard.flipCard(lastMove[0], lastMove[1], 0);
                cardMatchingBoard.flipCard(rowOfCard, colOfCard, 0);
                openPairExists = false;
            }
        }, 1500);
    }

    /**
     * Return the game's name.
     */
    public String getGameName() {
        return "Card Matching";
    }

    /**
     * Return the date and time of the game being played.
     */
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * return the game difficulty.
     */
    public String getGameDifficulty() {
        return String.format("%s by %s", cardMatchingBoard.numCardPerRow, cardMatchingBoard.numCardPerCol);
    }

    public int getMove() {
        return move;
    }
}
