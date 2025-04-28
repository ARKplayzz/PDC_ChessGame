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
    
    // not completely convinced about leaving this private, make it public if you need to, (Modify lastMove if you do)
    @SuppressWarnings("FieldMayBeFinal")
    private List<Move> moveHistory;
    
    public Turn()
    {
        this.moveHistory = new ArrayList<>();
    }
    
    public void addMoveToHistory(Pieces p, Tile from, Tile to)
    {
        Move m = new Move(p, from, to);
        this.moveHistory.add(m);
    }
    
    public int getMoveCount()
    {
        return this.moveHistory.size();
    }
    
    public Move getPriorMove(int priorMoveNumber) // not last move, one before for undo indexing
    {
        return this.moveHistory.get(this.moveHistory.size() - priorMoveNumber);
    }
    
    private int lastMoveNum(Pieces p)
    {
        if(p == null)
            return -1;
        int id = -1;
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).piece.equals(p) /*&& m.moveNo < this.moveHistory.get(i).moveNo*/)
            {
                id = i;
            }
        }
        return id;
    }
    
    private Move lastMove(Pieces p)
    {
        Move m = new Move(null, null, null);
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).piece.equals(p) /*&& m.moveNo < this.moveHistory.get(i).moveNo*/)
            {
                m = this.moveHistory.get(i);
            }
        }
        if(m.piece == null)
            return null;
        return m;
    }
    
    // returns the amount of distance the specified piece moved on its last turn
    public int distanceLastMoved(Pieces p) // MIGHT NOT BE COMPLETLY FUNCTIONAL
    {
        Move m = this.lastMove(p);
        return GameTools.distanceBetween(m.from.getX(), m.to.getX(), m.from.getY(), m.to.getY());
    }
    
    // returns the amount of turns since the specified piece moved, returns 1 if the move was last turn
    public int turnsSinceLastMoved(Pieces p)
    {
        Move m = this.lastMove(p);
        if(m != null) 
        {
            return this.lastMoveNum(p) - this.turn;
        }
        return 0;
    }
    
    public boolean historyContains(Move m)
    {
        return this.moveHistory.contains(m);
    }
    
    public boolean hasPieceMoved(Pieces p)
    {
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).piece.equals(p))
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
            if(this.moveHistory.get(i).piece.equals(p))
            {
                n++;
            }
        }
        return n;
    }
    
    public void printMoveHistory()
    {
        System.out.println("Move history:\nPIECE    FROM    TO"); //turn removed
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
