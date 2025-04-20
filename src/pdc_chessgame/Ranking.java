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
 * @author ARKen
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
    public void getElo(String file)
    {
        FileReader f = null;
        try {
            f = new FileReader(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find input file");
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
           return;
        }
        
        try {
           fp.close();
        } catch (IOException ex) {
           Logger.getLogger(Ranking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean saveElo(String file)
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
    
    public void printRankings()
    {
        
    }
}
