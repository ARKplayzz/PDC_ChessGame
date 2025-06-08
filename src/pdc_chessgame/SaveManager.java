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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    public void SaveGameToUser(HashMap<Team, Player> players, Turn history)
    {
        PrintWriter pw;
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm"); //format may be modified if format is to hard to sort
        
        // add the actaul file extension, this is so the .gitignore will work and the markers won't end up with our saves in their copy
        String saveFile = currentDateTime.format(dateFormat).concat(".sav");
        
        try {
            pw = new PrintWriter(new FileOutputStream(saveFile));
        } 
        catch (FileNotFoundException ex) 
        {
            System.out.println("Failed to create file, game has not been saved");
            return;
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
        
        // Add to database under both players' usernames
        try {
            // Only add to DB if both players are not guests
            String player1 = players.get(Team.WHITE) != null ? players.get(Team.WHITE).getName() : null;
            String player2 = players.get(Team.BLACK) != null ? players.get(Team.BLACK).getName() : null;
            if (player1 != null && player2 != null) {
                if (!player1.equalsIgnoreCase("guest") || !player2.equalsIgnoreCase("guest")) {
                    // Save name for DB is the filename without extension
                    String saveName = saveFile.replace(".sav", "");
                    // File path is just the filename here (could be a directory if needed)
                    Database db = new Database();
                    // Only insert if not already present
                    if (!db.gameExists(saveName)) {
                        db.insertGame(saveName, player1, player2, saveFile);
                    }
                    db.terminate();
                }
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not add save to database: " + e.getMessage());
        }
    }
    
    //legacy game saver
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
        // Remove this check, as saves from DB will have .sav extension
        // if(file.contains("."))
        // {
        //     System.out.println("Please do not enter anything that could be\ninterpreted a a file extension");
        //     return false;
        // }
        if(file.contains(" "))
        {
            System.out.println("Please no whitespace");
            return false;
        }
        FileReader f = null;
        // Only add .sav if not already present
        if (!file.endsWith(".sav")) {
            file = file.concat(".sav");
        }
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
        // clear loadedGame to avoid accumulating moves from previous loads
        this.loadedGame.clear();
        // actully load the history while checking for errors
        try 
        {
            // loop through the whole save file
            while((currentLine=fp.readLine())!= null)
            {
                if(currentLine.startsWith("$"))
                { // if the current line contains player info
                    currentLine = currentLine.replace("$", "");
                    String[] parts = currentLine.trim().split(" ");
                    
                    // Defensive: skip malformed lines
                    if (parts.length < 2) {
                        System.out.println("Malformed player line in save file: " + currentLine);
                        continue;
                    }

                    // Accept both "guest WHITE" and "GUEST WHITE"
                    if(parts[0].equalsIgnoreCase("guest"))
                    {
                        // Defensive: check length before accessing
                        if (parts.length < 2) {
                            System.out.println("Malformed guest player line in save file: " + currentLine);
                            continue;
                        }
                        parts[0] = "GUEST";
                        // parts[1] is already the team
                    }
                    // Accept "GUESTWHITE BLACK" as well (legacy)
                    if(parts[0].toUpperCase().startsWith("GUEST") && parts.length == 2) {
                        // e.g. "GUESTWHITE BLACK"
                        // leave as is
                    }
                    
                    // Only add valid teams
                    if(parts[1].equals("BLACK"))
                        players.put(Team.BLACK, new Player(parts[0], Team.BLACK));
                    else if(parts[1].equals("WHITE"))
                        players.put(Team.WHITE, new Player(parts[0], Team.WHITE));
                    // else skip invalid team
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
           System.out.println("Error closing fp "+ex.getMessage());
           return false;
        }
        // Defensive: require both players to be present
        if (!players.containsKey(Team.WHITE) || !players.containsKey(Team.BLACK)) {
            System.out.println("Save file missing player(s), cannot load.");
            return false;
        }
        return true;
    }
}
