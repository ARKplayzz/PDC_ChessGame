/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class Rook extends Pieces {


    public Rook(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "r" : "R");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public boolean canMove(Input moveSet, ChessBoard board) {        

        if (moveSet.isMoveStraight() && moveSet.isPathClear(board)) { // Straight path is clear?
            
            Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY);
            
            if (targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam){
                return true;
            }
        }
        
        return false;
    }
}
