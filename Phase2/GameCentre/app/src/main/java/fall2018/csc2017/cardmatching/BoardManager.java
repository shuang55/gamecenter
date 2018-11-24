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
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable, GameManager {
//TODO: make boardmanager an interface
    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The current number of moves that the player has made.
     */
    private int move = 0;

    private int[] lastMove = new int[2];
    private boolean openPairExists;
    /**
     * whether or not it is the first time that the player has two covered cards remaining
     */
    private boolean firstTime = true;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a board with varies complexities.
     *
     * @param numCardPair the size of the board.
     */
    BoardManager(int numCardPair) {
        List<Card> cards = new ArrayList<>();
        final int NUMCARDS = numCardPair * 2;
        for (int cardNum = 0; cardNum != NUMCARDS; cardNum++) {
            cards.add(new Card(cardNum));
        }
        Collections.shuffle(cards);
        this.board = new Board(cards, numCardPair);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        boolean solved = true;
        int numNotPaired= 0;
        for (Card card : board) {
            if (!(card.isPaired())) {
                numNotPaired++;
            }
        }
        if (numNotPaired == 2){
            solved = !firstTime;
            firstTime = !firstTime;
        } else{
            solved = false;
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
        int row = position / board.numCardPerCol;
        int col = position % board.numCardPerCol;
        Card cardTapped = board.getCard(row, col);
        return !(cardTapped.isOpen() == 1 || openPairExists) ;
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * <p>
     * Mode 0 is to cover the card, Mode 1 is to open the card.
     *
     * @param position the position of the tile that is tapped
     */
    public void touchMove(int position) {
        final int row = position / board.numCardPerCol;
        final int col = position % board.numCardPerCol;
        move++;
        board.flipCard(row, col, 1);
        if (move % 2 == 0) {
            openPairExists = true;
            Card card1 = board.getCard(lastMove[0], lastMove[1]);
            Card card2 = board.getCard(row, col);
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
     * @return the player's score
     */
    public int getScore() {
        int score = 1000 - move * 2 * (13 - board.numCardPair);
        if (score < 0){
            return 0;
        }
        return score;
    }

    /**
     * checks whether card1 and card2 are a pair
     * @param card1 the first card to be checked
     * @param card2 the second card to be checked
     * @return whether the two cards are a pair, or not
     */

    private boolean checkMatch(Card card1, Card card2){
        if (card1.getCardFaceId() == card2.getCardFaceId()){
            card1.setPaired(true);
            card2.setPaired(true);
            return true;
        }
        return false;
    }

    /**
     * covers the card at row, col after a fixed delay of 800 ms
     * @param row the row of the tile to be covered
     * @param col the column of the tile to be covered
     */

    private void coverCardAfterFixedDelay(int row, int col){
        final int rowOfCard = row;
        final int colOfCard = col;
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                board.flipCard(lastMove[0], lastMove[1], 0);
                board.flipCard(rowOfCard, colOfCard, 0);
                openPairExists = false;
            }
        }, 800);
    }

    /**
     * Return the game's name.
     * @return the name of the game
     */
    public String getGameName() {
        return "Card Matching";
    }

    /**
     * Return the date and time of the game being played.
     * @return a string representation of the time
     */
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * return the game difficulty.
     * @return string representation of the game difficulty
     */
    public String getGameDifficulty() {
        return String.format("%s by %s", board.numCardPerRow, board.numCardPerCol);
    }

    /**
     *
     * @return the current move number
     */
    public int getMove() {
        return move;
    }
}
