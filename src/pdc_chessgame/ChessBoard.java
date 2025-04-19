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
    private ArrayList<Pieces>[][] board;
    
    public int width;
    public int height;
    
    public ChessBoard(int width, int height)
    {
        // init board
        this.board = new ArrayList[width][height];
        this.width = width;
        this.height = height;
        
        // Set all to NULL, not neccecery on most systems but some might play up without it
        for (int i = 0; i < this.height; i++)
            for(int j = 0; j < this.width; j++)
                this.board[i][j] = null;
    }
    
    public void printBoard()
    {
        
    }
}
