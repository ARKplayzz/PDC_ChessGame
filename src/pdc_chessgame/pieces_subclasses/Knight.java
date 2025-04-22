/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
public class Knight extends Pieces {

    public Knight(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "n" : "N", pieceTeam); // Need to confirm we are doing subclassess correctly
    }
    
    @Override
    public boolean isSingleStep() 
    {
        return true;
    }
    
    @Override
    public int[][] getDirection()
    {
        return new int[][]{
        {2, 1},     // 2 right, 1 up
        {1, 2},     // 1 right, 2 up
        {-1, 2},    // 1 left, 2 up
        {-2, 1},    // 2 left, 1 up
        {-2, -1},   // 2 left, 1 down
        {-1, -2},   // 1 left, 2 down
        {1, -2},    // 1 right, 2 down
        {2, -1}     // 2 right, 1 down
        };
    }
}
