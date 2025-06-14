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
public class ChessBoard implements BoardState
{
    private Tile[][] board;
    private List<Piece> capturedPieces;
        
    private int width;
    private int height;
    
    private Turn turnCounter = new Turn();
    
    public ChessBoard(int width, int height)
    {
        // init board
        this.board = new Tile[width][height];
        this.width = width;
        this.height = height;
        this.capturedPieces = new ArrayList<>();
        
        // init tiles of board
        for (int y = 0; y < this.height; y++) 
        {
            for (int x = 0; x < this.width; x++)
            {
                this.board[x][y] = new Tile(x, y);
            }
        }
        initialiseBoard();
    }
    
    private void initialiseBoard() 
    {
        //place white pawns
        for (int row = 0; row < 8; row++) 
        {
            setTile(new Pawn(row, 1, Team.WHITE), row, 1);
        }

        //place black pawns
        for (int row = 0; row < 8; row++) 
        {
            setTile(new Pawn(row, 6, Team.BLACK), row, 6);
        }
        
        //place white back row
        setTile(new Rook(0, 0, Team.WHITE), 0, 0);
        setTile(new Knight(1, 0, Team.WHITE), 1, 0);
        setTile(new Bishop(2, 0, Team.WHITE), 2, 0);
        setTile(new King(3, 0, Team.WHITE), 3, 0);
        setTile(new Queen(4, 0, Team.WHITE), 4, 0);
        setTile(new Bishop(5, 0, Team.WHITE), 5, 0);
        setTile(new Knight(6, 0, Team.WHITE), 6, 0);
        setTile(new Rook(7, 0, Team.WHITE), 7, 0);

        //place black back row
        setTile(new Rook(0, 7, Team.BLACK), 0, 7);
        setTile(new Knight(1, 7, Team.BLACK), 1, 7);
        setTile(new Bishop(2, 7, Team.BLACK), 2, 7);
        setTile(new King(3, 7, Team.BLACK), 3, 7);
        setTile(new Queen(4, 7, Team.BLACK), 4, 7);
        setTile(new Bishop(5, 7, Team.BLACK), 5, 7);
        setTile(new Knight(6, 7, Team.BLACK), 6, 7);
        setTile(new Rook(7, 7, Team.BLACK), 7, 7);
    }
    
    @Override
    public Tile getTile(int x, int y)
    { // will return null if the tile is empty
        return this.board[x][y];
    }
        
    public void setTile(Piece p, int x, int y)
    {
        getTile(x, y).setPiece(p);
    }
    
    @Override
    public boolean isWithinBoard(int x, int y) 
    { // returns true if provided x and y are within the bounds of the board
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }
    
    public boolean captureTile(int x, int y) 
    { // captures the specified piece, returns false if it fails
        if (getTile(x, y).getPiece() != null) 
        {
            Piece capturedPiece = getTile(x, y).getPiece();
            this.capturedPieces.add(capturedPiece);
            getTile(x, y).deletePiece();
            return true;
        }
        return false;
    }
    
    public boolean moveTile(Move moveSet) 
    {
        // name is misleading, used by the board to move the piece on the tile
        Piece targetPiece = getTile(moveSet.getFromX(), moveSet.getFromY()).getPiece();
        
        // only one special can occur per move (ordered in likelyhood)
        if (tryCastle(targetPiece, moveSet)) 
        {} 
        else if (tryEnPessant(targetPiece, moveSet)) 
        {}
        
        // add move to history
        
        this.turnCounter.addMoveToHistory(targetPiece, getTile(moveSet.getToX(), moveSet.getToY()).getPiece(), getTile(moveSet.getFromX(), moveSet.getFromY()), getTile(moveSet.getToX(), moveSet.getToY()), moveSet.getInput());
        
        // check if a piece needs to be captured before its Tile is overridden
        if (getTile(moveSet.getToX(), moveSet.getToY()).getPiece() != null){
            captureTile(moveSet.getToX(), moveSet.getToY());
        }
        // move the piece
        return getTile(moveSet.getFromX(), moveSet.getFromY()).movePieceTo(getTile(moveSet.getToX(), moveSet.getToY()));
    
    }
    
    // try to castle
    private boolean tryCastle(Piece piece, Move moveSet)
    {
        if (piece instanceof King)
        {
            //find distance
            int distanceX = moveSet.getToX() - moveSet.getFromX();

            if (Math.abs(distanceX) == 2)  //check if move is a castle move
            {
                int x = moveSet.getFromY(); 
                int rookFromX, rookToX;

                if (distanceX > 0) 
                {
                    // King side
                    rookFromX = 7; 
                    rookToX = moveSet.getToX() - 1; // rook is left of the king
                } 
                else 
                {
                    // queen side
                    rookFromX = 0;  
                    rookToX = moveSet.getToX() + 1; // rook is right of the king
                    
                }
                this.board[rookFromX][x].movePieceTo(board[rookToX][x]);// do the castle
                return true;
            }
        }
        return false; // castle failed
    }
    
    // try to perform an en pessent
    private boolean tryEnPessant(Piece piece, Move moveSet)
    {
        if (piece instanceof Pawn)
        {
            int direction = piece.getPieceTeam() == Team.BLACK ? 1 : -1;
            
            Tile targetTile = getTile(moveSet.getToX(), moveSet.getToY() + direction);// get the target tile
            Piece targetPawn = targetTile.getPiece();// get the target
            
            if (targetPawn != null && //target is a piece
                targetPawn instanceof Pawn && // if target is a pawn
                piece.getPieceTeam() != targetPawn.getPieceTeam()) // if target is an enemy
            {
                captureTile(moveSet.getToX() , moveSet.getToY() + direction); // dewit!
                return true;
            }
        }
        return false;
    }
    
    public boolean isPawnPromotable()
    { // check to see if a pawn can turn into a queen
        Piece targetPiece = this.turnCounter.getPriorMove(0).getPiece();
        return targetPiece instanceof Pawn && ((Pawn) targetPiece).canPromotion(this);
    }
    
    public void promotePawn(PawnOption promotionPiece, Pawn pawn)
    { // promoting a pawn
        Piece newPiece = null;
        
        int x = pawn.getX();
        int y = pawn.getY();
        Team team = pawn.getPieceTeam();

        switch (promotionPiece) 
        {
            case ROOK -> newPiece = new Rook(x, y, team);
            case BISHOP -> newPiece = new Bishop(x, y, team);
            case KNIGHT -> newPiece = new Knight(x, y, team);
            case QUEEN -> newPiece = new Queen(x, y, team);
        }

        if (newPiece != null) {
            setTile(newPiece, x, y);
        }
    }
    
    public Turn getHistory()
    { // get the turn history
        return this.turnCounter;
    }
    
    @Override
    public int getHeight()
    {
        return this.height;
    }
    
    @Override
    public int getWidth()
    {
        return this.width;
    }
    
    @Override
    public int getPieceMoveCount(Piece piece) 
    {
        return turnCounter.getPieceMoveCount(piece);
    }
    
    @Override
    public int turnsSinceLastMoved(Piece piece) 
    {
        return turnCounter.turnsSinceLastMoved(piece);
    }
    
    @Override
    public boolean hasPieceMoved(Piece piece) 
    {
        return turnCounter.hasPieceMoved(piece);
    }
    
    @Override
    public MoveState getPieceLastMove(Piece piece) 
    {
        return turnCounter.getPieceLastMove(piece);
    }
    
    // switch to the next turn
    public void getNextTurn() 
    {
        turnCounter.nextTurn();
    }
    
    public Team getCurrentTeam() 
    {
        return turnCounter.getTeam();
    }
    
    // return the move history as a string
    public String getHistoryString() 
    {
        StringBuilder sb = new StringBuilder();
        Turn history = this.getHistory();
        
        for (int i = 0; i < history.getMoveCount(); i++) 
        {
            sb.append(i).append(1).append(". ").append(history.toString(i)).append("\n");
        }
        return sb.toString();
    }
    
    private List<Tile> getPiecePath(Tile tileFrom, Tile tileTo) 
    {
        List<Tile> path = new ArrayList<>();

        int xDirection = Integer.compare(tileTo.getX() - tileFrom.getX(), 0); // getting direction from 1 to 2
        int yDirection = Integer.compare(tileTo.getY() - tileFrom.getY(), 0);

        
        if (Math.abs(tileTo.getX() - tileFrom.getX()) > 1 && Math.abs(tileTo.getY() - tileFrom.getY()) > 1) { // pawn & knight dont have a path
            return path;
        }

        int currentX = tileFrom.getX() + xDirection;
        int currentY = tileFrom.getY() + yDirection;

        while (currentX != tileTo.getX() || currentY != tileTo.getY()) 
        {
            path.add(getTile(currentX, currentY));
            
            currentX += xDirection;
            currentY += yDirection;
        }

        return path;
    }
    
    public boolean undoMove() //boolean incase history is non existant (outside current usecase but will save time in assignment 2)
    {
        if (turnCounter.getMoveCount() < 1) 
        {
            return false; // empty
        }
        
        MoveState priorMoveState = turnCounter.deleteRecentMove(); 
        
        Piece movedPiece = priorMoveState.getPiece();

        Tile fromTile = priorMoveState.getFromTile();
        Tile toTile = priorMoveState.getToTile();

        Piece capturedPiece = priorMoveState.getCapturedPiece();

        Piece currentPiece = toTile.getPiece(); 
        if (currentPiece != null && currentPiece.getClass() != movedPiece.getClass()) //check if piece has changed during movement (pawn promotion)
        {
        toTile.setPiece(movedPiece);
        }

        toTile.movePieceTo(fromTile); //return piece

        if (capturedPiece != null) //return captured piece + remove from list
        {
            toTile.setPiece(capturedPiece);
            this.capturedPieces.remove(capturedPiece);
        }
        if (movedPiece instanceof King && Math.abs(toTile.getX() - fromTile.getX()) == 2) // castle check
        {
            int rookToX = toTile.getX() > fromTile.getX() ? fromTile.getX() + 1 : fromTile.getX() - 1;
            int rookFromX = toTile.getX() > fromTile.getX() ? 7 : 0;

            getTile(rookToX, fromTile.getY()).movePieceTo(getTile(rookFromX, fromTile.getY())); //return rook
        }
        return true;
    }

   
    private King getKing(Team team) 
    {
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Piece piece = this.getTile(x, y).getPiece();
                
                if (piece instanceof King && piece.getPieceTeam() == team) 
                {
                    return (King) piece;
                }
            }
        }
        return null;
    }
    
    public boolean isInCheck(Team team) 
    {
        King king = getKing(team);
        return king.isCheck(this);
    }
    
    //check to see if the king is in checkmate
    public boolean isCheckmate(Team team) 
    {        
        King king = getKing(team);

        if (king == null) // no king? may as well keep for extended usecase in asignment 2
        {
            return true; 
        }
        if (!king.isCheck(this)) // if king isnt in check
        {
            return false;
        }
        if (!king.canMove(this).isEmpty()) // king has posible moves
        {
            return false;
        }
        
        List<Piece> attackingPieces = king.getAttackingPieces(this);

        if (attackingPieces.size() > 1) // if more than one piece is checking the king then its jover
        {
            return true; 
        }
        
        Piece enemy = attackingPieces.get(0); // the numpty causing the check
        
        List<Tile> attackPath = getPiecePath(getTile(enemy.getX(), enemy.getY()), getTile(king.getX(), king.getY()));
        
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Piece targetPiece = getTile(x, y).getPiece();

                if (targetPiece != null && targetPiece.getPieceTeam() == team && !(targetPiece instanceof King)) 
                {
                    List<Tile> pieceMoveset = targetPiece.canMove(this);

                    if (pieceMoveset.contains(getTile(enemy.getX(), enemy.getY()))) // check if moveset contains a location kill enemy
                    { 
                        return false;
                    }

                    for (Tile blockTile : attackPath) // check if moveset contains a location that blocks the path
                    {
                        if (pieceMoveset.contains(blockTile)) 
                        {
                            return false;
                        }
                    }
                }
            }
        }
        return true; // YAHTZEE!!! 
    }
}
