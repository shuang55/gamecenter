package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

// Excluded from tests because it's a view class
public abstract class GameAcitivity extends AppCompatActivity {

    protected GameCentre gameCentre;

    protected GameManager gameManager;

    /**
     * Load necessary managers
     */
    protected void loadManagers(Context context) {
        gameCentre = new GameCentre(context);
        gameCentre.loadManager(GameManager.TEMP_SAVE_START);
        gameManager = gameCentre.getGameManager();
    }

    /**
     * swaps activity to you win activity
     */
    protected void switchToWinActivity(Context context) {
        gameCentre.gameManagerWin(gameManager);
        Intent win = new Intent(context, YouWinActivity.class);
        startActivity(win);
    }

    /**
     * sets the move count on screen
     */
    protected void setMoveCountText(int moveCountTextID) {
        TextView moves = findViewById(moveCountTextID);
        moves.setText(String.format("%s", gameManager.getMove()));
    }
}
