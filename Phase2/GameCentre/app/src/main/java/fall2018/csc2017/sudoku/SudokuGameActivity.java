package fall2018.csc2017.sudoku;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;

public class SudokuGameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SudokuGameActivity";
    /**
     * SudokubBoardManager
     */
    private SudokuBoardManager sudokuBoardManager;
    /**
     * Gridview
     */
    private GridView gridView;

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

        // set up button
        addSudokuButtonListener();

    }

    /**
     * Updates the gridview display
     */
    private void updateDisplay() {
        gridView = findViewById(R.id.game_grid);
        SudokuBoardAdapter sudokuBoardAdapter = new SudokuBoardAdapter(sudokuBoardManager, this);
        gridView.setAdapter(sudokuBoardAdapter);
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
     * Update the sudokuboard
     * @param num number to be updated
     */
    private void updateSudokuBoard(int num) {
        sudokuBoardManager.updateNumber(num);
        updateDisplay();
    }

    /**
     * Add the sudoku number select buttons
     */
    private void addSudokuButtonListener() {
        Button sudoku1 = findViewById(R.id.sudoku_1);
        sudoku1.setOnClickListener(this);
        Button sudoku2 = findViewById(R.id.sudoku_2);
        sudoku2.setOnClickListener(this);
        Button sudoku3 = findViewById(R.id.sudoku_3);
        sudoku3.setOnClickListener(this);
        Button sudoku4 = findViewById(R.id.sudoku_4);
        sudoku4.setOnClickListener(this);
        Button sudoku5 = findViewById(R.id.sudoku_5);
        sudoku5.setOnClickListener(this);
        Button sudoku6 = findViewById(R.id.sudoku_6);
        sudoku6.setOnClickListener(this);
        Button sudoku7 = findViewById(R.id.sudoku_7);
        sudoku7.setOnClickListener(this);
        Button sudoku8 = findViewById(R.id.sudoku_8);
        sudoku8.setOnClickListener(this);
        Button sudoku9 = findViewById(R.id.sudoku_9);
        sudoku9.setOnClickListener(this);
    }

    /**
     * Select what happens depending on which button was pressed
     * @param view the current view
     */
    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.sudoku_1:
                updateSudokuBoard(1);
                break;
            case R.id.sudoku_2:
                updateSudokuBoard(2);
                break;
            case R.id.sudoku_3:
                updateSudokuBoard(3);
                break;
            case R.id.sudoku_4:
                updateSudokuBoard(4);
                break;
            case R.id.sudoku_5:
                updateSudokuBoard(5);
                break;
            case R.id.sudoku_6:
                updateSudokuBoard(6);
                break;
            case R.id.sudoku_7:
                updateSudokuBoard(7);
                break;
            case R.id.sudoku_8:
                updateSudokuBoard(8);
                break;
            case R.id.sudoku_9:
                updateSudokuBoard(9);
                break;
            default:
                break;
        }
    }
}
