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

// class used for storing the moveHistory and handling turn transitions
public class Turn 
{
    // current turn number and current team taking its turn
    private int turn = 0;
    private Team team = Team.WHITE;
    
    // do not make this final
    @SuppressWarnings("FieldMayBeFinal")
    private List<MoveState> moveHistory; // this is a list of movestates recording every action taken by the players
    
    public Turn()
    {
        this.moveHistory = new ArrayList<>();
    }
    
    public void addMoveToHistory(Piece piece, Piece capturedPiece, Tile from, Tile to, String input)
    { // add a new entry to the history list
        MoveState m = new MoveState(piece, capturedPiece, from, to, this.turn, input);
        this.moveHistory.add(m);
    }
    
    public int getMoveCount()
    { // return the amount of elements int the move history
        return this.moveHistory.size();
    }
    
    public List<MoveState> getMoveHistory() 
    {
        return new ArrayList<>(this.moveHistory);
    }
    
    public MoveState getPriorMove(int priorMoveNumber) // not last move, one before for undo indexing
    {
        if (priorMoveNumber < 0 || priorMoveNumber > this.moveHistory.size()) 
        {
            return null;
        }
        
        if (priorMoveNumber == 0 && !this.moveHistory.isEmpty()) 
        {
            return this.moveHistory.get(this.moveHistory.size() - 1);
        }
        
        return this.moveHistory.get(this.moveHistory.size() - priorMoveNumber);
    }

    public MoveState getPieceLastMove(Piece p) //gets the last move for a piece
    {
        MoveState m = new MoveState(null, null, null, null, -1, "");
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

    // returns the amount of turns since the specified piece moved, returns 1 if the move was last turn
    public int turnsSinceLastMoved(Piece p)
    {
        MoveState m = this.getPieceLastMove(p);
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
    
    public boolean hasPieceMoved(Piece p) // returns true if the specified piece has moved at some point
    {
        for(int i = 0; i < this.moveHistory.size(); i++)
        {// just loops through the history
            if(this.moveHistory.get(i).getPiece().equals(p))
            {
                return true;
            }
        }
        return false;
    }
    
    public int getPieceMoveCount(Piece p) // returns the amount of times the specified piece has moved
    {
        int n = 0;
        for(int i = 0; i < this.moveHistory.size(); i++)
        {
            if(this.moveHistory.get(i).getPiece().equals(p))
            { // just counting them by incrementing n
                n++;
            }
        }
        return n;
    }

    public void deleteMoveHistory()
    { // clear the move history
        this.moveHistory.clear();
    }
    
    public MoveState getHistoryEntry(int i)
    { // get the specified entry in the history
        return this.moveHistory.get(i);
    }
    
    public int getTurn()
    { // get the current round number
        return this.turn;
    }
    
    public String toString(int i)
    { // return the specified memeber of the move history formatted into a string
        return (String)(this.moveHistory.get(i).getPiece().getPieceTeam().teamName() +" "+ this.moveHistory.get(i).getPiece().getName()+" From: "+(char) (this.moveHistory.get(i).getFromTile().getX()+ 65) +""+this.moveHistory.get(i).getFromTile().getY() +" To: "+(char) (this.moveHistory.get(i).getToTile().getX()+ 65) +""+this.moveHistory.get(i).getToTile().getY());
    }
    
    public Team getTeam()
    { // return current active team
        return this.team;
    }
    
    public void nextTurn() //increment turn count and swap current team
    {
        this.turn++;
        this.team = this.team == Team.BLACK ? Team.WHITE : Team.BLACK; 
    }
}
