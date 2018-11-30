package fall2018.csc2017.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SudokuPlayBoardTest {

    private SudokuBoard sudokuBoard = new SudokuBoard();
    private SudokuPlayBoard sudokuPlayBoard;

    @Before
    public void setUp() {
        sudokuPlayBoard = new SudokuPlayBoard(sudokuBoard.getSudokuBoard());
        SudokuBoardRandomizer randomizer = new SudokuBoardRandomizer(new SudokuBoard());
        randomizer.generateActiveBoard(sudokuPlayBoard);
    }

    @After
    public void tearDown() {
        sudokuPlayBoard = null;
    }

    @Test
    public void testSetSudokuBoardNumber() {
        int i = sudokuPlayBoard.popRemovedNumber();
        sudokuPlayBoard.setSudokuBoardNumber(i, 9);
        int row = i / 9;
        int col = i % 9;
        int num = sudokuPlayBoard.getSudokuBoard()[row][col];
        assertEquals(num, 9);
    }

    @Test
    public void testCopy() {
        SudokuPlayBoard copy = sudokuPlayBoard.copy();
        assertEquals(copy, sudokuPlayBoard);
    }

    @Test
    public void testCheckComplete() {
        assertTrue(sudokuPlayBoard.checkComplete());
        for (int i = 0; i < 36; i ++) {
            sudokuPlayBoard.setSudokuBoardNumber(sudokuPlayBoard.popRemovedNumber(), 9);
        }
        assertFalse(sudokuPlayBoard.checkComplete());
    }

    @Test
    public void testGetRepeats() {
        ArrayList<ArrayList<Integer>> repeats = sudokuPlayBoard.getRepeats();
        assertEquals(0, repeats.size());
    }

    @Test
    public void testAddRemovedNumber() {
        assertEquals(36, sudokuPlayBoard.getRemovedNumbers().size());
        sudokuPlayBoard.addRemovedNumber(29);
        assertEquals(37, sudokuPlayBoard.getRemovedNumbers().size());
        assertEquals(29, (int) sudokuPlayBoard.getRemovedNumbers().get(
                sudokuPlayBoard.getRemovedNumbers().size() - 1));
    }

    @Test
    public void testPopRemovedNumber() {
        assertNotNull(sudokuPlayBoard.popRemovedNumber());
        assertTrue(sudokuPlayBoard.popRemovedNumber() <= 80);
    }

    @Test
    public void testGetRemovedNumbers() {
        int num = sudokuPlayBoard.getRemovedNumbers().get(0);
        assertEquals((int) sudokuPlayBoard.popRemovedNumber(), num);
    }
}