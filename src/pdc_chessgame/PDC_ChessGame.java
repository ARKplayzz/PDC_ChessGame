/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */

public class PDC_ChessGame {

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("ConvertToTryWithResources") //whats this for?      //It's to stop netbeans from having a hissy fit about this perfectly fine line
    public static void main(String[] args) 
    {
        ChessGame game = new ChessGame();
        game.start();
    }

}
