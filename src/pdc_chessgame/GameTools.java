/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author Andrew 
 */
public class GameTools 
{
    public static boolean isOdd(int n)
    {
        return (n % 2 == 0);
    }
    
    public static int distanceBetween(int x1, int x2, int y1, int y2)
    {
        return (int)Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 = x1));
    }
}
