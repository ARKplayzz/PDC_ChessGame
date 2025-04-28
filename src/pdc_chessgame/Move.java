/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Finlay & Andrew
 */
public class Move 
{
    public Pieces piece;
    public Tile from;
    public Tile to;    
    
    public move(Pieces p, Tile from, Tile to, int turn)
        {
            this.piece = p;
            this.from = from;
            this.to = to;
            this.moveNo = turn;
        }
    
    
    public static Input getMove(String userMovement) { // This should be working now
                
        String[] parts = userMovement.trim().toUpperCase().split(" ");// chops by spaces
        
        if (parts.length != 2 || !isValidTileRange(parts[0]) || !isValidTileRange(parts[1])) {
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
        
    private static boolean isValidTileRange(String pos) 
    {
        if (pos.length() != 2) {
            return false;
        }
        return pos.charAt(0) >= 'A' && pos.charAt(0) <= 'H' && pos.charAt(1) >= '1' && pos.charAt(1) <= '8';
    }
}
