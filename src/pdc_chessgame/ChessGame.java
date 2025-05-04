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
    // Do not make the board final please
    private ChessBoard board;
    // hashmap of the players and teams
    private final HashMap<Team, Player> players;
    
    // the leaderboard
    private final Ranking leaderboard;
    
    // the savemanager and clock
    private SaveManager savemanager;
    private Clock clock;
    
        
    public final Display display = new Display();
    private final GameMenu menu = new GameMenu();
    private final InputHandler inputHandler = new InputHandler();

    public ChessGame() 
    {
        this.board = new ChessBoard(8, 8);
        this.leaderboard = new Ranking();
        this.savemanager = new SaveManager();
        
        this.players = new HashMap<>(); // player count for flexabuility in assignement 2

        players.put(Team.WHITE, new Player("Guest 1", Team.WHITE));
        players.put(Team.BLACK, new Player("Guest 2", Team.BLACK));
        
        this.leaderboard.getLeaderboard();
    }
    
    public void start() // this is recursive so it will start again after a game has finished
    {
        // reset the board, this is so 
        this.board = new ChessBoard(8, 8);
        this.board.getHistory().deleteMoveHistory();
        display.displayWelcome(); 
        // get the users choice
        MenuOption userSelection = menu.displayMenu(this.leaderboard, this.savemanager, this.players);
        // start load or quit
        if (userSelection == MenuOption.START_GAME) 
        {
            initialisePlayers();
            
            customiseClock();
            
            gameLoop();
                    
            start();
        } 
        else if(userSelection == MenuOption.LOAD_SAVE)
        { 
            this.savemanager.simulateGame(this.board);
            // I'm not bothering to save the clock because they might want to alter it + it's a bunch of effort for something irrelevent
            customiseClock();
            gameLoop();
            start();
        }
        else if (userSelection == MenuOption.EXIT) 
        {
            Display.displayExit();
            System.exit(0);
        }
    }
    
    private void gameLoop() // This is the actaul main loop of the program
    {
        board.displayBoard();
        this.clock.start(); // start the clock
        
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
            
            if(clock.getTime() < 1) // after the player enters his commmand check to see if he ran out of time during the wait
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
        
        this.clock.terminate(); // end the clock
            
        Player winner = getPlayerInTeam(board.getCurrentTeam().getOppositeTeam());
        Player loser = getPlayerInTeam(board.getCurrentTeam());
        double[] eloChanges = this.leaderboard.changeElo(winner.getName(), loser.getName()); // change the elos of the players
        
        int[] newElos = {this.leaderboard.getElo(winner.getName()), this.leaderboard.getElo(loser.getName())};
        
        display.displayEloChange(winner, loser, eloChanges, newElos);
        
        //saving scores to the file just before the program exits
        this.leaderboard.saveScores();
        
        players.clear();
        players.put(Team.WHITE, new Player("Guest 1", Team.WHITE));
        players.put(Team.BLACK, new Player("Guest 2", Team.BLACK));
    }
    
    private void initialisePlayers() //alows for multiple player support for assignment 2...
    {
        for (Team team : players.keySet()) 
        {
            playerLogin(players.get(team));
        }
        System.out.println("----------------------------------------------------");
    }
    
    private void playerLogin(Player player) // this is the function that asks for your usernames
    {
        
        String userInput = display.displayPlayerLogin(player.getTeam(), player.getName());

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
            Display.displayExit();
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
    
    private void customiseClock()
    {
        int timeLimit = display.getClockTimeLimit();
        System.out.println("Set players time limit as " + timeLimit + " minutes.");
        System.out.println("----------------------------------------------------");
        
        this.clock = new Clock(timeLimit, 2);
    }
    
    private Move getPlayerTurn(Player player)
    {
        String playerInput = display.displayPlayerMove(player, this.clock);
 
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
        
        if(playerInput.toUpperCase().equals("B"))
        {
            this.board.displayBoard();
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

    private void saveGame() /// will prompt the user to save the game
    {
        String fileName = display.getSaveFileName();
        boolean success = this.savemanager.SaveGameToFile(fileName, this.board.getHistory(), this.players);
        display.displaySaveResult(success, fileName);
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
    { // get the player for this team
        return players.get(team);
    }    
}
