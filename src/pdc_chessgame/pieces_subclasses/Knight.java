/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight, a subclass of Piece.
 * Includes move rules & capturing logic.
 * 
 * @author Andrew & Finlay
 */
public class Knight extends Piece {

    public Knight(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "n" : "N", pieceTeam);
    }
    
    @Override
    public int[][] getDirection() //returns the directional requirements for a move (x,y)
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
    
    @Override
    public List<Tile> canMove(BoardState board)  //returns a list of all tiles that the piece can move to
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        // runs through all the knight directions as "jumps"
        for (int[] dir : getDirection()) 
        {            
            int x = getX() + dir[0];
            int y = getY() + dir[1];

            if (board.isWithinBoard(x, y)) 
            {
                Tile targetTile = board.getTile(x, y);
                Piece targetPiece = targetTile.getPiece();

                // adds a tile if its empty or has enemy piece
                if (targetPiece == null || targetPiece.getPieceTeam() != getPieceTeam()) // if Tile empty or Contains enemy
                { 
                    possibleMoves.add(targetTile); 
                } 
            }
        }
        return possibleMoves;
    }

    @Override
    public String getName() 
    {
        return "Knight";
    }
}
