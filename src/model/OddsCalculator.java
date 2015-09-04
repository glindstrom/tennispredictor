package model;

/**
 *
 * @author gabriel
 */
public class OddsCalculator {

    TennisProb calculator;

    public OddsCalculator(TennisProb calculator) {
        this.calculator = calculator;
    }

    public void calculateOdds(Match match) {
        double deltaAB = ServeWinProb.deltaABC(match.getPlayerAstat(Statistics.SERVICE_POINT_WON_VS_COMMON_OPPONENT), match.getPlayerAstat(Statistics.RETURN_POINT_WON_VS_COMMON_OPPONENT), match.getPlayerBstat(Statistics.SERVICE_POINT_WON_VS_COMMON_OPPONENT),
                match.getPlayerBstat(Statistics.RETURN_POINT_WON_VS_COMMON_OPPONENT));
        double pWinCommonOpponetsA = 0.5 * (calculator.pMatch(0, 0, 0.6 + deltaAB, 0.6)
                + calculator.pMatch(0, 0, 0.6, 0.6 - deltaAB));
        double pWinHistoricalA = calculator.pMatch(0, 0, match.getPlayerAstat(Statistics.SERVICE_POINT_WON)
                - (match.getPlayerBstat(Statistics.RETURN_POINT_WON) - 0.6),
                match.getPlayerBstat(Statistics.SERVICE_POINT_WON)
                - (match.getPlayerAstat(Statistics.RETURN_POINT_WON) - 0.6));
        match.setOddsA(Odds.COMMON_OPPONENTS, formatOdds(pWinCommonOpponetsA));
        match.setOddsA(Odds.HISTORICAL, formatOdds(pWinHistoricalA));
        match.setOddsB(Odds.COMMON_OPPONENTS, formatOdds(1-pWinCommonOpponetsA));
        match.setOddsB(Odds.HISTORICAL, formatOdds(1-pWinHistoricalA));
        
    }
    
    private String formatOdds(double winProb){
        return String.format("%3.2f", 1 / winProb);
    }
    
    public static void main(String[] args) {
        double spwA = 0.586;
        double rpwA = 0.435;
        double spwB = 0.511;
        double rpwB = 0.44;
        double deltaAB = ServeWinProb.deltaABC(0.586, 0.435, 0.511, 0.44);
        System.out.println(deltaAB);
        TennisProb calculator = TennisProb.createTieBreakTennisProbCaclulator(false);
        OddsCalculator oddsCalc = new OddsCalculator(calculator);
        double pWinCommonOpponetsA = 0.5 * (calculator.pMatch(0, 0, 0.6 + deltaAB, 0.6)
                + calculator.pMatch(0, 0, 0.6, 0.6 - deltaAB));
        System.out.println(pWinCommonOpponetsA);
        Match match = new Match();
        match.setPlayerAname("A");
        match.setPlayerBname("B");
        match.setPlayerAstat(Statistics.SERVICE_POINT_WON_VS_COMMON_OPPONENT, spwA);
        match.setPlayerAstat(Statistics.RETURN_POINT_WON_VS_COMMON_OPPONENT, rpwA);
        match.setPlayerAstat(Statistics.SERVICE_POINT_WON, rpwA);
        match.setPlayerAstat(Statistics.RETURN_POINT_WON, rpwA);
        match.setPlayerBstat(Statistics.SERVICE_POINT_WON_VS_COMMON_OPPONENT, spwB);
        match.setPlayerBstat(Statistics.SERVICE_POINT_WON, spwB);
        match.setPlayerBstat(Statistics.RETURN_POINT_WON_VS_COMMON_OPPONENT, rpwB);
        match.setPlayerBstat(Statistics.RETURN_POINT_WON, rpwB);
        oddsCalc.calculateOdds(match);
        System.out.println(match);
        
    }

}
