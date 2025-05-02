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
public class ChessBoard 
{
    /*
        When changing to graphical for part2 make a tile class that
        holds piece and colour.
    */
    @SuppressWarnings("FieldMayBeFinal")
    
    private Tile[][] board;
    private List<Pieces> capturedPieces;
        
    private int width;
    private int height;
    
    public Turn turnCounter = new Turn();
    
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
        //    setTile(new Pawn(row, 1, Team.WHITE), row, 1);
        }

        //place black pawns
        for (int row = 0; row < 8; row++) 
        {
        //    setTile(new Pawn(row, 6, Team.BLACK), row, 6);
        }
        
        //place white back row
        setTile(new Rook(4, 2, Team.WHITE), 4, 2);

        //setTile(new Rook(0, 0, Team.WHITE), 0, 0);
        //setTile(new Knight(1, 0, Team.WHITE), 1, 0);
        //setTile(new Bishop(2, 0, Team.WHITE), 2, 0);
        //setTile(new Queen(3, 0, Team.WHITE), 3, 0);
        setTile(new King(4, 0, Team.WHITE), 4, 0);
        //setTile(new Bishop(5, 0, Team.WHITE), 5, 0);
        //setTile(new Knight(6, 0, Team.WHITE), 6, 0);
        //setTile(new Rook(7, 0, Team.WHITE), 7, 0);

        //place black back row
        setTile(new Queen(4, 6, Team.BLACK), 4, 6);

        
        setTile(new Rook(0, 7, Team.BLACK), 0, 7);
        setTile(new Knight(1, 7, Team.BLACK), 1, 7);
        setTile(new Bishop(2, 7, Team.BLACK), 2, 7);
        setTile(new King(3, 7, Team.BLACK), 3, 7);
        setTile(new Queen(4, 7, Team.BLACK), 4, 7);
        setTile(new Bishop(5, 7, Team.BLACK), 5, 7);
        setTile(new Knight(6, 7, Team.BLACK), 6, 7);
        setTile(new Rook(7, 7, Team.BLACK), 7, 7);
    }
    
    public Tile getTile(int x, int y)
    { // will return null if the tile is empty
        return this.board[x][y];
    }
        
    public void setTile(Pieces p, int x, int y)
    {
        getTile(x, y).setPiece(p);
    }
    
    public boolean captureTile(int x, int y) 
    {
        if (getTile(x, y).getPiece() != null) 
        {
            Pieces capturedPiece = getTile(x, y).getPiece();
            this.capturedPieces.add(capturedPiece);
            getTile(x, y).deletePiece();
            return true;
        }
        return false;
    }
    
    public boolean moveTile(Input moveSet) 
    {
        Pieces targetPiece = getTile(moveSet.fromX, moveSet.fromY).getPiece();
        
        // only one special can occur per move (ordered in likelyhood)
        if (tryCastle(targetPiece, moveSet)) {
        } else if (tryEnPessant(targetPiece, moveSet)) {
        } else if (tryPromotePawn(targetPiece, moveSet)) {
        }
        
        // add move to history
        this.turnCounter.addMoveToHistory(targetPiece, getTile(moveSet.toX, moveSet.toY).getPiece(), getTile(moveSet.fromX, moveSet.fromY), getTile(moveSet.toX, moveSet.toY));
        // check if a piece needs to be captured before its Tile is overridden
        if (getTile(moveSet.toX, moveSet.toY).getPiece() != null){
            captureTile(moveSet.toX, moveSet.toY);
        }
        // move the piece
        return getTile(moveSet.fromX, moveSet.fromY).movePieceTo(getTile(moveSet.toX, moveSet.toY));
    
    }
    
    private boolean tryCastle(Pieces piece, Input moveSet)
    {
        if (piece instanceof King)
        {
            int distanceX = moveSet.toX - moveSet.fromX;

            if (Math.abs(distanceX) == 2)  //check if move is a castle move
            {
                int x = moveSet.fromY; 
                int rookFromX, rookToX;

                if (distanceX > 0) 
                {
                    // King side
                    rookFromX = 7; 
                    rookToX = moveSet.toX - 1; // rook is left of the king
                } 
                else 
                {
                    // queen side
                    rookFromX = 0;  
                    rookToX = moveSet.toX + 1; // rook is right of the king
                    
                }
                this.board[rookFromX][x].movePieceTo(board[rookToX][x]);
                return true;
            }
        }
        return false;
    }
    
    private boolean tryEnPessant(Pieces piece, Input moveSet)
    {
        if (piece instanceof Pawn)
        {
            int direction = piece.getPieceTeam() == Team.BLACK ? 1 : -1;
            
            Tile targetTile = getTile(moveSet.toX, moveSet.toY + direction);
            Pieces targetPawn = targetTile.getPiece();
            
            if (targetPawn != null && //target is a piece
                targetPawn instanceof Pawn && // if target is a pawn
                piece.getPieceTeam() != targetPawn.getPieceTeam()) // if target is an enemy
            {
                captureTile(moveSet.toX , moveSet.toY + direction); // dewit!
                return true;
            }
        }
        return false;
    }
    
    private boolean tryPromotePawn(Pieces piece, Input moveSet)
    {
        if (piece instanceof Pawn && ((Pawn) piece).canPromotion(this))
        {
            setTile(ChessGame.getPromotionPiece("Player", (Pawn) piece), moveSet.fromX, moveSet.fromY);
            return true;
        }
        return false;
    }
    
    public Tile[][] getBoard()
    {
        return this.board;
    }
    
    public int getHeight()
    {
        return this.height;
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public void displayBoard()
    { //update this to use tile.white so we don't have to run isOdd 4 times every time we print
        // again changed from i,j to x,y to clear up confusion
        for(int y = 0; y < this.height; y++)
        {
            for(int k = 1; k < 4; k++)
            { // three rows
                if(k == 2)
                    System.out.print(" "+(y+1)+"  "); // new row labels based on y
                else
                    System.out.print("    ");

                for(int x = 0; x < this.width; x++) // inner loop over what were rows (now columns)
                {
                    if(k == 2 && this.board[x][y].getPiece() != null)
                    {
                        if(GameTools.isOdd(x+1) == GameTools.isOdd(y+1))
                            System.out.print(" ■ "+ this.board[x][y].getPiece().getPieceUnicode() +" ■");
                        else
                            System.out.print("   "+ this.board[x][y].getPiece().getPieceUnicode() +"  ");
                    }
                    else
                    {
                        if(GameTools.isOdd(x+1) == GameTools.isOdd(y+1))
                            System.out.print(" ■ ■ ■");
                        else
                            System.out.print("      ");
                    }
                }
                System.out.print("\n");
            }
        }
        
        System.out.print("\n    ");
        for(int i = 0; i < this.width; i++)
        {
            System.out.print("   "+(char)(i+65)+"  ");
        }
        System.out.print("\n");
    }
    
    private List<Tile> getPiecePath(Tile tileFrom, Tile tileTo) {
        
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
        
        Pieces movedPiece = priorMoveState.getPiece();

        Tile fromTile = priorMoveState.getFromTile();
        Tile toTile = priorMoveState.getToTile();

        Pieces capturedPiece = priorMoveState.getCapturedPiece();

        Pieces currentPiece = toTile.getPiece(); 
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

    
    // alternative: storing kingLocation within board...?
    private King getKing(Team team) 
    {
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Pieces piece = this.getTile(x, y).getPiece();
                
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
        System.out.println("king possible moves> "+ king.canMove(this));
        if (!king.canMove(this).isEmpty()) // king has posible moves
        {
            System.out.println("getting fucked here");
            return false;
        }
        
        List<Pieces> attackingPieces = king.getAttackingPieces(this);

        System.out.println("attackingPieces > " + attackingPieces.size());
        if (attackingPieces.size() > 1) // if more than one piece is checking the king then its jover
        {
            return true; 
        }
        
        Pieces enemy = attackingPieces.get(0); // the numpty causing the check
        
        List<Tile> attackPath = getPiecePath(getTile(enemy.getX(), enemy.getY()), getTile(king.getX(), king.getY()));
        
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Pieces targetPiece = getTile(x, y).getPiece();

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
