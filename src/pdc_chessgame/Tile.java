/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author finla
 */
public class Tile 
{
    public int x, y;
    private Pieces piece;
    boolean white;
    
    public Tile(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.piece = null;
        
    }
    
    public void setPiece(Pieces p)
    {
        this.piece = p;
    }
    
    public Pieces getPiece()
    {
        return this.piece;
    }
    
    public void deletePiece()
    {
        this.piece = null;
    }
    
    public boolean movePieceTo(Tile tile)
    {
        if(this.piece == null)
            return false;
        tile.setPiece(this.piece);
        this.deletePiece();
        return true;
    }
}
