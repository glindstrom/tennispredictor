package main;

import java.util.List;
import java.util.stream.Collectors;
import model.Match;
import model.OddsCalculator;
import model.TennisProb;
import scraper.TennisScracper;

/**
 *
 * @author gabriel
 */
public class Main {

    public static final double firstServeInATPAv = 0.599;
    public static final double firstServeInWTAAv = 0.611;
//    public static final double servicePointWinTournamentAvMen = 0.634;
//    public static final double servicePointWinTournamentAvWomen = 0.583;
    public static final double servicePointWinATPAv = 0.632;
    public static final double servicePointWinWTAAv = 0.627;
    public static final double returnPointWinATPAv = 0.368;
    public static final double returnPointWinWTAAv = 0.442;

    public static void main(String[] args) {

        TennisProb usOpen = TennisProb.createTieBreakTennisProbCaclulator(true);
        TennisProb usOpenWta = TennisProb.createTieBreakTennisProbCaclulator(false);
        //OddsCalculator calculator = new OddsCalculator(usOpen);
        OddsCalculator calculator = new OddsCalculator(usOpenWta);
        TennisScracper scraper = new TennisScracper();
        //List<Match> matches = scraper.fetchMatches("US Open ATP");
        List<Match> matches = scraper.fetchMatches("US Open WTA");
        matches = matches.stream().filter(m -> m.getMatchesPlayedA() > 4 
                && m.getMatchesPlayedB() > 4 && m.getCommonOpponentMatchesPlayedA() > 4 && m.getCommonOpponentMatchesPlayedB() > 4).collect(Collectors.toList());
        matches.forEach(match -> calculator.calculateOdds(match));
        matches.forEach(match -> System.out.println(match));


    

    }

}
