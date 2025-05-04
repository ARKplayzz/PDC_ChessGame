/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 * Bishop, a subclass of Piece.
 * Includes move rules & capturing logic.
 * 
 * @author Andrew & Finlay
 */
public class Bishop extends Piece {

   public Bishop(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "b" : "B", pieceTeam);
    }
   
    @Override
    public int[][] getDirection() //returns the directional requirements for a move (x,y)
    {
        return new int[][]{
        {-1, -1},   // up left
        {1, -1},    // up right
        {-1, 1},    // down left
        {1, 1}      // down right
        };
    } 

    @Override
    public String getName() 
    {
        return "Bishop";
    }
}
