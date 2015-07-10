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
public class ServeWinProbTest {
    
    @Test
    public void serveWinPercentageIsCorrect(){
        double spwA = 0.713;
        double rpwB = 0.375;
        double spwTournamentAv = 0.617;
        double spwTourAv = 0.616;
        double rpwTourAv = 0.384;
        assertEquals(0.723, ServeWinProb.serveWinningPercentage(spwA, rpwB, spwTournamentAv, spwTourAv, rpwTourAv), 0.001);
    }
    
    @Test
    public void returnWinningPercentageIsCorrect(){
        double fsAv = 0.587;
        double frpwB = 0.295;
        double srpwB = 0.489;
        assertEquals(0.375, ServeWinProb.returnWinninPercentage(fsAv, frpwB, srpwB), 0.001);
    }
}
