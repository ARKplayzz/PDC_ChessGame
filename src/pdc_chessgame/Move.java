/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Finlay & Andrew
 */
public class Move 
{
    private final int fromX, fromY, toX, toY; // efficent storing for move cases
    // stores the originial string, used for saving and loading
    private String input;
    
    public Move(int fromRow, int fromCol, int toRow, int toCol) 
    { 
        this.fromX = fromRow;
        this.fromY = fromCol;
        this.toX = toRow;
        this.toY = toCol;
        this.input = input;
    }
    
    public String getInput()
    {
        return this.input;
    }
    
    public int getFromX()
    {
        return fromX;
    }
    
    public int getFromY()
    {
        return fromY;
    }
    
    public int getToX()
    {
        return toX;
    }
    
    public int getToY()
    {
        return toY;
    }
}
