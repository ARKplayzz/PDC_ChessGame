/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package pdc_chessgame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author finla
 */
public class ClockTest {
    
    public ClockTest() {
    }
    
    Clock instance = new Clock(1, 2);
    
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
    public void testSetTime()
    {
        System.out.println("Setting time");
        instance.setBlacksTime(20);
        
        assertEquals(20, instance.getBlacksTime(), 0.0);
    }
    
    @Test
    public void testSwapClock()
    {
        System.out.println("Swapping clock");
        instance.swapClock();
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        
        assertNotEquals(20, instance.getTime(), 0.0);
    }
    
    @Test 
    public void testToString()
    {
        System.out.println("toString");
        instance.setWhitesTime(1*60);
        instance.setActive(Team.WHITE);
        
        String expected = "0:59";
        String actual = instance.toString();
        
        assertEquals(expected, actual);
    }
}
