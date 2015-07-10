package main;

import model.ServeWinProb;
import model.TennisProb;

/**
 *
 * @author gabriel
 */
public class Main {

    public static final double firstServeInATPAv = 0.634;
    public static final double firstServeInWTAAv = 0.638;
    public static final double servicePointWinWimbledonMAv = 0.664;
    public static final double servicePointWinWimbledonWAv = 0.583;
    public static final double servicePointWinATPAv = 0.658;
    public static final double servicePointWinWTAAv = 0.581;
    public static final double returnPointWinATPAv = 0.342;
    public static final double returnPointWinWTAAv = 0.419;

    public static void main(String[] args) {

        TennisProb wimbledon5 = TennisProb.createWimbledonTennisProbCalculator(true);
        TennisProb wimbledon3 = TennisProb.createWimbledonTennisProbCalculator(false);

        boolean men = true;

        double servicePointWinA = 0.696;
        double firstReturnWonA = 0.33;
        double secondReturnWonA = 0.579;

        double returnWonA = 0.431;

        double servicePointWinB = 0.714;
        double firstReturnWonB = 0.323;
        double secondReturnWonB = 0.507;

        double returnWonB = 0.398;
        
        double servicePointWonAC = 0.686;
        double returnPointWonAC = 0.419;
        
        double servicePointWonBC = 0.713;
        double returnPoinWonBC = 0.38;

        

        double deltaAB = ServeWinProb.deltaABC(servicePointWonAC, returnPointWonAC, servicePointWonBC, returnPoinWonBC);
        double pWinCommon;
        double pWinG;
        double pWinAdjusted;
        if (men) {
            double returnPointWinningPercentageA = ServeWinProb.returnWinninPercentage(firstServeInATPAv, firstReturnWonA, secondReturnWonA);
            double returnPointWinningPercentageB = ServeWinProb.returnWinninPercentage(firstServeInATPAv, firstReturnWonB, secondReturnWonB);
            double serveWinProbabilityA = ServeWinProb.serveWinningPercentage(servicePointWinA, returnPointWinningPercentageB,
                    servicePointWinWimbledonMAv, servicePointWinATPAv, returnPointWinATPAv);
            double serveWinProbabilityB = ServeWinProb.serveWinningPercentage(servicePointWinB, returnPointWinningPercentageA,
                    servicePointWinWimbledonMAv, servicePointWinATPAv, returnPointWinATPAv);

            pWinCommon = 0.5 * (wimbledon5.pMatch(0, 0, 0.6 + deltaAB, 0.6)
                    + wimbledon5.pMatch(0, 0, 0.6, 0.6 - deltaAB));
            pWinG = wimbledon5.pMatch(0, 0, serveWinProbabilityA, serveWinProbabilityB);
            pWinAdjusted = wimbledon5.pMatch(0, 0, servicePointWinA - (returnWonB - returnPointWinATPAv), servicePointWinB - (returnWonA - returnPointWinATPAv));
        } else {
            double returnPointWinningPercentageA = ServeWinProb.returnWinninPercentage(firstServeInWTAAv, firstReturnWonA, secondReturnWonA);
            double returnPointWinningPercentageB = ServeWinProb.returnWinninPercentage(firstServeInWTAAv, firstReturnWonB, secondReturnWonB);
            double serveWinProbabilityA = ServeWinProb.serveWinningPercentage(servicePointWinA, returnPointWinningPercentageB,
                    servicePointWinWimbledonWAv, servicePointWinWTAAv, returnPointWinWTAAv);
            double serveWinProbabilityB = ServeWinProb.serveWinningPercentage(servicePointWinB, returnPointWinningPercentageA,
                    servicePointWinWimbledonWAv, servicePointWinWTAAv, returnPointWinWTAAv);
            pWinCommon = 0.5 * (wimbledon3.pMatch(0, 0, 0.6 + deltaAB, 0.6)
                    + wimbledon3.pMatch(0, 0, 0.6, 0.6 - deltaAB));
            pWinG = wimbledon3.pMatch(0, 0, serveWinProbabilityA, serveWinProbabilityB);
            pWinAdjusted = wimbledon3.pMatch(0, 0, servicePointWinA - (returnWonB - returnPointWinWTAAv), servicePointWinB - (returnWonA - returnPointWinWTAAv));
        }
        System.out.println(deltaAB + "\n");
        System.out.println("Probability player A wins (Common opponent): " + pWinCommon);
        System.out.println("Probability player B wins (Common opponent): " + (1 - pWinCommon) + "\n");
        System.out.println("A odds (Common): " + String.format("%3.2f", 1 / pWinCommon));
        System.out.println("B odds (Common): " + String.format("%3.2f",1 / (1 - pWinCommon)) + "\n");
        System.out.println("Probability player A wins (G): " + pWinG);
        System.out.println("Probability player B wins (G): " + (1 - pWinG) + "\n");
        System.out.println("A odds (G): " + String.format("%3.2f", 1 / pWinG));
        System.out.println("B odds (G): " + String.format("%3.2f", 1 / (1 - pWinG)) + "\n");
        
        System.out.println("A odds (adjusted): " + String.format("%3.2f", 1 / pWinAdjusted));
        System.out.println("B odds (adjusted): " + String.format("%3.2f", 1 / (1-pWinAdjusted)));

    }

}
