/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.List;

/**
 *
 * @author ARKen
 */
public abstract class Pieces 
{ //its an abstract class btw
    //Unicode doesnt work in netbeans so we will use letters ):
    
    private String pieceUnicode = "?"; 
    public double value = 0;
    
    public Team pieceTeam;

    
    //public int xPosition = 0;
    //public int yPosition = 0;    

    public Pieces(String pieceUnicode) 
    {
        this.pieceUnicode = pieceUnicode;
    }

    public String getPieceUnicode() 
    {
        
        return this.pieceUnicode;
    }
    
    public Team getPieceTeam() 
    {
        
        return this.pieceTeam;
    }

    public void setTeamColour(String pieceUnicode) {//is this nessisary as a function?
        this.pieceUnicode = pieceUnicode;
    }

    //returns a list of all tiles that the piece can move to
    public abstract List<Tile> canMove(ChessBoard board);
    
}
