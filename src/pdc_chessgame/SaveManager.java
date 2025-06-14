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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Finlay & Andrew
 */

// this class manages saving and loading the game
public class SaveManager implements SaveGameInterface
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
    
    @Override
    public void SaveGameToUser(HashMap<Team, Player> players, Turn history, Clock clock, Database db)
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
                    // Only insert if not already present
                    if (!db.gameExists(saveName)) {
                        
                        db.insertGame(saveName, player1, player2, saveFile, clock.getWhitesTime(), clock.getBlacksTime());
                    }
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
        
        for(int i = 0; i < history.getMoveCount(); i++)
        { // print all of the players commands into the terminal
            pw.println(history.getHistoryEntry(i).getStringInput());
        }
        
        //exit & save
        pw.close();
        return true;
    }
    
    public boolean LoadGameFromFile(String file, HashMap<Team, Player> players, Database db)
    {
        if(file.contains(" "))
        {
            System.out.println("Please no whitespace");
            return false;
        }
        // Only add .sav if not already present
        if (!file.endsWith(".sav")) {
            file = file.concat(".sav");
        }
        FileReader f;
        try {
            f = new FileReader(file);
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find save file");
            return false;
        }
        BufferedReader fp = new BufferedReader(f);

        players.clear();
        this.loadedGame.clear();
        
        
        players.put(Team.WHITE, new Player(db.getPlayer1(file.replace(".sav", "")), Team.WHITE));
        players.put(Team.BLACK, new Player(db.getPlayer2(file.replace(".sav", "")), Team.BLACK));
        

        try 
        {
            String currentLine;
            while((currentLine = fp.readLine()) != null)
            {
                if(!currentLine.startsWith("$"))
                {
                    this.loadedGame.add(currentLine);
                }
            }
        } catch (IOException ex) {
            System.out.println("Program failed to load a line");
            return false;
        }
        try {
           fp.close();
        } catch (IOException ex) {
           Logger.getLogger(SaveManager.class.getName()).log(Level.SEVERE, null, ex);
           return false;
        }
        return true;
    }
    
    /**
     * Loads a game from a save file, removes the save from the database and disk, and simulates the moves.
     * Returns true if successful, false otherwise.
     * players will be filled with the loaded players.
     */
    @Override
    public boolean loadAndRemoveSaveFile(String saveFile, HashMap<Team, Player> players, ChessBoard boardToSimulate, Clock clock, Database db) 
    {
        boolean loaded = this.LoadGameFromFile(saveFile, players, db);
        if (!loaded || !players.containsKey(Team.WHITE) || !players.containsKey(Team.BLACK)) {
            System.out.println("Error: Save file missing player(s), cannot load.");
            return false;
        }

        // Remove the save from the database after loading
        try {
            String saveName = saveFile;
            if (saveName.endsWith(".sav")) {
                saveName = saveName.substring(0, saveName.length() - 4);
            }
            clock.setWhitesTime(db.getPlayer1Time(saveName));
            clock.setBlacksTime(db.getPlayer2Time(saveName));
            
            db.deleteGames(saveName);
        } catch (Exception e) {
            System.out.println("Warning: Could not remove save from database: " + e.getMessage());
        }

        // Remove the .sav file from disk if it exists
        try {
            String filePath = saveFile.endsWith(".sav") ? saveFile : saveFile + ".sav";
            java.io.File file = new java.io.File(filePath);
            if (file.exists()) {
                if (!file.delete()) {
                    System.out.println("Warning: Could not delete save file: " + filePath);
                }
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not delete save file: " + e.getMessage());
        }

        // Simulate moves to restore board state
        if (boardToSimulate != null) {
            this.simulateGame(boardToSimulate);
        }

        return true;
    }
}
