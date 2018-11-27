package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fall2018.csc2017.R;

/**
 * Activity for when a user completes the game.
 */
public class YouWinActivity extends AppCompatActivity {

    /**
     * The game state.
     */
    private GameManager gameManager;

    /**
     * The scoreboard.
     */
    private ScoreBoard scoreBoard;

    /**
     * The user manager that manages all users.
     */
    private UserManager userManager;

    /**
     * The game centre.
     */
    private GameCentre gameCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_win);

        loadManager();
        addWinNextButtonListener();
        setScoreDisplayText();
    }

    /**
     * Loads userManager, scoreBoard and gameManager.
     */
    private void loadManager(){
        Context youWin = YouWinActivity.this;
        gameCentre = new GameCentre(youWin);
        userManager = gameCentre.getUserManager();
        scoreBoard = gameCentre.getScoreBoard();
        gameCentre.loadManager(GameManager.TEMP_SAVE_WIN);
        gameManager = gameCentre.getGameManager();
    }

    /**
     * Activate next button
     */
    private void addWinNextButtonListener() {
        Button next = findViewById(R.id.WinNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateScore();
                switchToHighscore();
            }
        });
    }

    /**
     * Updates the personal score and scoreboard score.
     */
    private void updateScore() {
        Score newScore = new Score(
                gameManager.getGameName(),
                userManager.getCurrentUser().getUsername(),
                gameManager.getScore());
        scoreBoard.updateByGame(newScore);
        scoreBoard.updateByUser(newScore);
        gameCentre.saveManager(ScoreBoard.SCOREBOARD, scoreBoard);

    }

    /**
     * Set up score display.
     */
    private void setScoreDisplayText() {
        TextView score = findViewById(R.id.ScoreDisplay);
        score.setText(String.format("Score: %s", gameManager.getScore()));
    }

    /**
     * Switch activity to highscore activity.
     */
    private void switchToHighscore() {
        Intent highscore = new Intent(this, HighScoreActivity.class);
        startActivity(highscore);
    }

}