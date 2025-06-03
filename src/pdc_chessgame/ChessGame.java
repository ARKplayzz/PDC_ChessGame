/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
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
        this.game = new GameManager(p1, p2, time);

        this.boardView = new ChessBoardView(this);
        this.display.addChessBoard(boardView);
        
        this.game.start();
        updateGraphics();
        this.managerView.showGamePanel();

        this.sideBar.displayManager();
    }
  
    
    
    public void currentGameUndo()
    {
        this.game.undoMove();
        this.boardView.updateBoard();
        updateGraphics();
    }
    
    public void currentGameResignation()
    {
        this.managerView.showGameOverPanel(game.getBoardCurrentTeam().teamName());
        this.boardView.showGameOverOverlay(game.getBoardCurrentTeam().getOppositeTeam().toString());
        //this.endGame();
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

        updateGraphics();
        if (result == MoveResult.CHECKMATE)
        {
            this.managerView.showGameOverPanel(game.getBoardCurrentTeam().teamName());
        }
        return result;
    }
    
    public ChessBoard getBoard()//must be removed later
    {
        return game.board;
    }
    
    private void updateGraphics()
    {
        this.managerView.updateMoveHistory(this.game.getBoardHistoryString());
        this.managerView.updateCurrentTeam(game.getBoardCurrentTeam().toString());
        this.managerView.updateClock(this.game.getCurrentplayerTime());
    }
}
