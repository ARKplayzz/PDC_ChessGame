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
        
        int dx = Input.getXdifference(moveSet);
        int dy = Input.getYdifference(moveSet);
        
        Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY);

        if ((dx == 0 || dy == 0) && (dx + dy > 0)) { // Stright
            
            
            int directionX = Integer.compare(moveSet.toX, moveSet.fromX); // direction as - or +
            int directionY = Integer.compare(moveSet.toY, moveSet.fromY);

            int x = moveSet.fromX + directionX;
            int y = moveSet.fromY + directionY;

            while (x != moveSet.toX || y != moveSet.toY) {// steps towards space (make into seperate function??)
                
                if (board.getTile(x, y) != null) { // Something in the way
                    return false; 
                }
                
                x += directionX; 
                y += directionY;
            }
            
            if (targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam){
                return true;
            }
        }
        
        return false;
    }
}
