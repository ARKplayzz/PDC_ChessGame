/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew & Finlay
 */
public class King extends Pieces {

    public King(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "k" : "K", pieceTeam); // Need to confirm we are doing subclassess correctly
    }
    
    public boolean isCheck(ChessBoard board)
    {
        return isCheck(this.x, this.y, board);
    }
    
    public boolean isCheck(int x, int y, ChessBoard board)
    {
        //checks line of sight for pieces
        int[][] scanDirections = { 
        {0, 1},     // up
        {0, -1},    // down
        {1, 0},     // right
        {-1, 0},    // left
        {-1, -1},   // up left
        {1, -1},    // up right
        {-1, 1},    // down left
        {1, 1}      // down right
        };
        
        //checks for knights
        int[][] scanTiles = { 
        {2, 1},     // 2 right, 1 up
        {1, 2},     // 1 right, 2 up
        {-1, 2},    // 1 left, 2 up
        {-2, 1},    // 2 left, 1 up
        {-2, -1},   // 2 left, 1 down
        {-1, -2},   // 1 left, 2 down
        {1, -2},    // 1 right, 2 down
        {2, -1}     // 2 right, 1 down
        };
    
        for (int[] dir : scanDirections) 
        {
            int xDirection = dir[0];
            int yDirection = dir[1];
            
            int newX = x + xDirection; 
            int newY = y + yDirection;

            while (isWithinBoard(newX, newY, board)) 
            {
                Pieces kingPiece = this;

                if (kingPiece != null && kingPiece.getPieceTeam() != this.getPieceTeam())
                {
                    if (kingPiece.canMove(board).contains(board.getTile(newX, newY))) 
                    {
                        return true;
                    }
                }
                newX += xDirection;
                newY += yDirection;
            }
        }
        for (int[] dir : scanTiles) 
        {
            int xDirection = dir[0];
            int yDirection = dir[1];
            
            int newX = x + xDirection; 
            int newY = y + yDirection;

            if (isWithinBoard(newX, newY, board)) 
            {
                Tile targetTile = board.getTile(newX, newY); //these can be shrunken if tile did not exist (:
                Pieces targetPiece = targetTile.getPiece(); //these can be shrunken if tile did not exist (:

                if (targetPiece != null && targetPiece.getPieceTeam() != this.getPieceTeam())
                {
                    if (targetPiece.canMove(board).contains(board.getTile(newX, newY))) 
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public List<Pieces> getAttackingPieces(ChessBoard board) 
    {
        List<Pieces> attackers = new ArrayList<>();

        for (int x = 0; x < board.width; x++) 
        {
            for (int y = 0; y < board.height; y++) 
            {
                Pieces targetPiece = board.getTile(x, y).getPiece();

                if (targetPiece != null && targetPiece.getPieceTeam() != getPieceTeam()) // if enemy
                {
                    List<Tile> enemyMoveset = targetPiece.canMove(board);

                    if (enemyMoveset.contains(board.getTile(this.x, this.y))) 
                    {
                        attackers.add(targetPiece);
                    }
                }
            }
        }
        return attackers;
    }
    
    @Override
    public int[][] getDirection()
    {
        return new int[][]{
        {0, 1},     // up
        {0, -1},    // down
        {1, 0},     // right
        {-1, 0},    // left
        {-1, -1},   // up left
        {1, -1},    // up right
        {-1, 1},    // down left
        {1, 1}      // down right
        };
    } 
    
    @Override
    public List<Tile> canMove(ChessBoard board)
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        for (int[] dir : getDirection()) 
        {            
            int x = this.x + dir[0];
            int y = this.y + dir[1];

            if (isWithinBoard(x, y, board)) 
            {
                Tile targetTile = board.getTile(x, y);
                Pieces targetPiece = targetTile.getPiece();

                if (targetPiece == null || targetPiece.getPieceTeam() != this.getPieceTeam()) // if Tile empty or Contains enemy
                { 
                    if (!isCheck(x, y, board)) // King should not be able to move INTO check
                    {
                        //castle move
                        possibleMoves.add(targetTile); 
                    }
                } 
                
            }
        }
        //castle THIS NEEDS ALOT OF WORK
        if (board.turnCounter.pieceMoveCount(this) == 0 && !isCheck(board)) //if kings first move and not currently in check
        {
                        
            for (int dir : new int[]{1, -1})
            {
            int x = this.x + 1 * dir;
            
                while (isWithinBoard(x, this.y, board)) 
                {

                    Tile targetTile = board.getTile(x, this.y); //these can be shrunken if tile did not exist (:
                    Pieces targetPiece = targetTile.getPiece(); //these can be shrunken if tile did not exist (:

                    if (targetPiece != null) 
                    {
                        if(targetPiece instanceof Rook)
                        {
                            if (targetPiece.getPieceTeam() == this.getPieceTeam() && board.turnCounter.pieceMoveCount(targetPiece) == 0)
                            {
                                possibleMoves.add(targetTile = board.getTile(this.x + (dir * 2), this.y)); //king side Castle
                            }
                        }
                        else
                        {
                            break;
                        }
                    } 
                    else
                    {
                        if (isCheck(x, this.y, board))
                        {
                            break;   
                        }
                    }
                    x += dir;
                }
            }
        }
        return possibleMoves;
    }
}
