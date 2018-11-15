package fall2018.csc2017.sudoku;

import java.io.Serializable;

import fall2018.csc2017.gamecentre.GameManager;

public class SudokuBoardManager implements GameManager, Serializable {

    /**
     * Game difficulty, 0 being easiest and 2 being hardest
     */
    private int difficulty = 0;

    public SudokuBoardManager() {

    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public String getGameName() {
        return null;
    }

    @Override
    public String getTime() {
        return null;
    }

    @Override
    public String getGameDifficulty() {
        return null;
    }

    @Override
    public boolean isValidTap(int position) {
        return false;
    }

    @Override
    public boolean puzzleSolved() {
        return false;
    }

    @Override
    public void touchMove(int position) {

    }
}
