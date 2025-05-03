/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Finlay & Andrew
 */
public class Turn 
{
    private int turn = 0;
    private Team team = Team.WHITE;

    private List<MoveState> moveHistory;
    
    public Turn()
    {
        this.moveHistory = new ArrayList<>();
    }
    
    public void addMoveToHistory(Pieces piece, Pieces capturedPiece, Tile from, Tile to)
    {
        MoveState m = new MoveState(piece, capturedPiece, from, to, this.turn);
        this.moveHistory.add(m);
    }
    
    public int getMoveCount()
    {
        return this.moveHistory.size();
    }
    
    public MoveState getPriorMove(int priorMoveNumber) // not last move, one before for undo indexing
    {
        return this.moveHistory.get(this.moveHistory.size() - priorMoveNumber);
    }
    
    private MoveState lastMove(Pieces p)
    {
        MoveState m = new MoveState(null, null, null, null, -1);
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).getPiece().equals(p) && m.getMoveNumber() < this.moveHistory.get(i).getMoveNumber())
            {
                m = this.moveHistory.get(i);
            }
        }
        if(m.getPiece() == null)
            return null;
        return m;
    }
    
    public MoveState deleteRecentMove() 
    {
        if (this.moveHistory.isEmpty()) 
        {
            return null;
        }
        
        int lastIndex = this.moveHistory.size() - 1;
        MoveState lastMove = this.moveHistory.get(lastIndex);

        this.moveHistory.remove(lastIndex);

        return lastMove;
    }
   
    // returns the amount of distance the specified piece moved on its last turn
    public int distanceLastMoved(Pieces p) 
    {
        MoveState m = this.lastMove(p);
        
        int dx = m.getToTile().getX() - m.getFromTile().getX();
        int dy = m.getToTile().getY() - m.getFromTile().getY();
        
        int distance = (int) Math.sqrt(dx * dx + dy * dy);
        
        return distance;
    }

    // returns the amount of turns since the specified piece moved, returns 1 if the move was last turn
    public int turnsSinceLastMoved(Pieces p)
    {
        MoveState m = this.lastMove(p);
        if(m != null) 
        {
            return m.getMoveNumber() - this.turn;
        }
        return 0;
    }
    
    public boolean historyContains(MoveState m)
    {
        return this.moveHistory.contains(m);
    }
    
    public boolean hasPieceMoved(Pieces p)
    {
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).getPiece().equals(p))
            {
                return true;
            }
        }
        return false;
    }
    
    public int pieceMoveCount(Pieces p)
    {
        int n = 0;
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).getPiece().equals(p))
            {
                n++;
            }
        }
        return n;
    }
    
    public void printMoveHistory()
    {
        System.out.println("Move history:\nPIECE    FROM    TO    TURN");
        // do move.toString() in order of moveNo
    }
    
    public void deleteMoveHistory()
    {
        this.moveHistory.clear();
    }
    
    public int getTurn()
    {
        return this.turn;
    }
    
    public Team getTeam()
    {
        return this.team;
    }
    
    public void nextTurn()
    {
        this.turn++;
        
        if(this.team == Team.WHITE)
            this.team = Team.BLACK;
        else
            this.team = Team.WHITE;
    }
}
