/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public class ChessBoard 
{
    /*
        When changing to graphical for part2 make a tile class that
        holds piece and colour.
    */
    private Pieces[][] board;
    
    public int width;
    public int height;
    
    public ChessBoard(int width, int height)
    {
        // init board
        this.board = new Pieces[width][height];
        this.width = width;
        this.height = height;
        
        // Init all individual tiles
        for (int i = 0; i < this.height; i++)
            for(int j = 0; j < this.width; j++)
                this.board[i][j] = null;
        
        initialiseBoard();  //Could put this is a better space (can also reset board)
    }
    
    private void initialiseBoard() 
    {
        //place white pawns
        for (int col = 0; col < 8; col++) {
            setTile(new Pawn(Team.WHITE), 1, col);
        }

        //place black pawns
        for (int col = 0; col < 8; col++) {
            setTile(new Pawn(Team.BLACK), 6, col);
        }

        //place white back row
        setTile(new Rook(Team.WHITE), 0, 0);
        setTile(new Knight(Team.WHITE), 1, 0);
        setTile(new Bishop(Team.WHITE), 2, 0);
        setTile(new Queen(Team.WHITE), 3, 0);
        setTile(new King(Team.WHITE), 4, 0);
        setTile(new Bishop(Team.WHITE), 5, 0);
        setTile(new Knight(Team.WHITE), 6, 0);
        setTile(new Rook(Team.WHITE), 7, 0);

        //place black back row
        setTile(new Rook(Team.BLACK), 0, 7);
        setTile(new Knight(Team.BLACK), 1, 7);
        setTile(new Bishop(Team.BLACK), 2, 7);
        setTile(new Queen(Team.BLACK), 3, 7);
        setTile(new King(Team.BLACK), 4, 7);
        setTile(new Bishop(Team.BLACK), 5, 7);
        setTile(new Knight(Team.BLACK), 6, 7);
        setTile(new Rook(Team.BLACK), 7, 7);
    }
    
    public boolean setTile(Pieces p, int x, int y)
    {
        this.board[x][y] = p; //THIS USED TO CHECK IF NULL - It now overrides (kills pieces)
        return true;
        
    }
    
    public Pieces getTile(int x, int y)
    { // will return null if the tile is empty
        return this.board[x][y];
    }
    
    public Pieces[][] getBoard()
    {
        return this.board;
    }
    
    public void printBoard()
    {
        
        for(int i = 0; i < this.height; i++)
        {
            for(int k = 1; k < 4; k++)
            { // three rows
                if(k == 2)
                    System.out.print(" "+(i+1)+"  ");
                else
                    System.out.print("    ");
                
                for(int j = 0; j < this.width; j++)
                {
                    if(k == 2 && this.board[i][j] != null)
                    {
                        if(GameTools.isOdd(i+1) == GameTools.isOdd(j+1))
                            System.out.print(" ■ "+ this.board[i][j].getPieceUnicode() +" ■");
                        else
                            System.out.print("   "+ this.board[i][j].getPieceUnicode() +"  ");
                    }
                    else
                    {
                        if(GameTools.isOdd(i+1) == GameTools.isOdd(j+1))
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
