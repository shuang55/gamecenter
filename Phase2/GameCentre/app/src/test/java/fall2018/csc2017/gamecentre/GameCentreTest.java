package fall2018.csc2017.gamecentre;

import android.app.Activity;
import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileOutputStream;

import static org.mockito.Mockito.when;

public class GameCentreTest {

    @Mock
    private Context fakeContext;
    @Mock
    private FileOutputStream fakeOutputStream;
    @Mock
    private File fakeFile;

    private GameCentre gameCentre;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        instantiateGameCentre();
    }

    @After
    public void tearDown() {
        gameCentre = null;
    }

    private void instantiateGameCentre() {
        String[] fileList = {UserManager.USERS, ScoreBoard.SCOREBOARD, SavedGamesManager.SAVEDGAMES};
        for (String f : fileList) {
            when(fakeContext.getFileStreamPath(f)).thenReturn(fakeFile);
            when(fakeFile.exists()).thenReturn(true);
        }
        gameCentre = new GameCentre(fakeContext);
    }

    @Test
    public void loadManager() throws Exception {
        String[] fileList = {UserManager.USERS, ScoreBoard.SCOREBOARD, SavedGamesManager.SAVEDGAMES};
        for (String f : fileList) {
            when(fakeContext.getFileStreamPath(f)).thenReturn(fakeFile);
            when(fakeFile.exists()).thenReturn(false);
            when(fakeContext.openFileOutput(f, Activity.MODE_PRIVATE)).thenReturn(fakeOutputStream);
            gameCentre.loadManager(f);
        }
    }

    @Test
    public void saveManager() throws Exception {
        UserManager userManager = new UserManager();
        when(fakeContext.openFileOutput(UserManager.USERS, Activity.MODE_PRIVATE)).thenReturn(fakeOutputStream);
        gameCentre.saveManager(UserManager.USERS, userManager);
        Mockito.verify(fakeOutputStream).close();
    }

}