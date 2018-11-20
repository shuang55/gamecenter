package fall2018.csc2017.sudoku;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;
import fall2018.csc2017.gamecentre.UserManager;

public class SudokuStartingActivity extends AppCompatActivity {


    public static final String SUDOKU_START_FILE = GameManager.TEMP_SAVE_START;
    private GameCentre gameCentre;
    SudokuBoardManager sudokuBoardManager = new SudokuBoardManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_starting);
        gameCentre = new GameCentre(this);
        gameCentre.saveManager(SUDOKU_START_FILE, sudokuBoardManager);

        addSudokuLoadAutoSaveButtonListener();
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

    private void addSudokuLoadAutoSaveButtonListener() {
        Button sudokuAutoSave = findViewById(R.id.sudoku_load_auto);
        sudokuAutoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameCentre.loadManager(UserManager.USERS);
                SudokuBoardManager tempBoard = (SudokuBoardManager) gameCentre.getUserManager().
                        getSelectedGame("Sudoku");
                if (tempBoard != null) {
                    sudokuBoardManager = tempBoard;
                    gameCentre.saveManager(GameManager.TEMP_SAVE_START, sudokuBoardManager);
                    swapToSudokuGame();
                } else {
                    makeToastNoAutoSavedGame();
                }
            }
        });
    }

    private void swapToSudokuGame() {
        Intent sudoku = new Intent(this, SudokuGameActivity.class);
        startActivity(sudoku);
    }

    private void makeToastNoAutoSavedGame() {
        Toast.makeText(this, "No autosaved game", Toast.LENGTH_SHORT).show();
    }

}
