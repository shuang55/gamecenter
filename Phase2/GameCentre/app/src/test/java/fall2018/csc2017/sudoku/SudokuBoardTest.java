package fall2018.csc2017.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuBoardTest {

    /**
     * SudokuBoard for testing.
     */
    private SudokuBoard board = new SudokuBoard();

    @Test
    public void getSudokuBoard() {
        assertArrayEquals(board.sudokuBoard, board.getSudokuBoard());
    }

    @Test
    public void doubleInRow() {
        int expected = board.doubleInRow(5);
        int actual = -1;
        assertEquals(expected, actual);
        board.sudokuBoard[0][4] = 2;
        expected = board.doubleInRow(5);
        actual = 4;
        assertEquals(expected, actual);
    }

    @Test
    public void doubleInColumn() {
        int expected = board.doubleInColumn(25);
        int actual = -1;
        assertEquals(expected, actual);
        board.sudokuBoard[6][7] = 6;
        expected = board.doubleInColumn(61);
        actual = 25;
        assertEquals(expected, actual);
    }

    @Test
    public void doubleInBox() {
        int expected = board.doubleInBox(68);
        int actual = -1;
        assertEquals(expected, actual);
        board.sudokuBoard[8][3] = 3;
        expected = board.doubleInBox(68);
        actual = 75;
        assertEquals(expected, actual);
    }

}