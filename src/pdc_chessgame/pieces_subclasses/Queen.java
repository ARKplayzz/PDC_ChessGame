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
public class Queen extends Pieces {


    public Queen(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "q" : "Q");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public List<Tile> canMove(ChessBoard board) 
    {

        if ((moveSet.isMoveDiagonal() || moveSet.isMoveStraight()) && moveSet.isPathClear(board)) { // Straight or Diagonal path is clear?
            
            Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY).getPiece();
                    
            if (targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam){
                return true;
            }
        }
        
        return false;
    }
}
