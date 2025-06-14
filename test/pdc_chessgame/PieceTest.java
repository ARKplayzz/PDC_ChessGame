package pdc_chessgame;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 *
 * @author Andrew
 */

public class PieceTest {

    private ChessBoard board;

    @Before
    public void setUp() 
    {
        board = new ChessBoard(8, 8);
        clearBoard();
    }

    private void clearBoard() 
    {
        for (int x = 0; x < 8; x++) 
        {
            for (int y = 0; y < 8; y++) 
            {
                board.setTile(null, x, y);
            }
        }
    }

    @Test
    public void testPawnMovement() 
    {
        // add White pawn at e2
        Pawn whitePawn = new Pawn(4, 1, Team.WHITE);
        board.setTile(whitePawn, 4, 1);

        // 1 step forward
        List<Tile> moves = whitePawn.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 4 && t.getY() == 2));

        // Double jump forward from start position
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 4 && t.getY() == 3));

        // add black pawn for capture
        Pawn blackPawn = new Pawn(5, 2, Team.BLACK);
        board.setTile(blackPawn, 5, 2);
        moves = whitePawn.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 5 && t.getY() == 2));

        // En passant
        board.setTile(new Pawn(3, 4, Team.WHITE), 3, 4);
        Pawn blackPawn2 = new Pawn(2, 6, Team.BLACK);
        
        board.setTile(blackPawn2, 2, 6);
        Move blackDoubleStep = new Move(2, 6, 2, 4);
        
        board.moveTile(blackDoubleStep);
        List<Tile> enPassantMoves = board.getTile(3, 4).getPiece().canMove(board);
        
        assertTrue(enPassantMoves.stream().anyMatch(t -> t.getX() == 2 && t.getY() == 5));
    }

    @Test
    public void testKnightMovementAndCapture() 
    {
        Knight knight = new Knight(4, 4, Team.WHITE);
        board.setTile(knight, 4, 4);

        int[][] expected = 
        {
            {6,5},{5,6},{3,6},{2,5},{2,3},{3,2},{5,2},{6,3}
        };
        List<Tile> moves = knight.canMove(board);
        
        for (int[] pos : expected) 
        {
            assertTrue(moves.stream().anyMatch(t -> t.getX() == pos[0] && t.getY() == pos[1]));
        }

        // Capture
        Pawn enemy = new Pawn(6, 5, Team.BLACK);
        board.setTile(enemy, 6, 5);
        
        moves = knight.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 6 && t.getY() == 5));
    }

    @Test
    public void testBishopMovementAndBlock() 
    {
        Bishop bishop = new Bishop(3, 3, Team.WHITE);
        board.setTile(bishop, 3, 3);

        List<Tile> moves = bishop.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 0 && t.getY() == 0));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 6 && t.getY() == 0));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 0 && t.getY() == 6));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 6 && t.getY() == 6));

        // Blocked by own piece
        Pawn own = new Pawn(4, 4, Team.WHITE);
        board.setTile(own, 4, 4);
        moves = bishop.canMove(board);
        assertFalse(moves.stream().anyMatch(t -> t.getX() == 5 && t.getY() == 5));
    }

    @Test
    public void testRookMovementAndCapture() 
    {
        Rook rook = new Rook(4, 4, Team.WHITE);
        board.setTile(rook, 4, 4);

        List<Tile> moves = rook.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 4 && t.getY() == 0));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 4 && t.getY() == 7));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 0 && t.getY() == 4));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 7 && t.getY() == 4));

        // Blocked by enemy
        Pawn enemy = new Pawn(4, 6, Team.BLACK);
        board.setTile(enemy, 4, 6);
        moves = rook.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 4 && t.getY() == 6));
        assertFalse(moves.stream().anyMatch(t -> t.getX() == 4 && t.getY() == 7));
    }

    @Test
    public void testQueenMovement() 
    {
        Queen queen = new Queen(3, 3, Team.WHITE);
        board.setTile(queen, 3, 3);

        List<Tile> moves = queen.canMove(board);
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 3 && t.getY() == 0));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 0 && t.getY() == 3));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 0 && t.getY() == 0));
        assertTrue(moves.stream().anyMatch(t -> t.getX() == 7 && t.getY() == 7));
    }

    @Test
    public void testKingMovementAndCastling() 
    {
        King king = new King(4, 4, Team.WHITE);
        board.setTile(king, 4, 4);

        int[][] expected = 
        {
            {3,3},{3,4},{3,5},{4,3},{4,5},{5,3},{5,4},{5,5}
        };
        List<Tile> moves = king.canMove(board);
        for (int[] pos : expected) 
        {
            assertTrue(moves.stream().anyMatch(t -> t.getX() == pos[0] && t.getY() == pos[1]));
        }

        // castling
        King king2 = new King(4, 0, Team.WHITE);
        Rook rook = new Rook(7, 0, Team.WHITE);
        
        board.setTile(king2, 4, 0);
        board.setTile(rook, 7, 0);
        board.setTile(null, 5, 0);
        board.setTile(null, 6, 0);
        
        List<Tile> castleMoves = king2.canMove(board);
        assertTrue(castleMoves.stream().anyMatch(t -> t.getX() == 6 && t.getY() == 0));
    }
}
