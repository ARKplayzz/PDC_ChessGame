package pdc_chessgame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.HashMap;
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
    LOAD_SAVE,
    EXIT
}

public class GameMenu 
{
   private Scanner scanner;
    
    public GameMenu() 
    {
        this.scanner = new Scanner(System.in);
    }
    
    public MenuOption displayMenu(Ranking rankings, SaveManager loader, HashMap<Team, Player> players) 
    {
        System.out.println("CHESS MENU                               (X) TO QUIT\n");
        System.out.println("WHAT YOU WOULD LIKE TO DO?");
        System.out.println("  <Start a game>          =  'START',");
        System.out.println("  <Load a game>           =  'LOAD',");
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
        else if (userInput.equals("LOAD")) 
        {
            this.displayLoadGame(loader, players);
            return MenuOption.LOAD_SAVE;
        } 
        else if (userInput.equals("X")) 
        {
            return MenuOption.EXIT;
        } 
        else
        {
            System.out.println("----------------------------------------------------");
            System.out.println("Invalid option. Please try again.");
            System.out.println("----------------------------------------------------");  
        }   

        return displayMenu(rankings, loader, players);
    }
    
    private void displayLoadGame(SaveManager loader, HashMap<Team, Player> players)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("LOAD GAME");
        System.out.println("Please enter the name of the save file you wish to load\n(Case sensitive):");
        
        System.out.print("> ");
        String file = scanner.nextLine();
        
        if(!loader.LoadGameFromFile(file, players))
        {
            System.out.println("Please make sure you entered the name of an existing\nsave file.");
        }
        else
        {
            System.out.println("\nThank you, loading save file now");
        }
        System.out.println("----------------------------------------------------");
    }
    
    private void displayRankings(Ranking rankings) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER RANKINGS");
        System.out.println("Please enter your username to check your rank (Case sensitive):");
        
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