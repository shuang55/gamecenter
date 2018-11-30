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
    /**
     * The cardMatchingBoard being managed.
     */
    private CardMatchingBoard cardMatchingBoard;

    /**
     * The current number of moves that the player has made.
     */
    private int move = 0;

    /**
     * the most recent move that the player has made
     */
    private int[] lastMove = new int[4];

    /**
     * whether or not an open pair exists
     */
    private boolean openPairExists;

    /**
     * Set openPairExists to False
     */
    void setOpenPairExistsToFalse() {
        this.openPairExists = false;
    }

    /**
     * Return the current cardMatchingBoard.
     *
     * @return the current cardMatchingBoard
     */
    CardMatchingBoard getCardMatchingBoard() {
        return cardMatchingBoard;
    }

    /**
     * Manage a cardMatchingBoard with various complexities.
     *
     * @param numCardPair the size of the cardMatchingBoard.
     * @param easyBoard   whether or not to generate an easy board
     */
    public CardMatchingBoardManager(int numCardPair, boolean easyBoard) {
        List<Card> cards = new ArrayList<>();
        final int NUMCARDS = numCardPair * 2;
        for (int cardNum = 0; cardNum != NUMCARDS; cardNum++) {
            cards.add(new Card(cardNum));
        }
        if (!easyBoard) {
            Collections.shuffle(cards);
        }
        this.cardMatchingBoard = new CardMatchingBoard(cards, numCardPair);
    }

    /**
     * Return whether all cards have been successfully paired
     *
     * @return whether the cards have all been successfully paired
     */
    public boolean puzzleSolved() {
        boolean solved = false;
        int numNotPaired = getNumNotPaired();
        int numNotOpened = getNumNotOpened();
        if (numNotOpened == 0 && numNotPaired == 2) {
            solved = true;
        }
        return solved;
    }

    /**
     * return the number of unopened cards
     *
     * @return the number of unopened cards
     */
    private int getNumNotOpened() {
        int unopened = 0;
        for (Card card : cardMatchingBoard) {
            if (card.isOpen() == 0) {
                unopened++;
            }
        }
        return unopened;
    }

    /**
     * return the number of unpaired cards
     *
     * @return the number of unpaired cards
     */
    private int getNumNotPaired() {
        int unpaired = 0;
        for (Card card : cardMatchingBoard) {
            if (!(card.getIsPaired())) {
                unpaired++;
            }
        }
        return unpaired;
    }

    /**
     * Return whether the card is a valid tap. True if card isn't flipped, false if already flipped.
     *
     * @param position the Card to check
     * @return whether the Card is not paired or paired.
     */
    public boolean isValidTap(int position) {
        int row = position / cardMatchingBoard.getNumCardPerCol();
        int col = position % cardMatchingBoard.getNumCardPerCol();
        Card cardTapped = cardMatchingBoard.getCard(row, col);
        return !(cardTapped.isOpen() == 1 || openPairExists);
    }

    /**
     * Process a touch at position in the cardMatchingBoard, swapping tiles as appropriate.
     * Mode 0 is to cover the card, Mode 1 is to open the card.
     *
     * @param position the position of the tile that is tapped
     */
    public void touchMove(int position) {
        final int row = position / cardMatchingBoard.getNumCardPerCol();
        final int col = position % cardMatchingBoard.getNumCardPerCol();
        move++;
        cardMatchingBoard.flipCard(row, col, 1);
        if (move % 2 == 0) {
            openPairExists = false;
            lastMove[2] = row;
            lastMove[3] = col;
            Card card1 = cardMatchingBoard.getCard(lastMove[0], lastMove[1]);
            Card card2 = cardMatchingBoard.getCard(lastMove[2], lastMove[3]);
            boolean cardsMatch = card1.getCardFaceId() == card2.getCardFaceId();
            coverOrPair(card1, card2, cardsMatch);
        } else {
            lastMove[0] = row;
            lastMove[1] = col;
        }
    }

    /**
     * covers or pairs card1 and card2, depending on if cardsMatch
     *
     * @param card1      the first card
     * @param card2      the second card
     * @param cardsMatch whether or not the cards match
     */
    private void coverOrPair(Card card1, Card card2, boolean cardsMatch) {
        if (!cardsMatch) {
            openPairExists = true;
            coverCardAfterFixedDelay();
        } else {
            card1.setPaired();
            card2.setPaired();
            cardMatchingBoard.boardUpdate(null);
        }
    }

    /**
     * Return the player's score.
     *
     * @return the score
     */
    public int getScore() {
        int score = 1000 - move * 2 * (13 - cardMatchingBoard.getNumCardPair());
        if (score < 0) {
            return 0;
        }
        return score;
    }

    /**
     * covers the cards in the past two turns, after 1000 ms
     */
    private void coverCardAfterFixedDelay() {
        final int rowOfCard = lastMove[2];
        final int colOfCard = lastMove[3];
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                cardMatchingBoard.flipCard(lastMove[0], lastMove[1], 0);
                cardMatchingBoard.flipCard(rowOfCard, colOfCard, 0);
                openPairExists = false;
            }
        }, 1000);
    }

    /**
     * Return the game's name.
     *
     * @return the game's name
     */
    public String getGameName() {
        return "Card Matching";
    }

    /**
     * Return the date and time of the game being played.
     *
     * @return the current time
     */
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * return the game difficulty.
     *
     * @return the game difficulty
     */
    public String getGameDifficulty() {
        return String.format("%s by %s", cardMatchingBoard.getNumCardPerRow(), cardMatchingBoard.getNumCardPerCol());
    }

    /**
     * return the current move
     *
     * @return the current move
     */
    public int getMove() {
        return move;
    }
}
