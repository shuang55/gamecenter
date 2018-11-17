package fall2018.csc2017.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;

public class SudokuGameActivity extends AppCompatActivity {

    private static final String TAG = "SudokuGameActivity";
    /**
     * SudokubBoardManager
     */
    private SudokuBoardManager sudokuBoardManager;
    /**
     * Gridview
     */
    private GridView gridView;
    private GridView numberSelectGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_game);
        // set up board
        GameCentre gameCentre = new GameCentre(this);
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
        addSaveButtonListener();
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
     * @param num number to be updated
     */
    private void updateSudokuBoard(int num) {
        sudokuBoardManager.updateNumber(num);
        updateDisplay();
    }

    private void addUndoButtonListener() {
        Button undo = findViewById(R.id.sudoku_undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO something
            }
        });
    }

    private void addSaveButtonListener() {
        Button save = findViewById(R.id.sudoku_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO SOMETHING
            }
        });
    }

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


}
