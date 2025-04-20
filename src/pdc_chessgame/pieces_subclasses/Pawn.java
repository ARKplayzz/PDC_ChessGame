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
        
        super(pieceTeam == Team.BLACK ? "♟" : "♙");
        this.pieceTeam = pieceTeam;

    }
    
    
    //en pessaunt needs to know the prior move.. we may need move history. 
    
    @Override
    public boolean canMove(int fromX, int fromY, int toX, int toY, ChessBoard board) { // TRY MINAMISE THESE VARIABLES 
        
        Pieces targetPiece = board.getTile(toX, toY); // could pass this in?
 
        int direction = (pieceTeam == Team.BLACK) ? -1 : 1; // directionality for pawns

        // Normal forward move
        if (toX == fromX && toY == fromY + direction && targetPiece == null) {
            return true;
        }
        
        // Starting 2 step move
        int startRow = (pieceTeam == Team.BLACK) ? 6 : 1;
        
        if (toX == fromX && fromY == startRow && toY == fromY + 2 * direction) {
            Pieces nextTile = board.getTile(fromX, fromY + direction);
            if (nextTile == null && targetPiece == null) {
                return true;
            }
        }

        // Diagonal capture
        if (Math.abs(toX - fromX) == 1 && toY == fromY + direction) {
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