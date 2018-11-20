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
import fall2018.csc2017.gamecentre.UserManager;
import fall2018.csc2017.gamecentre.YouWinActivity;

public class SudokuGameActivity extends AppCompatActivity {

    private static final String TAG = "SudokuGameActivity";
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
        // set up board
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
        checkComplete();
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
     * Checks the activeBoard to see if the board is filled
     */
    private void checkComplete() {
        boolean containZero = false;
        for (Integer[] row : sudokuBoardManager.getActiveBoard().getSudokuBoard()) {
            for (Integer num : row) {
                if (num == 0) {
                    containZero = true;
                }
            }
        }
        checkSolved(containZero);
    }

    /**
     * Check the activeBoard to see if it is solved
     *
     * @param containZero whether the board contains 0 ==> not complete
     */
    private void checkSolved(boolean containZero) {
        if (!containZero) {
            if (sudokuBoardManager.puzzleSolved()) {
                gameCentre.saveManager(GameManager.TEMP_SAVE_WIN, sudokuBoardManager);
                gameCentre.getUserManager().getCurrentUser().removeSavedGame("Sudoku");
                gameCentre.saveManager(UserManager.USERS, gameCentre.getUserManager());
                swapToYouWin();
            }
        }
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
     * Update the sudokuboard
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
            }
        });
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
