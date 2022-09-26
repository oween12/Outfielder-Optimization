package players;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Player class to be extended by the specific types.
 * Has a full name, first name, last name, and team.
 * Has a URl to their baseball savant page.
 * Has getters for the parameters.
 */
public class Player {
    private final String fullName;
    private final String teamName;
    private final String URL;
    private final String teamURL;
    private final String leagueTeamsURL = "https://baseballsavant.mlb.com/league";

    /**
    * Constructor for a player.
    * Stores all parameters in lower case.
    *
    * @param name the name of a player formatted as "john doe"
    * @param team the team of a player
    */
    public Player(String name, String team) throws IOException {
      fullName = name.toLowerCase();
      this.teamName = team.toLowerCase();
      teamURL = findTeamURL();
      URL = findURL();
    }

    /**
     * Finds the URL of the player from Savant.
     *
     * @return the URL of the player.
     * @throws IOException for an invalid URL
     */
    private String findURL() throws IOException {
      Document doc = Jsoup.connect(teamURL).get();
      Elements links = doc.select("a[href]");
      for (Element link : links) {
        if (link.text().toLowerCase().equals(fullName)) {
          return link.attr("abs:href");
        }
      }
      return null;
    }

    /**
    * Finds the URL of the player's team from Savant.
    *
    * @return the URL of the player's team.
    * @throws IOException for an invalid URL
    */
    private String findTeamURL() throws IOException {
      Document doc = Jsoup.connect(leagueTeamsURL).get();
      Elements links = doc.select("a[href]");
      for (Element link : links) {
        if (link.text().toLowerCase().equals(teamName)) {
          return link.attr("abs:href");
        }
      }
      System.out.println("Cannot find team URL");
      return null;
    }

    /**
    * @return the full name of the player "john doe"
     */
   public String getFullName() {
    return fullName;
    }

    /**
     * @return the first name of the player capitalized "John"
     */
    public String getFirstName() {
      return capitalizeString(fullName.substring(0, fullName.indexOf(' ')));
    }

   /**
     * @return the last name of the player capitalized "Doe"
     */
   public String getLastName() {
        return capitalizeString(fullName.substring(fullName.indexOf(' ') + 1));
    }

   /**
    * @param word the word to be capitalized
    * @return a string with the first letter capitalized "doe" -> "Doe"
    */
   public String capitalizeString(String word) {
      return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

   /**
    * @return the teamName of a player
    */
   public String getTeam() {
        return this.teamName;
    }

   /**
    * @return the team's URL from Savant
    */
   public String getTeamURL() {
      return this.teamURL;
    }

   /**
    * @return the player's URL from Savant
    */
   public String getURL() {
      return this.URL;
    }
}