package fall2018.csc2017.sudoku;

import java.io.Serializable;
import java.util.Arrays;

public class SudokuBoard implements Serializable {

    /**
     * array of ints in sudoku board
     */
    private Integer[][] sudokuBoard = new Integer[9][9];

    /**
     * Constructor for sudokuboard
     */
    SudokuBoard() {
        setUpSolvedBoard();
    }

    /**
     * Getter for the sudokuBoard
     *
     * @return the sudokuBoard
     */
    Integer[][] getSudokuBoard() {
        return sudokuBoard;
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
     * Checks whether the int at index is the same value as another int in the array.
     *
     * @param items the array to check for a duplicate
     * @param value the value to check for a duplicate of in items
     * @return the index of the duplicate if there is one else -1
     */
    private int checkForDouble(Integer[] items, Integer value) {
        for (int i = 0; i < 9; i++) {
            if (items[i] != 0 && items[i].equals(value))
                return i;
        }
        return -1;
    }

    /**
     * Return the index of the duplicate in the row if there is one else -1.
     *
     * @param index the index of the value of the board to find the duplicate of.
     * @return the index of the duplicate if there is one else -1.
     */
    int doubleInRow(int index) {
        Integer[] rowToCheck = this.getSudokuBoard()[index / 9].clone();
        Integer compare = rowToCheck[index % 9];
        rowToCheck[index % 9] = 0;
        int indexDouble = checkForDouble(rowToCheck, compare);
        if (indexDouble != -1)
            return indexDouble + (index / 9) * 9;
        return indexDouble;
    }

    /**
     * Return the index of the duplicate in the column if there is one else -1.
     *
     * @param index the index of the value of the board to find the duplicate of.
     * @return the index of the duplicate if there is one else -1.
     */
    int doubleInColumn(int index) {
        Integer[] columnToCheck = new Integer[9];
        for (int i = 0; i < 9; i++) {
            columnToCheck[i] = this.getSudokuBoard()[i][index % 9];
        }
        Integer compare = columnToCheck[index / 9];
        columnToCheck[index / 9] = 0;
        int indexDouble = checkForDouble(columnToCheck, compare);
        if (indexDouble != -1)
            return indexDouble * 9 + index % 9;
        return indexDouble;
    }

    /**
     * Return the index of the duplicate in the box if there is one else -1.
     *
     * @param index the index of the value of the board to find a duplicate of.
     * @return the index of the duplicate if there is one else -1.
     */
    int doubleInBox(int index) {
        Integer[] boxToCheck = new Integer[9];
        Integer row = (index / 9 / 3) * 3;
        Integer column = (index % 9 / 3) * 3;
        for (int i = 0; i < 3; i++) {
            boxToCheck[i] = this.getSudokuBoard()[row][column + i];
            boxToCheck[i + 3] = this.getSudokuBoard()[row + 1][column + i];
            boxToCheck[i + 6] = this.getSudokuBoard()[row + 2][column + i];
        }
        Integer compare = boxToCheck[(index - row * 9) / 9 * 3 + index % 3];
        boxToCheck[(index - row * 9) / 9 * 3 + index % 3] = 0;
        int indexDouble = checkForDouble(boxToCheck, compare);
        if (indexDouble != -1)
            return (indexDouble / 3 + row) * 9 + indexDouble % 3 + column;
        return indexDouble;
    }

    /**
     * String representation of board for debugging purposes
     *
     * @return the string representation of the board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            s.append(Arrays.toString(sudokuBoard[i]) + "\n");
        }
        return s.toString();
    }

    /**
     * Returns the hashcode of the sudokuBoard
     * @return hashcode of the board
     */
    @Override
    public int hashCode() {
        return sudokuBoard[0][0];
    }

    /**
     * Checks the equality between this and obj
     * @param obj the obj to be checked
     * @return whether this == obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SudokuBoard)) {
            return false;
        }
        SudokuBoard other = (SudokuBoard) obj;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!((int) sudokuBoard[row][col] == ((other.sudokuBoard[row][col])))) {
                    return false;
                }
            }
        }
        return true;
    }
}
