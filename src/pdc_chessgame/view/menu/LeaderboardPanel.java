/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import pdc_chessgame.Database;
import pdc_chessgame.view.GraphicsUtil;

/**
 *
 * @author Andrew & Finlay
 */

// Panel used for displaying the leaderboard
public class LeaderboardPanel extends JPanel 
{
    private final Runnable backCallback;
    // all database instances are inherited down from the one in ChessGame.java
    private Database database;
    
    private final JTextArea leaderboardText;
    private final JScrollPane scrollPane;
    private final JButton backButton;
    
    @SuppressWarnings("OverridableMethodCallInConstructor")
    public LeaderboardPanel(Database database, Runnable backCallback) 
    {
        this.backCallback = backCallback;
        this.database = database;
        this.leaderboardText = new JTextArea();
        this.scrollPane = new JScrollPane(leaderboardText);
        this.backButton = new JButton("Back");
        
        initializePanel();
        setupEventHandlers();
        refreshLeaderboard();
    }
    
    private void initializePanel() 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        
        setupLeaderboardText();
        
        setupScrollPane();
        
        GraphicsUtil.setupButton(backButton);
        
        add(this.scrollPane, BorderLayout.CENTER);
        add(this.backButton, BorderLayout.SOUTH);
    }
    
    private void setupLeaderboardText() 
    {
        this.leaderboardText.setEditable(false);
        this.leaderboardText.setBackground(new Color(30, 30, 30));
        this.leaderboardText.setForeground(new Color(153, 233, 255));
        this.leaderboardText.setLineWrap(false); // Disable line wrap for table formatting
        this.leaderboardText.setWrapStyleWord(false);
        // Use a monospaced font for perfect alignment
        this.leaderboardText.setFont(new Font("Consolas", Font.PLAIN, 14));
    }
    
    // Make the leaderboard scrollable
    private void setupScrollPane() 
    {
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setBorder(null);
        this.scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        
        this.scrollPane.getVerticalScrollBar().setBackground(new Color(40, 40, 40));
        this.scrollPane.getHorizontalScrollBar().setBackground(new Color(40, 40, 40));
    }
        
    private void setupEventHandlers() 
    {
        // Back button listener
        this.backButton.addActionListener(e -> backCallback.run());
        
        // Dynamic text scaling based on panel size
        addComponentListener(new ComponentAdapter() 
        {
            @Override
            public void componentResized(ComponentEvent e) 
            {
                updateTextSize();
            }
        });
    }
    
    private void updateTextSize() 
    {
        int panelWidth = getWidth();
        if (panelWidth > 0) 
        {
            // Make text scale slightly smaller on average
            int fontSize = Math.max(12, Math.min(18, panelWidth / 22));
            this.leaderboardText.setFont(new Font("Consolas", Font.PLAIN, fontSize));
        }
    }
    
    // grab the leaderboard info from the database
    public void refreshLeaderboard() 
    {
        StringBuilder content = this.database.getLeaderboard();
        this.leaderboardText.setText(content.toString());
        this.leaderboardText.setCaretPosition(0); // Scroll to top
    }
    
    public void updateLeaderboard() 
    {
        refreshLeaderboard();
        revalidate();
        repaint();
    }
}