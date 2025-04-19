/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class Pawn extends Pieces {


    public Pawn(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "♟" : "♙");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public boolean canMove(int fromX, int fromY, int toX, int toY) {//must feed in POSITIONAL DATA
        ChessBoard board = ChessBoard.getCurrentBoard();
        if (fromX == toX){
            if (toY == fromY++ && ChessBoard.checkBoard(toX, toY) == null)
                return true; //standard move
        }
        else if
                
                
        return false; //move incomplete
        //make sure to include en passant
    }
    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }
}