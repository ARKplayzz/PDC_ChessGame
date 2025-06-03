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
import pdc_chessgame.Ranking;

/**
 *
 * @author Andrew
 */
public class LeaderboardPanel extends JPanel {
    
    private final Ranking rankings;
    private final Runnable backCallback;
    
    private final JTextArea leaderboardText;
    private final JScrollPane scrollPane;
    private final JButton backButton;
    
    public LeaderboardPanel(Ranking rankings, Runnable backCallback) 
    {
        this.rankings = rankings;
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
        this.leaderboardText.setLineWrap(true);
        this.leaderboardText.setWrapStyleWord(true);
        this.leaderboardText.setFont(new Font("Helvetica", Font.PLAIN, 16));
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
        this.rankings.getLeaderboard();
        
        StringBuilder content = new StringBuilder();
        String leaderboardString = this.rankings.getLeaderboardString();
        
        if (leaderboardString == null || leaderboardString.trim().isEmpty()) 
        {
            content.append("The leaderboard appears to be empty.\n Play some games to see rankings here!");
        } 
        else 
        {
            content.append(leaderboardString);
            content.append("\n\nPlay more games to improve your ranking!");
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