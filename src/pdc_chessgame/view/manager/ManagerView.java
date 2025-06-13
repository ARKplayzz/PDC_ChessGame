/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import pdc_chessgame.view.ControllerManagerActions;
import pdc_chessgame.view.menu.MenuView;

/**
 *
 * @author ARKen
 */
public class ManagerView extends JPanel
{
    // Panels
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;

    // Add these fields:
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String playerWhiteName = "White";
    private String playerBlackName = "Black";
    private pdc_chessgame.view.menu.MenuView menuViewRef;

    // Game over panel components
    private JLabel gameOverLabel;
    private JTextArea gameOverArea;
    private JScrollPane gameOverScroll;
    private JButton exitButton;

    private Timer countdownTimer;
    private int currentTime;
    private ActionListener clockActionPerformed;

    public ManagerView(ControllerManagerActions controller)
    {
        setBackground(new Color(30, 30, 30));
        setBorder(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Use new GamePanel and GameOverPanel
        gamePanel = new GamePanel(controller);
        gameOverPanel = new GameOverPanel(() -> {
            cardLayout.show(mainPanel, "GAME");
            javax.swing.RootPaneContainer root = (javax.swing.RootPaneContainer) this.getTopLevelAncestor();
            if (root != null && root.getGlassPane() != null)
            {
                root.getGlassPane().setVisible(false);
            }
            controller.currentGameExit();
        });

        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(gameOverPanel, "GAME_OVER");

        add(mainPanel, BorderLayout.CENTER);

        // Resize handling for both panels
        this.addComponentListener(new java.awt.event.ComponentAdapter() 
        {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) 
            {
                int width = getWidth();
                int height = getHeight();
                int buttonCount = 3; // undo, resign, saveQuit
                int buttonHeight = 38; // approx height per button
                int totalButtonHeight = buttonHeight * buttonCount + 10 * buttonCount;
                int scrollHeight = Math.max(0, height - totalButtonHeight);
                gamePanel.getScrollPane().setPreferredSize(new Dimension(width, scrollHeight));
                gamePanel.getScrollPane().setMaximumSize(new Dimension(width, scrollHeight));
                gamePanel.getScrollPane().revalidate();
                gameOverPanel.getScrollPane().setPreferredSize(new Dimension(width, scrollHeight));
                gameOverPanel.getScrollPane().setMaximumSize(new Dimension(width, scrollHeight));
                gameOverPanel.getScrollPane().revalidate();
            }
        });

        // Timer logic
        clockActionPerformed = new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (currentTime > 0) 
                {
                    currentTime--;
                    updateClockLabel();
                } 
                else 
                {
                    stopClock();
                    controller.currentGameClockEnd();
                }
            }
        };

        // Show game panel by default
        showGamePanel();
    }

    public void showGamePanel() 
    {
        cardLayout.show(mainPanel, "GAME");
    }

    public void showGameOverPanel(String winnerTeam, String whiteName, int whiteElo, String blackName, int blackElo) 
    {
        String winnerText;
        if (winnerTeam != null && winnerTeam.equalsIgnoreCase("BLACK")) 
        {
            winnerText = blackName + " wins!";
        } 
        else 
        {
            winnerText = whiteName + " wins!";
        }
        gameOverPanel.setGameOverText(
            winnerText,
            getMoveHistoryText(),
            whiteName, whiteElo, blackName, blackElo
        );
        cardLayout.show(mainPanel, "GAME_OVER");
    }

    public void startClock(int timeInSeconds, String currentTeam) 
    {
        stopClock();
        this.currentTime = timeInSeconds;
        updateClockLabel();
        countdownTimer = new Timer(1000, clockActionPerformed);
        countdownTimer.start();
    }
    
    public void stopClock() 
    {
        if (countdownTimer != null) 
        {
            countdownTimer.stop();
            countdownTimer = null;
        }
    }
    
    public void updateMoveHistory(String history) 
    {
        this.gamePanel.setMoveHistory(history);
    }

    public void setUndoEnabled(boolean enabled) 
    {
        this.gamePanel.setUndoEnabled(enabled);
    }
    
    public void updateCurrentTeam(String teamName) 
    {
        this.gamePanel.setCurrentTeam(teamName, playerWhiteName, playerBlackName);
    }
    
    public void updateClock(int time, String currentTeam)
    {
        startClock(time, currentTeam);
    }
    
    private void updateClockLabel() 
    {
        gamePanel.setClockLabel("Time: " + formatTime(this.currentTime));
    }
    
    private String formatTime(int totalSeconds) 
    {
        int mins = totalSeconds / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }

    //setter to provide player names and MenuView reference
    public void setPlayersAndMenuView(String white, String black, MenuView menuView) 
    {
        this.playerWhiteName = white;
        this.playerBlackName = black;
        this.menuViewRef = menuView;
        
        // Save & Quit is just Quit if both players are "guest"
        if ("guest".equalsIgnoreCase(white) && "guest".equalsIgnoreCase(black)) 
        {
            gamePanel.setSaveQuitButtonText("Quit");
        } 
        else 
        {
            gamePanel.setSaveQuitButtonText("Save & Quit");
        }
    }

    private String getMoveHistoryText() 
    {
        // Use the public getter from GamePanel
        return this.gamePanel != null ? this.gamePanel.getMoveHistoryText() : "";
    }
}

