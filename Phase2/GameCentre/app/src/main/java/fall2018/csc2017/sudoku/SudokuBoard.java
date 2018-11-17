package fall2018.csc2017.sudoku;


import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SudokuBoard implements Serializable {


    /**
     * array of ints in sudoku board
     */
    private Integer[][] sudokuBoard = new Integer[9][9];

    /**
     * Constructor for sudokuboard
     */
    public SudokuBoard() {
        setUpSolvedBoard();
    }

    public SudokuBoard(Integer[][] preSolvedBoard) {
        for (int i = 0; i < 9; i++) {
            sudokuBoard[i] = preSolvedBoard[i].clone();
        }

    }

    /**
     * Gets the sudokuBoard
     * @return the sudokuBoard
     */
    public Integer[][] getSudokuBoard() {
        return sudokuBoard;
    }

    /**
     * Sets the board at [row][col] to be num
     * @param row row to be updated
     * @param column column to be updated
     * @param number number to be updated
     */
    public void setSudokuBoardNumber(int row, int column, int number) {
        sudokuBoard[row][column] = number;
    }

    /**
     * Set up a solved board
     */
    private void setUpSolvedBoard() {
        sudokuBoard = new Integer[][]{
                {1, 4, 6, 7, 9, 2, 3, 8, 5},
                {2, 5, 8, 3, 4, 6, 7, 9, 1},
                {3, 7, 9, 5, 8, 1, 4, 6, 2},
                {4, 3, 7, 9, 1, 5, 8, 2, 6},
                {5, 8, 1, 6, 2, 7, 9, 3, 4},
                {6, 9, 2, 4, 3, 8, 1, 5, 7},
                {7, 1, 3, 2, 6, 9, 5, 4, 8},
                {8, 2, 4, 1, 5, 3, 6, 7, 9},
                {9, 6, 5, 8, 7, 4, 2, 1, 3}};
    }

    /**
     * Checks whether each row is solved
     *
     * @return whether each row is solved
     */
    public boolean solvedRow() {
        boolean solved = true;
        Set<Integer> solution = new HashSet<>(Arrays.asList(sudokuBoard[0]));
        for (int i = 0; i < 9; i++) {
            Set<Integer> set = new HashSet<>(Arrays.asList(sudokuBoard[i]));
            if (!set.equals(solution)) {
                solved = false;
                break;
            }
        }
        return solved;
    }
    /**
     * String representation of board for debugging purposes
     * @return the string representation of the board
     */
    @Override
    public String toString() {
        String s = "{";
        for (int i = 0; i < 9; i++) {
            s = s + Arrays.toString(sudokuBoard[i]) + "\n";
        }
        return s;
    }

}
