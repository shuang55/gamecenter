package fall2018.csc2017.sudoku;


import android.support.design.widget.TabLayout;
import android.util.Log;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import fall2018.csc2017.gamecentre.GameManager;

public class SudokuBoardManager implements GameManager, Serializable {

    /**
     * the active board for users to solve
     */
    private SudokuPlayBoard activeBoard;
    /**
     * the hidden board for giving hints
     */
    private SudokuBoard hiddenBoard;
    /**
     * A stack of boards for undo
     */
    private ArrayList<SudokuPlayBoard> undoStack = new ArrayList<>();
    /**
     * current selected position for activeboard
     */
    private int positionSelected;
    /**
     * Number of moves made
     */
    private int moves;

    /**
     * Constructor for SudokuBoardManager
     */
    SudokuBoardManager() {
        SudokuBoardRandomizer randomizer = new SudokuBoardRandomizer(new SudokuBoard());
        this.hiddenBoard = randomizer.generateRandomBoard();
        this.activeBoard = new SudokuPlayBoard(hiddenBoard.getSudokuBoard());
        randomizer.generateActiveBoard(activeBoard);
    }

    /**
     * Returns the score of the game
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return 1000 - (2 * (moves - 36));
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
        if (activeBoard.checkComplete()) {
            return false;
        }
        for (int i = 0; i < 81; i++) {
            Integer rowSolved = activeBoard.doubleInRow(i);
            Integer columnSolved = activeBoard.doubleInColumn(i);
            Integer boxSolved = activeBoard.doubleInBox(i);
            if (rowSolved != -1 | columnSolved != -1 | boxSolved != -1)
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
    void updateNumber(int num) {
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
    int getPositionSelected() {
        return positionSelected;
    }

    /**
     * gets the currently active board
     *
     * @return the activeBoard
     */
    SudokuPlayBoard getActiveBoard() {
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
        return activeBoard.getRemovedNumbers().contains(position);
    }

    /**
     * Erases the number at the current selected position
     */
    void erase() {
        if (isValidTap(positionSelected)) {
            undoStack.add(0, activeBoard.copy());
            activeBoard.setSudokuBoardNumber(
                    positionSelected / 9, positionSelected % 9, 0);
        }
    }

    /**
     * Gives hint by removing one of the squares needed to fill the board
     */
    void provideHint() {
        if (activeBoard.getRemovedNumbers().size() != 0) {
            int position = activeBoard.popRemovedNumber();
            int row = position / 9;
            int col = position % 9;
            activeBoard.setSudokuBoardNumber(row, col, hiddenBoard.getSudokuBoard()[row][col]);
            moves = moves + 5;

        }
    }

    /**
     * Getter for number of moves
     *
     * @return moves
     */
    int getMoves() {
        return moves;
    }

    /**
     * Pops the first element in undoStack and set activeBoard to it
     *
     * @return whether the stack was non empty
     */
    public boolean undo() {
        if (undoStack.size() > 0) {
            activeBoard = undoStack.remove(0);
            return true;
        }
        return false;
    }
}
