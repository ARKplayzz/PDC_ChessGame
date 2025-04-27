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
   
    
    // adding this so we can display captured pieces once we make it graphical + makes it %1 easier to check for a missing queen
    // tell me if this is a retarted idea
    private List<Pieces> capturedPieces;
    // moved this here so that the mateChecker can easily modify it
    public boolean checkmate = false;
    
    public int width;
    public int height;
    
    public Turn turnCounter = new Turn();
    
    public ChessBoard(int width, int height)
    {
        // init board
        this.board = new Tile[width][height];
        this.width = width;
        this.height = height;
        this.capturedPieces = new ArrayList<>();
        
        // Init all individual tiles
        // I changed it from i,j to x,y to hopefully clear some of the confusion we had last time
        //this is the dude who was causing issue ):<
        for (int y = 0; y < this.height; y++)
            for (int x = 0; x < this.width; x++)
                this.board[x][y] = new Tile(x, y);
        
        initialiseBoard();  //Could put this is a better space (can also reset board)
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
        setTile(new Pawn(0, 1, Team.BLACK), 0, 1);

        //setTile(new Rook(0, 0, Team.WHITE), 0, 0);
        //setTile(new Knight(1, 0, Team.WHITE), 1, 0);
        setTile(new Bishop(2, 0, Team.WHITE), 2, 0);
        setTile(new Queen(3, 0, Team.WHITE), 3, 0);
        setTile(new King(4, 0, Team.WHITE), 4, 0);
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
        //setTile(new Knight(6, 7, Team.BLACK), 6, 7);
        //setTile(new Rook(7, 7, Team.BLACK), 7, 7);
        setTile(new Pawn(7, 6, Team.WHITE), 7, 6);
    }
    
    public void setTile(Pieces p, int x, int y)
    {
        this.board[x][y].setPiece(p); //THIS USED TO CHECK IF NULL - It now overrides (kills pieces)
    }
    
    public boolean killTile(int x, int y) { // THIS KILLS A PIECE NOW (IT MAY BE BEST TO RENAME msoveTile & killTile to MoveTilePiece & killTilePiece)
        if (board[x][y].getPiece() != null) {
            board[x][y].deletePiece();
            return true;
        }
        return false;
    }
    
    public boolean moveTile(Input moveSet) 
    {
        Pieces targetPiece = this.board[moveSet.fromX][moveSet.fromY].getPiece();
        
        if (targetPiece instanceof King) // CHECK FOR CASTLE MOVE
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
            }
        }
     
        if (targetPiece instanceof Pawn)
        {
            int checkDirection = targetPiece.getPieceTeam() == Team.BLACK ? 1 : -1;
            
            Tile targetTile = getTile(moveSet.toX, moveSet.toY + checkDirection);
            Pieces targetPawn = targetTile.getPiece();
            
            if (targetPawn != null && //target is a piece
                targetPawn instanceof Pawn && // if target is a pawn
                targetPiece.getPieceTeam() != targetPawn.getPieceTeam()) // if target is an enemy
            {
                killTile(moveSet.toX , moveSet.toY + checkDirection); // dewit!
            }
            
            if (((Pawn) targetPiece).canPromotion(this))
            {
                setTile(Display.getPromotionPiece("Player", (Pawn) targetPiece), moveSet.fromX, moveSet.fromY);
            }
        }
        
        // add move to history
        this.turnCounter.addMoveToHistory(targetPiece, this.getTile(moveSet.fromX, moveSet.fromY), this.getTile(moveSet.toX, moveSet.toY));
        // actully do the move
        return this.board[moveSet.fromX][moveSet.fromY].movePieceTo(board[moveSet.toX][moveSet.toY]);
    
    }
    
    public Tile getTile(int x, int y)
    { // will return null if the tile is empty
        return this.board[x][y];
    }
    
    public Tile[][] getBoard()
    {
        return this.board;
    }
    
    public void printBoard()
    {
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
    
    public List<Tile> getPiecePath(Tile tileFrom, Tile tileTo) {
        
        List<Tile> path = new ArrayList<>();

        int xDirection = Integer.compare(tileTo.x - tileFrom.x, 0); // getting direction from 1 to 2
        int yDirection = Integer.compare(tileTo.y - tileFrom.y, 0);

        
        if (Math.abs(tileTo.x - tileFrom.x) > 1 && Math.abs(tileTo.y - tileFrom.y) > 1) { // pawn & knight dont have a path
            return path;
        }

        int currentX = tileFrom.x + xDirection;
        int currentY = tileFrom.y + yDirection;

        while (currentX != tileTo.x || currentY != tileTo.y) 
        {
            path.add(getTile(currentX, currentY));
            
            currentX += xDirection;
            currentY += yDirection;
        }

        return path;
    }
    
    public boolean isCheckmate(Team team) {
                
        King king = null;

        // Searches every tile for the king, not ideal, will need to come back to this
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Pieces targetPiece = this.getTile(x, y).getPiece();
                
                if (targetPiece instanceof King && targetPiece.getPieceTeam() == team) 
                {
                    king = (King) targetPiece;
                    break;
                }
            }
            if (king != null) //stop looking
            {
                break;
            }
        }
        
        if (king == null) // no king?
        {
            return false; 
        }
        if (!king.isCheck(this)) // if king isnt in check
        {
            return false;
        }
        if (!king.canMove(this).isEmpty()) // king has posible moves
        {
            return false;
        }
        
        List<Pieces> attackingPieces = king.getAttackingPieces(this);

        if (attackingPieces.size() > 1) // if more than one piece is checking the king then its jover
        {
            return true; 
        }
        
        Pieces enemy = attackingPieces.get(0); // the numpty causing the check
        
        List<Tile> attackPath = getPiecePath(getTile(enemy.x, enemy.y), getTile(king.x, king.y));
        
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Pieces targetPiece = this.getTile(x, y).getPiece();

                if (targetPiece != null && targetPiece.getPieceTeam() == team && !(targetPiece instanceof King)) 
                {
                    List<Tile> pieceMoveset = targetPiece.canMove(this);

                    if (pieceMoveset.contains(this.getTile(enemy.x, enemy.y))) // check if moveset contains a location kill enemy
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
