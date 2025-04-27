/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;
import java.util.Scanner;
import pdc_chessgame.ChessBoard;
import pdc_chessgame.Input;
import pdc_chessgame.Team;
import pdc_chessgame.GameMenu;
import pdc_chessgame.InputHandler;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame {
    
    private ChessBoard board;
    private Player[] players;
    
    private boolean isRunning;
    
    private GameMenu menu;
    private InputHandler inputHandler;

    public ChessGame() 
    {
        this.board = new ChessBoard(8, 8);
        this.menu = new GameMenu();
        this.inputHandler = new InputHandler();
        
        this.players = new Player[2]; // player count for flexabuility in assignement 2

        this.players[0] = new Player("Guest 1", Team.WHITE);
        this.players[1] = new Player("Guest 2", Team.BLACK);
        this.isRunning = false;
    }
    
    public void start() 
    {
        displayWelcome(); 
        
        MenuOption userSelection = menu.displayMenu();

        if (userSelection == MenuOption.START_GAME) 
        {
            initialisePlayers();
            gameLoop();
        } 
        else if (userSelection == MenuOption.EXIT) 
        {
            displayExit();
            return;
        }
    }
    
    private void gameLoop() 
    {
        isRunning = true;
        
        while (isRunning && !board.checkmate) 
        {
            board.printBoard();
            
            Team currentTeam = board.turnCounter.getTeam();
            Player currentPlayer = getPlayerInTeam(currentTeam);
            
            Input moveSet = getPlayerTurn(currentPlayer);
            
            
            if (moveSet == null) { // Check if has quit
                break;
                //need to handle ressignation
            }
            
            board.turnCounter.nextTurn(); //check with finlay if correct usage?
        }
        
        if (board.checkmate) {
            Team winningTeam = getEnemyTeam(board.turnCounter.getTeam());
            Player winner = getPlayerInTeam(winningTeam);
            System.out.println("Checkmate! " + winner.getName() + " wins!");

        }
    }
    
    private void initialisePlayers() //alows for multiple player support for assignment 2...
    {
        for (Player player : players) 
        {
            playerLogin(player);
        }
    }
    
    private void playerLogin(Player player)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER "+ (player.getTeam() == Team.WHITE ? "1" : "2") +" LOGIN                           (X) TO QUIT");
        System.out.println("Please enter your username or 'Guest' to skip");
        
        String userInput = inputHandler.getStringInput("> ");
        
        if (userInput.toUpperCase().equals("GUEST"))
        {
            System.out.println("PROCEEDING AS: " + player.getName());
            return;
        }
        System.out.println("----------------------------------------------------");
        System.out.println("You have selected ["+ userInput + "]");
        
        if (Ranking.hasPlayed(userInput) == false)
        {
            System.out.println("This account has not played before. By continuing,");
            System.out.println("you will be granted with a base rank of 100 Elo");
        }
        else
        {
            System.out.println("This account currently has " + Ranking.getElo(userInput) + " Elo");
        }
        System.out.println("");
        
        if (inputHandler.confirmAction("Confirm this selection is correct "))
        {
            player.setName(userInput);
            System.out.println("PROCEEDING AS: " + player.getName());
            return;
        }
        playerLogin(player);
    }
    
    private Input getPlayerTurn(Player player)
    {
        System.out.println("----------------------------------------------------");
        System.out.println(player.getTeam().toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.print(player.getName() + "> ");
        
         String playerInput = inputHandler.getStringInput("");

 
        if (playerInput.toUpperCase().equals("X"))
        {
            return null;  //end
        }
        
        if (playerInput.toUpperCase().equals("H"))
        {
            displayHelp();
            return getPlayerTurn(player);  //try again
        }
        
        if (Input.getMove(playerInput.trim().toUpperCase()) == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println("'"+playerInput+ "' Is not a valid chess Input, Eg 'A1 B2'");
            
            return getPlayerTurn(player);  //try again
        }
        
        Input moveSet = Input.getMove(playerInput.trim().toUpperCase());
        
        if (this.board.getTile(moveSet.fromX, moveSet.fromY) == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Does not contain a piece, Eg 'A1 B2'");
            
           return getPlayerTurn(player);  //try again
            
        }
        if (this.board.getTile(moveSet.fromX, moveSet.fromY).getPiece() == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Does not contain a Piece, please try again");
            
            return getPlayerTurn(player);  //try again
        }
        if (this.board.getTile(moveSet.fromX, moveSet.fromY).getPiece().getPieceTeam() != player.getTeam())
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput.charAt(0) +""+ playerInput.charAt(1) + " Is not your Piece, please try again");
            
            return getPlayerTurn(player);  //try again
        }
        
        // if the list of possible moves does not contain our destination tile        
        if (!this.board.getTile(moveSet.fromX, moveSet.fromY).getPiece().canMove(board).contains(board.getTile(moveSet.toX, moveSet.toY)))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(playerInput + " Is an Invalid Chess Move, please try again");
            
            return getPlayerTurn(player);  //try again
        }
        
        System.out.println("----------------------------------------------------");        
        
        return moveSet;
    }
    
    public static Pieces getPromotionPiece(Player player, Pawn pawn)//this can be simplified
    {
        InputHandler inputHandler = new InputHandler();

        System.out.println(pawn.getPieceTeam().toString()+" PAWN PROMOTION!                    (X) TO QUIT");
        System.out.println();
        System.out.println("INPUT WHAT YOU WOULD LIKE TO PROMOTE YOUR PAWN TOO:");
        System.out.println("   <BISHOP>  =  R,   <ROOK>   =  B, ");
        System.out.println("   <KNIGHT>  =  N,   <QUEEN>  =  Q ");
      
        String playerInput = inputHandler.getStringInput(player.getName() + ">");
 
        if (playerInput.toUpperCase().equals("X"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println(player+" HAS RESIGNED ");
            
            return null;
        }
        if (playerInput.toUpperCase().equals("R"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Rook(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        if (playerInput.toUpperCase().equals("B"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Bishop(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        if (playerInput.toUpperCase().equals("N"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Knight(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        if (playerInput.toUpperCase().equals("Q"))
        {
            System.out.println("----------------------------------------------------");
            
            return new Queen(pawn.x, pawn.y, pawn.getPieceTeam()); 
        }
        System.out.println("----------------------------------------------------");
        System.out.println("Invalid option chosen, please try again");
        System.out.println("----------------------------------------------------");

        
        return getPromotionPiece(player, pawn);  //try again
    }
    
    private void displayWelcome() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to Chess!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham");
        System.out.println();
        System.out.println("This Program is a part of Assignment 1 for PDC 2025");
        System.out.println("----------------------------------------------------");
    }
    
    private void displayHelp() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("CHESS HELP");
        System.out.println("Resign Game   > X");
        System.out.println("Chess Help    > H");
        System.out.println("Move format   > From Tile -> 'A1 B2' <- To Tile");
    }
    
    private void displayExit() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("THANKS FOR PLAYING!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham");
        System.out.println("----------------------------------------------------");
    }
    
    private Player getPlayerInTeam(Team team) 
    {
        return players[team == Team.WHITE ? 0 : 1];
    }
    
    private void undoLastMove() {
        //placeholder for Assignment 2..
    }
    
    private Team getEnemyTeam(Team team) { // will need to update for scalabuility
        return team == Team.WHITE ? Team.BLACK : Team.WHITE;
    }
    
}
