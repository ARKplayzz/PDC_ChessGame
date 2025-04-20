/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

//import pdc_chessgame.*; //IS THIS THE BEST WAY TO DO THIS???? Idk sub classess suck


/**
 *
 * @author ARKen
 */
public class Pawn extends Pieces {


    public Pawn(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "p" : "P");
        this.pieceTeam = pieceTeam;

    }
    
    
    //en pessaunt needs to know the prior move.. we may need move history. 
    
    //need to check if can accidently attack self?
    
    @Override
    public boolean canMove(Input moveSet, ChessBoard board) { // TRY MINAMISE THESE VARIABLES 
        
        Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY); // could pass this in?
 
        int direction = (this.pieceTeam == Team.BLACK) ? -1 : 1; // directionality for pawns

        // Normal forward move
        if (moveSet.toX == moveSet.fromX && moveSet.toY == moveSet.fromY + direction && targetPiece == null) {
            return true;
        }
        
        // Starting 2 step move
        int startRow = (this.pieceTeam == Team.BLACK) ? 6 : 1;
        
        if (moveSet.toX == moveSet.fromX && moveSet.fromY == startRow && moveSet.toY == moveSet.fromY + 2 * direction) {
            Pieces nextTile = board.getTile(moveSet.fromX, moveSet.fromY + direction);
            if (nextTile == null && targetPiece == null) {
                return true;
            }
        }

        // Diagonal capture
        if (Math.abs(moveSet.toX - moveSet.fromX) == 1 && moveSet.toY == moveSet.fromY + direction) {
            if (targetPiece != null && targetPiece.getPieceTeam() != this.pieceTeam) {
                return true;
            }
        }

        // Need to add en pesuant

        return false;
    }

    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }
}