package pdc_chessgame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;

/**
 *
 * @author Andrew & Finlay
 */

enum MenuOption 
{
    START_GAME,
    VIEW_RANK,
    VIEW_LEADERBOARD,
    EXIT
}

public class GameMenu 
{
   private Scanner scanner;
    
    public GameMenu() 
    {
        this.scanner = new Scanner(System.in);
    }
    
    public MenuOption displayMenu(Ranking rankings) 
    {
        System.out.println("CHESS MENU                               (X) TO QUIT\n");
        System.out.println("WHAT YOU WOULD LIKE TO DO?");
        System.out.println("  <Start a game>          =  'START',");
        System.out.println("  <Check your ranking>    =  'RANK'");
        System.out.println("  <View the leaderboard>  =  'LEADERBOARD',");

        System.out.print("> ");
        String userInput = scanner.nextLine().toUpperCase();

        if (userInput.equals("START")) 
        {
            return MenuOption.START_GAME;
        } 
        else if (userInput.equals("RANK")) 
        {
            this.displayRankings(rankings);
        } 
        else if (userInput.equals("LEADERBOARD")) 
        {
            this.displayLeaderboard(rankings);
        } 
        else if (userInput.equals("X")) 
        {
            return MenuOption.EXIT;
        } 

        System.out.println("----------------------------------------------------");
        System.out.println("Invalid option. Please try again.");
        System.out.println("----------------------------------------------------");     

        return displayMenu(rankings);
    }
    
    private void displayRankings(Ranking rankings) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER RANKINGS");
        System.out.println("Please enter your username to check your rank:");
        
        System.out.print("> ");
        String username = scanner.nextLine();
        
        if(rankings.hasPlayed(username)) 
        {
            System.out.println(username + " has an Elo rating of " + rankings.getElo(username));
        } else 
        {
            System.out.println("Player not found. New players will start with 100 Elo.");
        }
        
        System.out.println("----------------------------------------------------");
    }
    
    private void displayLeaderboard(Ranking rankings) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("CHESS LEADERBOARD");
        
        if(rankings.isLeaderboardEmpty())
            System.out.println("The leaderboard appears to be empty, \nyou should play some games to fill it in.");
        rankings.printLeaderboard(); 
        
        System.out.println("----------------------------------------------------");
    }
}