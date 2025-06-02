/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;
import pdc_chessgame.ChessGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import pdc_chessgame.Clock;
import pdc_chessgame.GameManager;
import pdc_chessgame.Player;
import pdc_chessgame.Ranking;
import pdc_chessgame.SaveManager;
import pdc_chessgame.Team;


/**
 *
 * @author ARKen
 */
public class MenuView extends JPanel {
    
    private ChessGame controller;
    private Ranking rankings = new Ranking();
    
    JPanel leaderBoardPanel = new JPanel(new BorderLayout());
    JPanel mainMenuPanel = new JPanel(new BorderLayout());
    
    JPanel dualButtonGrid = new JPanel(new GridLayout(1, 2, 1, 1));
    JPanel rowButtonGrid = new JPanel(new GridLayout(4, 1, 1, 1)); //4 rows
    
    private JButton menuStartButton = new JButton("New Game");
    private JButton menuSaveButton = new JButton("Load Game");
    private JButton menuRankButton = new JButton("View Ranking");
    private JButton menuLeaderboardButton = new JButton("View Leaderboard");
    private JButton menuExitButton = new JButton("Exit");
    private JButton escapeButton = new JButton("Back");

    private JPanel newGamePanel = null;
    private JTextField username1Field = null;
    private JTextField username2Field = null;
    private JLabel username1Status = null;
    private JLabel username2Status = null;
    

    public MenuView(ChessGame controller) 
    {
        this.controller = controller;
        
        setBackground(new Color(60, 60, 60));
        setLayout(new BorderLayout(10, 10));
        
        mainMenuPanel.setOpaque(false);
        leaderBoardPanel.setOpaque(false);
        dualButtonGrid.setOpaque(false); // transparent background
        rowButtonGrid.setOpaque(false); // transparent background\

        Color normalBg = new Color(40, 40, 40);
        Color hoverBg = new Color(50, 50, 50);
        Color textColor = new Color(189, 229, 225);

        setupButton(menuStartButton, normalBg, hoverBg, textColor);
        setupButton(menuSaveButton, normalBg, hoverBg, textColor);
        setupButton(menuRankButton, normalBg, hoverBg, textColor);
        setupButton(menuLeaderboardButton, normalBg, hoverBg, textColor);
        setupButton(menuExitButton, normalBg, hoverBg, textColor);
        setupButton(escapeButton, normalBg, hoverBg, textColor);
        
        menuStartButton.addActionListener(e -> getNewGamePanel());

        //menuSaveButton.addActionListener(e -> displayLoadGame(loader, players));
        menuRankButton.addActionListener(e -> displayRankings(rankings));
        menuLeaderboardButton.addActionListener(e -> displayLeaderboard(rankings));
        
        menuExitButton.addActionListener(e -> System.exit(0));
                
        escapeButton.addActionListener(e -> openMenu());

        rowButtonGrid.add(dualButtonGrid);
        
        dualButtonGrid.add(menuStartButton);
        dualButtonGrid.add(menuSaveButton);
        rowButtonGrid.add(menuRankButton);
        rowButtonGrid.add(menuLeaderboardButton);
        rowButtonGrid.add(menuExitButton);
        
        leaderBoardPanel.add(escapeButton, BorderLayout.NORTH);

        mainMenuPanel.add(rowButtonGrid, BorderLayout.NORTH);
        this.add(mainMenuPanel, BorderLayout.NORTH);
        
        setVisible(true);
    }
    
    private void openMenu()
    {
        this.removeAll();
        this.add(this.mainMenuPanel, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
    }

    private void setupButton(JButton button, Color normalBg, Color hoverBg, Color textColor) {
        button.setBackground(normalBg);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        //button.setBorder(null);
        button.setMargin(new Insets(2, 2, 2, 2));
        button.setFont(new Font("HelveticaNeue", Font.PLAIN, 16));

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
    
    private void getNewGamePanel() 
    {
        if (newGamePanel != null) 
        {
            // there is already a current game
            return;
        }
        newGamePanel = new JPanel();
        newGamePanel.setLayout(new GridLayout(0, 1, 5, 5));
        newGamePanel.setBackground(new Color(50, 50, 50));

        username1Field = new JTextField();
        username2Field = new JTextField();
        username1Status = new JLabel(" ");
        username2Status = new JLabel(" ");

        username1Status.setForeground(new Color(189, 229, 225));
        username2Status.setForeground(new Color(189, 229, 225));

        username1Field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(username1Field, username1Status); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(username1Field, username1Status); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(username1Field, username1Status); }
        });
        username2Field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(username2Field, username2Status); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(username2Field, username2Status); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateUserStatus(username2Field, username2Status); }
        });

        newGamePanel.add(new JLabel("Player 1"));
        newGamePanel.add(username1Field);
        newGamePanel.add(username1Status);
        newGamePanel.add(new JLabel("Player 2"));
        newGamePanel.add(username2Field);
        newGamePanel.add(username2Status);

        JButton startButton = new JButton("Start Game");
        setupButton(startButton, new Color(40, 40, 40), new Color(50, 50, 50), new Color(189, 229, 225));
        
        startButton.addActionListener(e -> 
        {
            String user1 = username1Field.getText().trim();
            String user2 = username2Field.getText().trim();
            
            if (user1.isEmpty() || user2.isEmpty()) 
            {
                showWarning("Two usernames are required.");
                return;
            }
            
            int time = 200; //slows down call time (should be ms)
            
            this.controller.createGame(user1, user2, time); //hmmmm
            
            // Hide panel after starting
            mainMenuPanel.remove(newGamePanel);
            newGamePanel = null;
            mainMenuPanel.revalidate();
            mainMenuPanel.repaint();
        });
        newGamePanel.add(startButton);

        // Insert newGamePanel between dualButtonGrid and menuRankButton
        mainMenuPanel.add(newGamePanel, BorderLayout.CENTER);
        mainMenuPanel.revalidate();
        mainMenuPanel.repaint();

        // Initial status update
        updateUserStatus(username1Field, username1Status);
        updateUserStatus(username2Field, username2Status);
    }

    private void updateUserStatus(JTextField field, JLabel statusLabel) {
        String username = field.getText().trim();
        if (username.isEmpty()) 
        {
            statusLabel.setText(""); //NEED TO ADD TEMP TEXT HERE
            return;
        }
        if (rankings.hasPlayed(username)) 
        {
            statusLabel.setText(username +" has: " + rankings.getElo(username) + " Elo");
        } 
        else 
        {
            statusLabel.setText(username +" has not played before (this will create a new user with 100 Elo)");
        }
    }
    private void displayLoadGame(SaveManager loader, HashMap<Team, Player> players) 
    {
        String file = JOptionPane.showInputDialog(
            this,
            "Please enter the name of the save file to load\n(Case sensitive / do not include a file extension):",
            "Load Game",
            JOptionPane.PLAIN_MESSAGE
        );

        if (file == null) return; // User cancelled

        if (file.contains(".")) {
            JOptionPane.showMessageDialog(
                this,
                "Please do not enter anything that could be interpreted as a file extension.",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (file.equalsIgnoreCase("X")) {
            Display.displayExit();
            System.exit(0);
            return;
        }

        if (!loader.LoadGameFromFile(file, players)) {
            JOptionPane.showMessageDialog(
                this,
                "Please make sure you entered the name of an existing save file.",
                "File Not Found",
                JOptionPane.ERROR_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Thank you, loading save file now.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    

    private void displayRankings(Ranking rankings) 
    {
        String username = JOptionPane.showInputDialog(
            this,
            "Please enter your username to check your rank (Case sensitive):",
            "Player Rankings",
            JOptionPane.PLAIN_MESSAGE
        );

        if (username == null) return; // User cancelled

        String message;
        if (rankings.hasPlayed(username)) {
            message = username + " has an Elo rating of " + rankings.getElo(username);
        } else {
            message = "Player not found. New players will start with 100 Elo.";
        }

        JOptionPane.showMessageDialog(
            this,
            message,
            "Player Rankings",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void displayLeaderboard(Ranking rankings) 
    {
        rankings.getLeaderboard();
       
        StringBuilder sb = new StringBuilder();
        String lb = rankings.getLeaderboardString();
        if (lb == "") 
        {
            sb.append("The leaderboard appears to be empty, you should play some games to fill it in.\n");
        }
        sb.append(lb);

        JTextArea leaderboardText = new JTextArea(sb.toString());
        leaderboardText.setEditable(false);
        leaderboardText.setBackground(new Color(60, 60, 60));
        leaderboardText.setForeground(new Color(189, 229, 225));
                
        leaderboardText.setLineWrap(true);
        leaderboardText.setWrapStyleWord(true); 

        JScrollPane scrollPane = new JScrollPane(leaderboardText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        leaderBoardPanel.add(scrollPane, BorderLayout.CENTER);
        leaderBoardPanel.add(escapeButton, BorderLayout.SOUTH);
        
        //scaling text
        leaderBoardPanel.addComponentListener(new ComponentAdapter() 
        {
            @Override
            public void componentResized(ComponentEvent e) 
            {
                int panelWidth = leaderBoardPanel.getWidth();
                int fontSize = Math.max(15, Math.min(24, panelWidth / 15));
                leaderboardText.setFont(new Font("HelveticaNeue", Font.PLAIN, fontSize));
            }
        });

        this.removeAll();
        this.add(leaderBoardPanel, BorderLayout.CENTER); // Changed from NORTH to CENTER

        this.revalidate();
        this.repaint();
    }

    
    
    private void playerLogin(Player player) 
    {
        while (true) {
            String userInput = javax.swing.JOptionPane.showInputDialog(
                this,
                "Enter username for " + player.getTeam() + " (max 16 chars, no '$', not 'BLACK'/'WHITE', 'X' to exit):",
                "Player Login",
                javax.swing.JOptionPane.PLAIN_MESSAGE
            );

            if (userInput == null) return; // Cancelled

            if (userInput.length() > 16) {
                showWarning("Please keep your username within 16 characters long");
                continue;
            }
            if (userInput.contains("$")) {
                showWarning("Please do not include '$' in your name");
                continue;
            }
            if (userInput.equalsIgnoreCase("BLACK") || userInput.equalsIgnoreCase("WHITE")) {
                showWarning("Please refrain from using team names as logins");
                continue;
            }
            if (userInput.equalsIgnoreCase("X")) {
                Display.displayExit();
                System.exit(0);
                return;
            }
            if (userInput.equalsIgnoreCase("GUEST")) {
                showInfo("PROCEEDING AS: " + player.getName());
                return;
            }

            String message;
            if (!this.leaderboard.hasPlayed(userInput)) {
                message = "This account has not played before. By continuing, you will be granted with a base rank of 100 Elo";
            } else {
                message = "This account currently has " + this.leaderboard.getElo(userInput) + " Elo";
            }

            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "YOU HAVE SELECTED [" + userInput + "]\n" + message + "\nConfirm this selection?",
                "Confirm Username",
                javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                player.setName(userInput);
                if (!this.leaderboard.hasPlayed(userInput))
                    this.leaderboard.isNewUser(userInput);
                showInfo("PROCEEDING AS: " + player.getName());
                return;
            }
        }
    }
    
    
    //need to include clock too

    private void showWarning(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg, "Warning", javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    private void showInfo(String msg) {
        javax.swing.JOptionPane.showMessageDialog(this, msg, "Info", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
}
