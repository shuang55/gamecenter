package fall2018.csc2017.cardmatching;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

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
        for (Card card : board) {
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
        int row = position / board.numCardPerCol;
        int col = position % board.numCardPerCol;
        Card cardTapped = board.getCard(row, col);
        return !(cardTapped.isOpen() == 1);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * <p>
     * Mode 0 is to cover the card, Mode 1 is to open the card.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        System.out.println(position);
        int row = position / board.numCardPerCol;
        int col = position % board.numCardPerCol;
        if (isValidTap(position) && (move % 2 == 0)) {
            board.flipCard(row, col, 1);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            board.flipCard(row, col, 0);
            move++;
        } else if (isValidTap(position)) {
            board.flipCard(row, col, 1);
            move++;
        }

    }

    /**
     * Return the player's score.
     * TODO: change this implementation.
     */
    public int getScore() {
        return 100 - move;
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

    public int getMove() {
        return move;
    }
}
