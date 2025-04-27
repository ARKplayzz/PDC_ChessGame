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
    
        instead of using lots of println's we should format this betetr
    */
    public static Pieces getPromotionPiece(String player, Pawn pawn)
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(pawn.getPieceTeam().toString()+" PAWN PROMOTION!                    (X) TO QUIT");
        System.out.println();
        System.out.println("INPUT WHAT YOU WOULD LIKE TO PROMOTE YOUR PAWN TOO:");
        System.out.println("   <BISHOP>  =  R,   <ROOK>   =  B, ");
        System.out.println("   <KNIGHT>  =  N,   <QUEEN>  =  Q ");
      
        System.out.print(player+"> ");
        
        String playerInput = scanner.nextLine();
 
        if (playerInput.toUpperCase().equals("X"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(player+" HAS RESIGNED ");
            
            return null;
        }
        if (playerInput.toUpperCase().equals("R"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Rook(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        if (playerInput.toUpperCase().equals("B"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Bishop(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        if (playerInput.toUpperCase().equals("N"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Knight(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        if (playerInput.toUpperCase().equals("Q"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Queen(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Invalid option chosen, please try again");
        System.out.println("----------------------------------------------------");

        
        return getPromotionPiece(player, pawn);  //try again
    }
    
    
            
          
    
}
