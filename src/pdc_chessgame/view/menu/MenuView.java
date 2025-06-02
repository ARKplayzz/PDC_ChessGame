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
import pdc_chessgame.Ranking;


/**
 *
 * @author ARKen
 */
public class MenuView extends JPanel {
    
    private final ChessGame controller;
    private final Ranking rankings;
    
    // Main menu components
    private final JLabel chessGameTitle;
    private final JPanel mainMenuPanel;
    private final JPanel buttonContainer;
    private final JButton menuStartButton;
    private final JButton menuSaveButton;
    private final JButton menuRankButton;
    private final JButton menuLeaderboardButton;
    private final JButton menuExitButton;
    
    // Sub-panels
    private NewGamePanel newGamePanel;
    private LeaderboardPanel leaderboardPanel;
    private RankPanel rankPanel;
    
    public MenuView(ChessGame controller) 
    {
        this.controller = controller;
        this.rankings = new Ranking();
        
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
        
        chessGameTitle.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 4));
        chessGameTitle.setForeground(new Color(153, 233, 255));
        chessGameTitle.setFont(new Font("Helvetica", Font.BOLD, 20));
        chessGameTitle.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 20));
        chessGameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        mainMenuPanel.setOpaque(false);
        buttonContainer.setOpaque(false);
        
        //setup all buttons
        setupButton(menuStartButton);
        setupButton(menuSaveButton);
        setupButton(menuRankButton);
        setupButton(menuLeaderboardButton);
        setupButton(menuExitButton);
        
        //add buttons to containers
        JPanel dualButtonGrid = new JPanel(new GridLayout(1, 2, 1, 1));
        dualButtonGrid.setOpaque(false);
        dualButtonGrid.add(menuStartButton);
        dualButtonGrid.add(menuSaveButton);
        
        buttonContainer.add(dualButtonGrid);
        buttonContainer.add(menuRankButton);
        buttonContainer.add(menuLeaderboardButton);
        buttonContainer.add(menuExitButton);
        
        mainMenuPanel.add(buttonContainer, BorderLayout.NORTH);
        this.add(chessGameTitle, BorderLayout.NORTH);
        this.add(mainMenuPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private void setupEventHandlers() 
    {
        menuStartButton.addActionListener(e -> showNewGamePanel());
        menuRankButton.addActionListener(e -> showRankPanel());
        menuLeaderboardButton.addActionListener(e -> showLeaderboardPanel());
        menuExitButton.addActionListener(e -> System.exit(0));
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
        if (newGamePanel == null) 
        {
            newGamePanel = new NewGamePanel(rankings, controller, this::returnToMainMenu);
        }
        switchToPanel(newGamePanel);
    }
    
    private void showLeaderboardPanel() 
    {
        if (leaderboardPanel == null) 
        {
            leaderboardPanel = new LeaderboardPanel(rankings, this::returnToMainMenu);
        }
        leaderboardPanel.refreshLeaderboard();
        switchToPanel(leaderboardPanel);
    }
    
    private void showRankPanel() 
    {
        if (rankPanel == null) 
        {
            rankPanel = new RankPanel(rankings, this::returnToMainMenu);
        }
        switchToPanel(rankPanel);
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
        this.add(chessGameTitle, BorderLayout.NORTH);
        this.add(mainMenuPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}