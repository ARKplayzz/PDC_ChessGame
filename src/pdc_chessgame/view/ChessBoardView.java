/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame.view;
import pdc_chessgame.MoveResult;
import pdc_chessgame.ChessGame;
import pdc_chessgame.PawnOption;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import pdc_chessgame.King;
import pdc_chessgame.Team;
import pdc_chessgame.Tile;
import java.util.List;
import javax.imageio.ImageIO;
import pdc_chessgame.Move;
import static pdc_chessgame.MoveResult.CHECKMATE;
import static pdc_chessgame.MoveResult.INVALID;
import pdc_chessgame.Pawn;

/**
 *
 * @author ARKen
 */
public class ChessBoardView extends JPanel {
    
    private ChessGame controller;
    private JButton[][] boardTileButtons;
    private Tile selectedTile;
    private List<Tile> possibleMoves = new ArrayList<>();
    
    private final Color LIGHT_SQUARE = new Color(153, 233, 255);
    private final Color DARK_SQUARE = new Color(0, 108, 137);
    private final Color SELECTED_SQUARE = new Color(255, 255, 0, 128);
    private final Color VALID_MOVE_SQUARE = new Color(0, 255, 0, 128);
    private final Color CHECK_SQUARE = new Color(255, 0, 0, 128);
    
    public ChessBoardView(ChessGame controller) 
    {
        this.controller = controller;
        this.selectedTile = null;
        
        this.setBackground(new Color(100, 100, 100));
        this.setBorder(BorderFactory.createLineBorder(Color.white));
        this.setLayout(new GridLayout(8, 8));
        
        initializeBoard();
        updateBoard();
        
        //refreshed board when scaled
        this.addComponentListener(new java.awt.event.ComponentAdapter() 
        {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) 
        {
            updateBoard();
        }
        });
    }
    
    private void initializeBoard() 
    {
        this.boardTileButtons = new JButton[8][8];
        
        // Start from rank 8 (y=7) and go down to rank 1 (y=0)
        for (int y = 7; y >= 0; y--) 
        { 
            for (int x = 0; x < 8; x++) 
            {
                JButton tileButton = new JButton();
                tileButton.setFont(new Font("Helvetica", Font.BOLD, 24));
                tileButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
                tileButton.setFocusPainted(false);
                
                // Set square color
                if ((x + y) % 2 == 0) 
                {
                    tileButton.setBackground(this.LIGHT_SQUARE);
                } else 
                {
                    tileButton.setBackground(this.DARK_SQUARE);
                }
                
                // Add mouse listener for piece selection and movement
                final int finalX = x;
                final int finalY = y;
                tileButton.addMouseListener(new MouseAdapter() 
                {
                    @Override
                    public void mousePressed(MouseEvent e) 
                    {
                        handleButtonClick(finalX, finalY);
                    }
                });
                
                this.boardTileButtons[x][y] = tileButton;
                this.add(tileButton);
            }
        }
    }
    
    private void handleButtonClick(int x, int y) 
    {
        Tile newSelectedTile = this.controller.getBoard().getTile(x, y);
        
        // If no tile is currently selected then select
        if (this.selectedTile == null) 
        {
            // Select tile if it has a piece of the current player's team
            if (newSelectedTile.getPiece() != null && 
                newSelectedTile.getPiece().getPieceTeam() == this.controller.getBoard().getCurrentTeam()) 
            {
                selectTile(newSelectedTile);
            }
        }
        else 
        {
            // If tile is currently selected then deselect before selecting
            if (newSelectedTile == this.selectedTile) 
            {
                deselectTile();
            } 
            // if new piece isnt empty and has a piece of the correct team then select it
            else if (newSelectedTile.getPiece() != null && 
                     newSelectedTile.getPiece().getPieceTeam() == this.controller.getBoard().getCurrentTeam()) 
            {
                selectTile(newSelectedTile);
            }
            // attempt to move
            else
            {
                attemptMove(this.selectedTile, newSelectedTile);
            }
        }
    }
    
    private void selectTile(Tile tile) 
    {
        this.selectedTile = tile;
        
        if (tile.getPiece() != null) 
        {
            possibleMoves = tile.getPiece().canMove(controller.getBoard());
        } 
        else 
        {
            possibleMoves = new ArrayList<>();
        }
        updateBoard();
    }
    
    private void deselectTile() 
    {
        selectedTile = null;
        possibleMoves = new ArrayList<>();
        updateBoard();
    }
    
    private void attemptMove(Tile fromTile, Tile toTile) 
    {
        Move moveInput = new Move(fromTile.getX(), fromTile.getY(), toTile.getX(), toTile.getY());
       
        if (possibleMoves == null || !possibleMoves.contains(toTile)) 
        {
            JOptionPane.showMessageDialog(this, "move is not valid for that piece.", "invalid move", JOptionPane.WARNING_MESSAGE);
            deselectTile();
            updateBoard();
            return; //move not possible: go to jain (dont pass Go)
        }
        
        MoveResult result = this.controller.passMove(moveInput);
        
        switch (result) //TEMPP MOVE THIS TO SIDEBAR LATER
        {
            case INVALID:
                JOptionPane.showMessageDialog(this, "invalid", "incorrect Move", JOptionPane.WARNING_MESSAGE);
                break;
            case CHECK:
                JOptionPane.showMessageDialog(this, "in check", "check", JOptionPane.WARNING_MESSAGE);
                break;
            case CHECKMATE:
                JOptionPane.showMessageDialog(this, "in checkmate", "checkmate", JOptionPane.WARNING_MESSAGE);
                break;   
            case PROMOTION:
                PawnOption[] optionList = {PawnOption.QUEEN, PawnOption.ROOK, PawnOption.BISHOP, PawnOption.KNIGHT};
                PawnOption newPiece = (PawnOption) JOptionPane.showInputDialog(this, "promote to:", "pawn promotion", JOptionPane.QUESTION_MESSAGE, null, optionList, PawnOption.QUEEN);
                
                if (newPiece != null) 
                {
                    controller.getBoard().promotePawn(newPiece, (Pawn) fromTile.getPiece());
                    controller.passMove(moveInput);
                    updateBoard();
                }
                break;                
            case RESIGNATION:
                JOptionPane.showMessageDialog(this, "player gave up", "end of match", JOptionPane.INFORMATION_MESSAGE);
                break;
            case TIMER_END:
                JOptionPane.showMessageDialog(this, "ran out of time", "end of match", JOptionPane.INFORMATION_MESSAGE);
                break;
            case SUCCESS:
            default:

                break;
        }

        deselectTile();
        updateBoard();
    }
 
    
    public void updateBoard() 
    {
        for (int x = 0; x < 8; x++) 
        {
            for (int y = 0; y < 8; y++) 
            {
                JButton button = boardTileButtons[x][y];
                Tile tile = controller.getBoard().getTile(x, y);
                
                // Reset background color
                if ((x + y) % 2 == 0) 
                {
                    button.setBackground(LIGHT_SQUARE);
                } 
                else 
                {
                    button.setBackground(DARK_SQUARE);
                }
                
                //show selected squares
                if (selectedTile != null && selectedTile == tile)
                {
                    button.setBackground(blendColours(button.getBackground(), SELECTED_SQUARE));
                }
                
                //show valid moves
                if (possibleMoves != null && !possibleMoves.isEmpty() && possibleMoves.contains(tile)) 
                {
                    button.setBackground(blendColours(button.getBackground(), VALID_MOVE_SQUARE));
                }
                
                //display king is in check
                if (tile.getPiece() instanceof King && 
                    controller.getBoard().isInCheck(tile.getPiece().getPieceTeam())) 
                {
                    button.setBackground(blendColours(button.getBackground(), CHECK_SQUARE));
                }
                
                // display piece
                if (tile.getPiece() != null) 
                {
                    String team = tile.getPiece().getPieceTeam().toString();
                    String pieceName = tile.getPiece().getPieceUnicode();
                    
                    int iconWidth = button.getWidth() > 0 ? button.getWidth() : 64;
                    int iconHeight = button.getHeight() > 0 ? button.getHeight() : 64;
                    
                    ImageIcon icon = getPieceIcon(team, pieceName, iconWidth, iconHeight);
                    button.setIcon(icon);
                    button.setText(""); // Remove text
                } 
                else 
                {
                    button.setIcon(null);
                    button.setText("");
                }
            }
        }
        
        this.repaint();
    }
    
    //grabs piece from file and scales it relative to the tile sizing
    private ImageIcon getPieceIcon(String team, String pieceName, int width, int height) 
    {
        String resourcePath = "/pdc_chessgame/resources/pieces/" + team + "_" + pieceName.toUpperCase() + ".png";
        URL imgUrl = getClass().getResource(resourcePath);
        ImageIcon icon = new ImageIcon(imgUrl);
        // Optionally scale the icon:
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
    
    //have to blend colours as there isnt an easy opacity modification in Jbutton
    private Color blendColours(Color base, Color overlay) 
    {
        int alpha = overlay.getAlpha();
        int invAlpha = 255 - alpha;
        
        int r = (alpha * overlay.getRed() + invAlpha * base.getRed()) / 255;
        int g = (alpha * overlay.getGreen() + invAlpha * base.getGreen()) / 255;
        int b = (alpha * overlay.getBlue() + invAlpha * base.getBlue()) / 255;
        
        return new Color(r, g, b);
    }
    
    public void clearSelection() {
        deselectTile();
    }
    
    public void flipBoard() 
    {
        this.removeAll();
        
        //re-builds buttons in reverse order
        for (int y = 0; y < 8; y++) 
        {
            for (int x = 7; x >= 0; x--) 
            {
                this.add(boardTileButtons[x][y]);
            }
        }
        
        this.revalidate();
        this.repaint();
    }
}
