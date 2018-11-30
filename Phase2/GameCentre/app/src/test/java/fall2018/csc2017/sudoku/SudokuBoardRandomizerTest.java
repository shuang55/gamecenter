package fall2018.csc2017.sudoku;

import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuBoardRandomizerTest {

    private SudokuBoard sudokuBoard = new SudokuBoard();
    private SudokuPlayBoard sudokuPlayBoard;

    /**
     * Test to see if generateRandomBoard successfully randomizes the board
     */
    @Test
    public void testGenerateRandomBoard() {
        sudokuPlayBoard = new SudokuPlayBoard(sudokuBoard.getSudokuBoard());
        SudokuPlayBoard oldBoard = sudokuPlayBoard.copy();
        SudokuBoardRandomizer randomizer = new SudokuBoardRandomizer(sudokuPlayBoard);
        randomizer.generateRandomBoard();
        assertNotEquals(sudokuPlayBoard, oldBoard);
    }

    /**
     * Test to see if generateActiveBoard removes random numbers from sudokuPlayBoard
     */
    @Test
    public void testGenerateActiveBoard() {
        sudokuPlayBoard = new SudokuPlayBoard(sudokuBoard.getSudokuBoard());
        SudokuBoardRandomizer randomizer = new SudokuBoardRandomizer(sudokuPlayBoard);
        randomizer.generateActiveBoard(sudokuPlayBoard);
        assertTrue(sudokuPlayBoard.checkComplete());
    }
}