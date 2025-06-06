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
{
    // Ensure Derby embedded driver is loaded
    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException ex) {
            System.out.println("FATAL ERROR: Derby EmbeddedDriver not found. Please ensure Derby is on the classpath.");
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private final String URL =  "jdbc:derby:ChessDB;create=true";
    
    private Connection connection;
    private Statement statement;
    
    public DataBase()
    {
        try {
            this.connection = DriverManager.getConnection(this.URL);
        } catch (SQLException ex) {
            if (ex.getSQLState() != null && ex.getSQLState().equals("XSDB6")) {
                System.out.println("FATAL ERROR: Derby database is already open in another process or was not shut down cleanly.");
                System.out.println("Please close all other Java programs using this database, and/or delete db.lck and dbex.lck in the ChessDB folder, then try again.");
            } else {
                System.out.println("FATAL ERROR: could not establish a connection to the database\n"+ex.getMessage());
            }
            ex.printStackTrace();
            System.exit(0);
        }
        
        try {
            this.statement = this.connection.createStatement();
        } catch (SQLException ex) {
            System.out.println("FATAL ERROR: failed to create statement\n"+ex.getMessage());
            System.exit(0);
        }
        
        this.createTables();

        // Print tables, columns, and row counts
        printDatabaseInfo();
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
        // Don't update the primary key (name)
        return this.executeSQLUpdate("UPDATE PLAYERS SET elo = "+elo+", games_won = "+gamesWon+", games_lost = "+gamesLost+" WHERE name = '"+name+"'");
    }
    
    public int getElo(String name)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
            if (rs.next()) {
                return rs.getInt("elo");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return 0;
    }

    public int getGamesWon(String name)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
            if (rs.next()) {
                return rs.getInt("games_won");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return 0;
    }

    public int getGamesLost(String name)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
            if (rs.next()) {
                return rs.getInt("games_lost");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return 0;
    }
    
    // returns true if the specified player exists inside the database
    public boolean playerExists(String name)
    {
        return this.checkExists("PLAYERS", "name", name);
    }
    
    // insert a new game, NOTE I WILL BE ASSUMING player1 IS WHITE
    // Add filePath parameter for directory column
    public boolean insertGame(String saveName, String player1, String player2, String filePath)
    {
        return this.executeSQL("INSERT INTO GAMES "+
                "VALUES ('"+saveName+"','"+player1+"','"+player2+"','"+filePath+"')");
    }
    
    // PUT A FUNCTIONS HERE THAT RETURNS A LIST OF ALL SAVED GAMES, maybe an arraylist
    
    // need to test this some more
    private boolean checkExists(String table, String col, String data)
    {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM "+table+" WHERE "+col+" = '"+data+"'");
            while(rs.next())
            {
                if(rs.getString(col).equals(data))
                {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
        }
        return false;
    }
    
    private boolean executeSQL(String sql)
    {
        try {
            this.statement.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            // Ignore "table already exists" error for CREATE TABLE
            if (ex.getSQLState() != null && ex.getSQLState().equals("X0Y32")) {
                return true;
            }
            System.out.println("ERROR: failed to execute SQL command: "+sql+"\n"+ex.getMessage());
            return false;
        }
    }

    // For UPDATE/INSERT/DELETE
    private boolean executeSQLUpdate(String sql)
    {
        try {
            int result = this.statement.executeUpdate(sql);
            return result > 0;
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute SQL update: "+sql+"\n"+ex.getMessage());
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
        
        // Drop GAMES table if it exists (for development only)
        try {
            this.statement.executeUpdate("DROP TABLE GAMES");
        } catch (SQLException ex) {
            // Ignore error if table does not exist
        }
        String createGames = "CREATE TABLE GAMES" 
            + "  (name           VARCHAR(25) NOT NULL PRIMARY KEY,"
            + "   player_1       VARCHAR(25),"
            + "   player_2       VARCHAR(25),"
            + "   directory      VARCHAR(35))";
        
        this.executeSQL(createGames);
    }
    
    // Add this method to print tables, columns, and row counts
    private void printDatabaseInfo() {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet tables = meta.getTables(null, null, "%", new String[] {"TABLE"});
            System.out.println("=== Database Tables Overview ===");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);

                // Print columns
                ResultSet columns = meta.getColumns(null, null, tableName, "%");
                System.out.print("  Columns: ");
                boolean first = true;
                while (columns.next()) {
                    if (!first) System.out.print(", ");
                    System.out.print(columns.getString("COLUMN_NAME"));
                    first = false;
                }
                columns.close();

                // Print row count
                int rowCount = 0;
                try (Statement st = connection.createStatement();
                     ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
                    if (rs.next()) rowCount = rs.getInt(1);
                } catch (SQLException e) {
                    // ignore
                }
                System.out.println("\n  Rows: " + rowCount);
            }
            tables.close();
            System.out.println("=== End Database Tables Overview ===");
        } catch (SQLException e) {
            System.out.println("ERROR: Could not print database info: " + e.getMessage());
        }
    }
    
    public void terminate()
    {
        try {
            if (this.statement != null) this.statement.close();
            if (this.connection != null) this.connection.close();
        } catch (SQLException ex) {
            System.out.println("An error occured while terminating the connection to the database\n"+ex.getMessage());
        }
    }
}
