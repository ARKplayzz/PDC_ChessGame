/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

//import pdc_chessgame.*; //IS THIS THE BEST WAY TO DO THIS???? Idk sub classess suck

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
    

    int[][] direction = (this.getPieceTeam() == Team.WHITE) ? {0, 1} : {0, -1};
        
    @Override
    public List<Tile> canMove(ChessBoard board) //en pessaunt needs to know the prior move.. we may need move history. 
    { 
        // 1. One square forward
        int forwardY = y + direction;
        if (forwardY >= 0 && forwardY < board.height) {
            Tile forwardTile = board.getTile(x, forwardY);
            if (forwardTile != null && forwardTile.getPiece() == null) {
                legalMoves.add(forwardTile);

                // 2. Two squares forward from starting position
                boolean isAtStartingRow = (this.getPieceTeam() == Team.WHITE && y == 1) ||
                                          (this.getPieceTeam() == Team.BLACK && y == 6);
                if (isAtStartingRow) {
                    int doubleForwardY = y + (2 * direction);
                    Tile doubleForwardTile = board.getTile(x, doubleForwardY);
                    if (doubleForwardTile != null && doubleForwardTile.getPiece() == null) {
                        legalMoves.add(doubleForwardTile);
                    }
                }
            }
        }
    }

    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }

    @Override
    public boolean isSingleStep() 
    {
        return false;
    }

    @Override
    public int[][] getDirection() 
    {
        
    }
}