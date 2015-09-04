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


    public static void main(String[] args) {
        boolean men = false;

        TennisProb usOpen = TennisProb.createTieBreakTennisProbCaclulator(true);
        TennisProb usOpenWta = TennisProb.createTieBreakTennisProbCaclulator(false);
        TennisScracper scraper = new TennisScracper();
        OddsCalculator calculator;
        List<Match> matches;
        if (men) {
            calculator = new OddsCalculator(usOpen);
            matches = scraper.fetchMatches("US Open ATP");

        } else {
            calculator = new OddsCalculator(usOpenWta);
            matches = scraper.fetchMatches("US Open WTA");            
        }
        matches = matches.stream().filter(m -> m.getMatchesPlayedA() > 4 
                && m.getMatchesPlayedB() > 4 && m.getCommonOpponentMatchesPlayedA() > 4 && m.getCommonOpponentMatchesPlayedB() > 4).collect(Collectors.toList());
        matches.forEach(match -> calculator.calculateOdds(match));
        matches.forEach(match -> System.out.println(match));   
    }

}
