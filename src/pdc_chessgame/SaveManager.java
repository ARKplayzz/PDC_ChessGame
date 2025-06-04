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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Finlay & Andrew
 */

// this class manages saving and loading the game
public class SaveManager 
{
    // the commands from movehistory are loaded into here in order
    // DO NOT MAKE THIS FINAL
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> loadedGame;
    
    public SaveManager()
    {
        // initilising the loaded game list
        this.loadedGame = new ArrayList<>();
    }
    
    // get the amount of items in the loaded game list
    public int getSaveLength()
    {
        return this.loadedGame.size();
    }
    
    // get a specific entry in the loaded game's history
    public String getSaveEntry(int i)
    {
        return this.loadedGame.get(i);
    }
    
    // used after loading the file to restore the board
    public void simulateGame(ChessBoard board)
    {
        for(int i = 0; i < this.getSaveLength(); i++)
        {
            board.moveTile(MoveInput.getMoveInput(this.getSaveEntry(i)));
            board.getNextTurn();
        }
    }
    
    public boolean SaveGameToFile(String file, Turn history, HashMap<Team, Player> players)
    {
        PrintWriter pw;
        // check user hasn't tried to add a file extension
        if(file.contains("."))
        {
            System.out.println("Please do not enter anything that could be\ninterpreted as a file extension");
            return false;
        }
        if(file.contains(" "))
        {
            System.out.println("Please no whitespace");
            return false;
        }
        // add the actaul file extension, this is so the .gitignore will work and the markers won't end up with our saves in their copy
        file = file.concat(".sav");
        
        // create the printwriter and catch errors
        try {
            pw = new PrintWriter(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to create file, game has not been saved");
            return false;
        }
        //save the game
        
        // Print all the players names onto the first line of the save file
        for(Map.Entry<Team, Player> entry : players.entrySet())
        {
            // the $ is to denote that this line should be interpreted as a player
            pw.println("$"+(String)entry.getValue().getName() + " " +(String)entry.getKey().teamName());
        }
        
        for(int i = 0; i < history.getMoveCount(); i++)
        { // print all of the players commands into the terminal
            pw.println(history.getHistoryEntry(i).getStringInput());
        }
        
        //exit & save
        pw.close();
        return true;
    }
    
    public boolean LoadGameFromFile(String file, HashMap<Team, Player> players)
    {
        // check for a users file extension
        if(file.contains("."))
        {
            System.out.println("Please do not enter anything that could be\ninterpreted a a file extension");
            return false;
        }
        if(file.contains(" "))
        {
            System.out.println("Please no whitespace");
            return false;
        }
        FileReader f = null;
        file = file.concat(".sav");
        // create the buffered reader
        try {
            f = new FileReader(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find save file");
            return false;
        }
        BufferedReader fp = new BufferedReader(f);
        //load the game
        
        String currentLine = null;
        // clear any existing players from previous games
        players.clear();
        // actully load the history while checking for errors
        try 
        {
            // loop through the whole save file
            while((currentLine=fp.readLine())!= null)
            {
                if(currentLine.startsWith("$"))
                { // if the current line contains player info
                    currentLine = currentLine.replace("$", "");
                    String[] parts = currentLine.trim().toUpperCase().split(" ");
                    
                    if(parts[0].toUpperCase().equals("GUEST"))
                    {
                        parts[0] = "GUEST"+parts[1];
                        parts[1] = parts[2];
                    }
                    
                    // actully add the players to the hashmap
                    if(parts[1].equals("BLACK"))
                        players.put(Team.BLACK, new Player(parts[0], Team.BLACK));
                    else if(parts[1].equals("WHITE"))
                        players.put(Team.WHITE, new Player(parts[0], Team.WHITE));
                    
                    
                }
                else
                { // if the current line contains move info
                    this.loadedGame.add(currentLine);
                }
            }
        } catch (IOException ex) {
            System.out.println("Program failed to load a line");
            return false;
        }
        
        //exit
        try {
           fp.close();
        } catch (IOException ex) {
           Logger.getLogger(Ranking.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        }
        return true;
    }
}
