
package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author gabriel
 */
public class Match {
    
    private String playerAname;
    private String playerBname;
    private final Map<Statistics, Double> playerAstats;
    private final Map<Statistics, Double> playerBstats;
    private final Map<Odds, String> oddsA;
    private final Map<Odds, String> oddsB;
    private int matchesPlayedA;
    private int matchesPlayedB;
    private int commonOpponentMatchesPlayedA;
    private int commonOpponentMatchesPlayedB;

    public Match() {
        playerAstats = new HashMap();
        playerBstats = new HashMap();
        oddsA = new HashMap();
        oddsB = new HashMap();
    }

    public void setPlayerAname(String playerAname) {
        this.playerAname = playerAname;
    }

    public void setPlayerBname(String playerBname) {
        this.playerBname = playerBname;
    }

    public String getPlayerAname() {
        return playerAname;
    }

    public String getPlayerBname() {
        return playerBname;
    }
    
    public void setPlayerAstat(Statistics stat, double value){
        playerAstats.put(stat, value);
    }
    
    public double getPlayerAstat(Statistics stat){
        return playerAstats.get(stat);
    }
    
    public void setPlayerBstat(Statistics stat, double value){
        playerBstats.put(stat, value);
    }
    
    public double getPlayerBstat(Statistics stat){
        return playerBstats.get(stat);
    }

    public String getOddsA(Odds oddsType) {
        return this.oddsA.get(oddsType);
    }

    public String getOddsB(Odds oddsType) {
        return this.oddsB.get(oddsType);
    }

    public void setOddsA(Odds oddsType, String odds) {
        this.oddsA.put(oddsType, odds);
    }

    public void setOddsB(Odds oddsType, String odds) {
        this.oddsB.put(oddsType, odds);
    }

    public void setMatchesPlayedA(int matchesPlayedA) {
        this.matchesPlayedA = matchesPlayedA;
    }

    public void setMatchesPlayedB(int matchesPlayedB) {
        this.matchesPlayedB = matchesPlayedB;
    }

    public void setCommonOpponentMatchesPlayedA(int commonOpponentMatchesPlayedA) {
        this.commonOpponentMatchesPlayedA = commonOpponentMatchesPlayedA;
    }

    public void setCommonOpponentMatchesPlayedB(int commonOpponentMatchesPlayedB) {
        this.commonOpponentMatchesPlayedB = commonOpponentMatchesPlayedB;
    }

    public int getMatchesPlayedA() {
        return matchesPlayedA;
    }

    public int getMatchesPlayedB() {
        return matchesPlayedB;
    }

    public int getCommonOpponentMatchesPlayedA() {
        return commonOpponentMatchesPlayedA;
    }

    public int getCommonOpponentMatchesPlayedB() {
        return commonOpponentMatchesPlayedB;
    }
    
    

    @Override
    public String toString() {
        return playerAname + ";" + playerBname + ";" + oddsA.get(Odds.COMMON_OPPONENTS) + ";" + 
                oddsB.get(Odds.COMMON_OPPONENTS) + ";" + oddsA.get(Odds.HISTORICAL) + ";" + oddsB.get(Odds.HISTORICAL);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Match)) return false;
        Match otherMatch = (Match) other;
        return (this.playerBname.equals(otherMatch.getPlayerAname()) && this.playerBname.equals(otherMatch.getPlayerBname()));        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.playerAname);
        hash = 47 * hash + Objects.hashCode(this.playerBname);
        return hash;
    }
    
    
    
    

}
