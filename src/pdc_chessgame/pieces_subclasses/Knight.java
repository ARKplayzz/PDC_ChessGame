/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class Knight extends Pieces {


    public Knight(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "n" : "N");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public boolean canMove(Input moveSet, ChessBoard board) {
        
        int dx = Input.getXdifference(moveSet);
        int dy = Input.getYdifference(moveSet);

        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) { // L shaped patern - needs testing (looks good)
        
            Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY).getPiece();
                    
            if (targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam){
                return true;
            }

        }

    
        return false;
    }
}
