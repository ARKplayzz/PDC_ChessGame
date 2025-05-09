/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
// simple enum to store teams
public enum Team 
{
    BLACK, WHITE;
    
    public String teamName()
    {
        return this.toString();
    }
    
    public Team getOppositeTeam()
    {
        return this == Team.WHITE ? Team.BLACK : Team.WHITE;
    }
}

