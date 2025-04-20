/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author finla
 */


/*
    I'm really tired right now so i'll work on this class tommorow,
    what it's meant to do is run in a seperate thread and constantly check for checkmate
    so we don't have to do that on the main thread.
*/

public class mateDetector extends Thread
{
    private boolean quit = false;
    
    /* the reason I gave a pointer to the board to this class is so
        that it doesn't need to wait for the main thread to pass its
        board to this thread and can instead check changes as soon as they happen */
    public ChessBoard board;
    
    public mateDetector(ChessBoard board)
    {
        this.board = board;
    }
    
    @Override
    public void run()
    {
        while(!this.quit)
        { // check for checkmate
            
        }
    }
    
    public void terminate()
    {
        this.quit = true;
    }
}
