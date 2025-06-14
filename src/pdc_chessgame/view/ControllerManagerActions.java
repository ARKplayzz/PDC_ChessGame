/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pdc_chessgame.view;

/**
 *
 * @author Andrew
 */

// interface implemented by ChessGame.java, and used in GamePanel
public interface ControllerManagerActions 
{
    void currentGameUndo();
    void currentGameResignation();
    void currentGameSaveAndQuit();
    void currentGameClockEnd();
    void currentGameExit();
}
