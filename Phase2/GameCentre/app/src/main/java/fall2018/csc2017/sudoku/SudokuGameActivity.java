package fall2018.csc2017.sudoku;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import fall2018.csc2017.R;
import fall2018.csc2017.gamecentre.GameCentre;
import fall2018.csc2017.gamecentre.GameManager;

public class SudokuGameActivity extends AppCompatActivity {

    private static final String TAG = "SudokuGameActivity";

    private SudokuBoardManager sudokuBoardManager;

    private GridView gridView;
    private SudokuBoardAdapter sudokuBoardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_game);
        GameCentre gameCentre = new GameCentre(this);
        gameCentre.loadManager(GameManager.TEMP_SAVE_START);
        sudokuBoardManager = (SudokuBoardManager) gameCentre.getGameManager();
        Log.v(TAG, String.valueOf(gameCentre.getGameManager() == null));

        gridView = findViewById(R.id.game_grid);
        sudokuBoardAdapter = new SudokuBoardAdapter(sudokuBoardManager.getActiveBoard(), this);
        gridView.setAdapter(sudokuBoardAdapter);
        addGridViewClickListener();
    }

    private void addGridViewClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sudokuBoardAdapter.setSelected(position);
                sudokuBoardAdapter.notifyDataSetChanged();
            }
        });
    }




}
