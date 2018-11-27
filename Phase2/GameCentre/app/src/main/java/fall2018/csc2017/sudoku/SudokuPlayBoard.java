package fall2018.csc2017.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A playable board that interacts with the user, inherts from SudokuBoard
 */
class SudokuPlayBoard extends SudokuBoard implements Serializable {

    /**
     * Arraylist of an arraylist of repeats on the board
     */
    private ArrayList<ArrayList<Integer>> repeats = new ArrayList<>();
    /**
     * Arraylist of removed numbers from the board
     */
    private ArrayList<Integer> removedNumbers = new ArrayList<>();

    /**
     * Constructor for SudokuPlayBoard
     *
     * @param preSolvedBoard a solved board
     */
    SudokuPlayBoard(Integer[][] preSolvedBoard) {
        for (int i = 0; i < 9; i++) {
            sudokuBoard[i] = preSolvedBoard[i].clone();
        }
    }

    /**
     * Sets the board at [row][col] to be num
     *
     * @param position the position to be updated
     * @param number number to be updated
     */
    void setSudokuBoardNumber(int position, int number) {
        int row = position / 9;
        int column = position % 9;
        sudokuBoard[row][column] = number;
        updateRepeats();

    }

    /**
     * Updates this.repeats list by looking for repeats in row, col, square
     */
    private void updateRepeats() {
        this.repeats = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int position = (row * 9) + col;
                ArrayList<Integer> repeats = new ArrayList<>(Arrays.asList(position,
                        doubleInRow(position), doubleInColumn(position), doubleInBox(position)));
                if ((repeats.get(1) != -1) | (repeats.get(2) != -1) | (repeats.get(3) != -1)) {
                    this.repeats.add(repeats);
                }
            }
        }
    }

    /**
     * Creates a copy of the current sudokuPlayBoard
     *
     * @return a copy of the sudokuplayboard
     */
    SudokuPlayBoard copy() {
        SudokuPlayBoard temp = new SudokuPlayBoard(sudokuBoard);
        for (Integer i : removedNumbers) {
            temp.removedNumbers.add(0, i);
        }
        for (ArrayList<Integer> i : repeats) {
            temp.repeats.add(0, i);
        }
        return temp;
    }

    /**
     * Checks the activeBoard to see if the board is filled
     * @return whether it is complete
     */
    boolean checkComplete() {
        boolean containZero = false;
        for (Integer[] row : sudokuBoard) {
            for (Integer num : row) {
                if (num == 0) {
                    containZero = true;
                }
            }
        }
        return containZero;
    }

    /**
     * Getter for arraylist of repeats
     *
     * @return arraylist of repeats
     */
    ArrayList<ArrayList<Integer>> getRepeats() {
        return repeats;
    }

    /**
     * Adds a number to the arraylist of removedNumbers
     *
     * @param position the position to be added
     */
    void addRemovedNumber(Integer position) {
        removedNumbers.add(position);
    }

    /**
     * Remove and Return the first index of removedNumbers
     *
     * @return the first index of removedNumbers
     */
    Integer popRemovedNumber() {
        return removedNumbers.remove(0);
    }

    /**
     * Getter for removedNumbers
     *
     * @return removedNumbers
     */
    ArrayList<Integer> getRemovedNumbers() {
        return removedNumbers;
    }
}
