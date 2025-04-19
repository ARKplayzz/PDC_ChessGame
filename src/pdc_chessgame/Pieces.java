/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public abstract class Pieces { //its an abstract class btw
    
    
    
    public String pieceUnicode = "?"; //maybe makes thise private as tje getFunction is included
    public double value = 0;
    
    public Team pieceTeam;
            
    //public int xPosition = 0;
    //public int yPosition = 0;    

    public Pieces(String pieceUnicode) {
        this.pieceUnicode = pieceUnicode;
    }

    public String getPieceUnicode() {
        
        return this.pieceUnicode;

    }

    public void setTeamColour(String pieceUnicode) {//is this nessisary as a function?
        this.pieceUnicode = pieceUnicode;
    }

    //returns True if move move completes successfully
    public abstract boolean canMove();
    
}
