/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */

// Used for translating a string input into a move and vice-versa
public class MoveInput 
{    
    // turns a string into a move
    public static Move getMoveInput(String userMovement) 
    { 
        String[] parts = userMovement.trim().toUpperCase().split(" ");// chops by spaces
        
        if (parts.length != 2 || !isValidTile(parts[0]) || !isValidTile(parts[1])) {
            return null;
        }
        
        int fromX = ( parts[0].charAt(0) - 65); // A to H = 0 to 7
        int fromY = Character.getNumericValue(parts[0].charAt(1))- 1; // Translates char to int (-1 for start point at 0)

        int toX = (parts[1].charAt(0) - 65); // A to H = 0 to 7
        int toY = Character.getNumericValue(parts[1].charAt(1))-1; //  Translates char to int (-1 for start point at 0)

        return new Move(fromX, fromY, toX, toY);
    }
    
    public static String getMoveOutput(Move move) 
    { // reverses a move structure into the command that produced it, need for saving and loading
        if (move == null) 
        {
            return null;
        }

        String from = "" + (char) (move.getFromX() + 65) + (move.getFromY() + 1);
        String to = "" + (char) (move.getToX() + 65) + (move.getToY() + 1);

        return from + " " + to;
    }
        
    private static boolean isValidTile(String pos) 
    {
        if (pos.length() != 2) {
            return false;
        }
        return pos.charAt(0) >= 'A' && pos.charAt(0) <= 'H' && pos.charAt(1) >= '1' && pos.charAt(1) <= '8';
    }
}
