package fall2018.csc2017.sudoku;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Random;


import fall2018.csc2017.gamecentre.GameManager;

public class SudokuBoardManager implements GameManager, Serializable {

    /**
     * the active board for users to solve
     */
    private SudokuBoard activeBoard;
    /**
     * the hidden board for giving hints
     */
    private SudokuBoard hiddenBoard;
    /**
     * Arraylist for keeping track of unchangeable generated numbers
     */
    private ArrayList<Integer> generatedNumbers = new ArrayList<>();
    private ArrayList<SudokuBoard> undoStack = new ArrayList<>();
    /**
     * current selected position for activeboard
     */
    private int positionSelected;
    private int moves;

    public SudokuBoardManager() {
        SudokuBoardRandomizer randomizer = new SudokuBoardRandomizer(new SudokuBoard());
        this.hiddenBoard = randomizer.generateRandomBoard();
        this.activeBoard = new SudokuBoard(hiddenBoard.getSudokuBoard());
        generateActiveBoard();
    }

    /**
     * Creates an active board by removing 36 random numbers from the board
     */
    private void generateActiveBoard() {
        Random random = new Random();
        int i = 0;
        while (i < 36) {
            int position = random.nextInt(81);
            int rowPosition = position / 9;
            int columnPosition = position % 9;
            if (!(activeBoard.getSudokuBoard()[rowPosition][columnPosition] == 0)) {
                activeBoard.setSudokuBoardNumber(rowPosition, columnPosition, 0);
                generatedNumbers.add(position);
                i++;
            }
        }
    }

    /**
     * Returns the score of the game
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return 0;
    }

    /**
     * Returns the game name
     *
     * @return the game name
     */
    @Override
    public String getGameName() {
        return "Sudoku";
    }

    /**
     * Returns the current time
     *
     * @return the current time
     */
    @Override
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }

    /**
     * Returns the game level
     *
     * @return the game difficulty
     */
    @Override
    public String getGameDifficulty() {
        return "Normal";
    }

    /**
     * Returns whether the puzzle has been solved
     *
     * @return if puzzle is solved
     */
    @Override
    public boolean puzzleSolved() {
        Integer[][] board = activeBoard.getSudokuBoard();
        for (int i = 0; i < 81; i++) {
            Integer value = board[i / 9][i % 9];
            Integer rowSolved = activeBoard.doubleInRow(value);
            Integer columnSolved = activeBoard.doubleInColumn(value);
            Integer boxSolved = activeBoard.doubleInBox(value);
            if (rowSolved != -1 || columnSolved != -1 || boxSolved != -1)
                return false;
        }
        return true;
    }


    /**
     * Updates the currently selected position
     *
     * @param position the position to be updated
     */
    @Override
    public void touchMove(int position) {
        positionSelected = position;
    }

    /**
     * Update the number on the active board with num
     *
     * @param num number to be updated
     */
    public void updateNumber(int num) {
        if (isValidTap(positionSelected)) {
            undoStack.add(0, activeBoard.copy());
            int row = positionSelected / 9;
            int col = positionSelected % 9;
            activeBoard.setSudokuBoardNumber(row, col, num);
            moves++;
        }
    }

    /**
     * gets the currently selected position
     *
     * @return the selected position
     */
    public int getPositionSelected() {
        return positionSelected;
    }

    /**
     * gets the currently active board
     *
     * @return the activeBoard
     */
    public SudokuBoard getActiveBoard() {
        return activeBoard;
    }

    /**
     * Checks whether position is a generated number
     *
     * @param position the position to be checked
     * @return whether it is a valid position to update
     */
    @Override
    public boolean isValidTap(int position) {
        return (generatedNumbers.contains(position));
    }

    public ArrayList<Integer> getGeneratedNumbers() {
        return generatedNumbers;
    }

    /**
     * Erases the number at a certain spot
     */
    public void erase() {
        if (isValidTap(positionSelected)) {
            undoStack.add(0, activeBoard.copy());
            activeBoard.setSudokuBoardNumber(
                    positionSelected / 9, positionSelected % 9, 0);
        }
    }

    /**
     * Gives hint by removing one of the squares needed to fill the board
     */
    public void provideHint() {
        if (generatedNumbers.size() != 0) {
            int position = generatedNumbers.remove(0);
            int row = position / 9;
            int col = position % 9;
            activeBoard.setSudokuBoardNumber(row, col, hiddenBoard.getSudokuBoard()[row][col]);
            moves = moves + 5;
        }
    }

    public int getMoves() {
        return moves;
    }

    public boolean undo() {
        if (undoStack.size() > 0) {
            activeBoard = undoStack.remove(0);
            return true;
        }
        return false;
    }
}
