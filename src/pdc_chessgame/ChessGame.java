/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import java.util.HashMap;
import pdc_chessgame.view.ChessBoardView;
import pdc_chessgame.view.ControllerManagerActions;
import pdc_chessgame.view.manager.ManagerView;
import pdc_chessgame.view.MasterFrame;
import pdc_chessgame.view.SideBarPanel;
import pdc_chessgame.view.menu.MenuView;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame implements ControllerManagerActions // Pretty much the main class of the game, every other class is in some way related to this one
{
    GameManager game;
    SaveGameInterface store = new SaveManager();

    // views/GUI inits
    private ChessBoardView boardView;
    private final MenuView menuView;
    private final ManagerView managerView;
    private final SideBarPanel sideBar;
    private final MasterFrame display;
    
    // database
    @SuppressWarnings("FieldMayBeFinal")
    private Database database;

    public ChessGame()
    { // init values
        this.database = new Database();
        this.menuView = new MenuView(this, this.database);
        this.managerView = new ManagerView(this);
        this.sideBar = new SideBarPanel(this.menuView, this.managerView);
        this.display = new MasterFrame(this.sideBar);
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
    @Override
    public void currentGameUndo()
    {
        this.game.undoMove();
        this.boardView.updateBoard();
        this.boardView.clearSelection();
        updateDisplay();
    }

    // Handles resignation
    @Override
    public void currentGameResignation()
    {
        this.boardView.showGameOverOverlay(game.getBoardCurrentTeam().getOppositeTeam().toString());
        this.boardView.setGameEnded(true); // Block further input
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
        this.boardView.setGameEnded(true); // Block further input
        handleGameOver();
    }

    // Saves the current game and returns to menu
    @Override
    public void currentGameSaveAndQuit()
    {
        // Only this method should ever call SaveGameToUser!
        this.store.SaveGameToUser(game.getPlayers(), game.getBoardHistory(), this.game.getClock(), this.database);
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

        // update Elo if both are not guest
        if (!winner.equalsIgnoreCase("guest") || !loser.equalsIgnoreCase("guest"))
        {
            this.database.updateEloAfterGame(winner, loser);
        }

        managerView.setPlayersAndMenuView
        (
            this.game.getPlayers().get(Team.WHITE).getName(),
            this.game.getPlayers().get(Team.BLACK).getName(),
            this.menuView
        );
        
        // Show "-" for guest Elo, otherwise show the actual EloAdd commentMore actions
        String whiteName = this.game.getPlayers().get(Team.WHITE).getName();
        String blackName = this.game.getPlayers().get(Team.BLACK).getName();
        String whiteEloStr = whiteName.equalsIgnoreCase("guest") ? "-" : String.valueOf(this.database.getElo(whiteName));
        String blackEloStr = blackName.equalsIgnoreCase("guest") ? "-" : String.valueOf(this.database.getElo(blackName));
        
        this.managerView.showGameOverPanel(winnerTeam, 
            whiteName, whiteEloStr,
            blackName, blackEloStr);
    }
    
    // get the chessboard
    public ChessBoard getBoard() 
    {
        if (this.game != null) {
            return this.game.board;
        }
        return null;
    }
    
    // reset the game, used for when you exit your current game
    private void clear()
    {
        this.game = null;
        this.boardView = null;
    }

    // loads a game from a save file (filename only no extension)
    public boolean loadGameFromSaveFile(String saveFile) 
    {
        HashMap<Team, Player> players = new HashMap<>();
        GameManager tempGame = new GameManager(players);
        boolean loadedSuccessfully = store.loadAndRemoveSaveFile(saveFile, players, tempGame.board, tempGame.getClock(), this.database);
        if (!loadedSuccessfully) 
        {
            return false;
        }

        clear();
        this.game = new GameManager(players);
        this.game.board = tempGame.board;
        this.game.setClock(tempGame.getClock());

        this.boardView = new ChessBoardView(this);
        this.display.addChessBoard(boardView);
       

        this.managerView.setPlayersAndMenuView(
            players.get(Team.WHITE).getName(),
            players.get(Team.BLACK).getName(),
            this.menuView
        );

        updateDisplay();
        this.managerView.showGamePanel();
        this.sideBar.displayManager();
        return true;
    }
}
