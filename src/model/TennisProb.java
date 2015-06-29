package model;

/**
 *
 * @author gabriel
 */
public class TennisProb {

    private final static int[][] A = {{1, 3, 0, 4, 0, 0}, {3, 3, 1, 4, 0, 0}, {4, 4, 0, 3, 1, 0},
    {6, 3, 2, 4, 0, 0}, {16, 4, 1, 3, 1, 0}, {6, 5, 0, 2, 2, 0}, {10, 2, 3, 5, 0, 0},
    {40, 3, 2, 4, 1, 0}, {30, 4, 1, 3, 2, 0}, {4, 5, 0, 2, 3, 0}, {5, 1, 4, 6, 0, 0}, {50, 2, 3, 5, 1, 0},
    {100, 3, 2, 4, 2, 0}, {50, 4, 1, 3, 3, 0}, {5, 5, 0, 2, 4, 0}, {1, 1, 5, 6, 0, 0}, {30, 2, 4, 5, 1, 0},
    {150, 3, 3, 4, 2, 0}, {200, 4, 2, 3, 3, 0}, {75, 5, 1, 2, 4, 0}, {6, 6, 0, 1, 5, 0},
    {1, 0, 6, 6, 0, 1}, {36, 1, 5, 5, 1, 1}, {225, 2, 4, 4, 2, 1}, {400, 3, 3, 3, 3, 1},
    {225, 4, 2, 2, 4, 1}, {36, 5, 1, 1, 5, 1}, {1, 6, 0, 0, 6, 1}};

    private final static int[][] B = {{1, 3, 0, 3, 0, 0}, {3, 3, 1, 3, 0, 0}, {3, 4, 0, 2, 1, 0},
    {6, 2, 2, 4, 0, 0}, {12, 3, 1, 3, 1, 0}, {3, 4, 0, 2, 2, 0}, {4, 2, 3, 4, 0, 0}, {24, 3, 2, 3, 1, 0},
    {24, 4, 1, 2, 2, 0}, {4, 5, 0, 1, 3, 0}, {5, 1, 4, 5, 0, 0}, {40, 2, 3, 4, 1, 0}, {60, 3, 2, 3, 2, 0},
    {20, 4, 1, 2, 3, 0}, {1, 5, 0, 1, 4, 0}, {25, 1, 4, 4, 1, 1}, {100, 2, 3, 3, 2, 1},
    {100, 3, 2, 2, 3, 1}, {25, 4, 1, 1, 4, 1}, {1, 5, 0, 0, 5, 1}};
    
    private final boolean wimbledon;
    private final boolean tieBreak;
    private final boolean fiveSetter;
    
    private TennisProb(boolean wimbledon, boolean tieBreak, boolean fiveSetter){
        this.wimbledon = wimbledon;
        this.tieBreak = tieBreak;
        this.fiveSetter = fiveSetter;
    }
    
    public static TennisProb createWimbledonTennisProbCalculator(boolean fiveSetter){
        return new TennisProb(true, false, fiveSetter);
    }
    
    public static TennisProb createTieBreakTennisProbCaclulator(boolean fiveSetter){
        return new TennisProb(false, true, fiveSetter);
    }
    
    public static TennisProb createAdvantageTennisProbCalculator(boolean fiveSetter){
        return new TennisProb(false, false, fiveSetter);
    }

    /**
     * Returns the probability of winning a game given the probability of
     * winning point on serve.
     *
     * @param p the probability of winning point on serve
     * @return the probability of winning game when serving
     */
    public static double pGame(double p) {
        return Math.pow(p, 4) * (15 - 4 * p - (10 * Math.pow(p, 2)) / (1 - 2 * p * (1 - p)));
    }

    public static double pTieBraker(double p, double q) {
        double pWin = 0;
        for (int[] A1 : A) {
            pWin += A1[0] * Math.pow(p, A1[1]) * Math.pow(1 - p, A1[2])
                    * Math.pow(q, A1[3]) * Math.pow(1 - q, A1[4]) * Math.pow(d(p, q), A1[5]);
        }
        return pWin;
    }

    private static double d(double p, double q) {
        return p * q * Math.pow(1 - (p * (1 - q) + (1 - p) * q), -1);
    }

    /**
     * Returns the probability of winning set given probabilities of winning
     * points when serving and returning serves.
     *
     * @param p the probability of winning point when serving
     * @param q the probability of winning point when returning serve
     * @return the probability of winning a set
     */
    public static double pSet(double p, double q) {
        double pWin = 0;
        for (int[] B1 : B) {
            pWin += B1[0] * Math.pow(pGame(p), B1[1]) * Math.pow(1 - pGame(p), B1[2])
                    * Math.pow(pGame(q), B1[3]) * Math.pow(1 - pGame(q), B1[4])
                    * Math.pow(pGame(p) * pGame(q) + (pGame(p) * (1 - pGame(q)) + (1 - pGame(p)) * pGame(q)) * pTieBraker(p, q), B1[5]);
        }
        return pWin;
    }

    /**
     * Returns the probability of winning match given
     *
     * @param p the probability of winning point when serving
     * @param q the probability of winning point when returning serve
     * @return the probability of winning match
     */
    public double pMatch(double p, double q) {
        if (fiveSetter) {
            return Math.pow(pSet(p, q), 3) * (1 + 3 * (1 - pSet(p, q)) + 6 * (1 - Math.pow(pSet(p, q), 2)));
        } else {
            return Math.pow(pSet(p, q), 2) * (1 + 2 * (1 - pSet(p, q)));
        }
    }

    /**
     * Returns the probability of winning game on serve from score x-y given
     * probability p of winning point
     *
     * @param x the score of player A expressed as an integer in the interval
     * [1,4}
     * @param y the score of player B expressed as an integer in the interval
     * [1,4]
     * @param p the probability of player A winning point on serve
     * @return the probability of player A winning game
     */
    public static double pGame(int x, int y, double p) {
        if (x == 4) {
            return 1;
        }
        if (y == 4) {
            return 0;
        }
        if (x == 3 && y == 3) {
            return Math.pow(p, 2) / (Math.pow(p, 2) + Math.pow(1 - p, 2));
        }
        return p * pGame(x + 1, y, p) + (1 - p) * pGame(x, y + 1, p);
    }

    /**
     * Returns the probability of player A, serving first, winning a tie-break.
     *
     * @param gamesWonA the number of games won by player A
     * @param gamesWonB the number of games won by player B
     * @param pA the probability of player A winning point when serving
     * @param pB the probability of player B winning point when serving
     * @return the probability of player A winning the tie-break
     */
    public static double pTieBraker(int gamesWonA, int gamesWonB, double pA, double pB) {
        if (gamesWonA == 7 && (gamesWonA - gamesWonB) >= 2) {
            return 1;
        }
        if (gamesWonB == 7 && (gamesWonB - gamesWonA) >= 2) {
            return 0;
        }
        if (gamesWonA == 6 && gamesWonB == 6) {
            return (pA * (1 - pB)) / ((pA * (1 - pB) + (1 - pA) * pB));
        }
        if ((gamesWonA + gamesWonB + 3) % 4 > 1) {
            return pA * pTieBraker(gamesWonA + 1, gamesWonB, pA, pB) + (1 - pA) * pTieBraker(gamesWonA, gamesWonB + 1, pA, pB);
        } else {
            return pB * pTieBraker(gamesWonA, gamesWonB+1, pA, pB) + (1 - pB) * pTieBraker(gamesWonA+1, gamesWonB, pA, pB);
        }

    }

    public double pSet(int gamesWonA, int gamesWonB, double pA, double pB) {
        if (gamesWonA >= 6 && (gamesWonA - gamesWonB) >= 2) {
            return 1;
        }
        if (gamesWonB >= 6 && (gamesWonB - gamesWonA) >= 2) {
            return 0;
        }
        if (tieBreak) {

            if (gamesWonA == 6 && gamesWonB == 6) {
                return pTieBraker(0,0,pA, pB);
            }
        } else {
            if (gamesWonA == 5 && gamesWonB == 5) {
                return (pGame(pA) * (1 - pGame(pB))) / (pGame(pA) * (1 - pGame(pB)) + (1 - pGame(pA)) * pGame(pB));
            }
        }
        if ((gamesWonA+gamesWonB)%2 == 0){
            return pGame(0,0,pA)*pSet(gamesWonA+1, gamesWonB, pA, pB) +
                    (1-pGame(0,0,pA))*pSet(gamesWonA, gamesWonB+1, pA, pB);
        } else{
            return pGame(0,0,pB)*pSet(gamesWonA, gamesWonB+1, pA, pB) + 
                    (1-pGame(0,0,pB))*pSet(gamesWonA+1, gamesWonB, pA, pB);
        }
    }
    
    public double pMatch(int setsWonA, int setsWonB, double pA, double pB){
        if (fiveSetter){
            if (setsWonA == 3) return 1;            
            if (setsWonB == 3) return 0;
            if (setsWonA == 2 && setsWonB == 2) return pSet(0,0,pA,pB);
        } else{
            if (setsWonA == 2) return 1;
            if (setsWonB == 2)return 0;
            if (setsWonA == 1 && setsWonB == 1) return pSet(0,0,pA,pB);
        }
        if ((setsWonA+setsWonB) % 2 == 0){
            double pSetA = pSet(0,0,pA,pB);
            return pSetA*pMatch(setsWonA+1, setsWonB, pA, pB)+
                (1-pSetA)*pMatch(setsWonA, setsWonB+1, pA, pB);    
        } else{
            double pSetB = pSet(0, 0, pB, pA);
            return pSetB*pMatch(setsWonA, setsWonB+1, pA, pB)+
                    (1-pSetB)*pMatch(setsWonA+1, setsWonB, pA, pB);
        }
    }
    

}
