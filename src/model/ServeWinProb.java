
package model;

/**
 *
 * @author gabriel
 */
public class ServeWinProb {
    
    /**
     * Returns the advantage (disadvantage) Player A has over Player B in t
     * terms of the percentage of service points won against opponent C  
     * @param spwAC the percentage of service points won by A against C
     * @param rpwAC the percentage of returning points won by A against C
     * @param spwBC the percentage of service points won by B against C 
     * @param rpwBC the percentage of returning points won by B against C
     * @return the advantage (disadvantage) A has over B
     */
    public static double deltaABC(double spwAC, double rpwAC, double spwBC, double rpwBC){
        return (spwAC-(1-rpwAC))-(spwBC-(1-rpwBC));
    }
    
    public static double serveWinningPercentage(double serviceWinningPercentageA, double returnWinningPercentageB, double serveWinTournamentAv, double serveWinTourAv, double returnWinTourAv){     
        return serveWinTournamentAv + (serviceWinningPercentageA-serveWinTourAv)-(returnWinningPercentageB-returnWinTourAv);
    }
    
    /**
     * Returns the percentage of points won on return
     * @param firstServeInAv tour (ATP/WTA) average first serve in percentage 
     * @param firstReturnWin percentage of points won on first return
     * @param secondReturnWin percentage of points won on second return
     * @return the percentage of points won on return
     */
    public static double returnWinninPercentage(double firstServeInAv, double firstReturnWin, double secondReturnWin){
        return firstServeInAv*firstReturnWin + (1-firstServeInAv)*secondReturnWin; 
    }

}
