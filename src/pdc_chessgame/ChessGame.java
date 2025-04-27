/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import java.util.Scanner;
import pdc_chessgame.ChessBoard;
import pdc_chessgame.Input;
import pdc_chessgame.Team;

/**
 *
 * @author ARKen
 */
public class ChessGame {
    
    private ChessBoard board;
    private boolean checkmate;
    
    private String playerOne;
    private String playerTwo;
    
    public ChessGame() {
        this.board = new ChessBoard(8, 8);
        this.checkmate = false;
    }
    
    public void start() {
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to Chess!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham");
        System.out.println();
        System.out.println("This Program is a part of Assignment 1 for PDC 2025");
        System.out.println("----------------------------------------------------");
        
        gameMenu();
        System.out.println("----------------------------------------------------");
        
        int i = 0; //THIS IS TEMPORRARY untill checkmate exists
        
        while(!board.checkmate)
        {
            
            this.board.printBoard();
            this.board.moveTile(getPlayerTurn(board.turnCounter.getTeam().toString(), board.turnCounter.getTeam())); 
            
            i++;
            if (i == 50) {
                this.board.checkmate = true;
            }
            
            this.board.turnCounter.nextTurn();
        }
    }
    
    private void gameLogin(Team colour){
        
        int playerNo = colour == Team.WHITE ? 1 : 2;
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER "+ playerNo +" LOGIN                           (X) TO QUIT");
        System.out.println("Please enter your username or 'Guest' to skip");
        //need to complete this
    }
    
    private void gameMenu(){
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("CHESS MENU                     (X) TO QUIT");
        System.out.println();
        System.out.println("WHAT YOU WOULD LIKE TO DO?");
        System.out.println("  <Start a game>          =  'START',");
        System.out.println("  <Check your ranking>    =  'RANK'");
        System.out.println("  <View the leaderboard>  =  'LEADERBOARD',");
        
        
        System.out.print("> ");
        String userInput = scanner.nextLine();
        
        if (userInput.toUpperCase().equals("START"))
        {
            gameLogin(Team.BLACK);
            gameLogin(Team.WHITE);
            return;
        }
        if (userInput.toUpperCase().equals("RANK"))
        {
            // get and display player rank
            gameMenu();
            return;
        }
        if (userInput.toUpperCase().equals("LEADERBOARD"))
        {
            //display leaderboard
            gameMenu();
            return;
        }
        if (userInput.toUpperCase().equals("X"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println("THANKS FOR PLAYING!");
            System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham");
            System.out.println("----------------------------------------------------");
            return;
        }
        gameMenu();
    }

    private Input getPlayerTurn(String player, Team colour)
    { //May be best to split this up a bit
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("----------------------------------------------------");
        System.out.println(colour.toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.print(player+"> ");
        
        String playerInput = scanner.nextLine();
 
        if (playerInput.toUpperCase().equals("X"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(player+" HAS RESIGNED ");
            
            return null;  //end
        }
        
        if (playerInput.toUpperCase().equals("H"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println("CHESS HELP");
            System.out.println("Resign Game   > X");
            System.out.println("Chess Help    > H");
            System.out.println("Move format   > From Tile -> 'A1 B2' <- To Tile");
            
            return getPlayerTurn(player, colour);  //try again
        }
        
        if (Input.getMove(playerInput.trim().toUpperCase()) == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println("'"+playerInput+ "' Is not a valid chess Input, Eg 'A1 B2'");
            
            return getPlayerTurn(player, colour);  //try again
        }
        
        Input moveSet = Input.getMove(playerInput.trim().toUpperCase());
        //System.out.println(" fromX>"+moveSet.fromX+" fromY>"+moveSet.fromY+" toX>"+moveSet.toX+" toy>"+moveSet.toY);
        
        if (this.board.getTile(moveSet.fromX, moveSet.fromY) == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Does not contain a piece, Eg 'A1 B2'");
            
           return getPlayerTurn(player, colour);  //try again
            
        }
        if (this.board.getTile(moveSet.fromX, moveSet.fromY).getPiece() == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Does not contain a Piece, please try again");
            
            return getPlayerTurn(player, colour);  //try again
        }
        if (this.board.getTile(moveSet.fromX, moveSet.fromY).getPiece().getPieceTeam() != colour)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Is not your Piece, please try again");
            
            return getPlayerTurn(player, colour);  //try again
        }
        
        // if the list of possible moves does not contain our destination tile        
        if (!this.board.getTile(moveSet.fromX, moveSet.fromY).getPiece().canMove(board).contains(board.getTile(moveSet.toX, moveSet.toY)))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput + " Is an Invalid Chess Move, please try again");
            
            return getPlayerTurn(player, colour);  //try again
        }
        
        System.out.println("----------------------------------------------------");        
        
        return moveSet;
    }
    
    private void undoLastMove() {
        //placeholder for Assignment 2..
    }
    
    private Team getOppositeTeam(Team team) {
        return team == Team.WHITE ? Team.BLACK : Team.WHITE;
    }
    
}
