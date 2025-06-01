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
public class SideBar extends JPanel {

    private JPanel menuPanel;
    private JPanel managerPanel;
    
    public SideBar(JPanel menuPanel, JPanel managerPanel)
    {
        this.setBackground(new Color(80, 80, 80));
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setLayout(new BorderLayout());
        
        this.add(menuPanel, BorderLayout.NORTH);
        this.add(managerPanel, BorderLayout.CENTER);
    }
}
