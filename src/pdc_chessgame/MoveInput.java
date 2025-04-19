/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class MoveInput extends Thread
{ 
    public int fromX, fromY, toX, toY; // efficent storing for move cases

    public MoveInput(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }
    
    public static MoveInput getMove(String userMovement) { //IM NOT SURE IF THIS WORKS YET
        
        String[] parts = userMovement.trim().toUpperCase().split(" ");// chops by spaces
        
        if (parts.length != 2 || !isValidTile(parts[0]) || !isValidTile(parts[1])) {
            System.out.println("> " +userMovement+ " < Is an invalid input format, try something Like > A1 A2");
            return null;
        }
        
        int fromY = parts[0].charAt(0) - 'A'; // A to H, 1 to 8
        int fromX = 8 - Character.getNumericValue(parts[0].charAt(1)); 

        int toY = parts[1].charAt(0) - 'A'; 
        int toX = 8 - Character.getNumericValue(parts[1].charAt(1));

        return new MoveInput(fromX, fromY, toX, toY);
    }
        
    private static boolean isValidTile(String pos) {
        if (pos.length() != 2) return false;

        return pos.charAt(0) >= 'A' && pos.charAt(0) <= 'H' && pos.charAt(1) >= '1' && pos.charAt(1) <= '8';
    }
            

}