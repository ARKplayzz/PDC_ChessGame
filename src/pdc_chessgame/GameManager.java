/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pdc_chessgame;

import java.util.HashMap;

/**
 *
 * @author Andrew & Finlay
 */

// contains various functions to interface between classes and the ui & chessgame
public class GameManager 
{       
    // Do not make the board final please
    public ChessBoard board;
    // hashmap of the players and teams
    @SuppressWarnings("FieldMayBeFinal")
    private HashMap<Team, Player> players;
    
    private Clock clock;
        
    public GameManager(String p1, String p2, int time) 
    {
        this.board = new ChessBoard(8, 8);
        this.clock = new Clock(time, 2);

        this.players = new HashMap<>();
        this.players.put(Team.WHITE, new Player(p1, Team.WHITE));
        this.players.put(Team.BLACK, new Player(p2, Team.BLACK));
    }

    // Add this constructor for loading from save
    public GameManager(HashMap<Team, Player> players) 
    {
        this.players = players;
        this.board = new ChessBoard(8, 8);

        // Initialize clock with a default time (you might want to save/load the clock state too)
        this.clock = new Clock(20, 2);
    }
    
    // start the clock
    public void start()
    {
        this.clock.start();
    }
    
    // get the clock
    public Clock getClock()
    {
        return this.clock;
    }
    
    // copy another clock, used in save loading
    public void setClock(Clock clock)
    {
        this.clock = clock;
    }
    
    public MoveResult makeMove(Move move) 
    {
        Team currentTeam = board.getCurrentTeam();

        if (move == null) 
        {
            return MoveResult.RESIGNATION;
        }

        if (this.clock.getTime() < 1) 
        {
            return MoveResult.TIMER_END;
        }

        boolean moved = this.board.moveTile(move);
        if (!moved) 
        {
            return MoveResult.INVALID;
        }

        if (this.board.isInCheck(currentTeam)) 
        {
            this.board.undoMove();
            return MoveResult.CHECK;
        }

        Team enemyTeam = currentTeam.getOppositeTeam();

        // Check for checkmate before swapping turn
        if (this.board.isCheckmate(enemyTeam)) 
        {
            return MoveResult.CHECKMATE;
        }

        if (this.board.isInCheck(enemyTeam)) 
        {
            this.board.getNextTurn();
            this.clock.swapClock();
            return MoveResult.CHECK;
        }

        if (this.board.isPawnPromotable()) 
        {
            this.board.undoMove();
            return MoveResult.PROMOTION;
        }

        this.board.getNextTurn();
        this.clock.swapClock();
        return MoveResult.SUCCESS;
    }
    
    public void undoMove()
    {
        this.board.undoMove();
        this.board.getNextTurn();
    }
    
    public String getBoardHistoryString()
    {
        return this.board.getHistoryString();
    }
    
    public Turn getBoardHistory() 
    {
        return this.board.getHistory();
    }
    
    public Team getBoardCurrentTeam() 
    {
        return this.board.getCurrentTeam();
    }
    
    public int getCurrentplayerTime() 
    {
        return this.clock.getTime();
    }
    
    public HashMap<Team, Player> getPlayers() 
    {
        return this.players;
    }   
    
    public Player getWinner() 
    {
        return null;
    }     
}
