package pdc_chessgame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;

/**
 *
 * @author Andrew
 */
public class ChessGameplayTest {

    private GameManager gameManager;

    @Before
    public void setUp() 
    {
        gameManager = new GameManager("Andrew", "Finlay", 10);
    }

    @After
    public void tearDown() 
    {
        gameManager = null;
    }

    @Test
    public void testInitialSetup() 
    {
        assertNotNull(gameManager.board);
        assertEquals(8, gameManager.board.getWidth());
        assertEquals(8, gameManager.board.getHeight());
        
        assertEquals("Andrew", gameManager.getPlayers().get(Team.WHITE).getName());
        assertEquals("Finlay", gameManager.getPlayers().get(Team.BLACK).getName());
        assertNotNull(gameManager.getClock());
    }
    
    @Test
    public void testMove()
    {
        // White pawn A2 to A3
        Move move = new Move(0, 1, 0, 2);
        MoveResult result = gameManager.makeMove(move);
        assertEquals(MoveResult.SUCCESS, result);
    }
    
    @Test
    public void testUndoMove() 
    {
        // Undo move
        gameManager.undoMove();
        assertNotNull(gameManager.board.getTile(0, 1).getPiece());
        assertNull(gameManager.board.getTile(0, 3).getPiece());
    }

    @Test
    public void testResignation() 
    {
        // pass null move to resign
        MoveResult result = gameManager.makeMove(null);
        assertEquals(MoveResult.RESIGNATION, result);
    }

    @Test
    public void testTimerEnd() 
    {
        // Set clock to 0 and try to move
        gameManager.getClock().setTime(0);
        Move move = new Move(0, 1, 0, 2);
        MoveResult result = gameManager.makeMove(move);
        assertEquals(MoveResult.TIMER_END, result);
    }

    @Test
    public void testCheck() 
    {
        // E2E4
        gameManager.makeMove(new Move(4, 1, 4, 3));
        // F7F5
        gameManager.makeMove(new Move(5, 6, 5, 4));
        // D1H5
        MoveResult result = gameManager.makeMove(new Move(3, 0, 7, 4));
        assertEquals(MoveResult.CHECK, result);
    }
    
    @Test
    public void testCheckmate() 
    {
        // Setup an instant checkmate
        // F2F3 E7E5
        gameManager.makeMove(new Move(5, 1, 5, 2));
        gameManager.makeMove(new Move(4, 6, 4, 4));
        
        // G2G4 D8H4
        gameManager.makeMove(new Move(6, 1, 6, 3));
        MoveResult result = gameManager.makeMove(new Move(3, 7, 7, 3));
        assertEquals(MoveResult.CHECKMATE, result);
    }
}
