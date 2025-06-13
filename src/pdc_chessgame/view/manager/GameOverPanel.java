/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view.manager;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Andrew
 */

public class GameOverPanel extends JPanel 
{
    private JLabel gameOverLabel;
    private JTextArea gameOverArea;
    private JButton exitButton;
    private JButton helpButton;
    private JScrollPane gameOverScroll;

    public GameOverPanel(Runnable onExit) 
    {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));

        JPanel gameOverTopPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        gameOverTopPanel.setOpaque(false);

        gameOverLabel = new JLabel("White Wins");
        gameOverLabel.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 4));
        gameOverLabel.setForeground(new Color(153, 233, 255));
        gameOverLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);

        helpButton = new JButton("Help");
        setupButton(helpButton);
        helpButton.addActionListener(e -> {
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
        gameOverTopPanel.add(helpButton);

        JPanel exitPanel = new JPanel(new GridLayout(1, 1, 1, 1));
        exitPanel.setOpaque(false);
        exitButton = new JButton("Exit");
        setupButton(exitButton);
        exitButton.addActionListener(e -> onExit.run());
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

        add(gameOverTopPanel, BorderLayout.NORTH);
        add(gameOverScroll, BorderLayout.CENTER);
        add(exitPanel, BorderLayout.SOUTH);
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
    public void setGameOverText(String winnerText, String moveHistory, String whiteName, int whiteElo, String blackName, int blackElo) 
    {
        gameOverLabel.setText(winnerText);
        gameOverArea.setText(
            moveHistory + "\n" +
            winnerText + " Thanks for playing!\n" +
            whiteName + " Elo = " + whiteElo + "\n" +
            blackName + " Elo = " + blackElo
        );
        gameOverArea.setCaretPosition(gameOverArea.getDocument().getLength());
    }

    public JScrollPane getScrollPane() 
    {
        return gameOverScroll;
    }
}
