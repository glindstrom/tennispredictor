/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gabriel
 */
public class TennisPredictorTest {
    
    public static final double EPSILON = 0.01;
    
    @Test
    public void correctProbabilityForWinningGame(){
        assertEquals("Probability when p is 0.5: ",0.5, TennisProb.pGame(0.5), EPSILON);
        assertEquals("Probability when p is 0.6: ", 0.74, TennisProb.pGame(0.6), EPSILON);
    }
    
    @Test
    public void correctProbabilityForWinningSet(){
       assertEquals("Probability when p and q are 0.5: ",0.5, TennisProb.pSet(0.5, 0.5), EPSILON); 
    }
    
    @Test
    public void correctProbabilityForWinningMatc(){
        assertEquals("Probability when p is 0.65 and q is 0.37: ", 0.5985, TennisProb.pMatch(0.65, 0.37, false), EPSILON);
    }
    
}
