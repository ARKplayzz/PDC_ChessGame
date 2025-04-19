/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class King extends Pieces {


    public King(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "♚" : "♔");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public boolean canMove(int fromX, int fromY, int toX, int toY, ChessBoard board) {
    
        return false;
    }
}
