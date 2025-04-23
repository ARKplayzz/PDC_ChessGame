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
            setTile(new Pawn(row, 1, Team.WHITE), row, 1);
        }

        //place black pawns
        for (int row = 0; row < 8; row++) 
        {
            setTile(new Pawn(row, 6, Team.BLACK), row, 6);
        }

        setTile(new Pawn(0, 3, Team.BLACK), 0, 3);
        
        //place white back row
        setTile(new Rook(0, 0, Team.WHITE), 0, 0);
        setTile(new Knight(1, 0, Team.WHITE), 1, 0);
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
        setTile(new Knight(6, 7, Team.BLACK), 6, 7);
        setTile(new Rook(7, 7, Team.BLACK), 7, 7);
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
        
        if (targetPiece instanceof Rook) // CHECK FOR CASTLE MOVE
        {
            if (turnCounter.pieceMoveCount(targetPiece) == 0 && // if rook hassent moved
                (targetPiece.getPieceTeam() == Team.BLACK ? 2 : -2) == (moveSet.fromX - moveSet.toX)) //if the move is a castling move
            {
                int kingDirection = targetPiece.getPieceTeam() == Team.BLACK ? 1 : -1; //direction king is from moveTo
                
                if (getTile(moveSet.toX + kingDirection, moveSet.toY + kingDirection).getPiece() instanceof King)  //if the tile next too is a king
                    { 
                    King king = (King) getTile(moveSet.toX, moveSet.toY).getPiece(); 
                    int kingMoveDir = targetPiece.getPieceTeam() == Team.BLACK ? -2 : 2; //direction king will moveTo

                    if (turnCounter.pieceMoveCount(king) == 0 && // check if the king has moved
                        !king.isCheck(this) && // check if the current king tile is in check
                        !king.isCheck(king.x + kingMoveDir, king.y, this)) // check if the new king tile is in check
                    {
                        moveTile(Input.getMove(king.x, king.y, king.x + kingMoveDir, king.y)); //check if this overrides current move? shouldent...
                    }
                }
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
    
    // these two functions are useful for mate detection rook, queen and bishop
    // gets all tiles in all four directions until it hits a unit
    
    
    public List<Tile> getTilesLine(int x, int y) //Not sure if this is nessisary anymore
    {
        List<Tile> linearTiles = new ArrayList<>();
        
        //right
        for(int i = x; i < this.width; i++)
            if(this.getTile(i, y).getPiece() == null && linearTiles.add(this.getTile(i, y)))
                linearTiles.add(this.getTile(i, y)); 
            else break;
        //left
        for(int i = x; i >= 0; i--)
            if(this.getTile(i, y).getPiece() == null)
                linearTiles.add(this.getTile(i, y));
            else break;
        //up
        for(int i = y; i >= 0; i--)
            if(this.getTile(x, i) == null)
                linearTiles.add(this.getTile(x, i));
            else break;
        //up
        for(int i = y; i < this.height; i++)
            if(this.getTile(x, i) == null)
                linearTiles.add(this.getTile(x, i));
            else break;
        
        return linearTiles;
    }
    
    public List<Tile> getTilesDiagonal() //Not sure if this is nessisary anymore
    {
        List<Tile> diagonalTiles = new ArrayList<>();
        
        //todo
        
        return diagonalTiles;
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
    
    public boolean isCheckmate(Team team) {
        
        //If you had some time to work on this that would be good cheers
        
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
        }
        if (king == null) 
        {
            return false;
        }
        if (!king.isCheck(this)) // has it just been put in check?
        {
            return false;
        }
        if (!king.canMove(this).isEmpty()) // does it have any avalible moves?
        {
            return false;
        }
        
        // checks EVERY TILE of the same team to see if it could block the mate, not ideal, will need to come back to this
        for (int x = 0; x < this.width; x++) 
        {
            for (int y = 0; y < this.height; y++) 
            {
                Pieces piece = this.getTile(x, y).getPiece();
                
                if (piece != null && piece.getPieceTeam() == team && !(piece instanceof King)) 
                {
                    return true; //Need to compare if by moving infront will fix... maybe a recursive function... 
                }
            }
        }

        return true;
    }
}
