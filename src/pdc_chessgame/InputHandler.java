/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

import java.util.Scanner;

/**
 *
 * @author Andrew & Finlay
 */
public class InputHandler
{
    private Scanner scanner;

    public InputHandler() 
    { // init the scanner
        this.scanner = new Scanner(System.in);
    }
    
    public String getStringInput(String prompt) 
    {
        if (!prompt.isEmpty()) 
        {
            System.out.print(prompt);
        }
        return scanner.nextLine();
    }

    public boolean confirmAction(String message) 
    { // used to ask the player yes or no
        System.out.println(message + " ('Y' or 'N')");
        System.out.print("> ");
        
        String userInput = scanner.nextLine();
        
        return userInput.toUpperCase().equals("Y");
    }
}