package fall2018.csc2017.gamecentre;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * GameCentre for users to choose which game to play, load their past games, view high scores.
 */
public class GameCentre {

    /**
     * Context of current state of the application.
     */
    public Context context;

    /**
     * User manager that manages all users.
     */
    UserManager userManager;

    /**
     * Scoreboard that records scores.
     */
    ScoreBoard scoreBoard;


    /**
     * Game manager that manages all games.
     */
    GameManager gameManager;


    /**
     * Keep track of all saved games.
     */
    SavedGames savedGames;

    /**
     * Initialize a Game Centre.
     *
     * @param context context of current state of the application
     */
    public GameCentre(Context context) {
        this.context = context;
        String[] fileList = {UserManager.USERS, ScoreBoard.SCOREBOARD, SavedGames.SAVEDGAMES};
        for (String s : fileList) {
            loadManager(s);
        }
    }

    /**
     * Check if the file exists.
     *
     * @return whether file exists
     */
    private boolean checkFile(String filename) {
        File file = context.getFileStreamPath(filename);
        return file.exists();
    }

    /**
     * Creates a new files.
     *
     * @param fileName the filename to be created
     */
    private void createManager(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, AppCompatActivity.MODE_PRIVATE));
            switch (fileName) {
                case UserManager.USERS:
                    outputStream.writeObject(new UserManager());
                    break;
                case ScoreBoard.SCOREBOARD:
                    outputStream.writeObject(new ScoreBoard());
                    break;
                case SavedGames.SAVEDGAMES:
                    outputStream.writeObject(new SavedGames());
                    break;
                default:
                    outputStream.writeObject(new UserManager());

            }
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load files to their destination.
     *
     * @param fileName loads the file with name filename
     */
    public void loadManager(String fileName) {
        try {
            if (!checkFile(fileName)) {
                createManager(fileName);
            }
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                managerSelect(fileName, input);
                inputStream.close();
            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Select the right destination for file.
     *
     * @param fileName the file being accessed
     * @param input    data being loaded
     * @throws IOException when reading input file gets interrupted
     * @throws ClassNotFoundException when the input class cannot be found
     */
    private void managerSelect(String fileName, ObjectInputStream input) throws IOException, ClassNotFoundException {
        switch (fileName) {
            case ScoreBoard.SCOREBOARD:
                scoreBoard = (ScoreBoard) input.readObject();
                break;
            case UserManager.USERS:
                userManager = (UserManager) input.readObject();
                break;
            case GameManager.TEMP_SAVE_WIN:
                gameManager = (GameManager) input.readObject();
                break;
            case SavedGames.SAVEDGAMES:
                savedGames = (SavedGames) input.readObject();
                break;
        }
    }

    /**
     * Save Object save to fileName.
     *
     * @param fileName saves the object save onto a file with filename
     */
    void saveManager(String fileName, Object save) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, AppCompatActivity.MODE_PRIVATE));
            outputStream.writeObject(save);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Get userManager.
     *
     * @return userManager
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Get savedGames.
     *
     * @return savedGames
     */
    public SavedGames getSavedGames() {
        return savedGames;
    }
}
