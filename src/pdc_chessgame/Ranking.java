/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andrew & Finlay
 */
public class Ranking 
{
    // DO NOT make this final
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<String, Integer> leaderboard;

    public Ranking() 
    {
        this.leaderboard = new HashMap<>();
    }
    
    // Loads elo scores from the specified file into this.leaderboard
    public int getElo(String user)
    {
        if(!this.leaderboard.containsKey(user))
            return -1;
        return this.leaderboard.get(user);
    }
    
    // call at the end of the game to compute elo
    public int[] changeElo(String winner, String loser)
    {
        int winnerElo = this.leaderboard.get(winner);
        int loserElo = this.leaderboard.get(loser);
        
        this.leaderboard.put(winner, (this.leaderboard.get(winner) + 25 *(1 - this.predictElo(winner, loser))) );
        this.leaderboard.put(loser, (this.leaderboard.get(loser) + 25 *(0 - this.predictElo(loser, winner))) );
        
        int[] t = {winnerElo, loserElo};
        return t;
    }
    
    private int predictElo(String player, String opponent)
    {
        return (int)(1.0 / (1 + Math.pow(10, (this.leaderboard.get(player) - this.leaderboard.get(opponent)) / 400.0)));
    }
    
    public boolean newUser(String user)
    {
        if(this.leaderboard.containsKey(user))
            return false;
        
        // 100 base elo
        this.leaderboard.put(user, 100);
        return true;
    }
    
    public boolean hasPlayed(String user) //has played before?
    {
        if(this.leaderboard.containsKey(user))
            return false;
        return false;
    }
    
    public void printLeaderboard()
    { // unordered, add order later
        for(Map.Entry<String, Integer> entry : this.leaderboard.entrySet())
        {
            System.out.println((String)entry.getKey() + " " + entry.getValue());
        }
    }
    
    public boolean isEmpty()
    {
        if(this.leaderboard.size() < 1)
            return true;
        return false;
    }
    
    public boolean getLeaderboard(String file)
    {
        FileReader f = null;
        try {
            f = new FileReader(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find input file");
            return false;
        }
        
        BufferedReader fp = new BufferedReader(f);
        @SuppressWarnings("UnusedAssignment")
        String i = null;
        
        try {
           while((i=fp.readLine())!= null)
           {
               if(i.isBlank() || i.isEmpty())
                   continue;
               
               String[] r = i.split(" ");
               
               this.leaderboard.put(r[0], Integer.valueOf(r[1]));
           }
        } catch (IOException ex) {
           System.out.println("IO exception");
           return false;
        }
        
        try {
           fp.close();
        } catch (IOException ex) {
           Logger.getLogger(Ranking.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        }
        return true;
    }
    
    public boolean saveScores(String file)
    { 
        PrintWriter pw;
        
        try {
            pw = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to create file output");
            return false;
        }
        
        for(Map.Entry<String, Integer> entry : this.leaderboard.entrySet())
        {
            pw.println((String)entry.getKey() + " " + entry.getValue());
        }
        
        pw.close();
        return true;
    }
}
