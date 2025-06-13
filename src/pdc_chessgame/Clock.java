/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew & Finlay
 */
public class Clock extends Thread
{   
    // for quitting the clock from another thread
    private boolean quit = false;
    
    private int activePlayer;
    private int[] playerTimes;
    
    // playerTime is the amount of time each player gets
    public Clock(int playerTime, int numPlayers)
    {
        this.playerTimes = new int[numPlayers];
        
        for(int i = 0; i < numPlayers; i++)
        {
            this.playerTimes[i] = (playerTime * 60);
        }
    }
    
    @Override
    public String toString()
    { // return the current time formatted as MM:SS
        return (String)((this.playerTimes[this.activePlayer] / 60) +":"+ ((int)(this.playerTimes[this.activePlayer] % 60)));
    }
    
    public int getWhitesTime()
    { // not great if we wanted to add more teams but it seems like we won't be doing that so oh well
        return this.playerTimes[0];
    }
    
    public void setActive(Team team)
    {
        if(team == Team.WHITE)
            this.activePlayer = 0;
        else if(team == Team.BLACK)
            this.activePlayer = 1;
    }
    
    public int getBlacksTime()
    {
        return this.playerTimes[1];
    }
    
    public void setWhitesTime(int time)
    {
        this.playerTimes[0] = time;
    }
    
    public void setBlacksTime(int time)
    {
        this.playerTimes[1] = time;
    }
    
    public int getTime()
    {
        return this.playerTimes[this.activePlayer];
    }
    
    public void setTime(int time)
    {
        this.playerTimes[this.activePlayer] = time;
    }
    
    public void swapClock()
    {
        // switch active player first
        if(this.activePlayer < this.playerTimes.length-1)
            this.activePlayer++;
        else
            this.activePlayer = 0;
        

        // add 10 seconds to the new active player's time
        //this.playerTimes[this.activePlayer].time += 10;
    }
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run()
    {
        while(!this.quit)
        {
            // reduce this players limit
            if(this.playerTimes[this.activePlayer] > 0)
            {
                this.playerTimes[this.activePlayer]--;
                //this.playerTimes[this.activePlayer].time--;
            }
            
            try { // sleep for 1 second
                Thread.sleep(1000L);
            } catch (InterruptedException ex) {
                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void terminate()
    { // used for the main thread to terminate this one
        this.quit = true;
    }
}

