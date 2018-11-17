package fall2018.csc2017.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

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

}
