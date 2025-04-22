/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew & Finlay
 */
public class Knight extends Pieces {

    public Knight(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "n" : "N", pieceTeam); // Need to confirm we are doing subclassess correctly
    }
    
    int[][] direction = {
        {2, 1},     // 2 right, 1 up
        {1, 2},     // 1 right, 2 up
        {-1, 2},    // 1 left, 2 up
        {-2, 1},    // 2 left, 1 up
        {-2, -1},   // 2 left, 1 down
        {-1, -2},   // 1 left, 2 down
        {1, -2},    // 1 right, 2 down
        {2, -1}     // 2 right, 1 down
    };
    
    @Override
    public List<Tile> canMove(ChessBoard board)
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        for (int[] dir : direction) {
            
            int x = this.x + dir[0];
            int y = this.y + dir[1];

            if (x >= 0 && x < board.width && y >= 0 && y < board.height) { // only checks once per adjacent tile - only checks within board (need to test)
                
                Tile targetTile = board.getTile(x, y); //these can be shrunken if tile did not exist (:
                Pieces targetPiece = targetTile.getPiece(); //these can be shrunken if tile did not exist (:

                if (targetPiece == null || targetPiece.getPieceTeam() != this.getPieceTeam()) // if Tile empty or Contains enemy
                { 
                    possibleMoves.add(targetTile); 
                } 
            }
        }
        return possibleMoves;
    }
}
