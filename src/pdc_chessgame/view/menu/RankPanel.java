/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import pdc_chessgame.Database;
import pdc_chessgame.view.GraphicsUtil;

/**
 *
 * @author Andrew
 */
// JPanel used for the rank screen
public class RankPanel extends JPanel 
{
    private final Runnable backCallback;
    
    private Database database;

    private final JTextField usernameField;
    private final JLabel statusLabel;
    private final JLabel eloLabel;
    private final JLabel instructionLabel;
    private final JButton backButton;
    
    public RankPanel(Database database, Runnable backCallback) 
    {
        this.backCallback = backCallback;
        this.database = database;
        
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

        // use BoxLayout for vertical stacking at the top
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new javax.swing.BoxLayout(contentPanel, javax.swing.BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(30, 30, 30));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(18, 38, 8, 38)); // decreased by 2px

        setupLabels();
        GraphicsUtil.setupButton(this.backButton);

        JLabel titleLabel = createTitleLabel();

        // set max sizes to prevent stretching
        titleLabel.setMaximumSize(new java.awt.Dimension(400, 40));
        this.instructionLabel.setMaximumSize(new java.awt.Dimension(400, 30));
        this.usernameField.setMaximumSize(new java.awt.Dimension(400, 30));
        this.statusLabel.setMaximumSize(new java.awt.Dimension(400, 25));
        this.eloLabel.setMaximumSize(new java.awt.Dimension(400, 100));

        // Center alignment for all
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.instructionLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.usernameField.setAlignmentX(CENTER_ALIGNMENT);
        this.statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        this.eloLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Place boxes
        contentPanel.add(titleLabel);
        contentPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
        contentPanel.add(instructionLabel);
        contentPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        contentPanel.add(usernameField);
        contentPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
        contentPanel.add(statusLabel);
        contentPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 10)));
        contentPanel.add(eloLabel);
        contentPanel.add(javax.swing.Box.createVerticalGlue());

        add(contentPanel, BorderLayout.NORTH);
        add(this.backButton, BorderLayout.SOUTH);  
    }
    
    private JLabel createTitleLabel() 
    {
        JLabel titleLabel = new JLabel("Elo Checker");
        titleLabel.setForeground(new Color(153, 233, 255));
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return titleLabel;
    }
    
    private void setupLabels() 
    {
        // Instruction label
        this.instructionLabel.setText("Enter a username:");
        this.instructionLabel.setForeground(new Color(153, 233, 255));
        this.instructionLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        this.instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //status label
        this.statusLabel.setForeground(new Color(153, 233, 225));
        this.statusLabel.setFont(new Font("Helvetica", Font.PLAIN, 14));
        this.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //Elo label
        this.eloLabel.setForeground(new Color(255, 255, 255));
        this.eloLabel.setFont(new Font("Helvetica", Font.BOLD, 90));
        this.eloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // user input
        this.usernameField.setFont(new Font("Helvetica", Font.BOLD, 16));
        this.usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        this.usernameField.setHorizontalAlignment(JTextField.CENTER);
    }
    
    private void setupEventHandlers() 
    {
        // Username listener for dynamicness
        this.usernameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() 
        {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateEloStatus(); 
            }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateEloStatus(); 
            }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) 
            { 
                updateEloStatus(); 
            }
        });
        
        //backkk button
        this.backButton.addActionListener(e -> backCallback.run());
    }
    
    private void updateEloStatus() 
    {
        String username = this.usernameField.getText().trim();
        
        if (username.isEmpty()) 
        {
            this.statusLabel.setText("Type a username above");
            this.eloLabel.setText("?");
        } 
        else if (username.contains(" ")) 
        {
            this.statusLabel.setText("Invalid Username (no spaces)");
            this.eloLabel.setText("?");
        } 
        else if (username.length() < 3) 
        {
            this.statusLabel.setText("Username too short");
            this.eloLabel.setText("?");
        } 
        else if (username.length() > 16) 
        {
            this.statusLabel.setText("Username too long");
            this.eloLabel.setText("?");
        } 
        else if (username.equalsIgnoreCase("guest")) 
        {
            this.statusLabel.setText("Guest's don't save Elo");
            this.eloLabel.setText("?");
        } 
        else if (database.playerExists(username)) 
        {
            int elo = database.getElo(username);
            this.statusLabel.setText("User found: " + username);
            this.eloLabel.setText(String.valueOf(elo));
        } 
        else 
        {
            this.statusLabel.setText("Player not found");
            this.eloLabel.setText("?");
        }
    }
    
    private void updateInitialStatus() 
    {
        updateEloStatus();
    }
    
    public void clearInput() 
    {
        this.usernameField.setText("");
        updateEloStatus();
    }
    
    public void focusInput() 
    {
        this.usernameField.requestFocusInWindow();
    }
}
