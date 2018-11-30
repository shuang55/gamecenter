package fall2018.csc2017.gamecentre;

import org.junit.Test;

import fall2018.csc2017.sudoku.SudokuBoardManager;

import static org.junit.Assert.*;

public class UserManagerTest {

    /**
     * Create User, UserManager, SudokuBoardManager for testing.
     */
    private User user = new User("user1", "pass1");
    private UserManager manager = new UserManager();

    private SudokuBoardManager sudokuManager = new SudokuBoardManager();

    @Test
    public void signUp() {
        Boolean sign1 = manager.signUp(user.getUsername(), "pass1");
        Boolean sign2 = manager.signUp(user.getUsername(), "notPass");

        assertTrue(sign1);
        assertFalse(sign2);
    }

    @Test
    public void authenticate() {
        manager.signUp(user.getUsername(), "pass1");
        Boolean auth1 = manager.authenticate("user1", "notPass");
        Boolean auth2 = manager.authenticate("user1", "pass1");

        assertFalse(auth1);
        assertTrue(auth2);

    }

    @Test
    public void getCurrentUser() {
        manager.signUp("user1", "pass1");
        manager.authenticate("user1", "pass1");
        assertEquals(manager.getCurrentUser().getUsername(), user.getUsername());
    }

    @Test
    public void autoSaveGame() {
        manager.signUp("user1", "pass1");
        manager.authenticate("user1", "pass1");
        manager.autoSaveGame(sudokuManager);
        assertEquals(manager.getCurrentUser().getSelectedGame("Sudoku").getGameName(), "Sudoku");
    }

    @Test
    public void getSelectedGame() {
        manager.signUp("user1", "pass1");
        manager.authenticate("user1", "pass1");
        manager.autoSaveGame(sudokuManager);
        GameManager manager1 = manager.getSelectedGame("Sudoku");
        assertEquals(manager1.getGameName(), "Sudoku");
    }
}