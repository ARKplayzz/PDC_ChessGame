/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pdc_chessgame;

// do not delete any dependancies
import java.sql.Connection;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
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
    
    /*@BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }*/
    
    @Before
    public void setUp() 
    {
        instance = new Database();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addPlayer method, of class Database.
     */
    @Test
    public void testAddPlayer() {

    }
    

    /**
     * Test of alterPlayer method, of class Database.
     */
    @Test
    public void testAlterPlayer() {
        
    }

    /**
     * Test of getElo method, of class Database.
     */
    @Test
    public void testGetElo() {
        
    }

    /**
     * Test of getGamesWon method, of class Database.
     */
    @Test
    public void testGetGamesWon() {
        
    }

    /**
     * Test of getGamesLost method, of class Database.
     */
    @Test
    public void testGetGamesLost() {
       
    }

    /**
     * Test of playerExists method, of class Database.
     */
    @Test
    public void testPlayerExists() {
        
    }

    /**
     * Test of gameExists method, of class Database.
     */
    @Test
    public void testGameExists() {
        
    }

    /**
     * Test of insertGame method, of class Database.
     */
    @Test
    public void testInsertGame() {
        
    }

    /**
     * Test of getSavesForUser method, of class Database.
     */
    @Test
    public void testGetSavesForUser() {
        
    }

    /**
     * Test of executeSQLUpdate method, of class Database.
     */
    @Test
    public void testExecuteSQLUpdate() {
        
    }

    /**
     * Test of terminate method, of class Database.
     */
    @Test
    public void testTerminate() {
        
    }

    /**
     * Test of getConnection method, of class Database.
     */
    @Test
    public void testGetConnection() {
       
    }

    /**
     * Test of calculateEloChange method, of class Database.
     */
    @Test
    public void testCalculateEloChange() {
        
    }

    /**
     * Test of updateEloAfterGame method, of class Database.
     */
    @Test
    public void testUpdateEloAfterGame() {
        
    }
    
}
