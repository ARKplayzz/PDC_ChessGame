package pdc_chessgame;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
/**
 *
 * @author Andrew
 */
public class SaveManagerTest 
{

    private SaveManager saveManager;
    private Database db;
    private HashMap<Team, Player> players;
    private Turn history;
    private Clock clock;
    private GameManager game;

    @Before
    public void setUp() 
    {
        saveManager = new SaveManager();
        db = new Database();
        players = new HashMap<>();
        players.put(Team.WHITE, new Player("TEST_USER_WHITE", Team.WHITE));
        players.put(Team.BLACK, new Player("TEST_USER_BLACK", Team.BLACK));
        db.addPlayer("TEST_USER_WHITE", 100);
        db.addPlayer("TEST_USER_BLACK", 100);
        
        game = new GameManager("TEST_USER_WHITE", "TEST_USER_BLACK", 5);
        game.makeMove(new Move(4, 1, 4, 3)); // E2E4
        game.makeMove(new Move(4, 6, 4, 4)); // E7E5
        history = game.getBoardHistory();
        clock = game.getClock();
    }

    @After
    public void tearDown() 
    {
        db.deletePlayers("TEST_USER_WHITE");
        db.deletePlayers("TEST_USER_BLACK");
        // Remove all .sav files created during tests
        File dir = new File(".");
        for (File f : dir.listFiles()) 
        {
            if (f.getName().endsWith(".sav")) 
            {
                f.delete();
            }
        }
        db.terminate();
    }

    @Test
    public void testSaveGameToFileAndLoad() 
    {
        System.out.println("TestSaveAndLoad");
        String fileName = "TEST_SAVE_123456";
        boolean saved = saveManager.SaveGameToFile(fileName, history, players);
        assertTrue(saved);

        // Check file exists and content
        File file = new File(fileName + ".sav");
        assertTrue(file.exists());
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            assertEquals(2, lines.size());
            assertEquals("E2 E4", lines.get(0));
            assertEquals("E7 E5", lines.get(1));
        } catch (IOException e) {
            fail("Could not read save file");
        }

        boolean loaded = saveManager.LoadGameFromFile(fileName, players, db);
        assertTrue(loaded);
    }

    @Test
    public void testSaveGameToUser() 
    {
        System.out.println("SaveGameToUser");
        saveManager.SaveGameToUser(players, history, clock, db);
        // Check that a .sav file exists and is registered in DB
        File dir = new File(".");
        boolean found = false;
        String foundFile = null;
        for (File f : dir.listFiles()) 
        {
            if (f.getName().endsWith(".sav")) 
            {
                found = true;
                foundFile = f.getName();
                break;
            }
        }
        assertTrue(found);
        // Check DB entry exists for the save
        if (foundFile != null) {
            String saveName = foundFile.replace(".sav", "");
            assertTrue(db.gameExists(saveName));
            // Check DB player names match
            assertEquals("TEST_USER_WHITE", db.getPlayer1(saveName));
            assertEquals("TEST_USER_BLACK", db.getPlayer2(saveName));
        }
    }

    @Test
    public void testLoadGameFromFileNonExistent() 
    {
        System.out.println("LoadNonExistant");
        boolean loaded = saveManager.LoadGameFromFile("NON_EXISTENT_SAVE_FILE", players, db);
        assertFalse(loaded);
    }

    @Test
    public void testSaveAndRemoveTrace() 
    {
        System.out.println("SaveAndRemove");
        // Save a game
        saveManager.SaveGameToUser(players, history, clock, db);
        // finding the save file
        File dir = new File(".");
        String foundFile = null;
        for (File f : dir.listFiles()) 
        {
            if (f.getName().endsWith(".sav")) 
            {
                foundFile = f.getName();
                break;
            }
        }
        assertNotNull(foundFile);
        String saveName = foundFile.replace(".sav", "");

        assertTrue(db.gameExists(saveName));
        assertTrue(new File(foundFile).exists());
        db.deleteGames(saveName);
        new File(foundFile).delete();
        
        assertFalse(db.gameExists(saveName));
        assertFalse(new File(foundFile).exists());
    }
}
