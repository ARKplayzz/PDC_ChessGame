/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.ArrayList;

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
    }
    
    public void printBoard()
    {
        for(int i = 0; i < this.height; i++)
        {
            for(int k = 1; k < 4; k++)
            { // three rows
                
                for(int j = 0; j < this.width; j++)
                {
                    if(k == 2 && this.board[i][j] != null)
                    {
                        if(GameTools.isOdd(i+1) == GameTools.isOdd(j+1))
                            System.out.print("■"+ this.board[i][j].getPieceUnicode() +"■");
                        else
                            System.out.print(" "+ this.board[i][j].getPieceUnicode() +" ");
                    }
                    else
                    {
                        if(GameTools.isOdd(i+1) == GameTools.isOdd(j+1))
                            System.out.print("■■■");
                        else
                            System.out.print("   ");
                    }
                }
                System.out.print("\n");
            }
        }
    }
}
