package fall2018.csc2017.gamecentre;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Test SavedGamesManager
 */
public class SavedGamesManagerTest {

    @Mock
    private Map<String, ArrayList<GameToSave>> savedGames;

    @Mock
    private GameToSave gameToSave;

    @Mock
    private GameManager gameManager;

    @Mock
    private User user;

    @Mock
    private SavedGamesManager savedGamesManager;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown(){
       savedGamesManager = null;
    }

    @Test
    public void testUpdateSavedGames(){
        when(user.getUsername()).thenReturn("User");
        //RIP RIP RIP RIP RIP RIP RIP RIP RIP RIP RIP, ugh i will work on this later
    }
}
