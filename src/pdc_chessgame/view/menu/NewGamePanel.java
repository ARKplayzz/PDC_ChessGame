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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import pdc_chessgame.ChessGame;
import pdc_chessgame.Ranking;
        
/**
 *
 * @author Andrew
 */
public class NewGamePanel extends JPanel {
    
    private final Ranking rankings;
    private final ChessGame controller;
    private final Runnable backCallback;
    
    // UI Components
    private final JTextField timeField;
    private final JTextField username1Field;
    private final JTextField username2Field;
    private final JLabel timeStatus;

    private final JLabel username1Status;
    private final JLabel username1Status2;
    private final JLabel username2Status;
    private final JLabel username2Status2;
    private final JButton startButton;
    private final JButton backButton;
    
    public NewGamePanel(Ranking rankings, ChessGame controller, Runnable backCallback) 
    {
        this.rankings = rankings;
        this.controller = controller;
        this.backCallback = backCallback;
        
        this.timeField = new JTextField("20", 10); // thinner
        this.username1Field = new JTextField(10);  // thinner
        this.username2Field = new JTextField(10);  // thinner

        // Center and bold text in input fields
        Font boldFont = new Font("Helvetica", Font.BOLD, 16);
        this.timeField.setHorizontalAlignment(JTextField.CENTER);
        this.timeField.setFont(boldFont);
        this.username1Field.setHorizontalAlignment(JTextField.CENTER);
        this.username1Field.setFont(boldFont);
        this.username2Field.setHorizontalAlignment(JTextField.CENTER);
        this.username2Field.setFont(boldFont);

        this.username1Status = new JLabel();
        this.username1Status2 = new JLabel();
        this.username2Status = new JLabel();
        this.username2Status2 = new JLabel();
        this.timeStatus = new JLabel();
        this.startButton = new JButton("Start Game");
        this.backButton = new JButton("Back");
        
        initializePanel();
        setupEventHandlers();
        updateInitialStatus();
    }
    
    private void initializePanel() 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        
        //main panel
        JPanel contentPanel = new JPanel(new GridLayout(0, 1, 0, -10));
        contentPanel.setBackground(new Color(30, 30, 30));
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 20));
        
        //status labels
        setupStatusLabels();
        
        //player labels
        JLabel playerOneLabel = createPlayerLabel("Player 1: (White)");
        JLabel playerTwoLabel = createPlayerLabel("Player 2: (Black)");
        JLabel timeLabel = createPlayerLabel("Time per Player:");
        
        setupButtons();
        
        //adding to display panel
        contentPanel.add(playerOneLabel);
        contentPanel.add(username1Status);
        contentPanel.add(username1Status2);
        contentPanel.add(Box.createVerticalStrut(1));
        contentPanel.add(username1Field);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(playerTwoLabel);
        contentPanel.add(username2Status);
        contentPanel.add(username2Status2);
        contentPanel.add(Box.createVerticalStrut(1));
        contentPanel.add(username2Field);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(timeLabel);
        contentPanel.add(timeStatus);
        contentPanel.add(Box.createVerticalStrut(1));
        contentPanel.add(timeField);
        contentPanel.add(Box.createVerticalStrut(10));

        add(contentPanel, BorderLayout.CENTER);

        // Button grid panel (no border, outside contentPanel)
        JPanel buttonGrid = new JPanel(new GridLayout(2, 1, 0, 1)); // set vertical gap to 1 for small gap
        buttonGrid.setBackground(new Color(30, 30, 30));
        buttonGrid.add(startButton);
        buttonGrid.add(backButton);

        add(buttonGrid, BorderLayout.SOUTH);
    }
    
    private void setupStatusLabels() 
    {
        JLabel[] statusLabels = {this.username1Status, this.username2Status, this.username1Status2, this.username2Status2, this.timeStatus};
        
        for (JLabel label : statusLabels) 
        {
            label.setForeground(new Color(153, 233, 255));
            label.setFont(new Font("Helvetica", Font.PLAIN, 14));
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    
    private JLabel createPlayerLabel(String text) 
    {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(153, 233, 255));
        label.setFont(new Font("Helvetica", Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
    
    private void setupButtons() 
    {
        setupButton(startButton);
        setupButton(backButton);
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
        
    private void setupEventHandlers() 
    {
        username1Field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateUserStatus(username1Field, username1Status, username1Status2); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateUserStatus(username1Field, username1Status, username1Status2); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateUserStatus(username1Field, username1Status, username1Status2); 
            }
        });
        
        username2Field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateUserStatus(username2Field, username2Status, username2Status2); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateUserStatus(username2Field, username2Status, username2Status2); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateUserStatus(username2Field, username2Status, username2Status2); 
            }
        });
        
        timeField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateTimeStatus(); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateTimeStatus(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateTimeStatus(); 
            }
        });
        
        //button listeners
        startButton.addActionListener(e -> handleStartGame());
        backButton.addActionListener(e -> backCallback.run());
    }

    
    private void updateUserStatus(JTextField field, JLabel statusLabel, JLabel statusLabel2) 
    {
        String username = field.getText().trim();
        
        if (username.isEmpty()) 
        {
            statusLabel.setText("Please Enter your Username"); 
            statusLabel2.setText("(or enter 'guest' to skip)");
        } 
        else if (username.contains(" ")) 
        {
            statusLabel.setText("Please ensure no spaces");
            statusLabel2.setText("are used within your username");
        } 
        else if (username.length() < 4) 
        {
            statusLabel.setText("Username must be");
            statusLabel2.setText("more than 4 Characters");
        } 
        else if (username.length() > 16) 
        {
            statusLabel.setText("Username must be");
            statusLabel2.setText("less than 16 Characters");
        } 
        else if (username.toUpperCase().equals("GUEST")) 
        {
            statusLabel.setText("Playing as a Guest");
            statusLabel2.setText("(Game Scores will not be Saved)");
        } 
        else if (rankings.hasPlayed(username)) 
        {
            statusLabel.setText("Welcome back, " + username + "!");
            statusLabel2.setText("You currently have " + rankings.getElo(username) + " Elo");
        } 
        else 
        {
            statusLabel.setText(username + " has not played before");
            statusLabel2.setText("(A new user will be created)");
        }
    }
    
    private void updateTimeStatus() 
    {
        String timeText = timeField.getText().trim();
        
        if (timeText.isEmpty()) 
        {
            timeStatus.setText("Please enter time in minutes");
        } 
        else 
        {
            try 
            {
                int time = Integer.parseInt(timeText);
                if (time <= 0) 
                {
                    timeStatus.setText("Time must be greater than 0");
                } 
                
                else if (time > 120) 
                {
                    timeStatus.setText("Maximum 2 hours (120 minutes)");
                } 
                else 
                {
                    timeStatus.setText(String.format("Game time: "+time+" minutes per player"));
                }
            } 
            catch (NumberFormatException e) 
            {
                timeStatus.setText("Please enter a valid number");
            }
        }
    }
    
    private void handleStartGame() 
    {
        String user1 = username1Field.getText().trim();
        String user2 = username2Field.getText().trim();
        String timeText = timeField.getText().trim();

        // Username validation
        if (user1.isEmpty() || user2.isEmpty()) {
            showWarning("Two players are required.");
            return;
        }
        if (user1.equals(user2)) {
            showWarning("You can't play against yourself! Try using a 'Guest'.");
            return;
        }
        if (user1.contains(" ")) {
            showWarning("Player 1 username cannot contain spaces.");
            return;
        }
        if (user2.contains(" ")) {
            showWarning("Player 2 username cannot contain spaces.");
            return;
        }
        if (user1.length() < 4) {
            showWarning("Player 1 username must be at least 4 characters.");
            return;
        }
        if (user2.length() < 4) {
            showWarning("Player 2 username must be at least 4 characters.");
            return;
        }
        if (user1.length() > 16) {
            showWarning("Player 1 username must be less than 17 characters.");
            return;
        }
        if (user2.length() > 16) {
            showWarning("Player 2 username must be less than 17 characters.");
            return;
        }
        if (user1.equalsIgnoreCase("guest") && user2.equalsIgnoreCase("guest")) {
            showWarning("At least one player must use a non-guest username.");
            return;
        }

        // Time validation
        int time;
        if (timeText.isEmpty()) 
        {
            showWarning("Please enter a time limit.");
            return;
        }
        try 
        {
            time = Integer.parseInt(timeText);
            if (time <= 0) 
            {
                showWarning("Time must be greater than 0 mins.");
                return;
            }
            if (time > 120) 
            {
                showWarning("Maximum 2 hours (120 minutes) allowed.");
                return;
            }
        } 
        catch (NumberFormatException e) 
        {
            showWarning("Please enter a valid number for time.");
            return;
        }

        // All checks passed, start game
        this.controller.createGame(user1, user2, time);
        clearInputs(); // Clear input fields after starting a game
        backCallback.run();
    }
    
    private void updateInitialStatus() 
    {
        updateUserStatus(username1Field, username1Status, username1Status2);
        updateUserStatus(username2Field, username2Status, username2Status2);
        updateTimeStatus();
    }
    
    private void clearInputs() 
    {
        username1Field.setText("");
        username2Field.setText("");
        timeField.setText("20");
        updateInitialStatus();
    }
    
    private void showWarning(String msg) 
    {
        javax.swing.JOptionPane.showMessageDialog(this, msg, "Warning", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
    }
}
