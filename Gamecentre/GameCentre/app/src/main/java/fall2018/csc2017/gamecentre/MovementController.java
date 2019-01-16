package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.widget.Toast;

class MovementController {

    private GameManager gameManager = null;

    MovementController() {
    }

    void setBoardManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    void processTapMovement(Context context, int position) {
        if (gameManager.isValidTap(position)) {
            gameManager.touchMove(position);
            if (gameManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
