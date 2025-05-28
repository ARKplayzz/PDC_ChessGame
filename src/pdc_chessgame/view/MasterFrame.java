/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Andrew & Finlay
 */
public class MasterFrame extends JFrame {
    
    private JPanel chessBoardPanel;
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

                chessBoardPanel.setBounds(startX, boardY, boardSize, boardSize);
                sideBarPanel.setBounds(startX + boardSize + 20, sideBarY, sideBarWidth, sideBarHeight);
            }
        };
    
    public MasterFrame(JPanel chessBoardPanel, JPanel sideBarPanel)
    {
        this.chessBoardPanel = chessBoardPanel;
        this.sideBarPanel = sideBarPanel;
        
        this.setTitle("PDC ChessGame | by Andrew & Finlay");
        this.setSize(900,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(50, 50, 50)); //dark grey
        this.setLayout(new BorderLayout());
        
        //ImageIcon image new ImageIcon("logo.png");
        //frame.setIconImage(image.getImage());
        
        this.masterPanel.setBackground(new Color(50, 50, 50));
        this.masterPanel.add(this.chessBoardPanel);
        this.masterPanel.add(this.sideBarPanel);
        
        this.add(this.masterPanel, BorderLayout.CENTER);
    }
    
    public void visible(Boolean state)
    {
        this.setVisible(state);
    }

}
