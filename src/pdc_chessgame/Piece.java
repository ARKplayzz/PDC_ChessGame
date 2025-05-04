/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Piece, an abstract master class.
 * Includes abstract piece requirements, base piece variables, base move rules.
 * 
 * @author Andrew & Finlay
 */
public abstract class Piece 
{ 
    //Unicode doesnt work in netbeans so we will use letters ):
    private String pieceUnicode = "?"; 
        
    private int x, y;
    
    private Team pieceTeam;  

    public Piece(int x, int y, String pieceUnicode, Team pieceTeam) 
    {
        this.x = x;
        this.y = y;
        this.pieceUnicode = pieceUnicode;
        this.pieceTeam = pieceTeam;
    }

    public String getPieceUnicode() 
    {
        return this.pieceUnicode;
    }
    
    public Team getPieceTeam() 
    {
        return this.pieceTeam;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void setX(int x)
    {
       this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
        
    // movement function oveeridden by the subclasses
    public abstract int[][] getDirection();
    
    public abstract String getName();
    
    //returns a list of all tiles that the piece can move to
    public List<Tile> canMove(BoardState board)
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        for (int[] dir : getDirection()) 
        {
            int xDirection = dir[0];
            int yDirection = dir[1];
            
            int x = getX() + xDirection;
            int y = getY() + yDirection;

            while (board.isWithinBoard(x, y)) 
            {
                Tile targetTile = board.getTile(x, y); 
                Piece targetPiece = targetTile.getPiece();

                if (targetPiece == null) // if Tile empty or Contains enemy
                { 
                    possibleMoves.add(targetTile); 
                } 
                else 
                {
                    if (targetPiece.getPieceTeam() != getPieceTeam())
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
