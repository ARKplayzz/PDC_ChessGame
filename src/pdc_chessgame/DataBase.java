/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.sql.*;

/**
 *
 * @author Finlay & Andrew
 */

// ONLY WORKS UNDER CERTAIN CIRCUMSTANCES RN 
// STILL DOING THE NETBEANS INTERGRATION

public class DataBase 
{ // store players, elo, games won, games lost
    // possibly store games
    // possibly add names of save files
    // option: run whole game in a database
    private final String URL =  "jdbc:derby:ChessDB; create=true";
    
    private Connection connection;
    private Statement statement;
    
    public DataBase()
    {
        try {
            this.connection = DriverManager.getConnection(this.URL);
        } catch (SQLException ex) {
            System.out.println("FATAL ERROR: could not establish a connection to the database\n"+ex.getMessage());
            System.exit(0);
        }
        
        try {
            this.statement = this.connection.createStatement();
        } catch (SQLException ex) {
            System.out.println("FATAL ERROR: failed to create statement\n"+ex.getMessage());
            System.exit(0);
        }
        
        this.createTables();
    }
    
    // used for adding a new player to the database
    public boolean addPlayer(String name, int elo)
    {
        if(this.playerExists(name))
            return false;
        
        return this.executeSQL("INSERT INTO PLAYERS "+
                "VALUES ('"+name+"',"+elo+",0,0)");
    }
    
    // used for altering an existing player
    public boolean alterPlayer(String name, int elo, int gamesWon, int gamesLost)
    {// might have a mistake in it
        return this.executeSQL("UPDATE PLAYERS SET name = '"+name+"', elo = "+elo+", games_won = '"+gamesWon+"', games_lost = '"+gamesLost+"' WHERE name = '"+name+"'");
    }
    
    public int getElo(String name)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        }
        try {
            return rs.getInt("elo");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to grab value\n"+ex.getMessage());
        }
        return 0;
    }
    
    public int getGamesWon(String name)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        }
        try {
            return rs.getInt("games_won");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to grab value\n"+ex.getMessage());
        }
        return 0;
    }
    
    public int getGamesLost(String name)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        }
        try {
            return rs.getInt("games_lost");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to grab value\n"+ex.getMessage());
        }
        return 0;
    }
    
    // returns true if the specified player exists inside the database
    public boolean playerExists(String name)
    {
        return this.checkExists("PLAYERS", "name", name);
    }
    
    // insert a new game, NOTE I WILL BE ASSUMING player1 IS WHITE
    public boolean insertGame(String saveName, String player1, String player2)
    {
        return this.executeSQL("INSERT INTO GAMES "+
                "VALUES ('"+saveName+"','"+player1+"','"+player2+"')");
    }
    
    // PUT A FUNCTIONS HERE THAT RETURNS A LIST OF ALL SAVED GAMES, maybe an arraylist
    
    // need to test this some more
    private boolean checkExists(String table, String col, String data)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM "+table+" WHERE "+col+" = '"+data+"'");
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        }
        
        try {
            while(rs.next()) // make this better
            {
                if(rs.getString(col).equals(data))
                {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to finalise query\n"+ex.getMessage());
        }
        return false;
    }
    
    private boolean executeSQL(String sql)
    {
        try {
            return this.statement.execute(sql);
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute SQL command: "+sql+"\n"+ex.getMessage());
            return false;
        }
    }
    
    private void createTables()
    { // find a better way to do this
        String createPlayers = "CREATE TABLE PLAYERS"
            + "  (name           VARCHAR(25) NOT NULL PRIMARY KEY,"
            + "   elo            INTEGER,"
            + "   games_won      INTEGER,"
            + "   games_lost     INTEGER)";
        
        this.executeSQL(createPlayers);
        
        String createGames = "CREATE TABLE GAMES" 
            + "  (name           VARCHAR(25) NOT NULL PRIMARY KEY,"
            + "   player_1       VARCHAR(25),"
            + "   player_2       VARCHAR(25))";
        
        this.executeSQL(createGames);
    }
    
    public void terminate()
    {
        try {
            this.connection.close();
            this.statement.close();
        } catch (SQLException ex) {
            System.out.println("An error occured while terminating the connection to the database\n"+ex.getMessage());
        }
    }
}
