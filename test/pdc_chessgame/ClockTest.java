/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pdc_chessgame;

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
public class ClockTest {
    
    public ClockTest() {
    }
    
    Clock instance = new Clock(25, 2);
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() 
    {
        instance.start();
    }
    
    @After
    public void tearDown() 
    {
        instance.terminate();
    }

    @Test
    void testSetTime()
    {
        System.out.println("Setting time");
        
    }
    
}
