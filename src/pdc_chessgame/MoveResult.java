/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */

// enum of all the possible results for a given move
public enum MoveResult 
{
    SUCCESS,
    INVALID,
    CHECK,
    CHECKMATE,
    PROMOTION,
    RESIGNATION,
    TIMER_END
}
