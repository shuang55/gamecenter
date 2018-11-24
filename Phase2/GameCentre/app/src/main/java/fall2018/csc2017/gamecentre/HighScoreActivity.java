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
        userManager = gameCentre.userManager;
        scoreBoard = gameCentre.scoreBoard;
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
        TextView textScore = findViewById(R.id.score_2);
        TextView textName = findViewById(R.id.score_1);
        if (select.equals("User")) {
            textScore.setText(perUserListGenerator(false));
            textName.setText(perUserListGenerator(true));
        } else {
            textName.setText(perGameListGenerator(select, false));
            textScore.setText(perGameListGenerator(select, true));
        }
    }

    /**
     * Generates the string display for user highscore and game name
     * @param isScore whether the string output should be a list of score or list of game name
     * @return the string output
     */
    private String perUserListGenerator(boolean isScore) {
        Score[] scoreSorted = scoreBoard.getTopTenSelect(userManager.getCurrentUser().getUsername(),
                "user");
        StringBuilder game = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (!isScore) {
                game.append(String.format("%s. %s \n", i + 1, scoreSorted[i].getGame()));
            } else {
                game.append(String.format(" %s \n", scoreSorted[i].getScore()));
            }
        }
        return game.toString();
    }

    /**
     * Generates the string display for game highscore and username
     *
     * @param game    the game name
     * @param isScore whether the string output is a list of score or list of name
     * @return the string display
     */
    private String perGameListGenerator(String game, boolean isScore) {
        Score[] score = checkSelectAll(game);
        StringBuilder gameDisplay = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            if (!isScore) {
                gameDisplay.append(String.format("%s. %s \n", i + 1, score[i].getUser()));
            } else {
                gameDisplay.append(String.format(" %s \n", score[i].getScore()));
            }
        }
        return gameDisplay.toString();
    }

    /**
     * Checks if selected is a selection containing (ALL).
     *
     * @param selected the selected spinner
     * @return array of score that needs to be displayed
     */
    private Score[] checkSelectAll(String selected) {
        boolean containAll = selected.contains("(All)");

        if (containAll) {
            String gameName = selected.substring(0, selected.length() - 5).trim();
            return scoreBoard.getTopTenSelect(gameName, "game");
        }
        String gameName = selected.trim();
        return scoreBoard.getTopTenByGameByUser(gameName,
                userManager.getCurrentUser().getUsername());
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
