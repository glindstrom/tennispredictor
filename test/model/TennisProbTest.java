/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author gabriel
 */
public class TennisProbTest {
    
    public static final double EPSILON = 0.01;
    public TennisProb tieBreakCalc3;
    public TennisProb tieBreakCalc5;
    public TennisProb advantageCalc3;
    public TennisProb advantageCalc5;
    
    @Before
    public void setUp(){
        tieBreakCalc5 = TennisProb.createTieBreakTennisProbCaclulator(true);
        tieBreakCalc3 = TennisProb.createTieBreakTennisProbCaclulator(false);
        advantageCalc3 = TennisProb.createAdvantageTennisProbCalculator(false);
        advantageCalc5 = TennisProb.createAdvantageTennisProbCalculator(true);
    }
    
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
        assertEquals("Probability when p is 0.65 and q is 0.37: ", 0.5985, tieBreakCalc3.pMatch(0.65, 0.37), EPSILON);
    }
    
    @Test
    public void correctProbabilitForWinningGameFromScore(){
        assertEquals("Probability when p is 0.5: ",0.5, TennisProb.pGame(0,0,0.5), EPSILON);
        assertEquals("Probability when p is 0.6: ", 0.74, TennisProb.pGame(0,0,0.6), EPSILON);
        assertEquals("Probability when p is 0.6: ", 0.74, TennisProb.pGame(0,0,0.6), EPSILON);
        assertEquals("Probability when p is 0.723: ", 0.957, TennisProb.pGame(2,1,0.723), EPSILON);
    }
    
    @Test
    public void correctProbabilityForWinningTieBreakSetFromScore(){
        assertEquals("Probability when pA and pB are 0.5: ",0.5, tieBreakCalc5.pSet(0,0,0.5, 0.5, 0), EPSILON); 
        assertEquals("Probability from 4-5 when pA is 0.68 and pB is 0.62: ",0.135, tieBreakCalc5.pSet(4,5,0.67, 0.62, 0), EPSILON); 
        assertEquals("Probability from 2-4 when pA is 0.62 and pB is 0.67: ",0.072, tieBreakCalc5.pSet(2,4,0.62, 0.67, 0), EPSILON);
        
    }
    
    @Test
    public void correctProbabilityForWinningAdvantageSetFromScore(){
        assertEquals(0.57, advantageCalc3.pSet(0, 0, 0.6, 0.58, 0), EPSILON);
        assertEquals(0.56, advantageCalc3.pSet(3, 3, 0.6, 0.58, 0), EPSILON);
        assertEquals(0.92, advantageCalc3.pSet(5, 3, 0.6, 0.58, 0), EPSILON);
    }
    
    @Test
    public void correctProbabilityForWinningMatchFromScore(){
        assertEquals(0.5, tieBreakCalc5.pMatch(0, 0, 0.5, 0.5), EPSILON);
        assertEquals(0.5985, tieBreakCalc3.pMatch(0, 0, 0.65, 0.63), EPSILON);
        assertEquals(0.8331, tieBreakCalc3.pMatch(0, 0, 0.709, 0.629), EPSILON);
    }
    
    
    
}
