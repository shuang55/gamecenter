package fall2018.csc2017.cardmatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import static org.junit.Assert.*;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CardMatchingBoardTest {

    /**
     * CardMatching Board for testing.
     */
    private CardMatchingBoard board;

    private List<Card> makeCards(int numCardPair) {
        List<Card> cards = new ArrayList<>();
        final int numCards = numCardPair * 2;
        for (int i = 0; i <= numCards; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }

    @Before
    public void setUp() {
        List<Card> cards = makeCards(8);
        this.board = new CardMatchingBoard(cards, 8);
    }

    @After
    public void tearDown() {
        this.board = null;
    }

    @Test
    public void testFlipCard() {
        board.flipCard(0, 0, 1);
        assertEquals(1, board.getCard(0, 0).isOpen());
        board.flipCard(0, 0, 0);
        assertEquals(0, board.getCard(0, 0).isOpen());
    }

    @Test
    public void testGetCard() {
        List<Card> sampleCards = makeCards(8);
        this.board = new CardMatchingBoard(sampleCards, 8);
        Card cardToGet = board.getCard(0, 0);
        assertEquals(sampleCards.get(0), cardToGet);
    }

    @Test
    public void testToString() {
        String messageToCheck = board.toString().substring(0, 18);
        assertEquals("CardMatchingBoard{", messageToCheck);
    }
    @Test
    public void testIterator(){
        int i = 0;
        List<Card> sampleCards = makeCards(8);
        this.board = new CardMatchingBoard(sampleCards, 8);
        for(Card card: this.board){
            assertEquals(sampleCards.get(i), card);
            i++;
        }
        assertEquals(16, i);
    }
    @Test
    public void testGetNumCardPair(){
        assertEquals(8, board.getNumCardPair());
    }
    @Test
    public void testGetNumCardPerRow(){
        assertEquals(4, board.getNumCardPerRow());
    }
    @Test
    public void testGetNumCardPerCol(){
        assertEquals(4, board.getNumCardPerCol());
    }
}