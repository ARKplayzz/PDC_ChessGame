/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author finlay
 */
public class Display 
{
    public void clearConsole()
    { // FIX LATER MIGHT NOT WORK ON SOME CONSOLES
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
