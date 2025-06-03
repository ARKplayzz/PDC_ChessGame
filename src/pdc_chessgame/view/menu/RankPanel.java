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
import pdc_chessgame.Ranking;

/**
 *
 * @author Andrew
 */
public class RankPanel extends JPanel {
    
    private final Ranking rankings;
    private final Runnable backCallback;

    private final JTextField usernameField;
    private final JLabel statusLabel;
    private final JLabel eloLabel;
    private final JLabel instructionLabel;
    private final JButton backButton;
    
    public RankPanel(Ranking rankings, Runnable backCallback) 
    {
        this.rankings = rankings;
        this.backCallback = backCallback;
        
        this.usernameField = new JTextField();
        this.statusLabel = new JLabel();
        this.eloLabel = new JLabel();
        this.instructionLabel = new JLabel();
        this.backButton = new JButton("Back");
        
        initializePanel();
        setupEventHandlers();
        updateInitialStatus();
    }
    
    private void initializePanel() 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        
        JPanel contentPanel = new JPanel(new GridLayout(5, 1, 1, 1));
        contentPanel.setBackground(new Color(30, 30, 30));
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 10));
        
        setupLabels();
        
        setupButton(backButton);
        
        JLabel titleLabel = createTitleLabel();
        
        contentPanel.add(titleLabel);
        contentPanel.add(instructionLabel, BorderLayout.NORTH);
        contentPanel.add(usernameField, BorderLayout.NORTH);
        contentPanel.add(statusLabel, BorderLayout.NORTH);
        contentPanel.add(eloLabel, BorderLayout.NORTH);
        
        add(backButton, BorderLayout.SOUTH);  
        add(contentPanel, BorderLayout.NORTH);
        
    }
    
    private JLabel createTitleLabel() 
    {
        JLabel titleLabel = new JLabel("Rank Checker");
        titleLabel.setForeground(new Color(153, 233, 255));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return titleLabel;
    }
    
    private void setupLabels() 
    {
        // Instruction label
        instructionLabel.setText("Enter a username:");
        instructionLabel.setForeground(new Color(153, 233, 255));
        instructionLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //status label
        statusLabel.setForeground(new Color(153, 233, 225));
        statusLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //Elo label
        eloLabel.setForeground(new Color(255, 255, 255));
        eloLabel.setFont(new Font("Helvetica", Font.BOLD, 100));
        eloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //buttons
        usernameField.setFont(new Font("Helvetica", Font.PLAIN, 16));
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
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
        // Username listener for dynamicness
        usernameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateEloStatus(); 
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateEloStatus(); 
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateEloStatus(); 
            }
        });
        
        //backkk button
        backButton.addActionListener(e -> backCallback.run());
    }
    
    private void updateEloStatus() 
    {
        String username = usernameField.getText().trim();
        
        if (username.isEmpty()) 
        {
            statusLabel.setText("Type a username above");
            eloLabel.setText("?");
        } 
        else if (username.contains(" ")) 
        {
            statusLabel.setText("Username cannot contain spaces");
            eloLabel.setText("?");
        } 
        else if (username.length() < 3) 
        {
            statusLabel.setText("Username too short (minimum 3 characters)");
            eloLabel.setText("?");
        } 
        else if (username.length() > 16) 
        {
            statusLabel.setText("Username too long (maximum 16 characters)");
            eloLabel.setText("?");
        } 
        else if (username.equalsIgnoreCase("guest")) 
        {
            statusLabel.setText("Guest players dontt have saved Elo ratings");
        } 
        else if (rankings.hasPlayed(username)) 
        {
            statusLabel.setText("User found");
            int elo = rankings.getElo(username);
            eloLabel.setText(""+elo);
        } 
    }
    
    private void updateInitialStatus() 
    {
        updateEloStatus();
    }
    
    public void clearInput() 
    {
        usernameField.setText("");
        updateEloStatus();
    }
    
    public void focusInput() 
    {
        usernameField.requestFocusInWindow();
    }
}
