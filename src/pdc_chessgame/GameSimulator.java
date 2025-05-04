/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Finlay and Andrew
 */
public class GameSimulator 
{
    public void simulateGame(SaveManager save, ChessBoard board)
    {
        for(int i = 0; i < save.getSaveLength(); i++)
        {
            board.moveTile(MoveInput.getMoveInput(save.getSaveEntry(i)));
            board.getNextTurn();
        }
    }
}
