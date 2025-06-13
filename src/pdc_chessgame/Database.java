/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Finlay & Andrew
 */

// ONLY WORKS UNDER CERTAIN CIRCUMSTANCES RN 
// STILL DOING THE NETBEANS INTERGRATION

public class Database 
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
    
    // Use 1 as the starting Elo minimum
    public static final int START_ELO = 100;
    
    private Connection connection;
    private Statement statement;
    
    public Database()
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
        
        //this.createTables();

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
        return this.executeSQL("UPDATE PLAYERS SET elo = "+elo+", games_won = "+gamesWon+", games_lost = "+gamesLost+" WHERE name = '"+name+"'");
    }
    
    public int getElo(String name)
    {
        ResultSet rs = this.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
        try {
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
        ResultSet rs = this.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
        try {
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
        ResultSet rs = this.executeQuery("SELECT * FROM PLAYERS WHERE name = '"+name+"'");
        try {
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
    
    public boolean gameExists(String name)
    {
        return this.checkExists("GAMES", "name", name);
    }
    
    public int getPlayer1Time(String name)
    {
        ResultSet rs = this.executeQuery("SELECT * FROM GAMES WHERE name = '"+name+"'");
        try {
            if (rs.next()) {
                return rs.getInt("player_1_time");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting time "+ex.getMessage());
        }
        return 5;
    }
    
    public int getPlayer2Time(String name)
    {
        ResultSet rs = this.executeQuery("SELECT * FROM GAMES WHERE name = '"+name+"'");
        try {
            if (rs.next()) {
                return rs.getInt("player_2_time");
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting time "+ex.getMessage());
        }
        return 5;
    }
    
    public ResultSet getLeaderboard()
    {
        return this.executeQuery("SELECT name, elo, games_won, games_lost FROM PLAYERS ORDER BY elo DESC");
    }
    
    public boolean deleteGames(String name)
    {
        return this.executeSQL("DELETE FROM GAMES WHERE name = '" + name + "'");
    }
    
    public boolean deletePlayers(String name)
    {
        return this.executeSQL("DELETE FROM PLAYERS WHERE name = '" + name + "'");
    }
    
    // insert a new game, NOTE I WILL BE ASSUMING player1 IS WHITE
    // Add filePath parameter for directory column
    public boolean insertGame(String saveName, String player1, String player2, String filePath, int player1time, int player2time)
    {
        // name = saveName, player_1 = player1, player_2 = player2, directory = filePath
        return this.executeSQL("INSERT INTO GAMES "+
                "VALUES ('"+saveName+"','"+player1+"','"+player2+"','"+filePath+"',"+player1time+","+player2time+")");
    }
    
    public String getPlayer1(String save)
    {
        ResultSet rs = this.executeQuery("SELECT player_1 FROM GAMES WHERE name = '"+save+"'");
        
        try {
            if(rs.next())
            {
                return rs.getString("player_1");
            }
        } catch (SQLException ex) {
            System.out.println("Error with player query "+ex.getMessage());
        }
        
        return "null";
    }
    
    public String getPlayer2(String save)
    {
        ResultSet rs = this.executeQuery("SELECT player_2 FROM GAMES WHERE name = '"+save+"'");
        
        try {
            if(rs.next())
            {
                return rs.getString("player_2");
            }
        } catch (SQLException ex) {
            System.out.println("Error with player query "+ex.getMessage());
        }
        
        return "null";
    }
    
    // Helper class for save info
    public static class SaveInfo 
    {
        public final String saveFile;
        public final String player1;
        public final String player2;
        public final String directory;
        public SaveInfo(String saveFile, String player1, String player2, String directory) 
        {
            this.saveFile = saveFile;
            this.player1 = player1;
            this.player2 = player2;
            this.directory = directory;
        }
    }

    // Returns all saves where username is player_1 or player_2
    @SuppressWarnings("UseSpecificCatch")
    public List<SaveInfo> getSavesForUser(String username) 
    {
        List<SaveInfo> saves = new ArrayList<>();
        try {
            var conn = getConnection();
            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "SELECT name, player_1, player_2, directory FROM GAMES WHERE player_1 = ? OR player_2 = ? ORDER BY name DESC"
            )) {
                stmt.setString(1, username);
                stmt.setString(2, username);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    saves.add(new SaveInfo(
                            rs.getString("name"),
                            rs.getString("player_1"),
                            rs.getString("player_2"),
                            rs.getString("directory")
                    ));
                }   rs.close();
                // Do not close conn here, as it's the main connection
            }
        } catch (Exception e) {
            System.out.println("error in getSavesForUser(): "+e.getMessage());
        }
        return saves;
    }
    
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
    
    private ResultSet executeQuery(String sql)
    {
        try {
            return this.statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("ERROR: failed to execute query\n"+ex.getMessage());
            return null;
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
            + "   player_2       VARCHAR(25),"
            + "   directory      VARCHAR(35),"
            + "   player_1_time  INTEGER,"
            + "   player_2_time  INTEGER)";
        
        this.executeSQL(createGames);
    }
    
    // Add this method to print tables, columns, and row counts
    private void printDatabaseInfo() 
    {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet tables = meta.getTables(null, null, "%", new String[] {"TABLE"});
            System.out.println("=== Database Tables Overview ===");
            while (tables.next()) 
            {
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

    private Connection getConnection() 
    {
        return this.connection;
    }

    // Returns the new Elo values for winner and loser after a game, but does not update the DB
    public int[] calculateEloChange(String winner, String loser) {
        if (winner == null || loser == null) return new int[]{0, 0};
        if (winner.equalsIgnoreCase("guest") || loser.equalsIgnoreCase("guest")) return new int[]{0, 0};
        if (!playerExists(winner) || !playerExists(loser)) return new int[]{0, 0};

        int winnerElo = getElo(winner);
        int loserElo = getElo(loser);

        // Use the same formulas as Ranking.java
        double expectedWinner = 1.0 / (1 + Math.pow(10, (winnerElo - loserElo) / 400.0));
        double expectedLoser = 1.0 / (1 + Math.pow(10, (loserElo - winnerElo) / 400.0));

        int newWinnerElo = (int)Math.round(winnerElo + 25 * (1 - expectedWinner));
        int newLoserElo = (int)Math.round(loserElo + 25 * (0 - expectedLoser));

        // Prevent Elo from dropping below a minimum (e.g., 1)
        newWinnerElo = Math.max(START_ELO, newWinnerElo);
        newLoserElo = Math.max(START_ELO, newLoserElo);

        return new int[]{newWinnerElo, newLoserElo};
    }


    public void updateEloAfterGame(String winner, String loser) 
    {
        if (winner == null || loser == null) 
            return;
        if (winner.equalsIgnoreCase("guest") || loser.equalsIgnoreCase("guest")) 
            return;
        if (!playerExists(winner) || !playerExists(loser)) 
            return;

        //int winnerEloBefore = this.getElo(winner);
        //int loserEloBefore = this.getElo(loser);

        int[] newElos = calculateEloChange(winner, loser);
        int newWinnerElo = newElos[0];
        int newLoserElo = newElos[1];

        // Get current stats
        int winnerGamesWon = getGamesWon(winner);
        int winnerGamesLost = getGamesLost(winner);
        int loserGamesWon = getGamesWon(loser);
        int loserGamesLost = getGamesLost(loser);

        // Increment stats
        winnerGamesWon += 1;
        loserGamesLost += 1;

        boolean winnerUpdated = alterPlayer(winner, newWinnerElo, winnerGamesWon, winnerGamesLost);
        boolean loserUpdated = alterPlayer(loser, newLoserElo, loserGamesWon, loserGamesLost);

    }
}
