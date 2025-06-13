/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew
 */

import java.util.HashMap;

public interface SaveGameInterface 
{
    void SaveGameToUser(HashMap<Team, Player> players, Turn history, Clock clock);
    boolean loadAndRemoveSaveFile(String saveFile, HashMap<Team, Player> players, ChessBoard board, Clock clock);
}
