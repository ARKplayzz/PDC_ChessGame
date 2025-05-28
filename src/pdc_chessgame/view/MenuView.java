/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;
import pdc_chessgame.ChessGame;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 *
 * @author ARKen
 */
public class MenuView extends JPanel{
    
    public MenuView(ChessGame controller)
    {
        this.setBackground(new Color(10, 10, 10));
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setLayout(new BorderLayout());
    }
}
