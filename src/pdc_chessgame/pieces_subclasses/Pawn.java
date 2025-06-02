/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawn, a subclass of Piece.
 * Includes move rules, capturing, en passant, and promotion logic.
 * 
 * @author Andrew & Finlay
 */
public class Pawn extends Piece {

    public Pawn(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "p" : "P", pieceTeam); // Need to confirm we are doing subclassess correctly
    }

    @Override
    public int[][] getDirection() //returns the directional requirements for a move (x,y)
    {
        return (getPieceTeam() == Team.WHITE) ? new int[][] {{0, 1}} : new int[][] {{0, -1}};
    }

    @Override
    public List<Tile> canMove(BoardState board)  //returns a list of all tiles that the piece can move to
    {        
        List<Tile> possibleMoves = new ArrayList<>();
        Tile targetTile;
        Piece targetPiece;

        int y = getY() + getDirection()[0][1]; // calculates one step forward
        
        // checks if the tile directly in front is empty
        if (board.isWithinBoard(getX(), y)) 
        {
            targetTile = board.getTile(getX(), y);
            targetPiece = targetTile.getPiece(); 

            // checks if the tile directly in front is within board
            if (targetPiece == null)  
            {
                possibleMoves.add(targetTile);

                // If the pawn hasnt moved before, check if a 'double jump' is possible
                if (!board.hasPieceMoved(this))
                {
                    y = getY() + (2 * getDirection()[0][1]); 
                    // checks if the double jump tile is empty and valid
                    if (board.isWithinBoard(getX(), y) && targetPiece == null && board.getTile(getX(), y).getPiece() == null)  //within for dupdated double jump size
                    {
                        targetTile = board.getTile(getX(), y);
                        targetPiece = targetTile.getPiece(); 

                        possibleMoves.add(targetTile);
                    }
                }
            }
        }
        
        // checks diagonally for attacking and en passant logic
        for (int dx = -1; dx <= 1; dx += 2) 
        {
            int x = getX() + dx;
            y = getY() + getDirection()[0][1];

            if (board.isWithinBoard(x, y)) 
            {
                targetTile = board.getTile(x, y);
                targetPiece = targetTile.getPiece();
                
                // diagonal attack
                if (targetPiece != null && targetPiece.getPieceTeam() != getPieceTeam()) 
                {
                    possibleMoves.add(targetTile);
                }
                
                // en passant logic 
                Tile pawnTile = board.getTile(x, getY()); //check behind for enemy pawn 
                Piece targetPawn = pawnTile.getPiece();

                if (targetPawn instanceof Pawn && // if target is a pawn
                    targetPawn.getPieceTeam() != getPieceTeam() && // if target is an enemy
                    board.turnsSinceLastMoved(targetPawn) == 0) // if target just moved
                {
                    MoveState lastMove = board.getPieceLastMove(targetPawn); //handling if last move is non existant/null
                    
                    if (lastMove != null && lastMove.getDistanceMoved() == 2)  // if last move was a double jump
                    {
                        possibleMoves.add(targetTile); // en passant capture
                    }
                }
            }
        }    
        return possibleMoves;
    }
    
    // is true if pawn can be promoted (has made it to the oppiste end of the board
    public boolean canPromotion(ChessBoard board) 
    {
        if (getPieceTeam() == Team.WHITE) 
        {
            return getY() == board.getHeight() - 1;
        } 
        else 
        {
            return getY() == 0;
        }
    }

    @Override
    public String getName()
    {
        return "Pawn";
    }
   
}