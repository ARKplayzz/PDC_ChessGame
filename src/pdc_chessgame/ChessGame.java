/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import java.util.HashMap;
import pdc_chessgame.view.ChessBoardView;
import pdc_chessgame.view.ControllerManagerActions;
import pdc_chessgame.view.ManagerView;
import pdc_chessgame.view.MasterFrame;
import pdc_chessgame.view.SideBar;
import pdc_chessgame.view.menu.MenuView;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame implements ControllerManagerActions 
{
    GameManager game;
    SaveManager store = new SaveManager();

    // views/GUI inits
    private ChessBoardView boardView;
    private final MenuView menuView;
    private final ManagerView managerView;
    private final SideBar sideBar;
    private final MasterFrame display;

    public ChessGame()
    {
        System.out.println("=== Initializing Chess Database... ===");
        this.menuView = new MenuView(this);
        this.managerView = new ManagerView(this);
        this.sideBar = new SideBar(this.menuView, this.managerView);
        this.display = new MasterFrame(this.sideBar);
        System.out.println("=== Chess Database initialized. ===");
    } 

    //Starts the application and shows the menu
    public void start()
    {
        this.display.visible(true);
        this.sideBar.displayMenu();
    }

    // Creates a new game with given player names and time
    public void createGame(String p1, String p2, int time)
    {
        clear();
        this.game = new GameManager(p1, p2, time);
        this.boardView = new ChessBoardView(this);
        this.display.addChessBoard(boardView);

        this.managerView.setPlayersAndMenuView(p1, p2, this.menuView);

        this.game.start();
        updateDisplay();
        this.managerView.showGamePanel();
        this.sideBar.displayManager();
    }

    // undo the last move in the current game
    public void currentGameUndo()
    {
        this.game.undoMove();
        this.boardView.updateBoard();
        updateDisplay();
    }

    // Handles resignation
    public void currentGameResignation()
    {
        this.boardView.showGameOverOverlay(game.getBoardCurrentTeam().getOppositeTeam().toString());
        handleGameOver();
    }
    
    // Handles game loss
    @Override
    public void currentGameExit()
    {
        endGame();
    }
    
    // Handles running out of time loss
    @Override
    public void currentGameClockEnd()
    {
        this.boardView.showGameOverOverlay(game.getBoardCurrentTeam().getOppositeTeam().toString());
        handleGameOver();
    }

    // Saves the current game and returns to menu
    public void currentGameSaveAndQuit()
    {
        // Only this method should ever call SaveGameToUser!
        this.store.SaveGameToUser(game.getPlayers(), game.getBoardHistory());
        this.endGame();
    }

    // ends the current game and returns to menu
    private void endGame()
    {
        this.display.addChessBoard(null);
        this.sideBar.displayMenu();
        clear();
    }

    //Passes a move to the game, updates board, and calls checkmate if needed
    public MoveResult passMove(Move move) 
    {
        MoveResult result = game.makeMove(move);
        this.boardView.updateBoard();
        updateDisplay();

        if (result == MoveResult.CHECKMATE)
        {
            handleGameOver();
        }
        return result;
    }

    //Updates move history, current team, and clock in the manager view
    private void updateDisplay()
    {
        this.managerView.updateMoveHistory(this.game.getBoardHistoryString());
        this.managerView.updateCurrentTeam(this.game.getBoardCurrentTeam().toString());
        this.managerView.updateClock(this.game.getCurrentplayerTime(), this.game.getBoardCurrentTeam().toString());
    }

    // handles Elo, database, and game over panel logic for both resignation and checkmate
    private void handleGameOver()
    {
        String loserTeam = this.game.getBoardCurrentTeam().toString();
        String winner, loser, winnerTeam;
        if (loserTeam.equalsIgnoreCase("WHITE")) 
        {
            loser = this.game.getPlayers().get(Team.WHITE).getName();
            winner = this.game.getPlayers().get(Team.BLACK).getName();
            winnerTeam = "BLACK";
        } 
        else 
        {
            loser = this.game.getPlayers().get(Team.BLACK).getName();
            winner = this.game.getPlayers().get(Team.WHITE).getName();
            winnerTeam = "WHITE";
        }

        Database db = this.menuView.getDatabase();

        // ensure both winner and loser exist in the database (add if missing, not guest)
        if (!winner.equalsIgnoreCase("guest") && !db.playerExists(winner)) 
        {
            db.addPlayer(winner, Database.START_ELO);
        }
        if (!loser.equalsIgnoreCase("guest") && !db.playerExists(loser)) 
        {
            db.addPlayer(loser, Database.START_ELO);
        }

        // update Elo if both are not guest
        if (!winner.equalsIgnoreCase("guest") && !loser.equalsIgnoreCase("guest")) 
        {
            db.updateEloAfterGame(winner, loser);
        }

        managerView.setPlayersAndMenuView(
            this.game.getPlayers().get(Team.WHITE).getName(),
            this.game.getPlayers().get(Team.BLACK).getName(),
            this.menuView
        );
        
        int whiteElo = db.getElo(game.getPlayers().get(Team.WHITE).getName());
        int blackElo = db.getElo(game.getPlayers().get(Team.BLACK).getName());
        
        this.managerView.showGameOverPanel(winnerTeam, 
            this.game.getPlayers().get(Team.WHITE).getName(), whiteElo,
            this.game.getPlayers().get(Team.BLACK).getName(), blackElo);
    }

    public ChessBoard getBoard() 
    {
        if (this.game != null) {
            return this.game.board;
        }
        return null;
    }

    private void clear()
    {
        this.game = null;
        this.boardView = null;
    }

    // loads a game from a save file (filename only no extension)
    public boolean loadGameFromSaveFile(String saveFile) 
    {
        HashMap<Team, Player> players = new HashMap<>();
        boolean loaded = store.LoadGameFromFile(saveFile, players);
        // Defensive: require both players to be present
        if (!loaded || !players.containsKey(Team.WHITE) || !players.containsKey(Team.BLACK)) {
            System.out.println("Error: Save file missing player(s), cannot load.");
            return false;
        }

        // Remove the save from the database after loading
        try {
            Database db = this.menuView.getDatabase();
            String saveName = saveFile;
            // Remove .sav extension if present
            if (saveName.endsWith(".sav")) {
                saveName = saveName.substring(0, saveName.length() - 4);
            }
            db.executeSQLUpdate("DELETE FROM GAMES WHERE name = '" + saveName + "'");
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

        clear();
        this.game = new GameManager(players);
        this.boardView = new ChessBoardView(this);
        this.display.addChessBoard(boardView);

        this.managerView.setPlayersAndMenuView(
            players.get(Team.WHITE).getName(),
            players.get(Team.BLACK).getName(),
            this.menuView
        );

        // Simulate moves to restore board state
        store.simulateGame(this.game.board);

        updateDisplay();
        this.managerView.showGamePanel();
        this.sideBar.displayManager();
        return true;
    }
}
