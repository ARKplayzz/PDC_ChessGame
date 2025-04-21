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
public class Bishop extends Pieces {


    public Bishop(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "b" : "B");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public List<Tile> canMove(ChessBoard board) {

        if (moveSet.isMoveDiagonal() && moveSet.isPathClear(board)) { // Diagonal path is clear?
            
            Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY).getPiece();
                    
            if (targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam){
                return true;
            }
        }
        
        return false;
    }
}
