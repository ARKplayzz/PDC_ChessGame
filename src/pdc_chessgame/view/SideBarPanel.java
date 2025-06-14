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
 * @author ARKen
 */

// class used for the side panel that holds the buttons for starting loading resigning and saving
public class SideBarPanel extends JPanel 
{
    private JPanel menuPanel;
    private JPanel managerPanel;
    
    public SideBarPanel(JPanel menuPanel, JPanel managerPanel)
    {
        this.setBackground(new Color(30, 30, 30));
        this.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30, 2)));
        this.setLayout(new BorderLayout());
        
        this.menuPanel = menuPanel;
        this.managerPanel = managerPanel;
    }
    
    public void displayMenu() 
    {
        this.removeAll();
        this.add(menuPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public void displayManager() 
    {
        this.removeAll();
        this.add(managerPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
