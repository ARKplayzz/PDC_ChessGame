/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pdc_chessgame;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author finla
 */
public class PieceTest {
    
    ChessBoard board = new ChessBoard(8, 8);
    Queen queen = new Queen(1, 1, Team.WHITE);
    
    public PieceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
        
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
        
    }
    
    @Before
    public void setUp() 
    {
        
    }
    
    @Test
    public void testMoveQueen()
    {
        System.out.println("Testing queen movement");
        List<Tile> list = queen.canMove(board);
        
    }
    
    @After
    public void tearDown() 
    {
    }
}
