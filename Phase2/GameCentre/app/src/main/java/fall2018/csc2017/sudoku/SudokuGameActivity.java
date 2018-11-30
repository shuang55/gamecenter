package fall2018.csc2017.sudoku;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameActivity;

// Excluded from tests because it's a view class
public class SudokuGameActivity extends GameActivity implements Observer {

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
     * onCreate method of sudokuGameActivity
     * @param savedInstanceState previous state of activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_game);
        loadManagers(this);
        sudokuBoardManager = (SudokuBoardManager) gameManager;
        sudokuBoardManager.addObserver(this);

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
        setMoveCountText(R.id.SudokuMoveCount);
        gameCentre.autoSave(sudokuBoardManager);
    }

    /**
     * Updates the Activity on whether the sudokuBoard is solved
     * @param o the observable calling notify, in this case sudokuBoardManager
     * @param arg optional arugment, not used
     */
    @Override
    public void update(Observable o, Object arg) {
        switchToWinActivity(this);
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
     * Activates number select clicks in gridview
     */
    private void addNumberSelectGridViewClickListener() {
        numberSelectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sudokuBoardManager.updateNumber(position + 1);
                updateDisplay();
            }
        });
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
     * Activate save button
     */
    private void addSaveButtonListener() {
        Button save = findViewById(R.id.sudoku_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCentre.saveGame(sudokuBoardManager);
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
     * Creates toast with msg "no more move"
     */
    private void makeToastNoMoreMoves() {
        Toast.makeText(this, "No more moves", Toast.LENGTH_SHORT).show();
    }

}
