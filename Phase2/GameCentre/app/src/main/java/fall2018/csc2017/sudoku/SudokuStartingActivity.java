package fall2018.csc2017.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;

public class SudokuStartingActivity extends AppCompatActivity {


    public static final String SUDOKU_START_FILE = GameManager.TEMP_SAVE_START;

    SudokuBoardManager sudokuBoardManager = new SudokuBoardManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_starting);
        GameCentre gameCentre = new GameCentre(this);
        gameCentre.saveManager(SUDOKU_START_FILE, sudokuBoardManager);

        addSudokuStartButtonListener();
    }

    private void addSudokuStartButtonListener() {
        Button sudokuStart = findViewById(R.id.sudoku_start);
        sudokuStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapToSudokuGame();
            }
        });
    }

    private void swapToSudokuGame() {
        Intent sudoku = new Intent(this, SudokuGameActivity.class);
        startActivity(sudoku);
    }

}
