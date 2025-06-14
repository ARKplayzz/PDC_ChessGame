/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */

// simple class used to represent the players
public class Player 
{
    private String name;
    private Team team;
    
    public Player(String name, Team team) 
    {
        this.name = name;
        this.team = team;
    }
    
    public String getName() 
    {
        return name;
    }
    
    public void setName(String name) 
    {
        this.name = name;
    }

    public Team getTeam() 
    {
        return team;
    }
}