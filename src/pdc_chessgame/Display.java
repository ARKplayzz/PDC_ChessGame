/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public final class Display {
    
    public static void displayInCheckWarning() 
    {
        System.out.println("Invalid move! Your king is facing check, try again");
    }
    
    public static void displayInCheckNotification(Team team) 
    {
        System.out.println("CHECK! " + team.teamName() + " IS NOW IN CHECK");
        System.out.println("----------------------------------------------------");  
    }
    
    public static void displayGameOver(Team winningTeam)
    {
        System.out.println("CHECKMATE! " + winningTeam.teamName() + " WINS!");
        System.out.println("----------------------------------------------------"); 
    }
    
    public static void displayResignation(Team resigningTeam)
    {
        System.out.println("----------------------------------------------------"); 
        System.out.println(resigningTeam.teamName() + " HAS RESIGNED! "+ resigningTeam.getOppositeTeam().teamName() +" WINS!");
        System.out.println("----------------------------------------------------");  
    }
    
    public static void displayWelcome() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to Chess!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham\n");
        System.out.println("This Program is a part of Assignment 1 for PDC 2025");
        System.out.println("----------------------------------------------------");
    }
    
    public static void displayHelp() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("CHESS HELP");
        System.out.println("Resign Game          > X");
        System.out.println("Chess Help           > H");
        System.out.println("Show remaining time  > T");
        System.out.println("Move format   > From Tile -> 'A1 B2' <- To Tile");
    }
    
    public static void displayExit() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("THANKS FOR PLAYING!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham");
        System.out.println("----------------------------------------------------");
    }
}
