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
    public boolean canMove(int fromX, int fromY, int toX, int toY, ChessBoard board) {//must feed in POSITIONAL DATA
        System.out.println("bruh0");
        System.out.println(board.getTile(fromX, fromY));
        System.out.println(board.getTile(toX, toY));
        if (board.getTile(toX, toY) == null){
            System.out.println("bruh1");
            if (toY == fromY++){
                System.out.println("bruh2");
                if (fromX == toX){//if not moving diaganaly AND
                    System.out.println("bruh3");

                        return true; //standard move TRUE (Cannot kill)
                    }
                }
            else if (toX == fromX++ && board.getTile(toX, toY).getPieceTeam() != null) { // moving from++ & If peice ahead
                if (board.getTile(toX, toY).getPieceTeam() != this.pieceTeam) {// if ENEMY ahead
                    if (board.getTile(toX, toY) == null){//if tile empty

                        return true;
                        }
                    }
                }
            }
                
        return false; //move incomplete
        //make sure to include en passant
    }
    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }
}