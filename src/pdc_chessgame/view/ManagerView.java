/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;

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

/**
 *
 * @author ARKen
 */
public class ManagerView extends JPanel
{
    // Panels
    private JPanel gamePanel;
    private JPanel gameOverPanel;

    // Game panel components
    private JTextArea moveHistoryArea;
    private JScrollPane scrollPane;
    private JLabel currentPlayerLabel;
    private JLabel currentPlayerClockLabel;
    private JButton helpButton;
    private JButton undoButton;
    private JButton resignButton;
    private JButton saveQuitButton;
    private JPanel rowButtonGrid;
    private JPanel rowPanelGrid;

    // Game over panel components
    private JLabel gameOverLabel;
    private JTextArea gameOverArea;
    private JScrollPane gameOverScroll;
    private JButton exitButton;

    private Timer countdownTimer;
    private int currentTime;

    private CardLayout cardLayout;
    private JPanel mainPanel; // holds both panels

    public ManagerView(ControllerManagerActions controller)
    {
        setBackground(new Color(30, 30, 30));
        setBorder(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // --- Game Panel ---
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(30, 30, 30));

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

        // Add help button action: append detailed help message to moveHistoryArea
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

        rowPanelGrid = new JPanel(new GridLayout(3, 1, 1, 1));
        rowPanelGrid.setOpaque(false);
        rowPanelGrid.setBackground(new Color(30, 30, 30));
        rowPanelGrid.add(currentPlayerLabel);
        rowPanelGrid.add(currentPlayerClockLabel);
        rowPanelGrid.add(helpButton);

        rowButtonGrid = new JPanel(new GridLayout(3, 1, 1, 1));
        rowButtonGrid.setOpaque(false);
        rowButtonGrid.add(undoButton);
        rowButtonGrid.add(resignButton);
        rowButtonGrid.add(saveQuitButton);

        gamePanel.add(rowPanelGrid, BorderLayout.NORTH);
        gamePanel.add(scrollPane, BorderLayout.CENTER);
        gamePanel.add(rowButtonGrid, BorderLayout.SOUTH);

        // --- Game Over Panel ---
        gameOverPanel = new JPanel(new BorderLayout());
        gameOverPanel.setBackground(new Color(30, 30, 30));

        // Top: Title above help button (vertical grid)
        JPanel gameOverTopPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        gameOverTopPanel.setOpaque(false);

        gameOverLabel = new JLabel("White Wins");
        gameOverLabel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 4));
        gameOverLabel.setForeground(new Color(153, 233, 255));
        gameOverLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton helpButtonGameOver = new JButton("Help");
        setupButton(helpButtonGameOver);
        // Add help button action: append detailed help message to gameOverArea
        helpButtonGameOver.addActionListener(e -> {
            gameOverArea.append(
                "\nChess Help:\n" +
                "Click on a piece to select it. Possible moves will be shown as green tiles.\n" +
                "Click one of these green tiles to move your piece.\n" +
                "If you make a mistake, you can use the Undo button (as long as your opponent agrees).\n" +
                "Press Resign to give up, or Save & Quit to save the game (you can continue it later).\n"
            );
            gameOverArea.setCaretPosition(gameOverArea.getDocument().getLength());
        });

        gameOverTopPanel.add(gameOverLabel);
        gameOverTopPanel.add(helpButtonGameOver);

        // Bottom: Exit button centered (single cell grid)
        JPanel exitPanel = new JPanel(new GridLayout(1, 1, 1, 1));
        exitPanel.setOpaque(false);
        exitButton = new JButton("Exit");
        setupButton(exitButton);
        exitButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "GAME");
            javax.swing.RootPaneContainer root = (javax.swing.RootPaneContainer) this.getTopLevelAncestor();
            if (root != null && root.getGlassPane() != null) {
                root.getGlassPane().setVisible(false);
            }
            controller.currentGameSaveAndQuit();
        });
        exitPanel.add(exitButton);

        gameOverArea = new JTextArea();
        gameOverArea.setEditable(false);
        gameOverArea.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 5));
        gameOverArea.setBackground(new Color(30, 30, 30));
        gameOverArea.setForeground(Color.WHITE);
        gameOverArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gameOverArea.setLineWrap(true);
        gameOverArea.setWrapStyleWord(true);

        gameOverScroll = new JScrollPane(gameOverArea);
        gameOverScroll.setBorder(null);
        gameOverScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        gameOverPanel.add(gameOverTopPanel, BorderLayout.NORTH);
        gameOverPanel.add(gameOverScroll, BorderLayout.CENTER);
        gameOverPanel.add(exitPanel, BorderLayout.SOUTH);

        // --- Add panels to mainPanel ---
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
                int buttonCount = rowButtonGrid.getComponentCount();
                int buttonHeight = 0;
                for (int i = 0; i < buttonCount; i++) {
                    buttonHeight += rowButtonGrid.getComponent(i).getPreferredSize().height;
                }
                int totalButtonHeight = buttonHeight + 10 * buttonCount;
                int scrollHeight = Math.max(0, height - totalButtonHeight);
                scrollPane.setPreferredSize(new Dimension(width, scrollHeight));
                scrollPane.setMaximumSize(new Dimension(width, scrollHeight));
                scrollPane.revalidate();
                gameOverScroll.setPreferredSize(new Dimension(width, scrollHeight));
                gameOverScroll.setMaximumSize(new Dimension(width, scrollHeight));
                gameOverScroll.revalidate();
            }
        });

        // Show game panel by default
        showGamePanel();
    }

    // --- Panel switching methods ---
    public void showGamePanel() 
    {
        cardLayout.show(mainPanel, "GAME");
    }

    public void showGameOverPanel(String winner) {
        String winnerText = (winner != null && winner.equalsIgnoreCase("BLACK")) ? "Black Wins!" : "White Wins!";
        gameOverLabel.setText(winnerText);

        // Example: get player names and their current Elo (replace with your actual player name variables)
        String player1 = "Player1";
        String player2 = "Player2";
        int player1Elo = 1200; // replace with actual Elo lookup
        int player2Elo = 1200; // replace with actual Elo lookup

        gameOverArea.setText(
            moveHistoryArea.getText() + "\n" +
            winnerText + ", Thanks for playing!\n" +
            player1 + " Elo = " + player1Elo + "\n" +
            player2 + " Elo = " + player2Elo
        );

        gameOverArea.setCaretPosition(gameOverArea.getDocument().getLength());
        cardLayout.show(mainPanel, "GAME_OVER");
    }

    // --- Existing methods for updating UI ---
    public void startClock(int timeInSeconds, String currentTeam) 
    {
        stopClock();
        this.currentTime = timeInSeconds;
        updateClockLabel();
        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentTime > 0) {
                    currentTime--;
                    updateClockLabel();
                } else {
                    stopClock();
                    // Show game over panel when clock runs out
                    // The losing team is the current team, so winner is the opposite
                    String winner = currentTeam.equalsIgnoreCase("BLACK") ? "WHITE" : "BLACK";
                    showGameOverPanel(winner);
                    // Also show overlay on board
                    if (controller instanceof pdc_chessgame.ChessGame) {
                        ((pdc_chessgame.ChessGame)controller).showGameOverOverlayForTeam(winner);
                    }
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
        // Enable undo only if there is at least one move to undo
        setUndoEnabled(history != null && !history.trim().isEmpty());
    }

    public void setUndoEnabled(boolean enabled) 
    {
        this.undoButton.setEnabled(enabled);
    }
    public void updateCurrentTeam(String teamName) 
    {
        if(teamName.equals("BLACK")){teamName = "Black";}
        if(teamName.equals("WHITE")){teamName = "White";}
        currentPlayerLabel.setText(teamName + "'s Turn");
        // No longer store current team internally
    }
    public void updateClock(int time, String currentTeam)
    {
        startClock(time, currentTeam);
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

