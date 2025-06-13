/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pdc_chessgame.Move;
import pdc_chessgame.MoveResult;
import pdc_chessgame.Pawn;
import pdc_chessgame.Team;
import pdc_chessgame.King;
import pdc_chessgame.Rook;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 *
 * @author Andrew
 */
public class ChessSpecialsTest {
    
    private GameManager gameManager;
    private ChessBoard board;

    @Before
    public void setUp() 
    {
        gameManager = new GameManager("Andrew", "Finlay", 10);
        board = gameManager.board;
    }

    @After
    public void tearDown() 
    {
        gameManager = null;
        board = null;
    }
    
    @Test
    public void testPawnPromotion() 
    {
        // add a white pawn at A7, move to A8
        board.setTile(new Pawn(0, 6, Team.WHITE), 0, 6);
        Move move = new Move(0, 6, 0, 7);
        MoveResult result = gameManager.makeMove(move);
        
        // should request promotion
        assertEquals(MoveResult.PROMOTION, result);
    }
    
    @Test
    public void testCastling()
    {
        // remove pieces between king and rook for white kingside castling
        board.setTile(null, 5, 0); // f1
        board.setTile(null, 6, 0); // g1

        board.setTile(new King(4, 0, Team.WHITE), 4, 0); // e1
        board.setTile(new Rook(7, 0, Team.WHITE), 7, 0); // h1

        // kingside castle E1G1
        Move castleMove = new Move(4, 0, 6, 0);
        MoveResult result = gameManager.makeMove(castleMove);

        assertEquals(MoveResult.SUCCESS, result);
        // the king should now be at G1 and rook at F1
        assertTrue(board.getTile(6, 0).getPiece() instanceof King);
        assertTrue(board.getTile(5, 0).getPiece() instanceof Rook);
        assertNull(board.getTile(4, 0).getPiece());
        assertNull(board.getTile(7, 0).getPiece());
    }

    @Test
    public void testEnPassant()
    {
        board.setTile(new Pawn(4, 4, Team.WHITE), 4, 4); // E5
        board.setTile(new Pawn(3, 6, Team.BLACK), 3, 6); // D7

        Move blackDoubleStep = new Move(3, 6, 3, 4);
        gameManager.makeMove(blackDoubleStep);

        Move enPassantMove = new Move(4, 4, 3, 5);
        MoveResult result = gameManager.makeMove(enPassantMove);

        assertEquals(MoveResult.SUCCESS, result);
        // The black pawn at D5 should be captured
        assertNull(board.getTile(3, 4).getPiece());
        // The white pawn should now be at D6
        assertTrue(board.getTile(3, 5).getPiece() instanceof Pawn);
        assertEquals(Team.WHITE, board.getTile(3, 5).getPiece().getPieceTeam());
    }
}
