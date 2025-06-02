/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author ARKen
 */
public class ManagerView extends JPanel
{
    private JTextArea moveHistoryArea;
    private JScrollPane scrollPane;
    
    private JLabel currentPlayerLabel;
    private JLabel currentPlayerClockLabel;
    
    private JButton helpButton;
    private JButton undoButton;
    private JButton resignButton;
    private JButton saveQuitButton;
    
    private JPanel rowButtonGrid = new JPanel(new GridLayout(3, 1, 1, 1)); //3 rows
    private JPanel rowPanelGrid = new JPanel(new GridLayout(3, 1, 1, 1)); //2 rows
    
    private Timer countdownTimer;
    private int currentTime;
    
    public ManagerView(ControllerManagerActions controller)
    {
        this.setBackground(new Color(30, 30, 30));
        this.setBorder(null);
        this.setLayout(new BorderLayout());
        
        currentPlayerLabel = new JLabel("White's Move");
        currentPlayerLabel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 4));
        currentPlayerLabel.setForeground(new Color(153, 233, 255));
        currentPlayerLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentPlayerClockLabel = new JLabel("Time: ");
        currentPlayerClockLabel.setForeground(new Color(189, 229, 225));
        currentPlayerClockLabel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        currentPlayerClockLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rowButtonGrid.setOpaque(false); // transparent background
        rowPanelGrid.setOpaque(false);
        rowPanelGrid.setBackground(new Color(30, 30, 30));
        
        helpButton = new JButton("Help");
        undoButton = new JButton("Undo Move");
        resignButton = new JButton("Resign");
        saveQuitButton = new JButton("Save & Quit");
        
        setupButton(helpButton);
        setupButton(undoButton);
        setupButton(resignButton);
        setupButton(saveQuitButton);
        
        undoButton.addActionListener(e -> controller.currentGameUndo());
        resignButton.addActionListener(e -> controller.currentGameResignation());
        saveQuitButton.addActionListener(e -> controller.currentGameSaveAndQuit());
        

        moveHistoryArea = new JTextArea();
        moveHistoryArea.setEditable(false);
        moveHistoryArea.setBorder(null);
        moveHistoryArea.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 5));
        moveHistoryArea.setBackground(new Color(30, 30, 30));
        moveHistoryArea.setForeground(Color.WHITE);
        moveHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); //looks fancy
        moveHistoryArea.setLineWrap(true);
        moveHistoryArea.setWrapStyleWord(true);
        


        scrollPane = new JScrollPane(moveHistoryArea);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //autoscroll (:
        
        // relative max growth size
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            int width = getWidth();
            int height = getHeight();

            // Estimate button height (getPreferredSize is more robust than hardcoding)
            int buttonCount = rowButtonGrid.getComponentCount();
            int buttonHeight = 0;
            for (int i = 0; i < buttonCount; i++) {
                buttonHeight += rowButtonGrid.getComponent(i).getPreferredSize().height;
            }
            int totalButtonHeight = buttonHeight + 10 * buttonCount; // add some margin

            // The scrollPane should never push the buttons out
            int scrollHeight = Math.max(0, height - totalButtonHeight);

            scrollPane.setPreferredSize(new Dimension(width, scrollHeight));
            scrollPane.setMaximumSize(new Dimension(width, scrollHeight));
            scrollPane.revalidate();
        }
    });
        
        rowPanelGrid.add(currentPlayerLabel);
        rowPanelGrid.add(currentPlayerClockLabel);       
        rowPanelGrid.add(helpButton);   
        
        rowButtonGrid.add(undoButton, BorderLayout.NORTH);
        rowButtonGrid.add(resignButton, BorderLayout.NORTH);
        rowButtonGrid.add(saveQuitButton, BorderLayout.NORTH);
        
        
        this.add(rowPanelGrid, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(rowButtonGrid, BorderLayout.SOUTH);
 
    }

    public void startClock(int timeInSeconds) 
    {
        stopClock(); // Stop any existing timer
        this.currentTime = timeInSeconds;
        updateClockLabel();

        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentTime > 0) {
                    currentTime--;
                    updateClockLabel();
                } else {
                    stopClock();
                }
            }
        });
        countdownTimer.start();
    }
    
    public void stopClock() 
    {
        if (countdownTimer != null) {
            countdownTimer.stop();
            countdownTimer = null;
        }
    }

    public void updateMoveHistory(String history) 
    {
        this.moveHistoryArea.setText(history);
        this.moveHistoryArea.setCaretPosition(moveHistoryArea.getDocument().getLength());
    }

    public void updateCurrentTeam(String teamName) 
    {
        if(teamName.equals("BLACK")){teamName = "Black";}
        if(teamName.equals("WHITE")){teamName = "White";}
        currentPlayerLabel.setText(teamName + "'s Turn");
    }

    public void updateClock(int time) 
    {
        startClock(time);
    }
    
    private void updateClockLabel() 
    {
        currentPlayerClockLabel.setText("Time: " + formatTime(this.currentTime));
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

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverBg);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalBg);
            }
        });
    }
    
    private String formatTime(int totalSeconds) 
    {
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }
    
    

    
}

