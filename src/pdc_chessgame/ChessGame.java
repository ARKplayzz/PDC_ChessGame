/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import pdc_chessgame.view.MenuView;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import pdc_chessgame.view.ChessBoardView;
import pdc_chessgame.view.ManagerView;
import pdc_chessgame.view.MasterFrame;
import pdc_chessgame.view.SideBar;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame {
    
    private ChessBoardView boardView = new ChessBoardView(this); //could use interfaces instead of the whole chessgame
    private MenuView menuView = new MenuView(this);
    private ManagerView managerView = new ManagerView();
    private SideBar sideBar = new SideBar(menuView, managerView);
    
    private MasterFrame display = new MasterFrame(boardView, sideBar);
    
    public ChessGame()
    {
        System.out.println("yoinker sploinker");
    }
    
    public void start()
    {
        this.display.visible(true);
        
        
        GameManager game = new GameManager();
        game.start();

    }
}
