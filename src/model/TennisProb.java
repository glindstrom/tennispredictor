

package model;

/**
 *
 * @author gabriel
 */
public interface TennisProb {
    
    public static int[][] A = {{1,3,0,4,0,0},{3,3,1,4,0,0},{4,4,0,3,1,0},
        {6,3,2,4,0,0},{16,4,1,3,1,0},{6,5,0,2,2,0},{10,2,3,5,0,0},
        {40,3,2,4,1,0},{30,4,1,3,2,0},{4,5,0,2,3,0},{5,1,4,6,0,0},{50,2,3,5,1,0},
        {100,3,2,4,2,0},{50,4,1,3,3,0},{5,5,0,2,4,0},{1,1,5,6,0,0},{30,2,4,5,1,0},
        {150,3,3,4,2,0},{200,4,2,3,3,0},{75,5,1,2,4,0},{6,6,0,1,5,0},
        {1,0,6,6,0,1},{36,1,5,5,1,1},{225,2,4,4,2,1},{400,3,3,3,3,1},
        {225,4,2,2,4,1},{36,5,1,1,5,1},{1,6,0,0,6,1}};
    
    public static int[][] B = {{1,3,0,3,0,0},{3,3,1,3,0,0},{3,4,0,2,1,0},
        {6,2,2,4,0,0},{12,3,1,3,1,0},{3,4,0,2,2,0},{4,2,3,4,0,0},{24,3,2,3,1,0},
        {24,4,1,2,2,0},{4,5,0,1,3,0},{5,1,4,5,0,0},{40,2,3,4,1,0},{60,3,2,3,2,0},
        {20,4,1,2,3,0},{1,5,0,1,4,0},{25,1,4,4,1,1},{100,2,3,3,2,1},
        {100,3,2,2,3,1},{25,4,1,1,4,1},{1,5,0,0,5,1}};
    
    /**
     * Returns the probability of winning a game given the probability of 
     * winning point on serve.
     * @param p the probability of winning point on serve
     * @return the probability of winning game when serving
     */
    public static double pGame(double p){
        return Math.pow(p, 4)*(15-4*p-(10*Math.pow(p, 2))/(1-2*p*(1-p)));
    }
    
    public static double pTieBraker(double p, double q){
        double pWin = 0;
        for (int[] A1 : A) {
            pWin += A1[0] * Math.pow(p, A1[1]) * Math.pow(1-p, A1[2]) * 
                    Math.pow(q, A1[3]) * Math.pow(1-q, A1[4]) * Math.pow(d(p,q), A1[5]);
        }
        return pWin;
    }
    
    public static double d(double p, double q){
        return p*q*Math.pow(1-(p*(1-q)+(1-p)*q),-1);
    }
    
    /**
     * Returns the probability of winning set given probabilities of winning 
     * points when serving and returning serves.
     * @param p the probability of winning point when serving
     * @param q the probability of  winning point when returning serve
     * @return the probability of winning a set
     */
    public static double pSet(double p, double q){
        double pWin = 0;
        for (int[] B1 : B) {
            pWin += B1[0] * Math.pow(pGame(p), B1[1]) * Math.pow(1-pGame(p), B1[2]) * 
                    Math.pow(pGame(q), B1[3]) * Math.pow(1-pGame(q), B1[4]) * 
                    Math.pow(pGame(p)*pGame(q)+(pGame(p)*(1-pGame(q))+(1-pGame(p))*pGame(q))*pTieBraker(p, q), B1[5]);
        }
        return pWin;
    }
    
    /**
     * Returns the probability of winning match given 
     * @param p the probability of winning point when serving
     * @param q the probability of  winning point when returning serve
     * @param fiveSetter true if the match is best of five, false if it is best of three
     * @return 
     */
    public static double pMatch(double p, double q, boolean fiveSetter){
        if (fiveSetter){
            return Math.pow(pSet(p,q), 3)*(1+3*(1-pSet(p,q))+6*(1-Math.pow(pSet(p,q), 2)));
        }
        else{
            return Math.pow(pSet(p,q), 2)*(1+2*(1-pSet(p, q)));
        }
    }

    

}
