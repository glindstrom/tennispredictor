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
     * @param pWinPointServe the probability of winning point on serve
     * @return the probability of winning game when serving
     */
    public static double pGame(double pWinPointServe) {
        return Math.pow(pWinPointServe, 4) * (15 - 4 * pWinPointServe - (10 * Math.pow(pWinPointServe, 2)) / (1 - 2 * pWinPointServe * (1 - pWinPointServe)));
    }

    public static double pTieBraker(double pWinPointServe, double pWinPointReturn) {
        double pWin = 0;
        for (int[] A1 : A) {
            pWin += A1[0] * Math.pow(pWinPointServe, A1[1]) * Math.pow(1 - pWinPointServe, A1[2])
                    * Math.pow(pWinPointReturn, A1[3]) * Math.pow(1 - pWinPointReturn, A1[4]) * Math.pow(d(pWinPointServe, pWinPointReturn), A1[5]);
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
     * @param pWinPointServe the probability of winning point when serving
     * @param pWinPointReturn the probability of winning point when returning serve
     * @return the probability of winning a set
     */
    public static double pSet(double pWinPointServe, double pWinPointReturn) {
        double pWin = 0;
        for (int[] B1 : B) {
            pWin += B1[0] * Math.pow(pGame(pWinPointServe), B1[1]) * Math.pow(1 - pGame(pWinPointServe), B1[2])
                    * Math.pow(pGame(pWinPointReturn), B1[3]) * Math.pow(1 - pGame(pWinPointReturn), B1[4])
                    * Math.pow(pGame(pWinPointServe) * pGame(pWinPointReturn) + (pGame(pWinPointServe) * (1 - pGame(pWinPointReturn)) + (1 - pGame(pWinPointServe)) * pGame(pWinPointReturn)) * pTieBraker(pWinPointServe, pWinPointReturn), B1[5]);
        }
        return pWin;
    }

    /**
     * Returns the probability of winning match given
     *
     * @param pWinPointServe the probability of winning point when serving
     * @param pWinPointReturn the probability of winning point when returning serve
     * @return the probability of winning match
     */
    public double pMatch(double pWinPointServe, double pWinPointReturn) {
        if (fiveSetter) {
            return Math.pow(pSet(pWinPointServe, pWinPointReturn), 3) * (1 + 3 * (1 - pSet(pWinPointServe, pWinPointReturn)) + 6 * (1 - Math.pow(pSet(pWinPointServe, pWinPointReturn), 2)));
        } else {
            return Math.pow(pSet(pWinPointServe, pWinPointReturn), 2) * (1 + 2 * (1 - pSet(pWinPointServe, pWinPointReturn)));
        }
    }

    /**
     * Returns the probability of winning game on serve from score x-y given
     * probability p of winning point
     *
     * @param scoreA the score of player A expressed as an integer in the interval
     * [1,4}
     * @param scoreB the score of player B expressed as an integer in the interval
     * [1,4]
     * @param p the probability of player A winning point on serve
     * @return the probability of player A winning game
     */
    public static double pGame(int scoreA, int scoreB, double p) {
        if (scoreA == 4) {
            return 1;
        }
        if (scoreB == 4) {
            return 0;
        }
        if (scoreA == 3 && scoreB == 3) {
            return Math.pow(p, 2) / (Math.pow(p, 2) + Math.pow(1 - p, 2));
        }
        return p * pGame(scoreA + 1, scoreB, p) + (1 - p) * pGame(scoreA, scoreB + 1, p);
    }

    /**
     * Returns the probability of player A, serving first, winning a tie-break.
     *
     * @param gamesWonA the number of games won by player A
     * @param gamesWonB the number of games won by player B
     * @param pWinPointA the probability of player A winning point when serving
     * @param pWinPointB the probability of player B winning point when serving
     * @return the probability of player A winning the tie-break
     */
    public static double pTieBraker(int gamesWonA, int gamesWonB, double pWinPointA, double pWinPointB) {
        if (gamesWonA == 7 && (gamesWonA - gamesWonB) >= 2) {
            return 1;
        }
        if (gamesWonB == 7 && (gamesWonB - gamesWonA) >= 2) {
            return 0;
        }
        if (gamesWonA == 6 && gamesWonB == 6) {
            return (pWinPointA * (1 - pWinPointB)) / ((pWinPointA * (1 - pWinPointB) + (1 - pWinPointA) * pWinPointB));
        }
        if ((gamesWonA + gamesWonB + 3) % 4 > 1) {
            return pWinPointA * pTieBraker(gamesWonA + 1, gamesWonB, pWinPointA, pWinPointB) + (1 - pWinPointA) * pTieBraker(gamesWonA, gamesWonB + 1, pWinPointA, pWinPointB);
        } else {
            return pWinPointB * pTieBraker(gamesWonA, gamesWonB+1, pWinPointA, pWinPointB) + (1 - pWinPointB) * pTieBraker(gamesWonA+1, gamesWonB, pWinPointA, pWinPointB);
        }

    }

    /**
     * Returns the probability of Player A winning the match.
     * @param gamesWonA the number of games Player A has won in the current set
     * @param gamesWonB the number of games Player B has won in the current set
     * @param pWinPointA the probability of Player A winning a point on serve
     * @param pWinPointB the probability of Player B winning a point on serve
     * @param setsPlayed the number of completed sets in the match
     * @return the probability of Player A winning the match
     */
    public double pSet(int gamesWonA, int gamesWonB, double pWinPointA, double pWinPointB, int setsPlayed) {
        if (gamesWonA >= 6 && (gamesWonA - gamesWonB) >= 2) {
            return 1;
        }
        if (gamesWonB >= 6 && (gamesWonB - gamesWonA) >= 2) {
            return 0;
        }
        if (tieBreak || (wimbledon && !isLastSet(setsPlayed))) {

            if (gamesWonA == 6 && gamesWonB == 6) {
                return pTieBraker(0,0,pWinPointA, pWinPointB);
            }
        } else {
            if (gamesWonA == 5 && gamesWonB == 5) {
                return (pGame(pWinPointA) * (1 - pGame(pWinPointB))) / (pGame(pWinPointA) * (1 - pGame(pWinPointB)) + (1 - pGame(pWinPointA)) * pGame(pWinPointB));
            }
        }
        if ((gamesWonA+gamesWonB)%2 == 0){
            return pGame(0,0,pWinPointA)*pSet(gamesWonA+1, gamesWonB, pWinPointA, pWinPointB, setsPlayed) +
                    (1-pGame(0,0,pWinPointA))*pSet(gamesWonA, gamesWonB+1, pWinPointA, pWinPointB, setsPlayed);
        } else{
            return pGame(0,0,pWinPointB)*pSet(gamesWonA, gamesWonB+1, pWinPointA, pWinPointB, setsPlayed) + 
                    (1-pGame(0,0,pWinPointB))*pSet(gamesWonA+1, gamesWonB, pWinPointA, pWinPointB, setsPlayed);
        }
    }
    
    /**
     * Returns the probability of Player A winning the match.
     * @param setsWonA the number of sets won by Player A so far
     * @param setsWonB the number of sets won by Player B so far
     * @param pWinPointA the probability of Player A winning point on serve
     * @param pWinPointB the probability of Player B winning point on serve
     * @return the probability of Player A winning the match.
     */
    public double pMatch(int setsWonA, int setsWonB, double pWinPointA, double pWinPointB){
        int setsPlayed = setsWonA+setsWonB;
        if (fiveSetter){
            if (setsWonA == 3) return 1;            
            if (setsWonB == 3) return 0;
            if (setsWonA == 2 && setsWonB == 2) return pSet(0,0,pWinPointA,pWinPointB, setsPlayed);
        } else{
            if (setsWonA == 2) return 1;
            if (setsWonB == 2)return 0;
            if (setsWonA == 1 && setsWonB == 1) return pSet(0,0,pWinPointA,pWinPointB, setsPlayed);
        }
        if ((setsWonA+setsWonB) % 2 == 0){
            double pSetA = pSet(0,0,pWinPointA,pWinPointB, setsPlayed);
            return pSetA*pMatch(setsWonA+1, setsWonB, pWinPointA, pWinPointB)+
                (1-pSetA)*pMatch(setsWonA, setsWonB+1, pWinPointA, pWinPointB);    
        } else{
            double pSetB = pSet(0, 0, pWinPointB, pWinPointA, setsPlayed);
            return pSetB*pMatch(setsWonA, setsWonB+1, pWinPointA, pWinPointB)+
                    (1-pSetB)*pMatch(setsWonA+1, setsWonB, pWinPointA, pWinPointB);
        }
    }

    private boolean isLastSet(int setsPlayed) {
        return (fiveSetter && setsPlayed == 4 || (!fiveSetter && setsPlayed == 2)); 
        
    }
    

}
