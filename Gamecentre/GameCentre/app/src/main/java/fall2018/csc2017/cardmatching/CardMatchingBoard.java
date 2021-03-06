package fall2018.csc2017.cardmatching;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.io.Serializable;
import java.util.Iterator;

/**
 * The card matching board.
 */
public class CardMatchingBoard extends Observable implements Serializable, Iterable<Card> {

    /**
     * The number of card pairs
     */
    private int numCardPair;

    /**
     * number of cards per row
     */
    private int numCardPerRow;

    /**
     * number of cards per column
     */
    private int numCardPerCol;

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * Initializes the CardMatchingBoard for game.
     * Choice of number of card pairs are: 8, 10, 12
     * BoardSize are: 4 X 4, 4 X 5, 4 X 6 (respectively)
     *
     * @param cards       list of card tiles
     * @param numCardPair the number of pairs there are
     */
    CardMatchingBoard(List<Card> cards, int numCardPair) {
        this.numCardPair = numCardPair;
        this.numCardPerCol = 4;

        switch (numCardPair) {
            case 8:
                this.numCardPerRow = 4;
                break;
            case 10:
                this.numCardPerRow = 5;
                break;
            case 12:
                this.numCardPerRow = 6;
                break;
        }
        this.cards = new Card[numCardPerRow][numCardPerCol];
        Iterator<Card> iter = cards.iterator();

        for (int row = 0; row != numCardPerRow; row++) {
            for (int col = 0; col != numCardPerCol; col++) {
                this.cards[row][col] = iter.next();
            }
        }
    }

    /**
     * Flip the card at (row, col).
     * Mode 0 is to cover the card, Mode 1 is to open the card.
     *
     * @param row the first tile row
     * @param col the first tile col
     */
    void flipCard(int row, int col, int mode) {
        int[] operation = {row, col, mode};
        getCard(row, col).setOpened(mode);
        boardUpdate(operation);
    }

    /**
     * Calls CardMatchingGameActivity to update the view.
     *
     * @param arg Object to be updated
     */
    void boardUpdate(Object arg){
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Card getCard(int row, int col) {
        return cards[row][col];
    }

    @Override
    public String toString() {
        return "CardMatchingBoard{" +
                "cards=" + Arrays.toString(cards) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Card> iterator() {
        return new BoardIterator();
    }

    /**
     * Get numCardPair.
     *
     * @return the number of card pairs
     */
    int getNumCardPair() {
        return numCardPair;
    }

    /**
     * Get NumCardPerRow.
     *
     * @return number of cards per row
     */
    int getNumCardPerRow() {
        return numCardPerRow;
    }

    /**
     * Get numCardPerCol.
     *
     * @return the number of cards per column
     */
    int getNumCardPerCol() {
        return numCardPerCol;
    }

    /**
     * An iterator that iterates through CardMatchingBoard.
     */
    private class BoardIterator implements Iterator<Card> {

        int row = 0;
        int col = 0;

        @Override
        public boolean hasNext() {
            return row <= numCardPerRow - 1 && col <= numCardPerCol - 1;
        }

        @Override
        public Card next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more cards");
            } else if (col == numCardPerCol - 1) {
                Card c = cards[row][col];
                row++;
                col = 0;
                return c;
            } else {
                Card c = cards[row][col];
                col++;
                return c;
            }
        }
    }
}