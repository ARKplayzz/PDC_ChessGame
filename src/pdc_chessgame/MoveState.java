/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */

// a class used to store entrys of the game's history in a list in Turn.java
public class MoveState
{
    // actual move information
    private Piece piece;
    private Piece capturedPiece;
    private Tile from;
    private Tile to;
    private int moveNo;
   
        
    public MoveState(Piece piece, Piece capturedPiece, Tile from, Tile to, int turn, String originialInput)
    {
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.from = from;
        this.to = to;
        this.moveNo = turn;
    }

    @Override
    public String toString() // toString for printing to a savefile
    {
        return (String)(this.piece.getPieceUnicode()+" "+this.from.getX()+","+this.from.getY()+" "+this.to.getX()+","+this.to.getY()+" "+this.moveNo);
    }
    
    public String getStringInput()
    {
        return MoveInput.getMoveOutput(new Move(from.getX(), from.getY(), to.getX(), to.getY()));
    }
        
    public Piece getPiece()
    {
        return this.piece;
    }
        
    public Piece getCapturedPiece()
    {
        return this.capturedPiece;
    }
        
    public Tile getFromTile()
    {
        return this.from;
    }
        
    public Tile getToTile()
    {
        return this.to;
    }
        
    public int getMoveNumber()
    {
        return this.moveNo;
    }
        
    public int getDistanceMoved() 
    {
        int dx = this.getToTile().getX() - this.getFromTile().getX();
        int dy = this.getToTile().getY() - this.getFromTile().getY();

        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        return distance;
    }
}