package fall2018.csc2017.sudoku;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import fall2018.csc2017.gamecentre.GameManager;

public class SudokuBoardManager implements GameManager, Serializable {

    /**
     * the active and hidden boards
     */
    private SudokuBoard activeBoard;
    private SudokuBoard hiddenBoard;

    /**
     * Constructor for sudokuboardmanager
     */

    public SudokuBoardManager() {
        this.hiddenBoard = new SudokuBoard();
        this.activeBoard = new SudokuBoard(hiddenBoard.getSudokuBoard());
        generateActiveBoard();
    }

    private void generateActiveBoard() {
        Random random = new Random();
        int i = 0;
        while (i < 36) {
            int rowPosition = random.nextInt(9);
            int columnPosition = random.nextInt(9);
            if (!(activeBoard.getSudokuBoard()[rowPosition][columnPosition] == 0)) {
                activeBoard.setSudokuBoardNumber(rowPosition, columnPosition, 0);
                i++;
            }
        }
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
        return "Normal";
    }

    @Override
    public boolean puzzleSolved() {
        return solvedRow();

    }

    private boolean solvedRow() {
        boolean solved = true;
        Set<Integer> solution = new HashSet<>(Arrays.asList(hiddenBoard.getSudokuBoard()[0]));
        for (int i = 0; i < 9; i++) {
            Set<Integer> set = new HashSet<>(Arrays.asList(activeBoard.getSudokuBoard()[i]));
            if (!set.equals(solution)) {
                solved = false;
                break;
            }
        }
        return solved;
    }

    @Override
    public void touchMove(int position) {

    }

    public SudokuBoard getActiveBoard() {
        return activeBoard;
    }

    public SudokuBoard getHiddenBoard() {
        return hiddenBoard;
    }



    @Override
    public boolean isValidTap(int position) {
        return false;
    }

    public static void main(String[] args) {
        SudokuBoardManager manager = new SudokuBoardManager();
        System.out.println(manager.getActiveBoard());
        System.out.println(manager.getHiddenBoard());
        System.out.println(manager.solvedRow());
    }
}
