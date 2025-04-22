/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
import java.util.Scanner;

public class PDC_ChessGame {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("ConvertToTryWithResources")
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
        mateDetector mateChecker = new mateDetector(board);
        Turn turnCounter = new Turn();
        
        int i = 0; //THIS IS TEMPORRARY untill checkmate exists
        
        mateChecker.start();
        while(!board.checkmate)
        {

            board.printBoard();
            board.moveTile(Display.playerTurn(turnCounter.getTeam().toString(), turnCounter.getTeam(), board), turnCounter); 
            
            i++;
            if (i == 50) {
                board.checkmate = true;
            }
            
            Display.clearConsole(); //  can confirm this function doesn't work, redo it to account for more than 1 type of console
            turnCounter.nextTurn();
        }
        mateChecker.terminate();
        
        scanner.close();
    }
}
