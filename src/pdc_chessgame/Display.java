/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Finlay 
 */
public class Display 
{
    public static void clearConsole() 
    { // Still doesn't work
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        
        if(System.getProperty("os.name").contains("Windows"))
        {
            try {  
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void gameWelcome()
    {
        
    }
    
    
    /*
        bro what the fuck is this doing in the display class
    */
    public static Input playerTurn(String player, Team colour, ChessBoard board)
    { //May be best to split this up a bit
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("----------------------------------------------------");
        System.out.println(colour.toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.print(player+"> ");
        
        String playerInput = scanner.nextLine();
 
        if (playerInput.toUpperCase().equals("X"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(player+" HAS RESIGNED ");
            
            return null;  //end
        }
        
        if (playerInput.toUpperCase().equals("H"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println("CHESS HELP");
            System.out.println("Resign Game   > X");
            System.out.println("Chess Help    > H");
            System.out.println("Move format   > From Tile -> 'A1 B2' <- To Tile");
            
            return playerTurn(player, colour, board);  //try again
        }
        
        if (Input.getMove(playerInput.trim().toUpperCase()) == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println("'"+playerInput+ "' Is not a valid chess Input, Eg 'A1 B2'");
            
            return playerTurn(player, colour, board);  //try again
        }
        
        Input moveSet = Input.getMove(playerInput.trim().toUpperCase());
        
        if (board.getTile(moveSet.fromX, moveSet.fromY) == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Does not contain a piece, Eg 'A1 B2'");
            
           return playerTurn(player, colour, board);  //try again
            
        }
        
        if (board.getTile(moveSet.fromX, moveSet.fromY).getPiece().getPieceTeam() != colour)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Is not your Piece, please try again");
            
            return playerTurn(player, colour, board);  //try again
        }
        
        // if the list of possible moves does not contain our destination tile
        if (!board.getTile(moveSet.fromX, moveSet.fromY).getPiece().canMove(board).contains(board.getTile(moveSet.toX, moveSet.toY)))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput + " Is an Invalid Chess Move, please try again");
            
            return playerTurn(player, colour, board);  //try again
        }
        
        System.out.println("----------------------------------------------------");        

        return moveSet;
    }
            
          
    
}
