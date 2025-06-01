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

public class GameManager 
{       
    // Do not make the board final please
    public ChessBoard board;
    // hashmap of the players and teams
    private final HashMap<Team, Player> players;
    
    // the savemanager and clock
    private SaveManager savemanager;
    private Clock clock;
    
    public final Display display = new Display();
    
    //private final GameMenu menu = new GameMenu();
    private final InputHandler inputHandler = new InputHandler(null);

    public GameManager(Player p1, Player p2, Clock clock) 
    {
        this.board = new ChessBoard(8, 8);
        this.savemanager = new SaveManager();
        this.clock = clock;
        
        this.players = new HashMap<>(); // player count flexabuility for future addition
        this.players.put(Team.WHITE, p2);
        this.players.put(Team.BLACK, p2);
    }
    
    public void start()
    {
        // establish new Board
        this.board = new ChessBoard(8, 8);
        this.board.getHistory().deleteMoveHistory();
        
        // runs the main gameloop
        gameLoop();
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
    }
    
    public MoveResult makeMove(Move move) 
    {
        Team currentTeam = board.getCurrentTeam();
        Player currentPlayer = getPlayerInTeam(currentTeam);

        if (move == null) 
        {
            return MoveResult.RESIGNATION;
        }

        if (clock.getTime() < 1) 
        {
            return MoveResult.TIMER_END;
        }

        boolean moved = board.moveTile(move);
        if (!moved) 
        {
            return MoveResult.INVALID;
        }

        if (board.isInCheck(currentTeam)) 
        {
            board.undoMove();
            return MoveResult.CHECK;
        }

        Team enemyTeam = currentTeam.getOppositeTeam();
        
        if (board.isCheckmate(enemyTeam)) 
        {
            return MoveResult.CHECKMATE;
        }

        if (board.isInCheck(enemyTeam)) 
        {
            return MoveResult.CHECK;
        }

        if (board.isPawnPromotable()) 
        {
            return MoveResult.PROMOTION;
        }

        board.getNextTurn();
        clock.swapClock();
        return MoveResult.SUCCESS;
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

    private void saveGame() // will prompt the user to save the game
    {
        String fileName = display.getSaveFileName();
        boolean success = this.savemanager.SaveGameToFile(fileName, this.board.getHistory(), this.players);
        display.displaySaveResult(success, fileName);
    }
    
    // get the player for this team
    private Player getPlayerInTeam(Team team) 
    { 
        return players.get(team);
    } 
    
    public HashMap<Team, Player> getPlayers() 
    {
        return players;
    }   
    
    public Player getWinner() 
    {
        return null;
    }   
}
