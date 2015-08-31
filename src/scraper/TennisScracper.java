package scraper;

import java.util.ArrayList;
import java.util.List;
import model.Match;
import model.Statistics;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author gabriel
 */
public class TennisScracper {

    private static final String USERNAME_ID = "login-username";
    private static final String PASSWORD_ID = "login-pass";
    WebDriver driver;
    WebDriverWait wait;

    public TennisScracper() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
    }

    public List<Match> fetchMatches(String tournamentName) {
        List<Match> matches = new ArrayList();
        driver.get(TennisInsight.FRONT_PAGE_URL);
        WebElement element = findElementById(USERNAME_ID);
        element.sendKeys(TennisInsight.USERNAME);
        element = findElementById(PASSWORD_ID);
        element.sendKeys(TennisInsight.PASSWORD);
        element = findElementByXPath(
                "//*[@id='header-benchmarking-login-container']/div[2]/form/div[3]/button");
        element.click();
        driver.get(TennisInsight.MATCH_PREVIEW_URL);       
        List<String> links = new ArrayList();List<WebElement> elements = findElementsByXPath("//section/div[@class='container']/div[div/a[contains(text(), '" + tournamentName + "')]]//td[@class='match-row-link']/a");
        elements.forEach(e -> links.add(e.getAttribute("href")));
        int i = 1;
        for (String link : links) {         
            Match match = new Match();
            driver.get(link);
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("matchStatsDraw")));
            Select select = new Select(findElementById("matchStatsDraw"));
            select.selectByValue("md");
            findElementById("matchStatsCompareSubmit").click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[27]/td/b")));
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col-md-4 match-stats-filter']")));
            extractPlayerNames(match);
            extractPlayerStats(match, false);
            select.selectByValue("all");
            select = new Select(findElementById("matchStatsDuration"));
            select.selectByValue("745");
            elements = driver.findElements(By.name("criteria"));
            elements.get(2).click();
            findElementById("matchStatsCompareSubmit").click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[27]/td/b")));
            extractPlayerStats(match, true);
            matches.add(match);
            i++;        
        }
        return matches;
    }

    public static void main(String[] args) {
        String input = "65% (14-8)";
        String matchesPlayed = input.substring(input.indexOf('(')+1, input.indexOf(')'));
        String[] nMatches = matchesPlayed.split("-");
        int numberOfMatchesPlayed = Integer.parseInt(nMatches[0]) + Integer.parseInt(nMatches[1]);
        System.out.println(numberOfMatchesPlayed);
//        TennisScracper scraper = new TennisScracper();
//        scraper.fetchMatches("US Open ATP");
    }

    private WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    private WebElement findElementByXPath(String path) {
        return driver.findElement(By.xpath(path));
    }

    private List<WebElement> findElementsByXPath(String path) {
        return driver.findElements(By.xpath(path));
    }

    private void extractPlayerNames(Match match) {        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/th[1]/b")));
        String name = extractName("//tr/th[1]/b");
        match.setPlayerAname(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/th[3]/b")));
        name = extractName("//tr/th[3]/b");
        match.setPlayerBname(name);
      
    }
   
    private String extractName(String path){
        try{
            return findElementByXPath(path).getText();
            
        } catch (StaleElementReferenceException e){
            return extractName(path);
        }
    }

    private void extractPlayerStats(Match match, boolean commonOpponent) {   
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[27]/td/b")));
        List<WebElement> elements;
        String stat = findElementByXPath("//table[@class='table table-condensed match-stats-table']/tbody/tr[15]/td[1]/b").getText();
        double servicePointWonA = parseDoubleFromPercentage(stat);
        stat = findElementByXPath("//table[@class='table table-condensed match-stats-table']/tbody/tr[27]/td[1]/b").getText();
        double returnWonA = parseDoubleFromPercentage(stat);
        stat = findElementByXPath("//table[@class='table table-condensed match-stats-table']/tbody/tr[15]/td[3]/b").getText();
        double servicePointWonB = parseDoubleFromPercentage(stat);
        stat = findElementByXPath("//table[@class='table table-condensed match-stats-table']/tbody/tr[27]/td[3]/b").getText();
        double returnWonB = parseDoubleFromPercentage(stat);
        elements = findElementsByXPath("//table[@class='table table-condensed match-stats-table']/tbody/tr/td");
        if (commonOpponent) {
            match.setPlayerAstat(Statistics.SERVICE_POINT_WON, servicePointWonA);
            match.setPlayerAstat(Statistics.RETURN_POINT_WON, returnWonA);
            match.setPlayerBstat(Statistics.RETURN_POINT_WON, returnWonB);
            match.setPlayerBstat(Statistics.SERVICE_POINT_WON, servicePointWonB);            
            match.setCommonOpponentMatchesPlayedA(extractNumberOfMatchesPlayed(elements.get(0).getText()));
            match.setCommonOpponentMatchesPlayedB(extractNumberOfMatchesPlayed(elements.get(2).getText()));
        }
        else{
            match.setPlayerAstat(Statistics.SERVICE_POINT_WON_VS_COMMON_OPPONENT, servicePointWonA);
            match.setPlayerAstat(Statistics.RETURN_POINT_WON_VS_COMMON_OPPONENT, returnWonA);
            match.setPlayerBstat(Statistics.RETURN_POINT_WON_VS_COMMON_OPPONENT, returnWonB);
            match.setPlayerBstat(Statistics.SERVICE_POINT_WON_VS_COMMON_OPPONENT, servicePointWonB);
            match.setMatchesPlayedA(extractNumberOfMatchesPlayed(elements.get(0).getText()));
            match.setMatchesPlayedB(extractNumberOfMatchesPlayed(elements.get(2).getText()));
        }

    }

    private double parseDoubleFromPercentage(String percentage) {
        String doubleString = percentage.substring(0, percentage.length() - 1);
        return Double.parseDouble(doubleString) / 100;
    }

    private int extractNumberOfMatchesPlayed(String text) {
        String matchesPlayed = text.substring(text.indexOf('(')+1, text.indexOf(')'));
        String[] nMatches = matchesPlayed.split("-");
        return Integer.parseInt(nMatches[0]) + Integer.parseInt(nMatches[1]);        
    }

}
