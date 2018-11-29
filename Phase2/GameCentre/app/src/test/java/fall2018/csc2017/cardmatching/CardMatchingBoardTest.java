package fall2018.csc2017.cardmatching;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CardMatchingBoardTest {

    /**
     * CardMatching Board for testing.
     */
    private CardMatchingBoard board;

    public List<Card> makeCards(int numCardPair){
        List<Card> cards = new ArrayList<>();
        final int numCards = numCardPair * 2;
        for (int i = 0; i <= numCards; i++){
            cards.add(new Card(i));
        }
        return cards;
    }

    @Before
    public void setUp(){
        List<Card> cards = makeCards(8);
        this.board = new CardMatchingBoard(cards, 8);
    }
    @After
    public void tearDown(){
        this.board = null;
    }
    @Test
    public void testFlipCard(){
        board.flipCard(0,0,1);
        assertTrue(board.getCard(0,0).isOpen() == 1);
    }
}