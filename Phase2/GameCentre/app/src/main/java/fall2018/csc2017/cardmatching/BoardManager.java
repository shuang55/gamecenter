package fall2018.csc2017.cardmatching;

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

    /**
     * The board being managed.
     */
    private Board board;

    /**
     * The current number of moves that the player has made.
     */
    private int move = 0;

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
        final int numCards = numCardPair * 2;
        for (int cardNum = 0; cardNum != numCards; cardNum++) {
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
        for (Card card : board) {
            if (!(card.isPaired())) {
                solved = false;
            }
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank Card.
     *
     * @param position the Card to check
     * @return whether the Card is not paired or paired.
     */
    public boolean isValidTap(int position) {
        int row = position / board.numCardPerRow;
        int col = position % board.numCardPerRow;
        return board.getCard(row, col).isPaired();
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / board.numCardPerRow;
        int col = position % board.numCardPerRow;
        if (isValidTap(position)) {
            //does the flip card and match
        }

    }

    /**
     * Return the player's score.
     *
     * TODO: find an implementation.
     */
    public int getScore() {
        return 100;
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
        return String.format("%s by %s", board.numCardPerRow, board.numCardPerCol);
    }
}
