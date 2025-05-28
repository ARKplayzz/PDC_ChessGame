/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author ARKen
 */
public class ManagerView extends JPanel{
    
    public ManagerView(){
        
        this.setBackground(new Color(30, 30, 30));
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setLayout(new BorderLayout());
    }
    
}
