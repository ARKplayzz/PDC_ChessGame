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

public class GameManager 
{       
    // Do not make the board final please
    public ChessBoard board;
    // hashmap of the players and teams
    private final HashMap<Team, Player> players;
    
    // the savemanager and clock
    private SaveManager savemanager;
    private Clock clock;
        
    private final InputHandler inputHandler = new InputHandler(null);

    public GameManager(String p1, String p2, int time) 
    {
        this.board = new ChessBoard(8, 8);
        this.savemanager = new SaveManager();
        this.clock = new Clock(time, 2);
        
        this.players = new HashMap<>(); // player count flexabuility for future addition
        this.players.put(Team.WHITE, new Player(p1, Team.WHITE));
        this.players.put(Team.BLACK, new Player(p2, Team.BLACK));
    }
    
    public void start()
    {
        // establish new Board
        this.board = new ChessBoard(8, 8);
        this.board.getHistory().deleteMoveHistory();
        this.clock.start();
    }
    
    public MoveResult makeMove(Move move) 
    {
        Team currentTeam = board.getCurrentTeam();
        Player currentPlayer = getPlayerInTeam(currentTeam);

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
    
    // get the player for this team
    private Player getPlayerInTeam(Team team) 
    { 
        return this.players.get(team);
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
