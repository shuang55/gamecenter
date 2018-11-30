package fall2018.csc2017.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SudokuPlayBoardTest {

    /**
     * A sudokuBoard for creating the sudokuPlayBoard
     */
    private SudokuBoard sudokuBoard = new SudokuBoard();
    /**
     * sudokuPlayBoard used in tests
     */
    private SudokuPlayBoard sudokuPlayBoard;

    /**
     * Setup
     */
    @Before
    public void setUp() {
        sudokuPlayBoard = new SudokuPlayBoard(sudokuBoard.getSudokuBoard());
        SudokuBoardRandomizer randomizer = new SudokuBoardRandomizer(new SudokuBoard());
        randomizer.generateActiveBoard(sudokuPlayBoard);
    }

    /**
     * TearDown
     */
    @After
    public void tearDown() {
        sudokuPlayBoard = null;
    }

    /**
     * Test to see if setSudokuBoardNumber successfully sets a number
     */
    @Test
    public void testSetSudokuBoardNumber() {
        int i = sudokuPlayBoard.popRemovedNumber();
        sudokuPlayBoard.setSudokuBoardNumber(i, 9);
        int row = i / 9;
        int col = i % 9;
        int num = sudokuPlayBoard.getSudokuBoard()[row][col];
        assertEquals(num, 9);
    }

    /**
     * Test to see if copy returns a board with the same numbers
     */
    @Test
    public void testCopy() {
        SudokuPlayBoard copy = sudokuPlayBoard.copy();
        assertEquals(copy, sudokuPlayBoard);
    }

    /**
     * Test to see if checkComplete returns whether the board contains any 0's
     */
    @Test
    public void testCheckComplete() {
        assertTrue(sudokuPlayBoard.checkComplete());
        for (int i = 0; i < 36; i ++) {
            sudokuPlayBoard.setSudokuBoardNumber(sudokuPlayBoard.popRemovedNumber(), 9);
        }
        assertFalse(sudokuPlayBoard.checkComplete());
    }

    /**
     * Test to see if getRepeats returns the correct repeats
     */
    @Test
    public void testGetRepeats() {
        ArrayList<ArrayList<Integer>> repeats = sudokuPlayBoard.getRepeats();
        assertEquals(0, repeats.size());
    }

    /**
     * Test to see if addRemovedNumber successfully adds an Integer to removedNumbers
     */
    @Test
    public void testAddRemovedNumber() {
        assertEquals(36, sudokuPlayBoard.getRemovedNumbers().size());
        sudokuPlayBoard.addRemovedNumber(29);
        assertEquals(37, sudokuPlayBoard.getRemovedNumbers().size());
        assertEquals(29, (int) sudokuPlayBoard.getRemovedNumbers().get(
                sudokuPlayBoard.getRemovedNumbers().size() - 1));
    }

    /**
     * Test to see if popRemovedNumber returns the number at index 0 of removedNumbers
     */
    @Test
    public void testPopRemovedNumber() {
        assertNotNull(sudokuPlayBoard.popRemovedNumber());
        assertTrue(sudokuPlayBoard.popRemovedNumber() <= 80);
    }

    /**
     * Test for removedNumbers getter
     */
    @Test
    public void testGetRemovedNumbers() {
        int num = sudokuPlayBoard.getRemovedNumbers().get(0);
        assertEquals((int) sudokuPlayBoard.popRemovedNumber(), num);
    }
}