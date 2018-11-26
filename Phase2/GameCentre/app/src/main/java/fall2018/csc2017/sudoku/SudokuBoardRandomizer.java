package fall2018.csc2017.sudoku;

import java.util.Random;

class SudokuBoardRandomizer {

    private SudokuBoard sudokuBoard;
    private Integer[][] sudokuBoardNumbers;

    /**
     * Constructor for SudokuBoardRandomizer
     *
     * @param sudokuBoard the sudokuBoard to be randomized
     */
    SudokuBoardRandomizer(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
        this.sudokuBoardNumbers = sudokuBoard.getSudokuBoard();
    }

    /**
     * Generates a random SudokuBoard
     *
     * @return a randomized sudokuboard
     */
    SudokuBoard generateRandomBoard() {
        randomizeBoard();
        return sudokuBoard;
    }

    /**
     * swaps the column of the board
     *
     * @param row1 first row
     * @param row2 second row
     */
    private void swapRows(int row1, int row2) {

        Integer[] temp = sudokuBoardNumbers[row1];
        sudokuBoardNumbers[row1] = sudokuBoardNumbers[row2];
        sudokuBoardNumbers[row2] = temp;

    }

    /**
     * swaps the column of the board
     *
     * @param col1 first column
     * @param col2 second column
     */
    private void swapColumns(int col1, int col2) {
        for (int i = 0; i < 9; i++) {
            Integer temp = sudokuBoardNumbers[i][col1];
            sudokuBoardNumbers[i][col1] = sudokuBoardNumbers[i][col2];
            sudokuBoardNumbers[i][col2] = temp;
        }
    }

    /**
     * swaps the major horizontal rows
     *
     * @param swapMethod 0 -> swap first second, 1 -> swap first third, 2 -> swap second third
     */
    private void swapHorizontal(int swapMethod) {
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
     *
     * @param swapMethod the way of swapping
     */
    private void swapVertical(int swapMethod) {
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
     * Randomize the board
     */
    private void randomizeBoard() {
        Random random = new Random();
        // swap the major columns/rows twice
        for (int i = 0; i < 2; i++) {
            swapHorizontal(random.nextInt(3));
            swapVertical(random.nextInt(3));
        }
        //iterate through the 3 major row/column to swap rows and columns within them
        randomizeSingleRowColumn(random);
    }

    /**
     * Randomizes the rows/columns within each major horizontal/vertical section
     *
     * @param random a random for randomizing
     */
    private void randomizeSingleRowColumn(Random random) {
        for (int i = 0; i < 3; i++) {
            // swapping rows and columns within each major column/row
            for (int j = 0; j < 2; j++) {
                swapRows(random.nextInt(3) + (3 * j), random.nextInt(3) + (3 * j));
                swapColumns(random.nextInt(3) + (3 * j), random.nextInt(3) + (3 * j));
            }
        }
    }

    /**
     * Remove numbers from the board
     *
     * @param board the board to remove from
     */
    void generateActiveBoard(SudokuPlayBoard board) {
        Random random = new Random();
        int i = 0;
        while (i < 36) {
            int position = random.nextInt(81);
            int rowPosition = position / 9;
            int columnPosition = position % 9;
            if (!(board.getSudokuBoard()[rowPosition][columnPosition] == 0)) {
                board.setSudokuBoardNumber(rowPosition, columnPosition, 0);
                board.addRemovedNumber(position);
                i++;
            }
        }
    }
}
