/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pdc_chessgame;

// do not delete any dependancies
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author finla
 */
public class DatabaseTest 
{    
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
        instance.deletePlayers("TEST__USER_9@3467%4321");
        assertNotEquals(expected, result);
    }
    
    @Test
    public void testPlayerExists()
    {
        System.out.println("Player exists");
        instance.addPlayer("TEST__USER_9@3467%4321", 7);
        boolean expected = true;
        boolean actual = instance.playerExists("TEST__USER_9@3467%4321");
        instance.deletePlayers("TEST__USER_9@3467%4321");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testAcessNonExistentPlayer()
    {
        System.out.println("Getting elo from a non-existant player");
        
        instance.deletePlayers("TEST__USER_9@3467%4321");
        double expected = 0.0;
        int actual = instance.getElo("TEST__USER_9@3467%4321");
        assertEquals(expected, actual, 0.0);
    }
    
    @Test
    public void testGetElo()
    {
        System.out.println("Getting elo");
        instance.addPlayer("TEST__USER_9@3467%4321", 7);
        int expected = 7;
        int actual = instance.getElo("TEST__USER_9@3467%4321");
        instance.deletePlayers("TEST__USER_9@3467%4321");
        assertEquals(expected, actual, 0.0);
    }
    
    @Test
    public void testInsertGame()
    {
        System.out.println("Inserting game");
        boolean expected = true;
        boolean actual = instance.insertGame("TEST_SAVE_191930506432", "TEST__USER_9@3467%4321", "fakename alias", "Fake path", 58, 57);
        instance.deleteGames("TEST_SAVE_191930506432");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testGetSavesForUser()
    {
        System.out.println("Getting saves for user");
        
        instance.insertGame("TEST_SAVE_191930506432", "TEST__USER_9@3467%4321", "fakename alias", "Fake path", 58, 57);
        List<Database.SaveInfo> saves = instance.getSavesForUser("TEST__USER_9@3467%4321");
        
        boolean expected = true;
        boolean actual = false;
        
        for(int i = 0; i < saves.size(); i++)
        {
            System.out.println("I: "+i+saves.get(i).saveFile);
            if(saves.get(i).saveFile.equals("TEST_SAVE_191930506432"))
                actual = true;
        }
        
        instance.deleteGames("TEST_SAVE_191930506432");
        assertEquals(expected, actual);
    }
    
    @Test
    public void testDeletePlayer()
    {
        System.out.println("Deleting player");
        instance.addPlayer("TEST__USER_9@3467%4321", 7);
        boolean expected = true;
        boolean actual = instance.deletePlayers("TEST__USER_9@3467%4321");
        assertEquals(expected, actual);
    }
}
