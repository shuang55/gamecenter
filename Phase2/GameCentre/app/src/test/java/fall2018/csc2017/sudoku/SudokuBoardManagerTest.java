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
        Integer[][] before = sudokuBoardManager.getActiveBoard().getSudokuBoard().clone();
        sudokuBoardManager.provideHint();
        Integer[][] after = sudokuBoardManager.getActiveBoard().getSudokuBoard();
        if(sudokuBoardManager.getActiveBoard().getRemovedNumbers().size() != 0) {
            assertNotEquals(before, after);
        }
    }

    @Test
    public void undoNoMoves() {
        int numUndo = sudokuBoardManager.getUndo().size();
        Object before = sudokuBoardManager.getActiveBoard().getRemovedNumbers().clone();
        sudokuBoardManager.undo();
        ArrayList<Integer> after = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
        if(numUndo != 0) {
            assertNotEquals(before, after);
        } else {
            assertEquals(before, after);
        }
    }

    @Test
    public void undoOneMove() {
        int position = sudokuBoardManager.getActiveBoard().getRemovedNumbers().get(0);
        SudokuPlayBoard oldBoard = sudokuBoardManager.getActiveBoard().copy();
        sudokuBoardManager.touchMove(position);
        sudokuBoardManager.updateNumber(9);
        int oldMoves = sudokuBoardManager.getMove();
        assertNotEquals(oldBoard, sudokuBoardManager.getActiveBoard());
        sudokuBoardManager.undo();
        assertEquals(oldBoard, sudokuBoardManager.getActiveBoard());
        assertEquals(oldMoves, sudokuBoardManager.getMove());
    }
}