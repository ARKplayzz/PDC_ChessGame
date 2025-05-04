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
    private Piece piece;
    
    // we are assuming that if a piece is odd it is white
    // odd is not called as of yet as it is only required in assignment 2 for GUI tile colouring
    private boolean odd = false;
    
    public Tile(int x, int y)
    {
        this.x = x;
        this.y = y;
        // setting default value to null so we can can check for it
        this.piece = null;

        if (!((x + 1) % 2 != 0) == ((y + 1) % 2 != 0)) //if tile is odd then tile = white
            this.odd = true;
    }
    
    public void setPiece(Piece p)
    {
        this.piece = p;
    }
    
    public Piece getPiece()
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
        // check that a piece actully exists
        if(this.piece == null)
            return false;
        
        this.piece.setX(tile.x); //updating piece to contain new location
        this.piece.setY(tile.y);
        
        // move piece to the new tile
        tile.setPiece(this.piece);
        // delete the instance on this tile
        this.deletePiece();
        return true;
    }
}
