/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class King extends Pieces {


    public King(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "k" : "K");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public boolean canMove(Input moveSet, ChessBoard board) { // TRY MINAMISE THESE VARIABLES 
        
        Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY); 

        // Multi-directional movement
        if (Math.abs(moveSet.toX - moveSet.fromX) == 1 && Math.abs(moveSet.toX - moveSet.fromX) == 1) {
            if (targetPiece != null){
                if (targetPiece.getPieceTeam() != this.pieceTeam) {
                    return true;
                }
            }
        }
        
        //need to ad check check
        return false;
    }
}
