/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 * Rook, a subclass of Piece.
 * Includes move rules & capturing logic.
 * 
 * @author Andrew & Finlay
 */
public class Rook extends Piece {

    public Rook(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "r" : "R", pieceTeam);
    }
    
    @Override
    public int[][] getDirection() //returns the directional requirements for a move (x,y)
    {
        return new int[][]{
        {0, 1},     // up
        {0, -1},    // down
        {1, 0},     // right
        {-1, 0},    // left
        };
    }

    @Override
    public String getName() 
    {
        return "Rook";
    }
}
