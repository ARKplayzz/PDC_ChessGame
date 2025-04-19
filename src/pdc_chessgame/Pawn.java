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


    public Pawn(int xInitPosition, int yInitPosition) {//can this init function be simplified (is it nessisary
        
        super("Pawn");
        
        this.xPosition = xInitPosition;
        this.yPosition = yInitPosition;
    }
    
    @Override
    public boolean movePiece() {
        return false; //move incomplete
        //make sure to include en passant
    }
    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }
}