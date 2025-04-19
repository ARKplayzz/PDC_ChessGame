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


    public Pawn(Team pieceTeam) {//can this init function be simplified (is it nessisary
        
        if (pieceTeam == Team.BLACK){//check if can be made more effient
            this.pieceUnicode = "♟"; 
        }
        else {
            this.pieceUnicode = "♙"; 
        }
        
        super("");//WHAT IS SUPER FOR
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public boolean movePiece() {//must feed in POSITIONAL DATA
        return false; //move incomplete
        //make sure to include en passant
    }
    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }
}