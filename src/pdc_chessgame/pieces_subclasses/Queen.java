/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

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
    public boolean canMove(Input moveSet, ChessBoard board) {
    
        return false;
    }
}
