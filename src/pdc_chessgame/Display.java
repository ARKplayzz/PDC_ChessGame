/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.awt.Color;
import javax.swing.*;

/**
 *
 * @author ARKen
 */
public final class Display extends JFrame {
    
    private final InputHandler inputHandler = new InputHandler();
    
    public final GameMenu gameMenu = new GameMenu();
    
    public Display() 
    {
        this.setTitle("Chess Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(50, 50, 50)); //dark grey
        
        this.setLayout(null);
        
        //ImageIcon image new ImageIcon("logo.png");
        //frame.setIconImage(image.getImage());
        
        JPanel SideBar = new JPanel();
        SideBar.setLayout(null);
        SideBar.setBackground(new Color(40, 40, 40));
        SideBar.setBorder(BorderFactory.createLineBorder(Color.white));
        SideBar.setBounds(450, 0, 200, 450);
        this.add(SideBar);
        
        JPanel chessBoard = new JPanel();
        chessBoard.setBackground(new Color(40, 40, 40));
        chessBoard.setBorder(BorderFactory.createLineBorder(Color.white));
        chessBoard.setBounds(0, 0, 450, 450);
        this.add(chessBoard);
        System.out.println("GameMenu constructed");
        SideBar.add(gameMenu);
        //this.add(gameMenu);
        
    }
    
    public void displayInCheckWarning() 
    {
        System.out.println("Invalid move! Your king is facing check, try again");
    }
    
    public void displayInCheckNotification(Team team) 
    {
        System.out.println("CHECK! " + team.teamName() + " IS NOW IN CHECK");
        System.out.println("----------------------------------------------------");  
    }
    
    public void displayGameOver(Team winningTeam)
    {
        System.out.println("CHECKMATE! " + winningTeam.teamName() + " WINS!");
        System.out.println("----------------------------------------------------"); 
    }
    
    public void displayResignation(Team resigningTeam)
    {
        System.out.println("----------------------------------------------------"); 
        System.out.println(resigningTeam.teamName() + " HAS RESIGNED! "+ resigningTeam.getOppositeTeam().teamName() +" WINS!");
        System.out.println("----------------------------------------------------");  
    }
    
    public void displayTimeOver(Team loser)
    {
        System.out.println("----------------------------------------------------"); 
        System.out.println(loser.teamName() + " HAS RUN OUT OF TIME!\n"+ loser.getOppositeTeam().teamName() +" WINS!");
        System.out.println("----------------------------------------------------");  
    }
    
    public void displayWelcome() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to Chess!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham\n");
        System.out.println("This Program is a part of Assignment 1 for PDC 2025");
        System.out.println("----------------------------------------------------");
    }
    
    public void displayHelp() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("CHESS HELP");
        System.out.println("Resign Game          > X");
        System.out.println("Chess Help           > H");
        System.out.println("Show remaining time  > T");
        System.out.println("Save the game        > S");
        System.out.println("Show the board again > B");
        System.out.println("View move history    > MH");
        System.out.println("Move format   > From Tile -> 'A1 B2' <- To Tile");
    }
    
    public static void displayExit() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("THANKS FOR PLAYING!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham");
        System.out.println("----------------------------------------------------");
    }
    
    public String promptPlayerMove(Player player, Clock clock) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println(player.getTeam().toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.println("Remaining time: " + clock.toString());
        
        return inputHandler.getStringInput(player.getName() + "> ");
    }
    
    public void displayInvalidMove(String message) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println(message);
    }
    
    @SuppressWarnings("NonPublicExported")
    public PawnOption getPromotionPiece(Team team, Player player) 
    {
        System.out.println(team.toString()+" PAWN PROMOTION!                    (X) TO QUIT");
        System.out.println();
        System.out.println("INPUT WHAT YOU WOULD LIKE TO PROMOTE YOUR PAWN TOO:");
        System.out.println("   <BISHOP>  =  R,   <ROOK>   =  B, ");
        System.out.println("   <KNIGHT>  =  N,   <QUEEN>  =  Q ");
      
        String userInput = inputHandler.getStringInput(player.getName() + ">");

        if (userInput.equals("X")) 
        {
            return PawnOption.EXIT_GAME;
        } 
        else if (userInput.equals("R")) 
        {
            return PawnOption.ROOK;
        } 
        else if (userInput.equals("N")) 
        {
            return PawnOption.KNIGHT;
        } 
        else if (userInput.equals("B")) 
        {
            return PawnOption.BISHOP;
        } 
        else if (userInput.equals("Q")) 
        {
            return PawnOption.QUEEN;
        } 

        System.out.println("----------------------------------------------------");
        System.out.println("Invalid option. Please try again.");
        System.out.println("----------------------------------------------------");     

        return getPromotionPiece(team, player);
    }
    
    public void printHistory(ChessBoard board)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("Move history:");
        
        for(int i = 0; i < board.getHistory().getMoveCount(); i++)
        {
            System.out.println((i+1)+": "+ board.getHistory().toString(i));
        }
    }
    
    public void displayEloChange(Player winner, Player loser, double[] eloChanges, int[] newElos) 
    {
        if (winner.getName().contains("Guest ")) 
        {
            System.out.println("CONGRATS TO " + winner.getName());  
        } 
        else 
        {
            System.out.println("CONGRATS TO " + winner.getName());  
        }
        
        if (loser.getName().contains("Guest ")) 
        {
            System.out.println("BETTER LUCK NEXT TIME " + loser.getName()); 
        } 
        else 
        {
            System.out.println("UNFORTUNATLY " + loser.getName() + ", HAS BEEN DEFEATED"); 
        }
        
        System.out.println("\n" + winner.getName() + " elo change: " + (int)eloChanges[0] + " -> " + newElos[0]);
        System.out.println("\n" + loser.getName() + " elo change: " + (int)eloChanges[1] + " -> " + newElos[1]);
        
        System.out.println();
        System.out.println("Login and play more games to rank up!");
    }
    
    public String displayPlayerLogin(Team team, String defaultName) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER " + (team == Team.WHITE ? "1" : "2") + " LOGIN                           (X) TO QUIT");
        System.out.println("Please enter your username or 'Guest' to skip \n(Case sensitive)");
        
        return inputHandler.getStringInput("> ");
    }
    
    public String displayPlayerMove(Player player, Clock clock) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println(player.getTeam().toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.println("Remaining time: " + clock.toString());
        
        return inputHandler.getStringInput(player.getName() + "> ");
    }
   
    public int getClockTimeLimit() 
    {
        System.out.println("CLOCK SETTING                           (X) TO QUIT\n");
        System.out.print("Please enter the time limit for every\nplayer (in minutes, will be rounded)\n");
        
        String userInput = inputHandler.getStringInput("> ").trim(); 
        
        if(userInput.trim().toUpperCase().equals("X"))
        {
            Display.displayExit();
            System.exit(0);
            return 0;
        }
        int t;
        try 
        {
            t = Integer.parseInt(userInput);
        } 
        catch (final NumberFormatException e) 
        {
            System.out.println("Please input a whole number");
            System.out.println("----------------------------------------------------");
            
            return getClockTimeLimit();
        }
        if(t > 300)// max of 300 minutes
        {
            System.out.println("5 hours is the maximum time allowed");
            System.out.println("----------------------------------------------------");
            
            return getClockTimeLimit();
        }
        
        return t;
    }
    
    public String getSaveFileName() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("Please enter the name of the new save file\n(Do not include a file extension):");
        
        return inputHandler.getStringInput("> ");
    }
    
    public void displaySaveResult(boolean success, String fileName) 
    {
        if (!success) 
        {
            System.out.println("\nPlease ensure that you input a valid file name.");
        } 
        else 
        {
            System.out.println("\nSuccesfully saved as '" + fileName + "'");
        }
    }
    
}
