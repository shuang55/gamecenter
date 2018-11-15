package fall2018.csc2017.cardmatching;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.io.Serializable;
import java.util.Iterator;

/**
 * The card matching board.
 */
public class Board extends Observable implements Serializable, Iterable<Card> {

    /**
     * The board size
     */
    int numCardPair;

    int numCardPerRow;

    int numCardPerCol;

    /**
     * The cards on the board in row-major order.
     */
    private Card[][] cards;

    /**
     * Initializes the Board for game.
     *
     * Choice of number of card pairs are: 8, 10, 12
     * BoardSize are: 4 X 4, 4 X 5, 4 X 6 (respectively)
     *
     * @param cards     list of tiles
     * @param numCardPair the number of pairs there are
     */
    Board(List<Card> cards, int numCardPair) {
        this.numCardPair = numCardPair;
        this.numCardPerRow = 4;

        switch(numCardPair){
            case 8:
                this.numCardPerCol = 4;
                break;
            case 10:
                this.numCardPerCol = 5;
                break;
            case 12:
                this.numCardPerCol = 6;
                break;
            default:
                this.numCardPerCol = 4;
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
     *
     * @param row the first tile row
     * @param col the first tile col
     */
    void flipCard(int row, int col, int mode) {
        int[] operation = {row, col, mode};
        setChanged();
        notifyObservers(operation);
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Card getCard(int row, int col) {
        return cards[row][col];
    }

    /**
     * Finds the card with id cardID
     *
     * @param cardID the id of the tile
     * @return the position of the tile
     */
    int findCard(int cardID) {
        int position = 0;
        for (Card[] c1 : cards) {
            for (Card c2 : c1) {
                if (c2.getId() == cardID) {
                    return position;
                }
                position++;
            }
        }
        return position;
    }

    @Override
    public String toString() {
        return "Board{" +
                "cards=" + Arrays.toString(cards) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Card> iterator() {
        return new BoardIterator();
    }

    /**
     * An iterator that iterates through Board
     */
    private class BoardIterator implements Iterator<Card> {

        private int row = 0;
        private int col = 0;

        @Override
        public boolean hasNext() {
            return !(row == numCardPerRow - 1 && col == numCardPerCol - 1);
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