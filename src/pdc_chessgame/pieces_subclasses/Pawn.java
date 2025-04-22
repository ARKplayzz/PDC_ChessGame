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
        super(x, y, pieceTeam == Team.BLACK ? "p" : "P", pieceTeam); // Need to confirm we are doing subclassess correctly
    }

    @Override
    public boolean isSingleStep() 
    {
        return true;
    }

    @Override
    public int[][] getDirection() 
    {
        return (this.getPieceTeam() == Team.WHITE) ? new int[][] {{0, 1}} : new int[][] {{0, -1}}; //idk this seems unessisary to have y dir in [0][1]
    }

    
    @Override
    public List<Tile> canMove(ChessBoard board) 
    {        
        List<Tile> possibleMoves = new ArrayList<>();
        int startRow = (getPieceTeam() == Team.WHITE) ? board.height-1 : board.height+1;

        int y = this.y + getDirection()[0][1];
        
        Tile targetTile = board.getTile(this.x, y);
        Pieces targetPiece = targetTile.getPiece(); 
        
        if (isWithinBoard(this.x, y, board) && targetPiece == null) 
        {
            possibleMoves.add(targetTile);
            
            y = this.y + 2 * getDirection()[0][1]; // double jump
            
            targetTile = board.getTile(this.x, y);
            targetPiece = targetTile.getPiece(); 

            if (this.y == startRow && targetPiece == null) 
            {
                possibleMoves.add(targetTile);
            }
        }

        for (int dx = -1; dx <= 1; dx += 2) 
        {
            int x = this.x + dx;
            y = this.y + getDirection()[0][1];

            if (isWithinBoard(x, y, board)) 
            {
                targetTile = board.getTile(x, y);
                targetPiece = targetTile.getPiece();
                
                if (targetPiece != null && targetPiece.getPieceTeam() != this.getPieceTeam()) {
                    possibleMoves.add(targetTile);
                }
            }
        }
        return possibleMoves;
    }

    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }

}