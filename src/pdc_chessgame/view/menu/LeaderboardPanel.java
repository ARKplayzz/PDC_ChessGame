/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import pdc_chessgame.Database;

/**
 *
 * @author Andrew
 */
public class LeaderboardPanel extends JPanel {
    
    private final Database database;
    private final Runnable backCallback;
    
    private final JTextArea leaderboardText;
    private final JScrollPane scrollPane;
    private final JButton backButton;
    
    public LeaderboardPanel(Database database, Runnable backCallback) 
    {
        this.database = database;
        this.backCallback = backCallback;
        
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
        
        setupButton(backButton);
        
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
        this.leaderboardText.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Use monospaced font for alignment
    }
    
    private void setupScrollPane() 
    {
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setBorder(null);
        this.scrollPane.getViewport().setBackground(new Color(30, 30, 30));
        
        this.scrollPane.getVerticalScrollBar().setBackground(new Color(40, 40, 40));
        this.scrollPane.getHorizontalScrollBar().setBackground(new Color(40, 40, 40));
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
            int fontSize = Math.max(14, Math.min(20, panelWidth / 15));
            this.leaderboardText.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        }
    }
    
    public void refreshLeaderboard() 
    {
        StringBuilder content = new StringBuilder();
        try {
            java.sql.ResultSet rs = database.getLeaderboard();
            int rank = 1;
            boolean hasRows = false;
            // Table header
            content.append(String.format("%-5s %-16s %-6s %-6s %-6s\n", "Rank", "Username", "Elo", "Wins", "Loss"));
            content.append("---------------------------------------------------\n");
            while (rs.next()) {
                hasRows = true;
                String name = rs.getString("name");
                int elo = rs.getInt("elo");
                int won = rs.getInt("games_won");
                int lost = rs.getInt("games_lost");
                // Truncate/pad username for consistent width
                String displayName = name.length() > 16 ? name.substring(0, 16) : name;
                content.append(String.format("%-5d %-16s %-6d %-6d %-6d\n", rank++, displayName, elo, won, lost));
            }
            rs.close();
            if (!hasRows) {
                content.append("The leaderboard appears to be empty.\nPlay some games to see rankings here!");
            } else {
                content.append("\nPlay more games to improve your ranking!");
            }
        } catch (Exception e) {
            content.append("Error loading leaderboard: ").append(e.getMessage());
        }
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