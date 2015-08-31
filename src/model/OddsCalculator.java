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

}
