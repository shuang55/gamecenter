package fall2018.csc2017.sudoku;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        return "Sudoku";
    }

    @Override
    public String getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
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
