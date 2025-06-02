/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import pdc_chessgame.view.menu.MenuView;
import pdc_chessgame.SaveManager;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import pdc_chessgame.view.ChessBoardView;
import pdc_chessgame.view.ControllerManagerActions;
import pdc_chessgame.view.ManagerView;
import pdc_chessgame.view.MasterFrame;
import pdc_chessgame.view.SideBar;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame implements ControllerManagerActions {
    
    private GameManager game;
    private SaveManager store = new SaveManager();
    
    
    private ChessBoardView boardView;//could use interfaces instead of the whole chessgame
    private MenuView menuView = new MenuView(this);
    private ManagerView managerView = new ManagerView(this);
    private SideBar sideBar = new SideBar(menuView, managerView);
    
    private MasterFrame display = new MasterFrame(sideBar);

    public ChessGame()
    {
        System.out.println("yoinker sploinker");
    }
    
    public void start()
    {
        this.display.visible(true);
        this.sideBar.displayMenu();
    }
    
    public void createGame(String p1, String p2, int time)
    {
        //players.put(Team.WHITE, new Player("Guest 1", Team.WHITE));
        //players.put(Team.BLACK, new Player("Guest 2", Team.BLACK));
        
        Player currentP1 = new Player(p1, Team.WHITE);
        Player currentP2 = new Player(p2, Team.BLACK);
        
        
        
        this.game = new GameManager(currentP1, currentP2, time);
        
        this.boardView = new ChessBoardView(this);
        this.display.addChessBoard(boardView);
        
        this.sideBar.displayManager();
       
    }
    
    public void runGame()
    {
        this.game.start();
        
        
        //need to get winner and handle ranking updates here
        
        //game.getPlayers()
        //game.getWinner()

        //double[] eloChanges = this.leaderboard.changeElo(winner.getName(), loser.getName()); // change the elos of the players
        
        //int[] newElos = {this.leaderboard.getElo(winner.getName()), this.leaderboard.getElo(loser.getName())};
                
        //saving scores to the file just before the program exits
        //this.leaderboard.saveScores();
    }
    
    
    public void currentGameUndo()
    {
        this.game.undoMove();
        this.boardView.updateBoard();
        this.managerView.updateMoveHistory(this.game.getBoardHistoryString());
        this.managerView.updateCurrentTeam(game.getBoardCurrentTeam().toString());
        this.managerView.updateClock(this.game.getCurrentplayerTime());
    }
    
    public void currentGameResignation()
    {
        this.endGame();
    }
    
    public void currentGameSaveAndQuit()
    {

        this.store.SaveGameToUser(game.getPlayers(), game.getBoardHistory());
        this.endGame();
       
    }
    
    private void endGame() //(infinity war was better)
    {
        this.display.addChessBoard(null);
        this.sideBar.displayMenu();
    }
    
    public MoveResult passMove(Move move) 
    {
        MoveResult result = game.makeMove(move);
        
        this.boardView.updateBoard();

        this.managerView.updateMoveHistory(this.game.getBoardHistoryString());
        this.managerView.updateCurrentTeam(game.getBoardCurrentTeam().toString());
        this.managerView.updateClock(this.game.getCurrentplayerTime());

        return result;
    }
    
    public ChessBoard getBoard()//must be removed later
    {
        return game.board;
    }
}
