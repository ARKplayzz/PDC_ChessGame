/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

//import pdc_chessgame.*; //IS THIS THE BEST WAY TO DO THIS???? Idk sub classess suck

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew & Finlay
 */
public class Pawn extends Pieces {

    public Pawn(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "p" : "P", pieceTeam); // Need to confirm we are doing subclassess correctly
    }

    @Override
    public int[][] getDirection() //maybe remove this for pawn
    {
        return (this.getPieceTeam() == Team.WHITE) ? new int[][] {{0, 1}} : new int[][] {{0, -1}}; //idk this seems unessisary to have y dir in [0][1]
    }

    @Override
    public List<Tile> canMove(ChessBoard board) 
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        int y = this.y + getDirection()[0][1];
        
        Tile targetTile = board.getTile(this.x, y);
        Pieces targetPiece = targetTile.getPiece(); 
        
        if (isWithinBoard(this.x, y, board) && targetPiece == null) 
        {
            possibleMoves.add(targetTile);
            
            y = this.y + (2 * getDirection()[0][1]); // double jump
            
            targetTile = board.getTile(this.x, y);
            targetPiece = targetTile.getPiece(); 

            if (!board.turnCounter.hasPieceMoved(this) && targetPiece == null) 
            {
                possibleMoves.add(targetTile);
            }
        }
        
        // attacking 
        for (int dx = -1; dx <= 1; dx += 2) 
        {
            int x = this.x + dx;
            y = this.y + getDirection()[0][1];
            System.out.println("x>"+x+"y>"+ this.y);

            if (isWithinBoard(x, y, board)) 
            {
                System.out.println("x>"+x+"y>"+ this.y);
                targetTile = board.getTile(x, y);
                targetPiece = targetTile.getPiece();
                
                if (targetPiece != null && targetPiece.getPieceTeam() != this.getPieceTeam()) {
                    possibleMoves.add(targetTile);
                }
                
                // en passant bellow 
                //int checkDirection = getPieceTeam() == Team.WHITE ? 1 : -1;
                System.out.println("x>"+x+"y>"+ (this.y - getDirection()[0][1]));
                    
                Tile pawnTile = board.getTile(x, (this.y - getDirection()[0][1])); //check behind for enemy pawn
                Pieces targetPawn = pawnTile.getPiece();
                if (targetPawn != null){
                    System.out.println("EEEEpawn x>"+targetPawn.x+"pawn y>"+ targetPawn.y);

                }
                System.out.println("got here4");
               
                
                //System.out.println("x>"+x+"y>"+ this.y);
                //targetTile = board.getTile(x, this.y); // check next too
                //targetPiece = targetTile.getPiece(); 

                if (targetPawn instanceof Pawn) { // if target is a pawn
                    if (targetPawn.getPieceTeam() != getPieceTeam()) { // if target is an enemy
                        if (board.turnCounter.turnsSinceLastMoved(targetPawn) == 0) { // if target just moved
                            if (board.turnCounter.distanceLastMoved(targetPawn) == 2) { // if has only moved once
                                System.out.println("got here3");
                            }
                            System.out.println("got here2");
                        }
                        System.out.println("got here1");
                    }
                    System.out.println("got here0");
                    possibleMoves.add(targetTile);
                }
            }
        }
        System.out.println("posMoves"+ possibleMoves.toString());
        return possibleMoves;
    }
    
    public boolean PawnPromotion() {
        return false; //checks if a pawn upgrade is possible
    }
}