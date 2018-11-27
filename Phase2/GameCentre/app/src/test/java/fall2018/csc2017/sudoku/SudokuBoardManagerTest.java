package fall2018.csc2017.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SudokuBoardManagerTest {

    private SudokuBoardManager sudokuBoardManager;

    @Before
    public void setUp() {
        sudokuBoardManager = new SudokuBoardManager();
    }

    @After
    public void tearDown() {
        sudokuBoardManager = null;
    }

    @Test
    public void getScoreNoMove() {
        int score = sudokuBoardManager.getScore();
        assertEquals(1072, score);
    }

    @Test
    public void getGameName() {
        assertEquals("Sudoku", sudokuBoardManager.getGameName());
    }

    @Test
    public void getGameDifficulty() {
        assertEquals("Normal", sudokuBoardManager.getGameDifficulty());
    }

    @Test
    public void puzzleSolvedFalse() {
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    @Test
    public void puzzleSolvedFalseCompleteBoard() {
        for (Integer i : sudokuBoardManager.getActiveBoard().getRemovedNumbers()) {
            sudokuBoardManager.touchMove(i);
            sudokuBoardManager.updateNumber(9);
        }
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    @Test
    public void puzzleSolvedTrue() {
        for (int i = 0; i < 36; i++) {
            sudokuBoardManager.provideHint();
        }
        assertTrue(sudokuBoardManager.puzzleSolved());
    }

    @Test
    public void touchMove() {
        sudokuBoardManager.touchMove(10);
        assertEquals(10, sudokuBoardManager.getPositionSelected());

    }

    @Test
    public void updateNumberUpdate() {
        ArrayList<Integer> list = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
        sudokuBoardManager.touchMove(list.get(0));
        sudokuBoardManager.updateNumber(9);
        int row = list.get(0) / 9;
        int col = list.get(0) % 9;
        Integer integer = 9;
        assertEquals(sudokuBoardManager.getActiveBoard().getSudokuBoard()[row][col], integer);

    }

    @Test
    public void getPositionSelected() {
        sudokuBoardManager.touchMove(10);
        assertEquals(10, sudokuBoardManager.getPositionSelected());
    }

    @Test
    public void isValidTap() {
        ArrayList<Integer> list = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
        assertTrue(sudokuBoardManager.isValidTap(list.get(0)));
        for (int i = 0; i < 80; i++) {
            if (!list.contains(i)) {
                assertFalse(sudokuBoardManager.isValidTap(i));
                break;
            }
        }
    }

    @Test
    public void erase() {
        int position = sudokuBoardManager.getActiveBoard().getRemovedNumbers().get(0);
        Integer[] number = {9, 0};
        sudokuBoardManager.touchMove(position);
        sudokuBoardManager.updateNumber(number[0]);
        assertEquals(sudokuBoardManager.getActiveBoard().
                getSudokuBoard()[position / 9][position % 9], number[0]);
        sudokuBoardManager.erase();
        assertEquals(sudokuBoardManager.getActiveBoard().
                getSudokuBoard()[position / 9][position % 9], number[1]);
    }

    @Test
    public void provideHint() {
        int position = sudokuBoardManager.getActiveBoard().getRemovedNumbers().get(0);
        sudokuBoardManager.provideHint();
        Integer zero = 0;
        assertNotEquals(sudokuBoardManager.getActiveBoard().
                getSudokuBoard()[position / 9][position % 9], zero);
    }

    @Test
    public void undo() {
        int position = sudokuBoardManager.getActiveBoard().getRemovedNumbers().get(0);
        SudokuPlayBoard oldBoard = sudokuBoardManager.getActiveBoard().copy();
        sudokuBoardManager.touchMove(position);
        sudokuBoardManager.updateNumber(9);
        assertNotEquals(oldBoard, sudokuBoardManager.getActiveBoard());
        sudokuBoardManager.undo();
        assertEquals(oldBoard, sudokuBoardManager.getActiveBoard().copy());
    }
}