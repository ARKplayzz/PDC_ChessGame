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
    
    private long[] playerTimes;
    
    // playerTime is the amount of time each player gets
    public Clock(int playerTime, int numPlayers)
    {
        this.playerTimes = new long[numPlayers];
        
        for(int i = 0; i < numPlayers; i++)
            this.playerTimes[i] = this.minsToNanos(playerTime);
    }
    
    private long minsToNanos(int minutes)
    {
        
    }
    
    public void changeClock()
    {
        
    }
    
    @Override
    public void run()
    {
        this.startTime = System.nanoTime();
        while(!this.quit)
        {
            
        }
    }
    
    public void terminate()
    {
        this.quit = true;
    }
}

