package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import fall2018.csc2017.R;

/**
 * Activity for HighScore.
 */
public class HighScoreActivity extends AppCompatActivity {

    /**
     * Manager that manages all users.
     */
    private UserManager userManager;

    /**
     * Stores all scores.
     */
    private ScoreBoard scoreBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Context highScore = HighScoreActivity.this;
        GameCentre gameCentre = new GameCentre(highScore);
        userManager = gameCentre.getUserManager();
        scoreBoard = gameCentre.getScoreBoard();
        addBackButtonListener();
        setDisplay("User");
        addSortScoreSpinnerListener();
    }

    /**
     * Activate the sort score spinner.
     */
    private void addSortScoreSpinnerListener() {
        final Spinner spin = findViewById(R.id.sortScore);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = spin.getItemAtPosition(position).toString();
                setDisplay(selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setDisplay("User");

            }
        });
    }

    /**
     * Set the displayed scoreboard to the selected one.
     *
     * @param select the score board user wants to view
     */
    private void setDisplay(String select) {
        String user = userManager.getCurrentUser().getUsername();
        TextView textName = findViewById(R.id.score_1);
        TextView textScore = findViewById(R.id.score_2);
        textName.setText(scoreBoard.createDisplayString(select, user, false));
        textScore.setText(scoreBoard.createDisplayString(select, user, true));
    }

    /**
     * Activates back button.
     */
    private void addBackButtonListener() {
        Button back = findViewById(R.id.backScore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameCentre();
            }
        });
    }

    /**
     * Switch to the GameCentreActivity view.
     */
    private void switchToGameCentre() {
        Intent gameCentre = new Intent(this, GameCentreActivity.class);
        startActivity(gameCentre);
    }
}
