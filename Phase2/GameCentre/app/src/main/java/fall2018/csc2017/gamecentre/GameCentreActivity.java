package fall2018.csc2017.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import fall2018.csc2017.cardmatching.CardStartingActivity;
import fall2018.csc2017.slidingtiles.StartingActivity;
import fall2018.csc2017.R;

/**
 * Activity for GameCentre.
 */
public class GameCentreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_centre);

        addSlidingTileButtonListener();
        addHighScoreButtonListener();
        addSavedGamesButtonListener();
        addCardMatchingButtonListener();
    }

    /**
     * Activate the sliding tile image button.
     */
    private void addSlidingTileButtonListener() {
        ImageButton slidingTilePlay = findViewById(R.id.SlidingTilePlay);
        slidingTilePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSlidingTile();
            }
        });
    }

    /**
     * Activate the card matching image button.
     */
    private void addCardMatchingButtonListener() {
        ImageButton slidingTilePlay = findViewById(R.id.CardMatchingPlay);
        slidingTilePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToCardMatching();
            }
        });
    }

    /**
     * Activate the high score button.
     */
    private void addHighScoreButtonListener() {
        Button highScore = findViewById(R.id.score);
        highScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToHighScore();
            }
        });
    }

    /**
     * Activate the saved games button.
     */
    private void addSavedGamesButtonListener() {
        Button savedGame = findViewById(R.id.savedGames);
        savedGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSavedGames();
            }
        });
    }

    /**
     * Switch to sliding tile starting activity.
     */
    private void switchToSlidingTile() {
        Intent swap = new Intent(this,  StartingActivity.class);
        startActivity(swap);
    }

    /**
     * Switch to card matching starting activity.
     */
    private void switchToCardMatching() {
        Intent swap = new Intent(this, CardStartingActivity.class);
        startActivity(swap);
    }

    /**
     * Switch to the high score activity.
     */
    private void switchToHighScore() {
        Intent score = new Intent(this, HighScoreActivity.class);
        startActivity(score);
    }

    /**
     * Switch to the saved games activity.
     */
    private void switchToSavedGames() {
        Intent savedGame = new Intent(this, SavedGamesActivity.class);
        startActivity(savedGame);
    }

}
