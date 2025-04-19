/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pdc_chessgame;

/**
 *
 * @author ARKen
 */
public abstract class Pieces { //its an abstract class btw
    
    public String piecesName = "";
    public double value = 0;
    
    public int xPosition = 0;
    public int yPosition = 0;    

    public Pieces(String name) {
        this.piecesName = name;
    }

    public void printInfo() {
        System.out.println(this.piecesName);

    }

    public void setName(String newName) {
        this.piecesName = newName;
    }

    //returns True if move move completes successfully
    public abstract boolean movePiece();
    
}
