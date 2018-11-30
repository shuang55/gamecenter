package fall2018.csc2017.gamecentre;

import org.junit.Test;
import fall2018.csc2017.sudoku.SudokuBoardManager;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * Create User and SudokuBoardManager to test.
     */
    private User user = new User("user1", "pass1");
    private SudokuBoardManager sudoku1 = new SudokuBoardManager();
    private SudokuBoardManager sudoku2 = new SudokuBoardManager();

    @Test
    public void getUsername() {
        assertEquals(user.getUsername(), "user1");
    }

    /**
     * Test that authenticate returns true only for the correct password input.
     */
    @Test
    public void authenticate() {
        boolean not = user.authenticate("notPass");
        boolean pass = user.authenticate("pass1");

        assertFalse(not);
        assertTrue(pass);
    }

    /**
     * Test that toString returns the username of user.
     */
    @Test
    public void toStringTest() {
        assertEquals(user.toString(), "username= user1");
    }

    /**
     * Test that addSavedGame adds the given game to savedGames and replaces the
     * previous save of the same game if it exists.
     */
    @Test
    public void addSavedGame() {
        user.addSavedGame(sudoku1);
        assertEquals(user.getSelectedGame("Sudoku"), sudoku1);
        user.addSavedGame(sudoku2);
        assertEquals(user.getSelectedGame("Sudoku"), sudoku2);

    }

    /**
     * Test that removeSavedGame removes the requested game from savedGames.
     */
    @Test
    public void removeSavedGame() {
        user.addSavedGame(sudoku1);
        user.addSavedGame(sudoku2);
        user.removeSavedGame("Sudoku");
        assertNull(user.getSelectedGame("Sudoku"));
    }
}