package pdc_chessgame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class GameMenu extends JPanel
{
    private Scanner scanner;
    
    private JButton menuStartButton = new JButton("Start Game");
    private JButton menuExitButton = new JButton("Exit");
    private JButton menuRankButton = new JButton("View Ranking");
    private JButton menuLeaderboardButton = new JButton("View Leaderboard");
    private JButton menuSaveButton = new JButton("Save Game");

    public GameMenu() 
    {

        
        //this.setLayout(new GridLayout(5, 1, 10, 10));
        this.setBackground(new Color(60, 60, 60));
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setBounds(0, 0, 200, 220);

        menuStartButton.setBounds(0, 0, 20, 20);
        menuStartButton.setText("Start");
        menuStartButton.setVisible(true);
        
        menuExitButton.setBounds(0, 0, 20, 20);
        menuExitButton.setVisible(true);
        
        menuRankButton.setBounds(0, 0, 20, 20);
        menuRankButton.setVisible(true);
        
        menuLeaderboardButton.setBounds(0, 0, 20, 20);
        menuLeaderboardButton.setVisible(true);
        
        menuSaveButton.setBounds(0, 0, 20, 20);
        menuSaveButton.setVisible(true);
        
        //testing reference
            int y = 10;
            int height = 30;
            int gap = 10;

            menuStartButton.setBounds(10, y, 180, height);
            y += height + gap;
            menuSaveButton.setBounds(10, y, 180, height);
            y += height + gap;
            menuRankButton.setBounds(10, y, 180, height);
            y += height + gap;
            menuLeaderboardButton.setBounds(10, y, 180, height);
            y += height + gap;
            menuExitButton.setBounds(10, y, 180, height);
        
        this.add(menuStartButton);
        this.add(menuExitButton);
        this.add(menuRankButton);
        this.add(menuLeaderboardButton);
        this.add(menuSaveButton);
        this.setVisible(true);

    }
    
    
    public MenuOption displayMenu(Ranking rankings, SaveManager loader, HashMap<Team, Player> players) 
    {
        this.setVisible(true);

        final MenuOption[] selectedOption = new MenuOption[1]; //chekc this is fine
                
        menuStartButton.addActionListener(e -> 
        {
            selectedOption[0] = MenuOption.START_GAME;

        });

        menuSaveButton.addActionListener(e -> 
        {
            selectedOption[0] = MenuOption.LOAD_SAVE;
        });

        menuRankButton.addActionListener(e -> 
        {
            displayRankings(rankings);
        });

        menuLeaderboardButton.addActionListener(e -> 
        {
            displayLeaderboard(rankings);
        });

        menuExitButton.addActionListener(e -> 
        {
            selectedOption[0] = MenuOption.EXIT;
        });

        while (selectedOption == null) //waits for input (decreases cpu usage?)
        {
            try 
            {
                Thread.sleep(100);
            } 
            catch (InterruptedException e) 
            {
                Thread.currentThread().interrupt();
            }
        }
        
        //this.setVisible(false);

        return selectedOption[0];
    }
    
    private boolean displayLoadGame(SaveManager loader, HashMap<Team, Player> players)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("LOAD GAME                                (X) TO QUIT");
        System.out.println("Please enter the name of the save file to load\n(Case sensitive / do not include a file extension):");
        
        System.out.print("> ");
        String file = scanner.nextLine();
        
        if(file.contains("."))
        {
            System.out.println("Please do not enter anything that could be\ninterpreted a a file extension");
            System.out.println("----------------------------------------------------");
            return false;
        }
        
        if(file.toUpperCase().equals("X"))
        {
            Display.displayExit();
            System.exit(0);
            return false;
        }
        
        if(!loader.LoadGameFromFile(file, players))
        {
            System.out.println("Please make sure you entered the name of an existing\nsave file.");
            System.out.println("----------------------------------------------------");
            return false;
        }
        else
        {
            System.out.println("\nThank you, loading save file now");
            System.out.println("----------------------------------------------------");
            return true;
        }
    }
    
    private void displayRankings(Ranking rankings) 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER RANKINGS");
        System.out.println("Please enter your username to check your rank \n(Case sensitive):");
        
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