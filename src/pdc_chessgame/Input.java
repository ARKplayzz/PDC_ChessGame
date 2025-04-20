/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author finlay
 */
public class Input extends Thread
{
    /*
        have this class running a sperate thread monitoring input
        I merged moveInput into this as having two input classes is
        redundant plus we'll refactor this later anyway
    
        If you want to keep MoveInput as a seperate class then I do have
        a back up of the files before they were merged
    */
    
    @Override
    public void run()
    { // multithreading stuff
        while(!this.quit)
        {
            
        }
    }
    
    public void terminate()
    {
        this.quit = true;
    }
    
    private boolean quit = false; // set to true to exit the thread
    public int fromX, fromY, toX, toY; // efficent storing for move cases

    /*
        Will redo alot of this later
    */
    
    public Input(int fromRow, int fromCol, int toRow, int toCol) {
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
        
    private static boolean isValidTile(String pos) 
    {
        if (pos.length() != 2) {
            return false;
        }
        return pos.charAt(0) >= 'A' && pos.charAt(0) <= 'H' && pos.charAt(1) >= '1' && pos.charAt(1) <= '8';
    }
    
    public static int Xdifference(Input moveSet){
        
        int dx = Math.abs(moveSet.toX - moveSet.fromX);
        return dx;
    }
    
    public static int Ydifference(Input moveSet){
        
        int dy = Math.abs(moveSet.toY - moveSet.fromY);
        return dy;
    }
}
