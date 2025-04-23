/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Finlay & Andrew
 */
public class Input 
{
    public int fromX, fromY, toX, toY; // efficent storing for move cases
    
    public Input(int fromRow, int fromCol, int toRow, int toCol) 
    {
        this.fromX = fromRow;
        this.fromY = fromCol;
        this.toX = toRow;
        this.toY = toCol;
    }
    
    
    public static Input getMove(String userMovement) { // This should be working now
        
        String[] parts = userMovement.trim().toUpperCase().split(" ");// chops by spaces
        
        if (parts.length != 2 || !isValidTile(parts[0]) || !isValidTile(parts[1])) {
            return null;
        }
        
        int fromX = ( parts[0].charAt(0) - 65); // A to H = 0 to 7
        int fromY = Character.getNumericValue(parts[0].charAt(1))- 1; // Translates char to int (-1 for start point at 0)

        int toX = (parts[1].charAt(0) - 65); // A to H = 0 to 7
        int toY = Character.getNumericValue(parts[1].charAt(1))-1; //  Translates char to int (-1 for start point at 0)

        return new Input(fromX, fromY, toX, toY);
    }
    
    public static Input getMove(int fromX, int fromY, int toX, int toY) {

        return new Input(fromX, fromY, toX, toY);
    }
        
    private static boolean isValidTile(String pos) 
    {
        if (pos.length() != 2) {
            return false;
        }
        return pos.charAt(0) >= 'A' && pos.charAt(0) <= 'H' && pos.charAt(1) >= '1' && pos.charAt(1) <= '8';
    }
    
    public static int getXdifference(Input moveSet){ //thoughts on this as a function (Maybe unessisary????)
        
        int dx = Math.abs(moveSet.toX - moveSet.fromX);
        return dx;
    }
    
    public static int getYdifference(Input moveSet){ //thoughts on this as a function (Maybe unessisary????)
        
        int dy = Math.abs(moveSet.toY - moveSet.fromY);
        return dy;
    }
    
    public boolean isMoveStraight(){
        
        int dx = getXdifference(this); //whys this not work?
        int dy = getYdifference(this);

        return ((dx == 0 || dy == 0) && (dx + dy > 0));
    }
    
    public boolean isMoveDiagonal(){
        
        int dx = getXdifference(this);
        int dy = getYdifference(this);
        
        return (dx == dy && dx != 0);
    }
    
    public boolean isPathClear(ChessBoard board){
        
        int directionX = Integer.compare(this.toX, this.fromX); // direction as - or +
        int directionY = Integer.compare(this.toY, this.fromY);

        int x = this.fromX + directionX;
        int y = this.fromY + directionY;
        
        while (x != this.toX || y != this.toY) 
        {// steps towards space   
            if (board.getTile(x, y) != null) 
            { // Something in the way
                return false; 
            }
                
            x += directionX; 
            y += directionY;
        }
        return true;
    }
}
