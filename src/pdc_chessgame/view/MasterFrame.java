/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Andrew & Finlay
 */
public class MasterFrame extends JFrame {
    
    private JPanel boardHolderPanel;
    private JPanel sideBarPanel;
    
    JPanel masterPanel = new JPanel(new GridBagLayout())
    {
        @Override
            public void doLayout() 
            {// ratio scaling my belloved (this took way too long but now it looks good)
                int frameWidth = getWidth();
                int frameHeight = getHeight();
                
                int availableHeight = frameHeight - 60; // margins
                int boardAvailableWidth = frameWidth - 60; // temporary calculation
                int boardSize = Math.min(boardAvailableWidth, availableHeight);
                
                int sideBarWidth = (int)(boardSize * 0.40); // sidebar is a ratio of chessboard
                
                int availableWidth = frameWidth - sideBarWidth - 60; // 60 for spacing/margins
                
                boardSize = Math.min(availableWidth, availableHeight); // board size
                
                int totalContentWidth = boardSize + sideBarWidth + 20; // 20 for spacing
                
                int startX = (frameWidth - totalContentWidth) / 2; //centering all horizontaly
                
                int boardY = (frameHeight - boardSize) / 2;
                int sideBarHeight = (int)(boardSize * 0.75); // sidebar height is half of board height
                int sideBarY = (frameHeight - sideBarHeight) / 2;

                boardHolderPanel.setBounds(startX, boardY, boardSize, boardSize);
                sideBarPanel.setBounds(startX + boardSize + 20, sideBarY, sideBarWidth, sideBarHeight);
            }
        };
    
    public MasterFrame(JPanel sideBarPanel)
    {
        this.boardHolderPanel = new JPanel(new BorderLayout()) 
        {
            private Image backgroundImage = new ImageIcon(getClass().getResource("/pdc_chessgame/resources/welcome_logo.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                if (backgroundImage != null) 
                {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        this.sideBarPanel = sideBarPanel;
        
        this.setTitle("PDC ChessGame | by Andrew & Finlay");
        this.setSize(1200,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(30, 30, 30)); //dark grey
        this.setLayout(new BorderLayout());
        
        String resourcePath = "/pdc_chessgame/resources/pieces/BLACK_R.png";
        URL imagePath = getClass().getResource(resourcePath);
        ImageIcon chessLogo = new ImageIcon(imagePath);
        
        this.setIconImage(chessLogo.getImage());
        
        this.masterPanel.setBackground(new Color(50, 50, 50));
        this.masterPanel.add(this.boardHolderPanel);
        this.masterPanel.add(this.sideBarPanel);
        
        this.add(this.masterPanel, BorderLayout.CENTER);
    }
    
    public void addChessBoard(JPanel board) 
    {
        this.boardHolderPanel.removeAll();
        if (board != null) 
        {
            this.boardHolderPanel.add(board, BorderLayout.CENTER);
        }
        this.boardHolderPanel.revalidate();
        this.boardHolderPanel.repaint();
    }
    
    public void visible(Boolean state)
    {
        this.setVisible(state);
    }

}
