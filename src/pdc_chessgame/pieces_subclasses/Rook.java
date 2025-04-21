/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ARKen
 */
public class Rook extends Pieces {


    public Rook(Team pieceTeam) {
        
        super(pieceTeam == Team.BLACK ? "r" : "R");
        this.pieceTeam = pieceTeam;

    }
    
    @Override
    public List<Tile> canMove(ChessBoard board)
    {        
        List<Tile> possibleMoves = new ArrayList<>();

        // position
        int startX = 0;
        int startY = 0;

        int[][] directions = {
            {0, 1},   // up
            {0, -1},  // down
            {1, 0},   // right
            {-1, 0}   // left
        };

        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];
            int x = startX + dx;
            int y = startY + dy;

            while (x >= 0 && x < board.width && y >= 0 && y < board.height) {
                
                Tile tile = board.getTile(x, y);
                
                if (tile == null) break;

                Pieces targetPiece = tile.getPiece();

                if (targetPiece == null) { // if empty
                    
                    possibleMoves.add(tile); 
                } else {
                    
                    if (targetPiece.getPieceTeam() != this.getPieceTeam()) {
                        possibleMoves.add(tile); // is enemy (fine)
                    }
                    break; // hit a piece
                }

                x += dx;
                y += dy;
            }
        }

        return possibleMoves;
    }

        /*if (moveSet.isMoveStraight() && moveSet.isPathClear(board)) { // Straight path is clear?
            
            Pieces targetPiece = board.getTile(moveSet.toX, moveSet.toY).getPiece();
            
            if (targetPiece == null || targetPiece.getPieceTeam() != this.pieceTeam){
                return true;
            }
        }
        
        return false;*/
        
        
    }
}
