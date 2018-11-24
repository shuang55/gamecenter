package fall2018.csc2017.sudoku;


import android.util.Log;

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
     * @param row    row to be updated
     * @param column column to be updated
     * @param number number to be updated
     */
    void setSudokuBoardNumber(int row, int column, int number) {
        sudokuBoard[row][column] = number;
        if (number != 0) {
            updateRepeatsArrayList(row, column);
        }
    }

    /**
     * Updates the repeats list if it the position contains any repeats
     *
     * @param row    the row of the position to be updated
     * @param column the column of the position to be updated
     */
    private void updateRepeatsArrayList(int row, int column) {
        int position = (row * 9) + column;
        ArrayList<Integer> repeats = new ArrayList<>(Arrays.asList(position,
                doubleInRow(position), doubleInColumn(position), doubleInBox(position)));
        int repeatPosition = checkRepeat(position);
        addOrRemoveFromRepeats(repeatPosition, repeats);
    }

    /**
     * Add or removes arraylists from this.repeats depending on whether it prev exists
     *
     * @param repeatPosition the position to be checked
     * @param repeats        the arraylist that contains the repeated positions for one set of repeat
     */
    private void addOrRemoveFromRepeats(int repeatPosition, ArrayList<Integer> repeats) {
        if ((repeats.get(1) != -1) | (repeats.get(2) != -1) | (repeats.get(3) != -1)) {
            // check if this.repeats already contains position
            if (repeatPosition != -1) {
                this.repeats.remove(repeatPosition);
            }
            this.repeats.add(repeats);
        } else {
            // remove repeat if it is in this.repeats
            if (repeatPosition != -1) {
                this.repeats.remove(repeatPosition);
            }
        }
    }

    /**
     * Check if position is already in the repeat list
     *
     * @param position the position to be checked
     * @return index of the arraylist in this.repeats if the arraylist
     * contains position, -1 if it doesn't
     */
    private int checkRepeat(int position) {
        for (int i = 0; i < this.repeats.size(); i++) {
            if (repeats.get(i).contains(position)) {
                return i;
            }
        }
        return -1;
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
