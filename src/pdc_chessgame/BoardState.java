/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
public interface BoardState // interface used for the chessboard
{
    // all these methods do exactly what they say they do
    Tile getTile(int x, int y);
    int getWidth();
    int getHeight();
    
    boolean hasPieceMoved(Piece piece);
    int turnsSinceLastMoved(Piece piece);
    MoveState getPieceLastMove(Piece piece);
    int getPieceMoveCount(Piece piece);
    
    boolean isWithinBoard(int x, int y);
}
