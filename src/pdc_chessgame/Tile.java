/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
public class Tile 
{
    private int x, y;
    private Pieces piece;
    // we are assuming that if a piece isn't white then it's black
    boolean white = false;
    
    public Tile(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.piece = null;

        if (!((x + 1) % 2 != 0) == ((y + 1) % 2 != 0)) //if tile is odd then tile = white
            this.white = true;
    }
    
    public void setPiece(Pieces p)
    {
        this.piece = p;
    }
    
    public Pieces getPiece()
    {
        return this.piece;
    }
    
    public int getX()
    {
        return this.x;
    }
    
    public int getY()
    {
        return this.y;
    }
    
    public void deletePiece()
    {
        this.piece = null;
    }
    
    public boolean movePieceTo(Tile tile)
    {
        if(this.piece == null)
            return false;
        
        this.piece.setX(tile.x); //updating piece to contain new location
        this.piece.setY(tile.y);
        
        tile.setPiece(this.piece);
        this.deletePiece();
        return true;
    }
}
