/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame 
{
    
    private ChessBoard board;
    private Player[] players;
    
    private static final String LEADERBOARD_FILE = "rankings.txt";
    private Ranking leaderboard;
        
    private Clock clock;
        
    private GameMenu menu;
    private InputHandler inputHandler;

    public ChessGame() 
    {
        this.board = new ChessBoard(8, 8);
        this.menu = new GameMenu();
        this.inputHandler = new InputHandler();
        this.leaderboard = new Ranking();
        
        this.players = new Player[2]; // player count for flexabuility in assignement 2

        this.players[0] = new Player("Guest 1", Team.WHITE);
        this.players[1] = new Player("Guest 2", Team.BLACK);
        this.leaderboard.getLeaderboard(LEADERBOARD_FILE);
    }
    
    public void start() 
    {
        displayWelcome(); 
        
        MenuOption userSelection = menu.displayMenu(this.leaderboard);

        if (userSelection == MenuOption.START_GAME) 
        {
            initialisePlayers();
            
            customiseClock();
            
            gameLoop();
                    
            start();
        } 
        else if (userSelection == MenuOption.EXIT) 
        {
            displayExit();
        }
    }
    
    private void gameLoop() 
    {
        board.displayBoard();
        this.clock.start();
        
        while (true) 
        {           
            Team currentTeam = board.getCurrentTeam();
            Team enemyTeam = getEnemyTeam(currentTeam);

            Player currentPlayer = getPlayerInTeam(currentTeam);

            Move moveSet = getPlayerTurn(currentPlayer); //gets player move

            if (moveSet == null)  //if player exits game (RESIGNATION)
            {
                displayResignation(currentTeam);
                break;
            }

            board.moveTile(moveSet); // Player move

            if (board.isInCheck(currentTeam)) // is Player move in check
            {
                board.undoMove();
                displayInCheckWarning(); 
            }
            else 
            {
                if (board.isCheckmate(enemyTeam)) { // ends game (CHECKMATE)
                    displayGameOver(currentTeam);
                    break;
                }
                else if (board.isInCheck(enemyTeam)) { // warns player of invalid move due to check
                    displayInCheckNotification(enemyTeam);
                }
                if (board.isPawnPromotable()) { // Gets user input for PawnPromotion
                    PawnOption promotionPiece = getPromotionPiece(currentTeam, currentPlayer);
                    if (promotionPiece == PawnOption.EXIT_GAME) 
                    {
                        displayResignation(currentTeam);
                        break;
                    }
                    else
                    {
                        board.promotePawn(promotionPiece);
                    }
                }
                board.getNextTurn(); //next turn
                board.displayBoard();
            } 
        }    
        
        this.clock.terminate();
            
        //displayEloPromotion(getPlayerInTeam(getEnemyTeam(board.getCurrentTeam())), getPlayerInTeam(board.getCurrentTeam()));
        this.changeElo(getPlayerInTeam(getEnemyTeam(board.getCurrentTeam())), getPlayerInTeam(board.getCurrentTeam()));
            
        //saving scores to the file just before the program exits
        this.leaderboard.saveScores(LEADERBOARD_FILE);
    }
    
    private void initialisePlayers() //alows for multiple player support for assignment 2...
    {
        for (Player player : players) 
        {
            playerLogin(player);
        }
        System.out.println("----------------------------------------------------");
    }
    
    private void playerLogin(Player player)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER "+ (player.getTeam() == Team.WHITE ? "1" : "2") +" LOGIN                           (X) TO QUIT");
        System.out.println("Please enter your username or 'Guest' to skip (Case sensitive)");
        
        String userInput = inputHandler.getStringInput("> ");
        
        if (userInput.toUpperCase().equals("GUEST"))
        {   
            System.out.println("----------------------------------------------------");
            System.out.println("PROCEEDING AS: " + player.getName());
            return;
        }
        System.out.println("----------------------------------------------------");
        System.out.println("YOU HAVE SELECTED ["+ userInput + "]\n");
        
        if (this.leaderboard.hasPlayed(userInput) == false)
        {
            System.out.println("This account has not played before. By continuing,");
            System.out.println("you will be granted with a base rank of 100 Elo");
        }
        else
        {
            System.out.println("This account currently has " + this.leaderboard.getElo(userInput) + " Elo");
        }
        System.out.println("");
        
        if (inputHandler.confirmAction("Confirm this selection is correct "))
        {
            player.setName(userInput);
            System.out.println("----------------------------------------------------");
            System.out.println("PROCEEDING AS: " + player.getName());
            
            if(this.leaderboard.hasPlayed(userInput) == false)
                this.leaderboard.isNewUser(userInput);
            return;
        }
        playerLogin(player);
    }
    
    private void customiseClock()
    {
        System.out.println("CLOCK SETTING                           (X) TO QUIT\n");
        System.out.print("Please enter the time limit for every\nplayer (in minutes, will be rounded)\n");
        
        String userInput = inputHandler.getStringInput("> ").trim(); 
        int tl = Integer.parseInt(userInput);
        
        System.out.println("Set players time limit as "+tl+" minutes.");
        System.out.println("----------------------------------------------------");
        this.clock = new Clock(tl, 2);
    }
    
    // used in chackmate and for a forfiet
    private void changeElo(Player winner, Player loser)
    {
        if (winner.getName().contains("Guest "))
        {
            System.out.println("CONGRATS TO " + winner.getName());  
        }
        else
        {
            System.out.println("CONGRATS TO " + winner.getName());  
        }
        if (loser.getName().contains("Guest "))
        {
            System.out.println("BETTER LUCK NEXT TIME " + loser.getName()); 
        }
        else
        {
            System.out.println("UNFORTUNATLY " + loser.getName() + ", HAS BEEN DEFEATED"); 
        }
        
        int[] t = this.leaderboard.changeElo(winner.getName(), loser.getName());
        System.out.println("\n"+winner+" elo change: "+t[0]+" -> "+this.leaderboard.getElo(winner.getName()));
        System.out.println("\n"+loser+" elo change: "+t[1]+" -> "+this.leaderboard.getElo(loser.getName()));
        
        System.out.println();
        System.out.println("Login and play more games to rank up!");
        System.out.println("----------------------------------------------------");  
    }
    
    public PawnOption getPromotionPiece(Team team, Player player)//need to handle resignation  
    {
        InputHandler inputHandler = new InputHandler();

        System.out.println(team.toString()+" PAWN PROMOTION!                    (X) TO QUIT");
        System.out.println();
        System.out.println("INPUT WHAT YOU WOULD LIKE TO PROMOTE YOUR PAWN TOO:");
        System.out.println("   <BISHOP>  =  R,   <ROOK>   =  B, ");
        System.out.println("   <KNIGHT>  =  N,   <QUEEN>  =  Q ");
      
        String userInput = inputHandler.getStringInput(player.getName() + ">");

        if (userInput.equals("X")) 
        {
            return PawnOption.EXIT_GAME;
        } 
        else if (userInput.equals("R")) 
        {
            return PawnOption.ROOK;
        } 
        else if (userInput.equals("N")) 
        {
            return PawnOption.KNIGHT;
        } 
        else if (userInput.equals("B")) 
        {
            return PawnOption.BISHOP;
        } 
        else if (userInput.equals("Q")) 
        {
            return PawnOption.QUEEN;
        } 

        System.out.println("----------------------------------------------------");
        System.out.println("Invalid option. Please try again.");
        System.out.println("----------------------------------------------------");     

        return getPromotionPiece(team, player);
    }
    
    private Move getPlayerTurn(Player player)
    {
        System.out.println("----------------------------------------------------");
        System.out.println(player.getTeam().toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.print(player.getName() + "> ");
        
        String playerInput = inputHandler.getStringInput("");
 
        if (playerInput.toUpperCase().trim().equals("X"))
        {
            return null;  //end
        }
        
        if(playerInput.toUpperCase().trim().equals("T"))
        {
            System.out.println(this.TimerToString());
            return getPlayerTurn(player);  //try again
        }
        
        if (playerInput.toUpperCase().equals("H"))
        {
            displayHelp();
            return getPlayerTurn(player);  //try again
        }
        
        Move moveSet = Move.getMove(playerInput.trim().toUpperCase());
        
        if (moveSet == null)
        {
            System.out.println("----------------------------------------------------");
            System.out.println("'"+playerInput+ "' Is not a valid chess Input, Eg 'A1 B2'");
            
            return getPlayerTurn(player);  //try again
        }
        
        if (!isValidMove(moveSet, player.getTeam(), playerInput)) 
        {
            return getPlayerTurn(player);
        }
        
        System.out.println("----------------------------------------------------");        
        return moveSet;
    }
    
    private boolean isValidMove(Move move, Team team, String input) 
    {
        if (board.getTile(move.fromX, move.fromY) == null || board.getTile(move.fromX, move.fromY).getPiece() == null) 
        {
            System.out.println("----------------------------------------------------");
            System.out.println(input.charAt(0) +""+ input.charAt(1) + " Does not contain a piece, Eg 'A1 B2'");
            return false;
        }
        Piece piece = board.getTile(move.fromX, move.fromY).getPiece();
        if (piece.getPieceTeam() != team) 
        {
            System.out.println("----------------------------------------------------");
            System.out.println(input.charAt(0) +""+ input.charAt(1) + " Is not your Piece, please try again");
            return false;
        }
        if (!piece.canMove(board).contains(board.getTile(move.toX, move.toY))) 
        {
            System.out.println("----------------------------------------------------");
            System.out.println(input + " is an invalid move for this piece, try again");
            return false;
        }
        return true;
    }
    
    private String TimerToString() // FIX THIS
    {
        Team currentTeam = board.getCurrentTeam();
        Player currentPlayer = getPlayerInTeam(currentTeam);
            
        long seconds = 0;
        for(int i = 0; i < this.players.length; i++)
        {
            if(this.players[i].equals(currentPlayer))
            {
                seconds = this.clock.getTime(i) / 1000;
            }
        }
        int mins = (int)(seconds/60);
        seconds = seconds%60;
        
        return ("Remaining time: "+mins+":"+seconds);
    }
    
    private void displayInCheckWarning() 
    {
        System.out.println("Invalid move! Your king is facing check, try again");
    }
    
    private void displayInCheckNotification(Team team) 
    {
        System.out.println("CHECK! " + team.teamName() + " IS NOW IN CHECK");
        System.out.println("----------------------------------------------------");  
    }
    
    private void displayGameOver(Team winningTeam)
    {
        System.out.println("CHECKMATE! " + winningTeam.teamName() + " WINS!");
        System.out.println("----------------------------------------------------"); 
    }
    
    private void displayResignation(Team resigningTeam)
    {
        System.out.println("----------------------------------------------------"); 
        System.out.println(resigningTeam.teamName() + " HAS RESIGNED! "+ getEnemyTeam(resigningTeam).teamName() +" WINS!");
        System.out.println("----------------------------------------------------");  
    }
    
    private void displayWelcome() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to Chess!");
        System.out.println("Program Produced by Andrew Kennedy & Finlay Baynham\n");
        System.out.println("This Program is a part of Assignment 1 for PDC 2025");
        System.out.println("----------------------------------------------------");
    }
    
    private void displayHelp() 
    {
        System.out.println("----------------------------------------------------");
        System.out.println("CHESS HELP");
        System.out.println("Resign Game          > X");
        System.out.println("Chess Help           > H");
        System.out.println("Show remaining time  > T");
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
        
    private Team getEnemyTeam(Team team) { // will need to update for scalabuility
        return team == Team.WHITE ? Team.BLACK : Team.WHITE;
    }
    
}
