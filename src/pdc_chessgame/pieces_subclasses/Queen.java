/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
public class Queen extends Piece {

    public Queen(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "q" : "Q", pieceTeam);
    }
    
    @Override
    public int[][] getDirection()
    {
        return new int[][]{
        {0, 1},     // up
        {0, -1},    // down
        {1, 0},     // right
        {-1, 0},    // left
        {-1, -1},   // up left
        {1, -1},    // up right
        {-1, 1},    // down left
        {1, 1}      // down right
        };
    }

    @Override
    public String getName() 
    {
        return "Queen";
    }
}
