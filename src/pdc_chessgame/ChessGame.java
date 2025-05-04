/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.HashMap;

/**
 *
 * @author Andrew & Finlay
 */
public class ChessGame 
{
    
    private final ChessBoard board;
    private final HashMap<Team, Player> players;
    
    private static final String LEADERBOARD_FILE = "rankings.txt";
    private final Ranking leaderboard;
    
    private SaveManager savemanager;
    private Clock clock;
    
    private GameSimulator simulator;
        
    public final Display display = new Display();
    private final GameMenu menu = new GameMenu();
    private final InputHandler inputHandler = new InputHandler();

    public ChessGame() 
    {
        this.board = new ChessBoard(8, 8);
        this.leaderboard = new Ranking();
        this.savemanager = new SaveManager();
        this.simulator = new GameSimulator();
        
        this.players = new HashMap<>(); // player count for flexabuility in assignement 2

        players.put(Team.WHITE, new Player("Guest 1", Team.WHITE));
        players.put(Team.BLACK, new Player("Guest 2", Team.BLACK));
        
        this.leaderboard.getLeaderboard(LEADERBOARD_FILE);
    }
    
    public void start() 
    {
        display.displayWelcome(); 
        
        MenuOption userSelection = menu.displayMenu(this.leaderboard, this.savemanager, this.players);

        if (userSelection == MenuOption.START_GAME) 
        {
            initialisePlayers();
            
            customiseClock();
            
            gameLoop();
                    
            start();
        } 
        else if(userSelection == MenuOption.LOAD_SAVE)
        { 
            this.simulator.simulateGame(this.savemanager, this.board);
            // I'm not bothering to save the clock because they might want to alter it + it's a bunch of effort for something irrelevent
            customiseClock();
            gameLoop();
            start();
        }
        else if (userSelection == MenuOption.EXIT) 
        {
            display.displayExit();
            System.exit(0);
        }
    }
    
    private void gameLoop() 
    {
        board.displayBoard();
        this.clock.start();
        
        while (true) 
        {           
            Team currentTeam = board.getCurrentTeam();
            Team enemyTeam = currentTeam.getOppositeTeam();

            Player currentPlayer = getPlayerInTeam(currentTeam);

            Move moveSet = getPlayerTurn(currentPlayer); //gets player move

            if (moveSet == null)  //if player exits game (RESIGNATION)
            {
                display.displayResignation(currentTeam);
                break;
            }
            
            if(clock.getTime() < 1)
            {
                display.displayTimeOver(currentTeam);
                break;
            }

            board.moveTile(moveSet); // Player move

            if (board.isInCheck(currentTeam)) // is Player move in check
            {
                board.undoMove();
                display.displayInCheckWarning(); 
            }
            else 
            {
                if (board.isCheckmate(enemyTeam)) { // ends game (CHECKMATE)
                    display.displayGameOver(currentTeam);
                    break;
                }
                else if (board.isInCheck(enemyTeam)) { // warns player of invalid move due to check
                    display.displayInCheckNotification(enemyTeam);
                }
                if (board.isPawnPromotable()) { // Gets user input for PawnPromotion
                    PawnOption promotionPiece = display.getPromotionPiece(currentTeam, currentPlayer);
                    if (promotionPiece == PawnOption.EXIT_GAME) 
                    {
                        display.displayResignation(currentTeam);
                        break;
                    }
                    else
                    {
                        board.promotePawn(promotionPiece);
                    }
                }
                board.getNextTurn(); //next turn
                this.clock.swapClock();
                board.displayBoard();
            } 
        }    
        
        this.clock.terminate();
            
        //displayEloPromotion(getPlayerInTeam(getEnemyTeam(board.getCurrentTeam())), getPlayerInTeam(board.getCurrentTeam()));
        this.changeElo(getPlayerInTeam(board.getCurrentTeam().getOppositeTeam()), getPlayerInTeam(board.getCurrentTeam()));
            
        //saving scores to the file just before the program exits
        this.leaderboard.saveScores(LEADERBOARD_FILE);
    }
    
    private void initialisePlayers() //alows for multiple player support for assignment 2...
    {
        for (Team team : players.keySet()) 
        {
            playerLogin(players.get(team));
        }
        System.out.println("----------------------------------------------------");
    }
    
    private void playerLogin(Player player)
    {
        System.out.println("----------------------------------------------------");
        System.out.println("PLAYER "+ (player.getTeam() == Team.WHITE ? "1" : "2") +" LOGIN                           (X) TO QUIT");
        System.out.println("Please enter your username or 'Guest' to skip \n(Case sensitive)");
        
        String userInput = inputHandler.getStringInput("> ");
        
        if (userInput.length() > 16) 
        {
            System.out.println("Please keep your username within 16 characters long");
            return;
        }
        
        if(userInput.contains("$"))
        {
            System.out.println("Please do not include '$' in your name");
            playerLogin(player);
            return;
        }
        
        if(userInput.toUpperCase().contains("BLACK") || userInput.toUpperCase().contains("WHITE"))
        {
            System.out.println("Please refrain from using team names as logins");
            playerLogin(player);
            return;
        }
        
        if(userInput.toUpperCase().equals("X"))
        {
            display.displayExit();
            System.exit(0);
            return;
        }
        
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
    
    // I don't know why netbeans is complaining it won't recurse infinitly, only as long as the user keeps input bogus numbers
    @SuppressWarnings("InfiniteRecursion")
    private void customiseClock()
    {
        System.out.println("CLOCK SETTING                           (X) TO QUIT\n");
        System.out.print("Please enter the time limit for every\nplayer (in minutes, will be rounded)\n");
        
        String userInput = inputHandler.getStringInput("> ").trim(); 
        
        int tl;
        try
        {
            tl = Integer.parseInt(userInput);
        } catch (final NumberFormatException e){
            System.out.println("Please input a whole number");
            System.out.println("----------------------------------------------------");
            this.customiseClock();
            return;
        }
        
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
        
        double[] t = this.leaderboard.changeElo(winner.getName(), loser.getName());
        System.out.println("\n"+winner.getName()+" elo change: "+(int)t[0]+" -> "+this.leaderboard.getElo(winner.getName()));
        System.out.println("\n"+loser.getName()+" elo change: "+(int)t[1]+" -> "+this.leaderboard.getElo(loser.getName()));
        
        System.out.println();
        System.out.println("Login and play more games to rank up!");
    }
    
    private Move getPlayerTurn(Player player)
    {
        System.out.println("----------------------------------------------------");
        System.out.println(player.getTeam().toString()+"'S MOVE        USE (H) FOR HELP, OR (X) TO QUIT");
        System.out.println("Remaining time: " +clock.toString());
        System.out.print(player.getName() + "> ");
        
        String playerInput = inputHandler.getStringInput("");
 
        if (playerInput.toUpperCase().trim().equals("X"))
        {
            return null;  //end
        }
        
        if(playerInput.toUpperCase().trim().equals("T"))
        {
            System.out.println("----------------------------------------------------");
            System.out.println("Remaining time: " +clock.toString());
            return getPlayerTurn(player);  //try again
        }
        
        if (playerInput.toUpperCase().equals("H"))
        {
            display.displayHelp();
            return getPlayerTurn(player);  //try again
        }
        
        if(playerInput.toUpperCase().equals("S"))
        { // save the game
            this.saveGame();
            return getPlayerTurn(player);  //try again
        }
        
        if(playerInput.toUpperCase().equals("MH"))
        { // show move history
            display.printHistory(this.board);
            return getPlayerTurn(player);  //try again
        }
        
        Move moveSet = MoveInput.getMoveInput(playerInput.trim().toUpperCase());
        
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

    private void saveGame()
    { // this might be better in display or some other class
        System.out.println("----------------------------------------------------");
        System.out.println("Please enter the name of the new save file\n(Do not include a file extension):");
        System.out.print("> ");
        
        String fileInput = inputHandler.getStringInput("");
        
        if(!this.savemanager.SaveGameToFile(fileInput, this.board.getHistory(), this.players))
        {
            System.out.println("\nPlease ensure that you input a valid file name.");
        }
        else
        {
            System.out.println("\nSuccesfully saved as '"+fileInput+"'");
        }
    }
    
    private boolean isValidMove(Move move, Team team, String input) 
    {
        if (board.getTile(move.getFromX(), move.getFromY()) == null || board.getTile(move.getFromX(), move.getFromY()).getPiece() == null) 
        {
            System.out.println("----------------------------------------------------");
            System.out.println(input.charAt(0) +""+ input.charAt(1) + " Does not contain a piece, Eg 'A1 B2'");
            return false;
        }
        Piece piece = board.getTile(move.getFromX(), move.getFromY()).getPiece();
        if (piece.getPieceTeam() != team) 
        {
            System.out.println("----------------------------------------------------");
            System.out.println(input.charAt(0) +""+ input.charAt(1) + " Is not your Piece, please try again");
            return false;
        }
        if (!piece.canMove(board).contains(board.getTile(move.getToX(), move.getToY()))) 
        {
            System.out.println("----------------------------------------------------");
            System.out.println(input + " is an invalid move for this piece, try again");
            return false;
        }
        return true;
    }
    
    private Player getPlayerInTeam(Team team) 
    {
        return players.get(team);
    }    
}
