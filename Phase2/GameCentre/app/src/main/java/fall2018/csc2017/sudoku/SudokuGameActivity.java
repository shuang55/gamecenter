package fall2018.csc2017.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;
import fall2018.csc2017.gamecentre.GameToSave;
import fall2018.csc2017.gamecentre.SavedGames;
import fall2018.csc2017.gamecentre.UserManager;
import fall2018.csc2017.gamecentre.YouWinActivity;

public class SudokuGameActivity extends AppCompatActivity {

    /**
     * SudokubBoardManager
     */
    private SudokuBoardManager sudokuBoardManager;

    /**
     * Gridview for sudokuboard
     */
    private GridView gridView;
    /**
     * Gridview for number buttons
     */
    private GridView numberSelectGridView;
    /**
     * Gamecentre for managing files
     */
    private GameCentre gameCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_game);

        // load the necessary managers
        gameCentre = new GameCentre(this);
        gameCentre.loadManager(GameManager.TEMP_SAVE_START);
        sudokuBoardManager = (SudokuBoardManager) gameCentre.getGameManager();

        // set up gridview
        updateDisplay();
        addGridViewClickListener();

        // set up number select
        numberSelectGridView = findViewById(R.id.SudokuSelect);
        SudokuNumberSelectAdapter sudokuNumberSelectAdapter = new SudokuNumberSelectAdapter(this);
        numberSelectGridView.setAdapter(sudokuNumberSelectAdapter);

        // set up button
        addNumberSelectGridViewClickListener();
        addEraseButtonListener();
        addHintButtonListener();
        addUndoButtonListener();
        addSaveButtonListener();
    }

    /**
     * Updates the gridview display
     */
    private void updateDisplay() {
        gridView = findViewById(R.id.game_grid);
        SudokuBoardAdapter sudokuBoardAdapter = new SudokuBoardAdapter(sudokuBoardManager, this);
        gridView.setAdapter(sudokuBoardAdapter);
        TextView textView = findViewById(R.id.sudoku_moves);
        textView.setText(String.format("Moves: %s", sudokuBoardManager.getMoves()));
        checkSolved();
    }

    /**
     * Checks if board is solved, swap to YouWin and delete the autosave game if it is
     */
    private void checkSolved() {
        if (sudokuBoardManager.puzzleSolved()) {
            gameCentre.saveManager(GameManager.TEMP_SAVE_WIN, sudokuBoardManager);
            gameCentre.getUserManager().getCurrentUser().removeSavedGame("Sudoku");
            gameCentre.saveManager(UserManager.USERS, gameCentre.getUserManager());
            swapToYouWin();
        }
    }

    /**
     * registers the gridview clicks
     */
    private void addGridViewClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sudokuBoardManager.touchMove(position);
                updateDisplay();
            }
        });
    }

    /**
     * Transitions to YouWinActivity
     */
    private void swapToYouWin() {
        Intent youWin = new Intent(this, YouWinActivity.class);
        startActivity(youWin);
    }

    /**
     * Activates number select clicks in gridview
     */
    private void addNumberSelectGridViewClickListener() {
        numberSelectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateSudokuBoard(position + 1);
            }
        });
    }

    /**
     * Update the sudokuboard with the user input number
     *
     * @param num number to be updated
     */
    private void updateSudokuBoard(int num) {
        sudokuBoardManager.updateNumber(num);
        autoSave();
        updateDisplay();
    }

    /**
     * Activate undo button
     */
    private void addUndoButtonListener() {
        Button undo = findViewById(R.id.sudoku_undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean reverted = sudokuBoardManager.undo();
                updateDisplay();
                if (!reverted) {
                    makeToastNoMoreMoves();
                }
            }
        });
    }

    /**
     * Activate Erase button
     */
    private void addEraseButtonListener() {
        Button erase = findViewById(R.id.sudoku_erase);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudokuBoardManager.erase();
                updateDisplay();
            }
        });
    }

    /**
     * Activate hint button
     */
    private void addHintButtonListener() {
        Button hint = findViewById(R.id.sudoku_hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudokuBoardManager.provideHint();
                updateDisplay();
                autoSave();
            }
        });
    }

    /**
     * Activate save button
     */
    private void addSaveButtonListener() {
        Button save = findViewById(R.id.sudoku_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedGames savedGames = gameCentre.getSavedGames();
                UserManager userManager = gameCentre.getUserManager();
                GameToSave gameToSave = new GameToSave(
                        sudokuBoardManager.getTime(), "Sudoku",
                        sudokuBoardManager.getGameDifficulty(), sudokuBoardManager);
                savedGames.updateSavedGames(gameToSave, userManager.getCurrentUser().getUsername());
                gameCentre.saveManager(SavedGames.SAVEDGAMES, savedGames);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Autosaves the board
     */
    private void autoSave() {
        UserManager userManager = gameCentre.getUserManager();
        userManager.autoSaveGame(sudokuBoardManager);
        gameCentre.saveManager(UserManager.USERS, userManager);
    }

    /**
     * Creates toast with msg "no more move"
     */
    private void makeToastNoMoreMoves() {
        Toast.makeText(this, "No more moves", Toast.LENGTH_SHORT).show();
    }

}
