/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Finlay & Andrew
 */
public class Turn 
{
    private int turn = 0;
    private Team team = Team.WHITE;
    
    // add movehistory here
    
    public Turn()
    {
        
    }
    
    public int getTurn()
    {
        return this.turn;
    }
    
    public Team getTeam()
    {
        return this.team;
    }
    
    public void nextTurn()
    {
        this.turn++;
        
        if(this.team == Team.WHITE)
            this.team = Team.BLACK;
        else
            this.team = Team.WHITE;
    }
}
