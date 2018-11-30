package fall2018.csc2017.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Test for getScore with no moves
     */
    @Test
    public void testGetScoreNoMove() {
        int score = sudokuBoardManager.getScore();
        assertEquals(1072, score);
    }

    /**
     * Test for getScore with 10 provideHint moves
     */
    @Test
    public void testGetScoreTenMoves() {
        for (int i = 0; i < 10; i++) {
            sudokuBoardManager.provideHint();
        }
        assertEquals(sudokuBoardManager.getScore(), 972);
    }

    /**
     * Test for game name getter
     */
    @Test
    public void testGetGameName() {
        assertEquals("Sudoku", sudokuBoardManager.getGameName());
    }

    /**
     * Test for time getter
     */
    @Test
    public void testGetTime() {
        Pattern pattern = Pattern.compile("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}");
        String time = sudokuBoardManager.getTime();
        Matcher matcher = pattern.matcher(time);
        assertTrue(matcher.matches());

    }

    /**
     * Test for game difficulty getter
     */
    @Test
    public void testGetGameDifficulty() {
        assertEquals("Normal", sudokuBoardManager.getGameDifficulty());
    }

    /**
     * Test for puzzleSolved when it is not solved
     */
    @Test
    public void testPuzzleSolvedFalse() {
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    /**
     * Test for puzzleSolved when board is complete, but not solved
     */
    @Test
    public void testPuzzleSolvedFalseCompleteBoard() {
        for (Integer i : sudokuBoardManager.getActiveBoard().getRemovedNumbers()) {
            sudokuBoardManager.touchMove(i);
            sudokuBoardManager.updateNumber(9);
        }
        assertFalse(sudokuBoardManager.puzzleSolved());
    }

    /**
     * Test for puzzleSolved when it is solved
     */
    @Test
    public void testPuzzleSolvedTrue() {
        for (int i = 0; i < 36; i++) {
            sudokuBoardManager.provideHint();
        }
        assertTrue(sudokuBoardManager.puzzleSolved());
    }

    /**
     * Test to see if touchMove updates currently selected position
     */
    @Test
    public void testTouchMove() {
        sudokuBoardManager.touchMove(10);
        assertEquals(10, sudokuBoardManager.getPositionSelected());

    }

    /**
     * Test to see if updateNumber correctly updates the number
     */
    @Test
    public void testUpdateNumber() {
        ArrayList<Integer> list = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
        sudokuBoardManager.touchMove(list.get(0));
        sudokuBoardManager.updateNumber(9);
        int row = list.get(0) / 9;
        int col = list.get(0) % 9;
        Integer integer = 9;
        assertEquals(sudokuBoardManager.getActiveBoard().getSudokuBoard()[row][col], integer);

    }

    /**
     * Test for getter for positionSelected
     */
    @Test
    public void testGetPositionSelected() {
        sudokuBoardManager.touchMove(10);
        assertEquals(10, sudokuBoardManager.getPositionSelected());
    }

    /**
     * Test for checking whether isValidTap returns a updatable grid
     */
    @Test
    public void testIsValidTap() {
        ArrayList<Integer> list = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
        assertTrue(sudokuBoardManager.isValidTap(list.get(0)));
        for (int i = 0; i < 80; i++) {
            if (!list.contains(i)) {
                assertFalse(sudokuBoardManager.isValidTap(i));
                break;
            }
        }
    }

    /**
     * Test for checking erase actually erasing a number
     */
    @Test
    public void testErase() {
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

    /**
     * Test to see if provideHint gives an unchangeable hint
     */
    @Test
    public void testProvideHint() {
        Integer[][] before = sudokuBoardManager.getActiveBoard().getSudokuBoard().clone();
        sudokuBoardManager.provideHint();
        Integer[][] after = sudokuBoardManager.getActiveBoard().getSudokuBoard();
        if (sudokuBoardManager.getActiveBoard().getRemovedNumbers().size() != 0) {
            assertNotEquals(before, after);
        }
    }

    /**
     * Test undo when no moves are available
     */
    @Test
    public void testUndoNoMoves() {
        int numUndo = sudokuBoardManager.getUndo().size();
        Object before = sudokuBoardManager.getActiveBoard().getRemovedNumbers().clone();
        sudokuBoardManager.undo();
        ArrayList<Integer> after = sudokuBoardManager.getActiveBoard().getRemovedNumbers();
        if (numUndo != 0) {
            assertNotEquals(before, after);
        } else {
            assertEquals(before, after);
        }
    }

    /**
     * Test undo when one move is available for undo
     */
    @Test
    public void testUndoOneMove() {
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