/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import pdc_chessgame.view.MenuView;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import pdc_chessgame.view.ChessBoardView;
import pdc_chessgame.view.ManagerView;
import pdc_chessgame.view.MasterFrame;
import pdc_chessgame.view.SideBar;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame {
    
    private GameManager game = new GameManager(new Player("player1", Team.WHITE), new Player("player1", Team.BLACK), new Clock(20, 2));
    
    private ChessBoardView boardView = new ChessBoardView(this); //could use interfaces instead of the whole chessgame
    private MenuView menuView = new MenuView(this);
    private ManagerView managerView = new ManagerView();
    private SideBar sideBar = new SideBar(menuView, managerView);
    
    private MasterFrame display = new MasterFrame(boardView, sideBar);
    
    public ChessGame()
    {
        System.out.println("yoinker sploinker");
    }
    
    public void start()
    {
        this.display.visible(true);
        
        
        createGame("player1", "player2", 20);

    }
    
    public void createGame(String p1, String p2, int time)
    {
        //players.put(Team.WHITE, new Player("Guest 1", Team.WHITE));
        //players.put(Team.BLACK, new Player("Guest 2", Team.BLACK));
        
        Player currentP1 = new Player(p1, Team.WHITE);
        Player currentP2 = new Player(p2, Team.BLACK);
        
        Clock gameClock = new Clock(time, 2);
        
        GameManager game = new GameManager(currentP1, currentP2, gameClock);
        
    }
    
    public void runGame()
    {
        game.start();
        
        
        //need to get winner and handle ranking updates here
        
        //game.getPlayers()
        //game.getWinner()

        //double[] eloChanges = this.leaderboard.changeElo(winner.getName(), loser.getName()); // change the elos of the players
        
        //int[] newElos = {this.leaderboard.getElo(winner.getName()), this.leaderboard.getElo(loser.getName())};
                
        //saving scores to the file just before the program exits
        //this.leaderboard.saveScores();
    }
    
    public MoveResult passMove(Move move) 
    {
        MoveResult result = game.makeMove(move);
        
        boardView.updateBoard();
        managerView.updateMoveHistory(game.board.getHistoryString());
        return result;
    }
    
    public ChessBoard getBoard()//must be removed later
    {
        return game.board;
    }
}
