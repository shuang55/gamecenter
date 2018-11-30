package fall2018.csc2017.cardmatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CardMatchingBoardTest {

    /**
     * CardMatching Board for testing.
     */
    private CardMatchingBoard board;

    /**
     * make the list of cards to be used in creating a board
     *
     * @param numCardPair number of card pairs
     * @return return list of cards to make the board manager
     */
    private List<Card> makeCards(int numCardPair) {
        List<Card> cards = new ArrayList<>();
        final int numCards = numCardPair * 2;
        for (int i = 0; i <= numCards; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }

    /**
     * create a board with 8 pairs
     */
    @Before
    public void setUp() {
        List<Card> cards = makeCards(8);
        this.board = new CardMatchingBoard(cards, 8);
    }

    /**
     * make the board equal to null after test is finished
     */
    @After
    public void tearDown() {
        this.board = null;
    }

    /**
     * test whether flip card covers and opens correctly
     */
    @Test
    public void testFlipCard() {
        board.flipCard(0, 0, 1);
        assertEquals(1, board.getCard(0, 0).isOpen());
        board.flipCard(0, 0, 0);
        assertEquals(0, board.getCard(0, 0).isOpen());
    }

    /**
     * test whether getCard() gets the correct card
     */
    @Test
    public void testGetCard() {
        List<Card> sampleCards = makeCards(8);
        this.board = new CardMatchingBoard(sampleCards, 8);
        Card cardToGet = board.getCard(0, 0);
        assertEquals(sampleCards.get(0), cardToGet);
    }

    /**
     * test whether getCard() gets the last card in a 4x5 board
     */
    @Test
    public void testGetCardOn4x5Board() {
        List<Card> sampleCards = makeCards(10);
        this.board = new CardMatchingBoard(sampleCards, 10);
        Card cardToGet = board.getCard(4, 3);
        assertEquals(sampleCards.get(19), cardToGet);
    }

    /**
     * test whether toString() returns (part of) the expected string
     */
    @Test
    public void testToString() {
        String messageToCheck = board.toString().substring(0, 18);
        assertEquals("CardMatchingBoard{", messageToCheck);
    }

    /**
     * test whether the iterator iterates through all the cards
     */
    @Test
    public void testIterator() {
        int i = 0;
        List<Card> sampleCards = makeCards(8);
        this.board = new CardMatchingBoard(sampleCards, 8);
        for (Card card : this.board) {
            assertEquals(sampleCards.get(i), card);
            i++;
        }
        assertEquals(16, i);
    }

    /**
     * test whether getNumCardPair() returns the correct number of card pairs
     */
    @Test
    public void testGetNumCardPair() {
        assertEquals(8, board.getNumCardPair());
    }

    /**
     * test whether whether getNumCardPerRow() returns the correct number of cards per row
     */
    @Test
    public void testGetNumCardPerRow() {
        assertEquals(4, board.getNumCardPerRow());
    }

    /**
     * test whether whether getNumCardPerCol() returns the correct number of cards per col
     */
    @Test
    public void testGetNumCardPerCol() {
        assertEquals(4, board.getNumCardPerCol());
    }
}