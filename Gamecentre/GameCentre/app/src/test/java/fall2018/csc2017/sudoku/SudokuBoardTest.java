package fall2018.csc2017.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuBoardTest {

    /**
     * SudokuBoard for testing.
     */
    private SudokuBoard board = new SudokuBoard();

    /**
     * Test for getter for sudokuboard integer array
     */
    @Test
    public void testGetSudokuBoard() {
        assertArrayEquals(board.sudokuBoard, board.getSudokuBoard());
    }

    /**
     * Test to see if doubleInRow returns -1 when there are no doubles
     */
    @Test
    public void testDoubleInRow() {
        int expected = board.doubleInRow(5);
        int actual = -1;
        assertEquals(expected, actual);
        board.sudokuBoard[0][4] = 2;
        expected = board.doubleInRow(5);
        actual = 4;
        assertEquals(expected, actual);
    }

    /**
     * Test to see if doubleInColumn returns -1 when there are no doubles
     */
    @Test
    public void testDoubleInColumn() {
        int expected = board.doubleInColumn(25);
        int actual = -1;
        assertEquals(expected, actual);
        board.sudokuBoard[6][7] = 6;
        expected = board.doubleInColumn(61);
        actual = 25;
        assertEquals(expected, actual);
    }

    /**
     * Test to see if doubleInBox returns -1 when there are no doubles
     */
    @Test
    public void testDoubleInBox() {
        int expected = board.doubleInBox(68);
        int actual = -1;
        assertEquals(expected, actual);
        board.sudokuBoard[8][3] = 3;
        expected = board.doubleInBox(68);
        actual = 75;
        assertEquals(expected, actual);
    }

    /**
     * Test for checking equality between two SudokuBoard
     */
    @Test
    public void testEquality() {
        SudokuBoard board1 = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        Integer int1 = 9;
        assertEquals(board1, board2);
        assertNotEquals(board1, int1);
    }

    /**
     * Test to see if two equal boards hash to the same number
     */
    @Test
    public void testHashCode() {
        SudokuBoard board1 = new SudokuBoard();
        SudokuBoard board2 = new SudokuBoard();
        assertEquals(board1.hashCode(), board2.hashCode());
    }
}