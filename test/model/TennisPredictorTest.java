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
    public void correctProbabilityForWinningMatch(){
        assertEquals("Probability when p is 0.65 and q is 0.37: ", 0.5985, TennisProb.pMatch(0.65, 0.37, false), EPSILON);
    }
    
    @Test
    public void correctProbabilitForWinningGameFromLoveAll(){
        assertEquals("Probability when p is 0.5: ",0.5, TennisProb.pGame(0,0,0.5), EPSILON);
        assertEquals("Probability when p is 0.6: ", 0.74, TennisProb.pGame(0,0,0.6), EPSILON);
    }
    
    @Test
    public void correctProbabilityForWinningTieBreakSetFromScore(){
        assertEquals("Probability when pA and pB are 0.5: ",0.5, TennisProb.pSet(0,0,0.5, 0.5, true), EPSILON); 
        assertEquals("Probability from 4-5 when pA is 0.68 and pB is 0.62: ",0.135, TennisProb.pSet(4,5,0.67, 0.62, true), EPSILON); 
        assertEquals("Probability from 2-4 when pA is 0.62 and pB is 0.67: ",0.072, TennisProb.pSet(2,4,0.62, 0.67, true), EPSILON);
        
    }
    
    @Test
    public void correctProbabilityForWinningAdvantageSetFromScore(){
        assertEquals(0.57, TennisProb.pSet(0, 0, 0.6, 0.58, false), EPSILON);
        assertEquals(0.56, TennisProb.pSet(3, 3, 0.6, 0.58, false), EPSILON);
        assertEquals(0.92, TennisProb.pSet(5, 3, 0.6, 0.58, false), EPSILON);
    }
    
    @Test
    public void correctProbabilityForWinningMatchFromScore(){
        assertEquals(0.5, TennisProb.pMatch(0, 0, 0.5, 0.5, true, true), EPSILON);
        assertEquals(0.5985, TennisProb.pMatch(0, 0, 0.65, 0.63, false, true), EPSILON);
        assertEquals(0.8331, TennisProb.pMatch(0, 0, 0.709, 0.629, false, true), EPSILON);
    }
    
    
    
}
