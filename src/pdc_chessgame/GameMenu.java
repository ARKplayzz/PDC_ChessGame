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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    
    private JButton menuStartButton = new JButton("New Game");
    private JButton menuSaveButton = new JButton("Load Game");
    private JButton menuRankButton = new JButton("View Ranking");
    private JButton menuLeaderboardButton = new JButton("View Leaderboard");
    private JButton menuExitButton = new JButton("Exit");


    public GameMenu() {
        setBackground(new Color(60, 60, 60));
        setBorder(BorderFactory.createLineBorder(Color.white));
        setLayout(new BorderLayout(10, 10));

        JPanel buttonGrid = new JPanel(new GridLayout(2, 2, 1, 1));
        buttonGrid.setOpaque(false); // transparent background

        Color normalBg = new Color(40, 40, 40);
        Color hoverBg = new Color(50, 50, 50);
        Color textColor = new Color(189, 229, 225);

        setupButton(menuStartButton, normalBg, hoverBg, textColor);
        setupButton(menuSaveButton, normalBg, hoverBg, textColor);
        setupButton(menuRankButton, normalBg, hoverBg, textColor);
        setupButton(menuLeaderboardButton, normalBg, hoverBg, textColor);
        
        menuStartButton.setMargin(new Insets(10, 20, 10, 20));
        menuSaveButton.setMargin(new Insets(10, 20, 10, 20));
        menuRankButton.setMargin(new Insets(10, 20, 10, 20));
        menuLeaderboardButton.setMargin(new Insets(10, 20, 10, 20));

        buttonGrid.add(menuStartButton);
        buttonGrid.add(menuSaveButton);
        buttonGrid.add(menuRankButton);
        buttonGrid.add(menuLeaderboardButton);

        add(buttonGrid, BorderLayout.NORTH);
        setVisible(true);
    }

    private void setupButton(JButton button, Color normalBg, Color hoverBg, Color textColor) {
        button.setBackground(normalBg);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorder(null);

        button.addMouseListener(new MouseAdapter() { //hovering effects
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalBg);
            }
        });
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

        while (selectedOption[0] == null) //waits for input (decreases cpu usage?)
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
        
        this.setVisible(false);
        System.out.println(">>> "+ selectedOption[0] +" <<<");
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