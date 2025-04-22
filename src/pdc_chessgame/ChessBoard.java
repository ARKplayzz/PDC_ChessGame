/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew 
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
    
    public ChessBoard(int width, int height)
    {
        // init board
        this.board = new Tile[width][height];
        this.width = width;
        this.height = height;
        this.capturedPieces = new ArrayList<>();
        
        // Init all individual tiles
        // I changed it from i,j to x,y to hopefully clear some of the confusion we had last time
        for (int x = 0; x < this.height; x++)
            for(int y = 0; y < this.width; y++)
                this.board[x][y] = new Tile(x, y);
        
        initialiseBoard();  //Could put this is a better space (can also reset board)
    }
    
    private void initialiseBoard() 
    {
        //place white pawns
        //for (int row = 0; row < 8; row++) {
        //    setTile(new Pawn(1, row, Team.WHITE), 1, row);
        //}

        //place black pawns
        //for (int row = 0; row < 8; row++) {
        //    setTile(new Pawn(6, row, Team.BLACK), 6, row);
        //}

        //place white back row
        setTile(new Rook(0, 0, Team.WHITE), 0, 0);
        setTile(new Knight(0, 1, Team.WHITE), 0, 1);
        setTile(new Bishop(0, 2, Team.WHITE), 0, 2);
        setTile(new Queen(0, 3, Team.WHITE), 0, 3);
        setTile(new King(0, 4, Team.WHITE), 0, 4);
        setTile(new Bishop(0, 5, Team.WHITE), 0, 5);
        setTile(new Knight(0, 6, Team.WHITE), 0, 6);
        setTile(new Rook(0, 7, Team.WHITE), 0, 7);

        //place black back row
        setTile(new Rook(7, 0, Team.BLACK), 7, 0);
        setTile(new Knight(7, 1, Team.BLACK), 7, 1);
        setTile(new Bishop(7, 2, Team.BLACK), 7, 2);
        setTile(new King(7, 3, Team.BLACK), 7, 3);
        setTile(new Queen(7, 4, Team.BLACK), 7, 4);
        setTile(new Bishop(7, 5, Team.BLACK), 7, 5);
        setTile(new Knight(7, 6, Team.BLACK), 7, 6);
        setTile(new Rook(7, 7, Team.BLACK), 7, 7);
    }
    
    public void setTile(Pieces p, int x, int y)
    {
        this.board[x][y].setPiece(p); //THIS USED TO CHECK IF NULL - It now overrides (kills pieces)
    }
    
    public boolean killTile(int x, int y) { // Is there much point having returns here
        if (board[x][y] != null) {
            board[x][y] = null;
            return true;
        }
        return false;
    }
    
    public boolean moveTile(Input moveSet) 
    {
        return this.board[moveSet.fromY][moveSet.fromX].movePieceTo(board[moveSet.toY][moveSet.toX]);
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
    public List<Tile> getTilesLine(int x, int y)
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
    
    public List<Tile> getTilesDiagonal()
    {
        List<Tile> diagonalTiles = new ArrayList<>();
        
        //todo
        
        return diagonalTiles;
    }
    
    public void printBoard()
    {
        // again changed from i,j to x,y to clear up confusion
        for(int x = 0; x < this.height; x++)
        {
            for(int k = 1; k < 4; k++)
            { // three rows
                if(k == 2)
                    System.out.print(" "+(x+1)+"  ");
                else
                    System.out.print("    ");
                
                for(int y = 0; y < this.width; y++)
                {
                    if(k == 2 && this.board[x][y].getPiece() != null)
                    {
                        if(GameTools.isOdd(x+1) == GameTools.isOdd(y+1) && this.board[x][y].getPiece() != null)
                            System.out.print(" ■ "+ this.board[x][y].getPiece().getPieceUnicode() +" ■");
                        else if(this.board[x][y].getPiece() != null)
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
}
