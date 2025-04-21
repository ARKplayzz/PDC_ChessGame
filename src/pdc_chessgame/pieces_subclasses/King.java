/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.List;

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
    public List<Tile> canMove(ChessBoard board) {
        
        int dx = Input.getXdifference(moveSet);
        int dy = Input.getYdifference(moveSet);
        
        Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY).getPiece();

        if (dx <= 1 && dy <= 1 && (dx + dy > 0)) { // I think I overcomplicated this bit
            
            if(targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam) {
            return true;    
            }
        }
        return false;
        
        //need to add a check check
    }
}
