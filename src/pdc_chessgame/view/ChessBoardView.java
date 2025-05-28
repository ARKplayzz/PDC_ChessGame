/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;
import pdc_chessgame.ChessGame;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ARKen
 */
public class ChessBoardView extends JPanel{
    
    public ChessBoardView(ChessGame controller)
    {
        this.setBackground(new Color(100, 100, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setLayout(new BorderLayout());
    }
    
}
