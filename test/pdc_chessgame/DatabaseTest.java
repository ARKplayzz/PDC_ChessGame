/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pdc_chessgame;

// do not delete any dependancies
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author finla
 */
public class DatabaseTest {
    
    Database instance;
    
    public DatabaseTest() {
    }
    
    
    @Before
    public void setUp() 
    {
        instance = new Database();
        instance.addPlayer("TEST__USER_9@3467%4321", 7);
    }
    
    @After
    public void tearDown()
    {
        instance.deletePlayers("TEST__USER_9@3467%4321");
        instance.deleteGames("TEST_SAVE_191930506432");
        instance.terminate();
    }

    /**
     * Test of addPlayer method, of class Database.
     */
    @Test
    public void testAddPlayer() 
    {
        System.out.println("Adding a player");
        boolean expected = instance.playerExists("TEST__USER_9@3467%4321"); // the random gibberish is to make sure no one accidently makes a user with this name
        boolean result = instance.addPlayer("TEST__USER_9@3467%4321", 7);
        assertNotEquals(expected, result);
    }
    
    @Test
    public void testPlayerExists()
    {
        System.out.println("Player exists");
        boolean expected = true;
        boolean actual = instance.playerExists("TEST__USER_9@3467%4321");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetElo()
    {
        System.out.println("Getting elo");
        int expected = 7;
        int actual = instance.getElo("TEST__USER_9@3467%4321");
        assertEquals(expected, actual, 0.0);
    }
    
    @Test
    public void testInsertGame()
    {
        System.out.println("Inserting game");
        boolean expected = true;
        boolean actual = instance.insertGame("TEST_SAVE_191930506432", "TEST__USER_9@3467%4321", "fakename alias", "Fake path", 58, 57);
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAlterPlayer() 
    {
        System.out.println("Altering player");
        boolean updated = instance.alterPlayer("TEST__USER_9@3467%4321", 42, 3, 2);
        assertTrue(updated);
        assertEquals(42, instance.getElo("TEST__USER_9@3467%4321"));
        assertEquals(3, instance.getGamesWon("TEST__USER_9@3467%4321"));
        assertEquals(2, instance.getGamesLost("TEST__USER_9@3467%4321"));
    }

    @Test
    public void testDeleteGames() 
    {
        System.out.println("Deleting game");
        instance.insertGame("TEST_SAVE_191930506432", "TEST__USER_9@3467%4321", "fakename alias", "Fake path", 58, 57);
        boolean deleted = instance.deleteGames("TEST_SAVE_191930506432");
        assertTrue(deleted);
        assertFalse(instance.gameExists("TEST_SAVE_191930506432"));
    }
    
    @Test
    public void testDeletePlayer() 
    {
        System.out.println("Deleting player");
        boolean expected = true;
        boolean actual = instance.deletePlayers("TEST__USER_9@3467%4321");
        assertEquals(expected, actual);
    }

    @Test
    public void testGameExists() 
    {
        System.out.println("Game exists?");
        instance.insertGame("TEST_SAVE_191930506432", "TEST__USER_9@3467%4321", "fakename alias", "Fake path", 58, 57);
        assertTrue(instance.gameExists("TEST_SAVE_191930506432"));
        instance.deleteGames("TEST_SAVE_191930506432");
        assertFalse(instance.gameExists("TEST_SAVE_191930506432"));
    }

    @Test
    public void testGetSavesForUser() 
    {
        System.out.println("Getting user saves");
        instance.insertGame("TEST_SAVE_191930506432", "TEST__USER_9@3467%4321", "fakename alias", "Fake path", 58, 57);
        var saves = instance.getSavesForUser("TEST__USER_9@3467%4321");
        assertFalse(saves.isEmpty());
        assertEquals("TEST_SAVE_191930506432", saves.get(0).saveFile);
    }

    @Test
    public void testGetLeaderboard() 
    {
        System.out.println("Getting leaderboard");
        String leaderboard = instance.getLeaderboard().toString();
        assertNotNull(leaderboard);
        assertTrue(leaderboard.contains("Username"));
        assertTrue(leaderboard.length() > 0);
    }
    

}
