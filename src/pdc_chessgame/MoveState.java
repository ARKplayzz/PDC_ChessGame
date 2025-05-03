/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
public class MoveState
    {
        public MoveState(Pieces piece, Pieces capturedPiece, Tile from, Tile to, int turn)
        {
            this.piece = piece;
            this.capturedPiece = capturedPiece;
            this.from = from;
            this.to = to;
            this.moveNo = turn;
        }
        
        private Pieces piece;
        private Pieces capturedPiece;
        private Tile from;
        private Tile to;
        private int moveNo;
        
        @Override
        public String toString()
        {
            return (String)(this.piece.getPieceUnicode()+" "+this.from.getX()+","+this.from.getY()+" "+this.to.getX()+","+this.to.getY()+" "+this.moveNo);
        }
        
        public Pieces getPiece()
        {
            return this.piece;
        }
        
        public Pieces getCapturedPiece()
        {
            return this.capturedPiece;
        }
        
        public Tile getFromTile()
        {
            return this.from;
        }
        
        public Tile getToTile()
        {
            return this.to;
        }
        
        public int getMoveNumber()
        {
            return this.moveNo;
        }
    }