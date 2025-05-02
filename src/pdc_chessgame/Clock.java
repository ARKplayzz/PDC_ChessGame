/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author finlay
 */
public class Clock extends Thread
{
    // start time holds the start of the game
    private long startTime;
    private boolean quit = false;
    
    private int activePlayer;
    private long[] playerTimes;
    
    // playerTime is the amount of time each player gets
    public Clock(int playerTime, int numPlayers)
    {
        this.playerTimes = new long[numPlayers];
        
        for(int i = 0; i < numPlayers; i++)
            this.playerTimes[i] = this.minsToMillis(playerTime);
    }
    
    private long minsToMillis(int minutes)
    {
        return (long)minutes * 60000;
    }
    
    public void changeClock()
    {
        // add 10 seconds to the current players time
        this.playerTimes[this.activePlayer] += 10000;
        
        if(this.activePlayer < this.playerTimes.length-1)
            this.activePlayer++;
        else
            this.activePlayer = 0;
    }
    
    @Override
    public void run()
    {
        this.startTime = System.currentTimeMillis();
        while(!this.quit)
        {
            
        }
    }
    
    public void terminate()
    {
        this.quit = true;
    }
}

