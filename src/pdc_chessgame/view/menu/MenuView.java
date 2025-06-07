/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import pdc_chessgame.ChessGame;
import pdc_chessgame.Database;


/**
 *
 * @author Andrew
 */
public class MenuView extends JPanel {
    
    private final ChessGame controller;
    private final Database database;
    
    // Main menu components
    private final JLabel chessGameTitle;
    private final JPanel mainMenuPanel;
    private final JPanel buttonContainer;
    private final JButton menuStartButton;
    private final JButton menuSaveButton;
    private final JButton menuRankButton;
    private final JButton menuLeaderboardButton;
    private final JButton menuExitButton;
    
    // sub-panels
    private NewGamePanel newGamePanel;
    private LeaderboardPanel leaderboardPanel;
    private RankPanel rankPanel;
    private SavePanel savePanel;
    
    public MenuView(ChessGame controller) 
    {
        this.controller = controller;
        this.database = new Database();
        
        //main menu panels
        this.chessGameTitle = new JLabel("Welcome to Chess");
        this.mainMenuPanel = new JPanel(new BorderLayout());
        this.buttonContainer = new JPanel(new GridLayout(5, 1, 1, 1));
        this.menuStartButton = new JButton("New Game");
        this.menuSaveButton = new JButton("Load Game");
        this.menuRankButton = new JButton("View Ranking");
        this.menuLeaderboardButton = new JButton("View Leaderboard");
        this.menuExitButton = new JButton("Exit");
        
        initializeMainMenu();
        setupEventHandlers();
    }
    
    private void initializeMainMenu() 
    {
        setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());
        
        this.chessGameTitle.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 4));
        this.chessGameTitle.setForeground(new Color(153, 233, 255));
        this.chessGameTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
        this.chessGameTitle.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 20));
        this.chessGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        this.mainMenuPanel.setOpaque(false);
        this.buttonContainer.setOpaque(false);
        
        //setup all buttons
        setupButton(this.menuStartButton);
        setupButton(this.menuSaveButton);
        setupButton(this.menuRankButton);
        setupButton(this.menuLeaderboardButton);
        setupButton(this.menuExitButton);
        
        //add buttons to containers
        JPanel dualButtonGrid = new JPanel(new GridLayout(1, 2, 1, 1));
        dualButtonGrid.setOpaque(false);
        dualButtonGrid.add(this.menuStartButton);
        dualButtonGrid.add(this.menuSaveButton);
        
        this.buttonContainer.add(dualButtonGrid);
        this.buttonContainer.add(this.menuRankButton);
        this.buttonContainer.add(this.menuLeaderboardButton);
        this.buttonContainer.add(this.menuExitButton);
        
        this.mainMenuPanel.add(this.buttonContainer, BorderLayout.NORTH);
        this.add(this.chessGameTitle, BorderLayout.NORTH);
        this.add(this.mainMenuPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private void setupEventHandlers() 
    {
        this.menuStartButton.addActionListener(e -> showNewGamePanel());
        this.menuSaveButton.addActionListener(e -> showSavePanel());
        this.menuRankButton.addActionListener(e -> showRankPanel());
        this.menuLeaderboardButton.addActionListener(e -> showLeaderboardPanel());
        this.menuExitButton.addActionListener(e -> System.exit(0));
    }
    
    private void setupButton(JButton button) 
    {
        Color normalBg = new Color(40, 40, 40);
        Color hoverBg = new Color(50, 50, 50);
        Color textColor = new Color(153, 233, 255);

        button.setBackground(normalBg);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setFont(new Font("Helvetica", Font.BOLD, 16));

        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(normalBg);
            }
        });
    }
    
    private void showNewGamePanel() 
    {
        if (this.newGamePanel == null) 
        {
            // Always pass the controller reference, never null
            this.newGamePanel = new NewGamePanel(this.database, this.controller, this::returnToMainMenu);
        }
        switchToPanel(this.newGamePanel);
    }
    
    private void showLeaderboardPanel() 
    {
        if (this.leaderboardPanel == null) 
        {
            this.leaderboardPanel = new LeaderboardPanel(this.database, this::returnToMainMenu);
        }
        this.leaderboardPanel.refreshLeaderboard();
        switchToPanel(this.leaderboardPanel);
    }
    
    private void showRankPanel() 
    {
        if (this.rankPanel == null) 
        {
            this.rankPanel = new RankPanel(this.database, this::returnToMainMenu);
        }
        switchToPanel(this.rankPanel);
    }
    
    private void showSavePanel()
    {
        if (this.savePanel == null)
        {
            this.savePanel = new SavePanel(this.database, this.controller, this::returnToMainMenu);
        }
        this.savePanel.refreshSaves(); // always refresh on open
        switchToPanel(this.savePanel);
    }
    
    private void switchToPanel(JPanel panel) 
    {
        this.removeAll();
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    public void returnToMainMenu() 
    {
        this.removeAll();
        this.add(this.chessGameTitle, BorderLayout.NORTH);
        this.add(this.mainMenuPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public Database getDatabase() {
        return this.database;
    }
}