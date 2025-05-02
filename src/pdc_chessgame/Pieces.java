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
public abstract class Pieces 
{ 
    //Unicode doesnt work in netbeans so we will use letters ):
    private String pieceUnicode = "?"; 
        
    private int x, y;
    
    private Team pieceTeam;  

    public Pieces(int x, int y, String pieceUnicode, Team pieceTeam) 
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

    public void setTeamColour(String pieceUnicode) {//is this nessisary as a function?
        this.pieceUnicode = pieceUnicode;
    }
    
    public boolean isWithinBoard(int x, int y, ChessBoard board) 
    {
        return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
    }
        
    public abstract int[][] getDirection();
    
    //returns a list of all tiles that the piece can move to
    public List<Tile> canMove(ChessBoard board)
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        for (int[] dir : getDirection()) 
        {
            int xDirection = dir[0];
            int yDirection = dir[1];
            
            int x = getX() + xDirection;
            int y = getY() + yDirection;

            while (isWithinBoard(x, y, board)) 
            {
                Tile targetTile = board.getTile(x, y); //these can be shrunken if tile did not exist (:
                Pieces targetPiece = targetTile.getPiece(); //these can be shrunken if tile did not exist (:

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
