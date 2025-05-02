/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

//import pdc_chessgame.*; //IS THIS THE BEST WAY TO DO THIS???? Idk sub classess suck

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew & Finlay
 */
public class Pawn extends Pieces {

    public Pawn(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "♟" : "♙", pieceTeam == Team.BLACK ? "p" : "P", pieceTeam); // Need to confirm we are doing subclassess correctly
    }

    @Override
    public int[][] getDirection() //maybe remove this for pawn
    {
        return (getPieceTeam() == Team.WHITE) ? new int[][] {{0, 1}} : new int[][] {{0, -1}}; //idk this seems unessisary to have y dir in [0][1]
    }

    @Override
    public List<Tile> canMove(ChessBoard board) 
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        int y = getY() + getDirection()[0][1];
        
        Tile targetTile = board.getTile(getX(), y);
        Pieces targetPiece = targetTile.getPiece(); 
        
        if (isWithinBoard(getX(), y, board) && targetPiece == null)  
        {
            possibleMoves.add(targetTile);
            
            if (!board.turnCounter.hasPieceMoved(this)) // double jump
            {
                y = getY() + (2 * getDirection()[0][1]); 

                if (isWithinBoard(getX(), y, board) && targetPiece == null)  //within for dupdated double jump size
                {
                    targetTile = board.getTile(getX(), y);
                    targetPiece = targetTile.getPiece(); 
                
                    possibleMoves.add(targetTile);
                }
            }
        }
        
        // attacking 
        for (int dx = -1; dx <= 1; dx += 2) 
        {
            int x = getX() + dx;
            y = getY() + getDirection()[0][1];

            if (isWithinBoard(x, y, board)) 
            {
                targetTile = board.getTile(x, y);
                targetPiece = targetTile.getPiece();
                
                if (targetPiece != null && targetPiece.getPieceTeam() != getPieceTeam()) 
                {
                    possibleMoves.add(targetTile);
                }
                
                // en passant bellow 
                Tile pawnTile = board.getTile(x, getY()); //check behind for enemy pawn  - getDirection()[0][1])
                Pieces targetPawn = pawnTile.getPiece();

                if (targetPawn instanceof Pawn && // if target is a pawn
                    targetPawn.getPieceTeam() != getPieceTeam() && // if target is an enemy
                    board.turnCounter.turnsSinceLastMoved(targetPawn) == 0 && // if target just moved CHECK IF -1 IS EXPECTED CASE
                    board.turnCounter.distanceLastMoved(targetPawn) == 2) { // if has only moved once

                    possibleMoves.add(targetTile);
                }
            }
        }
        return possibleMoves;
    }
    
    public boolean canPromotion(ChessBoard board) 
    {
        return getY() + ((getPieceTeam() == Team.BLACK) ? (board.getHeight() - 1) : 2) == board.getHeight();
    }
   
}