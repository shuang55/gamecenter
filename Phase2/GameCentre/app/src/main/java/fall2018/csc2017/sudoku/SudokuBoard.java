package fall2018.csc2017.sudoku;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Observable;

public class SudokuBoard extends Observable implements Serializable, Iterable<Integer> {


    /**
     * array of ints in sudoku board
     */
    private Integer[][] numbers;

    public SudokuBoard() {
        setUpSolvedBoard();
    }

    /**
     * Set up a solved board
     */
    private void setUpSolvedBoard() {
        numbers = new Integer[][]{
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
     * swaps the column of the board
     *
     * @param row1 first row
     * @param row2 second row
     */
    public void swapRows(int row1, int row2) {

        Integer[] temp = numbers[row1];
        numbers[row1] = numbers[row2];
        numbers[row2] = temp;

    }

    /**
     * swaps the column of the board
     *
     * @param col1 first column
     * @param col2 second column
     */
    public void swapColumns(int col1, int col2) {
        for (int i = 0; i < 9; i++) {
            Integer temp = numbers[i][col1];
            numbers[i][col1] = numbers[i][col2];
            numbers[i][col2] = temp;
        }
    }

    /**
     * swaps the major horizontal rows
     *
     * @param swapMethod 0 -> swap first second, 1 -> swap first third, 2 -> swap second third
     */
    public void swapHorizontal(int swapMethod) {
        switch (swapMethod) {
            case 0:
                swapRows(0, 3);
                swapRows(1, 4);
                swapRows(2, 5);
                break;
            case 1:
                swapRows(0, 6);
                swapRows(1, 7);
                swapRows(2, 8);
            default:
                swapRows(3, 6);
                swapRows(4, 7);
                swapRows(5, 8);
        }
    }

    /**
     * swaps the major vertical columns
     * @param swapMethod
     */
    public void swapVertical(int swapMethod) {
        switch (swapMethod) {
            case 0:
                swapColumns(0, 3);
                swapColumns(1, 4);
                swapColumns(2, 5);
                break;
            case 1:
                swapColumns(0, 6);
                swapColumns(1, 7);
                swapColumns(2, 8);
            default:
                swapColumns(3, 6);
                swapColumns(4, 7);
                swapColumns(5, 8);
        }
    }

    /**
     * iterator method for iterable
     *
     * @return a SudokuBoardIterator
     */
    @NonNull
    @Override
    public Iterator<Integer> iterator() {
        return new SudokuBoardIterator();
    }

    /**
     * Iterator class that iterates through the board
     */
    private class SudokuBoardIterator implements Iterator<Integer> {

        private int row = 0;
        private int column = 0;

        @Override
        public boolean hasNext() {
            return (row != 8 && column != 8);
        }

        @Override
        public Integer next() {

            if (!hasNext()) {
                throw new NoSuchElementException("out of elements");
            } else if (column == 8) {
                Integer i = numbers[row][column];
                row++;
                column = 0;
                return i;
            } else {
                Integer i = numbers[row][column];
                row++;
                column++;
                return i;
            }


        }
    }
}
