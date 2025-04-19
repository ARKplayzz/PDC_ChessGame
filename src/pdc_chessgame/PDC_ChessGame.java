/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
import java.util.Scanner;

public class PDC_ChessGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        Scanner scanner = new Scanner(System.in); //scanner my beloved 
        
        
        System.out.println("Welcome to chess!");
        System.out.println("");
        System.out.println("Player 1 please Login with your username, or enter: 'Guest' to play as a guest ");
        
        System.out.println("");
        System.out.println("P1> ");       
        System.out.println("WELCOME MESSAGE FROM 'User Login' class");
        System.out.println("");
        
        System.out.println("Player 2 please Login with your username, or enter: 'Guest' to play as a guest ");
        
        System.out.println("");
        System.out.println("P2> ");
        System.out.println("WELCOME MESSAGE FROM 'User Login' class");
        System.out.println("");
        
        ChessBoard board = new ChessBoard(8, 8);
        
        boolean checkmate = false;
        int i = 0; //THIS IS TEMPORRARY untill checkmate exists
        
        
        while(!checkmate)
        {
            board.printBoard();
            
            System.out.println("");
            System.out.println("P1> ");
            
            board.printBoard();
            
            System.out.println("");
            System.out.println("P1> "); //eg E9, b7
            
            Input.getMove(scanner.nextLine().trim().toUpperCase()); // CHANGE THIS LATER
            i++;
            if (i == 50) {
                checkmate = true;
            }
        }
        
        scanner.close();
    }
    
    
}
