package fall2018.csc2017.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SudokuBoardManagerTest {

    private SudokuBoardManager sudokuBoardManager;

    @Before
    public void setUp() throws Exception {
        sudokuBoardManager = new SudokuBoardManager();
    }

    @After
    public void tearDown() throws Exception {
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
    public void getActiveBoard() {

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
        ArrayList<Integer> list = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
    }

    @Test
    public void provideHint() {
    }

    @Test
    public void getMoves() {
    }

    @Test
    public void undo() {
    }
}