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
    
    // add movehistory here
    public class move
    {
        public move(Pieces p, Tile from, Tile to, int turn)
        {
            this. piece = p;
            this.from = from;
            this.to = to;
            this.moveNo = turn;
        }
        
        public Pieces piece;
        public Tile from;
        public Tile to;
        int moveNo;
        
        @Override
        public String toString()
        {// modify the spacing a little later
            return (String)(this.piece.getPieceUnicode()+" "+this.from.x+","+this.from.y+" "+this.to.x+","+this.to.y+" "+this.moveNo);
        }
    }
    
    // not completely convinced about leaving this private, make it public if you need to, (Modify lastMove if you do)
    private List<move> moveHistory;
    
    public Turn()
    {
        this.moveHistory = new ArrayList<>();
    }
    
    public void addMoveToHistory(Pieces p, Tile from, Tile to)
    {
        move m = new move(p, from, to, this.turn);
        this.moveHistory.add(m);
    }
    
    private move lastMove(Pieces p)
    {
        move m = new move(null, null, null, -1);
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).piece.equals(p) && m.moveNo < this.moveHistory.get(i).moveNo)
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
        move m = this.lastMove(p);
        return GameTools.distanceBetween(m.from.x, m.to.x, m.from.y, m.to.y);
    }
    
    // returns the amount of turns since the specified piece moved, returns 1 if the move was last turn
    public int turnsSinceLastMoved(Pieces p)
    {
        move m = this.lastMove(p);
        if(m != null)
            return m.moveNo - this.turn;
        return 0;
    }
    
    public boolean historyContains(move m)
    {
        return this.moveHistory.contains(m);
    }
    
    public boolean hasMoved(Pieces p)
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
