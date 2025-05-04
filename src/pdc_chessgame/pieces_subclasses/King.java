/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Knight, a subclass of Piece.
 * Includes move rules, capturing, castling, check & enemy logic.
 * 
 * @author Andrew & Finlay
 */
public class King extends Piece {

    public King(int x, int y, Team pieceTeam) 
    {
        super(x, y, pieceTeam == Team.BLACK ? "k" : "K", pieceTeam);
    }
    
    public boolean isCheck(BoardState board) // true if in check
    {
        return isCheck(getX(), getY(), board);
    }
    
    private boolean isCheck(int x, int y, BoardState board)
    {
        return !getAttackingPieces(x, y, board).isEmpty();
    }
    
    public List<Piece> getAttackingPieces(BoardState board)
    {
        return getAttackingPieces(getX(), getY(), board);
    }
    
    private List<Piece> getAttackingPieces(int x, int y, BoardState board) 
    {
        List<Piece> attackers = new ArrayList<>();

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
    
        // checks each direction for straight enemys
        for (int[] dir : scanDirections) 
        {
            int xDirection = dir[0];
            int yDirection = dir[1];
            
            int newX = x + xDirection;
            int newY = y + yDirection;

            // scans until it hits a piece or the edge of the board
            while (board.isWithinBoard(newX, newY)) 
            {
                Tile targetTile = board.getTile(newX, newY); 
                Piece targetPiece = targetTile.getPiece();
                
                if (targetPiece != null) // if Tile empty or Contains enemy
                { 
                    if (targetPiece.getPieceTeam() != getPieceTeam())
                    {
                        // Bulkeir code but more effiencent: checks if a logically attacking piece is in the way
                        if (targetPiece instanceof Queen ||
                            (targetPiece instanceof Rook && (xDirection == 0 || yDirection == 0)) ||
                            (targetPiece instanceof Bishop && xDirection != 0 && yDirection != 0) ||
                            (targetPiece instanceof King && Math.abs(newX - x) <= 1 && Math.abs(newY - y) <= 1) ||
                            (targetPiece instanceof Pawn && targetPiece.canMove(board).contains(board.getTile(x, y))))
                        {
                            attackers.add(targetPiece);
                        }
                    }
                    break; // break upon hitting any piece
                } 

                newX += xDirection;
                newY += yDirection;
            }
        }
        
        // checks knight positions for enemy knights
        for (int[] dir : scanTiles)
        {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (board.isWithinBoard(newX, newY)) 
            {
                Tile targetTile = board.getTile(newX, newY);
                Piece targetPiece = targetTile.getPiece();

                if (targetPiece != null && targetPiece.getPieceTeam() != getPieceTeam() && targetPiece instanceof Knight)
                {
                    attackers.add(targetPiece);
                }
            }
        }
        return attackers;
    }
    
    @Override
    public int[][] getDirection() //returns the directional requirements for a move (x,y)
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
    public List<Tile> canMove(BoardState board) //returns a list of all tiles that the piece can move to
    {        
        List<Tile> possibleMoves = new ArrayList<>();
        
        // checks all directions
        for (int[] dir : getDirection()) 
        {            
            int x = getX() + dir[0];
            int y = getY() + dir[1];

            if (board.isWithinBoard(x, y)) 
            {
                Tile targetTile = board.getTile(x, y);
                Piece targetPiece = targetTile.getPiece();
                
                // king can move if tile is empty or has enemy, and tile is not in check
                if (targetPiece == null || targetPiece.getPieceTeam() != getPieceTeam()) // if Tile empty or Contains enemy
                { 
                    if (!isCheck(x, y, board)) // King should not be able to move INTO check
                    {  
                        possibleMoves.add(targetTile); 
                    }
                } 
            }
        }
        
        // check if a castle move is possible
        if (board.getPieceMoveCount(this) == 0 && !isCheck(board)) //if kings first move and not currently in check
        {     
            for (int dir : new int[]{1, -1})
            {
            int x = getX() + 1 * dir;
            
                // scans either side of the king 
                while (board.isWithinBoard(x, getY())) 
                {
                    Tile targetTile = board.getTile(x, getY()); 
                    Piece targetPiece = targetTile.getPiece(); 

                    if (targetPiece != null) 
                    {
                        if (targetPiece instanceof Rook) 
                        {
                            // if rook is on same team and has not moved
                            if (targetPiece.getPieceTeam() == getPieceTeam() && board.getPieceMoveCount(targetPiece) == 0)
                            {
                                targetTile = board.getTile(getX() + (dir * 2), getY());
                                possibleMoves.add(targetTile); //king side Castle
                            }
                        }
                        else
                        {
                            // stop scanning if somethings in the way
                            break;
                        }
                    } 
                    else
                    {
                        // cannot castle through a checked tile
                        if (isCheck(x, getY(), board))
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

    @Override
    public String getName() 
    {
        return "King";
    }
}
