/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pdc_chessgame.view;

/**
 *
 * @author Andrew
 */

public interface ChessBoardViewInterface 
{
    void updateBoard();
    void showGameOverOverlay(String winningTeam);
    void clearSelection();
    void setGameEnded(boolean ended);
}
