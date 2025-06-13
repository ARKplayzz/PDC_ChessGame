/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.manager;

import java.awt.*;
import javax.swing.*;
import pdc_chessgame.view.ControllerManagerActions;

/**
 *
 * @author Andrew
 */

public class GamePanel extends JPanel 
{
    private JTextArea moveHistoryArea;
    private JLabel currentPlayerLabel;
    private JLabel currentPlayerClockLabel;
    private JLabel teamLabel;
    private JButton helpButton, undoButton, resignButton, saveQuitButton;
    private JPanel rowButtonGrid, rowPanelGrid;
    private JScrollPane scrollPane;

    public GamePanel(ControllerManagerActions controller) 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        teamLabel = new JLabel("WHITE");
        teamLabel.setForeground(Color.WHITE);
        teamLabel.setOpaque(false);
        teamLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        teamLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentPlayerLabel = new JLabel("White's Move");
        currentPlayerLabel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 4));
        currentPlayerLabel.setForeground(new Color(153, 233, 255));
        currentPlayerLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentPlayerClockLabel = new JLabel("Time: ");
        currentPlayerClockLabel.setForeground(new Color(189, 229, 225));
        currentPlayerClockLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        currentPlayerClockLabel.setHorizontalAlignment(SwingConstants.CENTER);

        helpButton = new JButton("Help");
        undoButton = new JButton("Undo Move");
        resignButton = new JButton("Resign");
        saveQuitButton = new JButton("Save & Quit");
        
        setupButton(helpButton);
        setupButton(undoButton);
        setupButton(resignButton);
        setupButton(saveQuitButton);

        helpButton.addActionListener(e -> {
            moveHistoryArea.append(
                "\nChess Help:\n" +
                "Click on a piece to select it. Possible moves will be shown as green tiles.\n" +
                "Click one of these green tiles to move your piece.\n" +
                "If you make a mistake, you can use the Undo button (as long as your opponent agrees).\n" +
                "Press Resign to give up, or Save & Quit to save the game (you can continue it later).\n"
            );
            moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
        });

        undoButton.addActionListener(e -> controller.currentGameUndo());
        resignButton.addActionListener(e -> controller.currentGameResignation());
        saveQuitButton.addActionListener(e -> controller.currentGameSaveAndQuit());

        moveHistoryArea = new JTextArea();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 5));
        moveHistoryArea.setBackground(new Color(30, 30, 30));
        moveHistoryArea.setForeground(Color.WHITE);
        moveHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        moveHistoryArea.setLineWrap(true);
        moveHistoryArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(moveHistoryArea);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        rowPanelGrid = new JPanel(new GridLayout(4, 1, 1, 1));
        rowPanelGrid.setOpaque(false);
        rowPanelGrid.setBackground(new Color(30, 30, 30));
        rowPanelGrid.add(teamLabel);
        rowPanelGrid.add(currentPlayerLabel);
        rowPanelGrid.add(currentPlayerClockLabel);
        rowPanelGrid.add(helpButton);

        rowButtonGrid = new JPanel(new GridLayout(3, 1, 1, 1));
        rowButtonGrid.setOpaque(false);
        rowButtonGrid.add(undoButton);
        rowButtonGrid.add(resignButton);
        rowButtonGrid.add(saveQuitButton);

        add(rowPanelGrid, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(rowButtonGrid, BorderLayout.SOUTH);
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

    // public methods for ManagerView to update gui
    public void setMoveHistory(String history) 
    {
        moveHistoryArea.setText(history);
        moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
        setUndoEnabled(history != null && !history.trim().isEmpty());
    }
    
    public void setUndoEnabled(boolean enabled) 
    {
        undoButton.setEnabled(enabled);
    }
    
    public void setCurrentTeam(String teamName, String whiteName, String blackName) 
    {
        String displayName = teamName;
        if(teamName.equals("BLACK")){displayName = "Black";}
        if(teamName.equals("WHITE")){displayName = "White";}
        
        String playerName = displayName.equals("White") ? whiteName : blackName;
        currentPlayerLabel.setText(playerName + "'s Turn");
        teamLabel.setText(teamName.toUpperCase());
        
        if (teamName.equalsIgnoreCase("BLACK")) 
        {
            teamLabel.setForeground(new Color(160, 160, 160));
        } 
        else 
        {
            teamLabel.setForeground(new Color(255, 255, 255));
        }
    }
    
    public void setClockLabel(String text) 
    {
        currentPlayerClockLabel.setText(text);
    }
    
    public JScrollPane getScrollPane() 
    {
        return scrollPane;
    }
    
    public String getMoveHistoryText() 
    {
        return moveHistoryArea.getText();
    }
    
    public void setSaveQuitButtonText(String text) 
    {
        saveQuitButton.setText(text);
    }
}
