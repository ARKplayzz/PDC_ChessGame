/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ARKen
 */
public class King extends Pieces {

    public King(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "k" : "K", pieceTeam); // Need to confirm we are doing subclassess correctly
    }
    
    int[][] directions = {
            {0, 1},     // up
            {0, -1},    // down
            {1, 0},     // right
            {-1, 0},    // left
            {-1, -1},   // up left
            {1, -1},    // up right
            {-1, 1},    // down left
            {1, 1}      // down right
        };
    
    @Override
    public List<Tile> canMove(ChessBoard board) //Need to make sure king cant move INTO check!
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        for (int[] dir : directions) {
            
            int xDirection = dir[0];
            int yDirection = dir[1];
            
            int x = this.x + xDirection;
            int y = this.y + yDirection;

            while (x >= 0 && x < 1 && y >= 0 && y < 1) { //this should mean a distance of 1 tile (need to test)
                
                Tile targetTile = board.getTile(x, y); //these can be shrunken if tile did not exist (:
                Pieces targetPiece = targetTile.getPiece(); //these can be shrunken if tile did not exist (:

                if (targetPiece == null) // if Tile empty or Contains enemy
                { 
                    possibleMoves.add(targetTile); 
                } 
                else 
                {
                    if (targetPiece.getPieceTeam() != this.getPieceTeam())
                    {
                        possibleMoves.add(targetTile); 
                        break;
                    }
                } 
                
                x += xDirection;
                y += yDirection;
            }
        }

        return possibleMoves;
    }
}
