package players;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Fielder class that extends the Player class.
 * Has a position LF, CF, RF, or B for batter.
 * Has an armStrength in miles per hour.
 * Has speedSplits (i = 0 ~ 0 meters, i = 1 ~ 5 feet, ..., i = 18 ~ 90 feet).
 * Has sprintData extracted from Savant for the speed splits.
 * Has the runningSplitsLeaderboardURL from Savant.
 *
 * Has a function to determine how long it takes a player to go a distance.
 * Has a function to determine how far a player will go in a certain time.
 */
public class Fielder extends Player {
    private final String position;
    private final Double armStrength;
    private final double[] speedSplits;
    private final String sprintData;
    private final static String runningSplitsURL = "https://baseballsavant.mlb.com/leaderboard/running_splits";

    /**
     * Default-ish constructor for a fielder
     *
     * @param position the position of the fielder (LF, CF, RF, or B)
     */
    public Fielder(String position) throws IOException {
        super("michael conforto", "mets");
        this.position = position;
        this.armStrength = 90.0 * 1.467;
        sprintData = findData();
        speedSplits = findSpeed();
    }

    /**
     * Constructor for a fielder
     *
     * @param name the name of the fielder
     * @param team the team of the fielder
     * @param position the position of the fielder (LF, CF, RF, or B)
     */
    public Fielder(String name, String team, String position) throws IOException {
      super(name, team);
      this.position = position;
      this.armStrength = 90.0 * 1.467;
      sprintData = findData();
      speedSplits = findSpeed();
    }

    /**
     * @return the data of a player from Savant speedSplitsLeaderboard
     * @throws IOException for an invalid URL
     */
    private String findData() throws IOException {
      Document doc = Jsoup.connect(runningSplitsURL).get();
      Elements scriptElements = doc.getElementsByTag("script");
      String data = "not found";
      for (Element element : scriptElements) {
          if (element.data().contains("var data")) {
              data = element.toString();
          }
      }
      int index = data.indexOf(getLastName() + ", " + getFirstName()) - 11;
      return data.substring(index, data.indexOf("}", index) + 1);
    }

    /**
     * Finds the speedSplits of a player and store them into an array
     *
     * @return speedSplits
     */
    private double[] findSpeed() throws IOException {
      double[] speedSplits = new double[19];
      String time = "";

      time = sprintData.substring(sprintData.indexOf("_000") + 7, sprintData.indexOf("_000") + 11);
      speedSplits[0] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_005") + 7, sprintData.indexOf("_005") + 11);
      speedSplits[1] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_010") + 7, sprintData.indexOf("_010") + 11);
      speedSplits[2] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_015") + 7, sprintData.indexOf("_015") + 11);
      speedSplits[3] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_020") + 7, sprintData.indexOf("_020") + 11);
      speedSplits[4] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_025") + 7, sprintData.indexOf("_025") + 11);
      speedSplits[5] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_030") + 7, sprintData.indexOf("_030") + 11);
      speedSplits[6] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_035") + 7, sprintData.indexOf("_035") + 11);
      speedSplits[7] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_040") + 7, sprintData.indexOf("_040") + 11);
      speedSplits[8] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_045") + 7, sprintData.indexOf("_045") + 11);
      speedSplits[9] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_050") + 7, sprintData.indexOf("_050") + 11);
      speedSplits[10] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_055") + 7, sprintData.indexOf("_055") + 11);
      speedSplits[11] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_060") + 7, sprintData.indexOf("_060") + 11);
      speedSplits[12] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_065") + 7, sprintData.indexOf("_065") + 11);
      speedSplits[13] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_070") + 7, sprintData.indexOf("_070") + 11);
      speedSplits[14] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_075") + 7, sprintData.indexOf("_075") + 11);
      speedSplits[15] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_080") + 7, sprintData.indexOf("_080") + 11);
      speedSplits[16] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_085") + 7, sprintData.indexOf("_085") + 11);
      speedSplits[17] = Double.parseDouble(time);
      time = sprintData.substring(sprintData.indexOf("_090") + 7, sprintData.indexOf("_090") + 11);
      speedSplits[18] = Double.parseDouble(time);

      return speedSplits;
    }

    /**
     * @param t the time in seconds
     * @return the distance a fielder would run in feet
     */
    public double distanceAfterTime(double t) {
        if (t > speedSplits[18]) {
            double slope = (5.0) / (speedSplits[18] - speedSplits[17]);
            return 90 + ((t - speedSplits[18]) * slope);
        } else {
            int counter = 0;
            for (int i = 0; i < 19; i++) {
                if ((t - speedSplits[i] < t - speedSplits[counter]) && t - speedSplits[i] >= 0){
                    counter = i;
                }
            }
            double slope = (5.0) / (speedSplits[1] - speedSplits[0]);
            if (counter != 0) {
                slope = (5.0) / (speedSplits[counter] - speedSplits[counter - 1]);
            }
            return (counter * 5.0) + ((t - speedSplits[counter]) * slope);
        }
    }

    /**
     * @param ft a distance in feet
     * @return an approximate time it would take the player to run the distance in seconds
     */
    public double timeAfterDistance(double ft) {
        if (ft > 90) {
            double slope = (speedSplits[18] - speedSplits[17]) / (5.0);
            return speedSplits[18] + ((ft - 90.0) * slope);
        } else {
            int counter = 0;
            for (int i = 0; i < 19; i++) {
                if ((ft - (5.0 * i)) < ft - (5.0 * counter) && ft - (5.0 * i) >= 0) {
                    counter = i;
                }
            }

            if (counter == 0) {
                double slope = (speedSplits[1] - speedSplits[0]) / (5.0);
                return ft * slope;
            }
            double slope = (speedSplits[counter] - speedSplits[counter - 1]) / (5.0);
            return speedSplits[counter - 1] + ((ft - (5.0 * (counter - 1))) * slope);
        }
    }

    /**
     * @param xPos the x coordinate of where the fielder throws the ball
     * @param yPos the y coordinate of where the fielder throws the ball
     * @return the time it takes for a player to throw from the landing position of the ball to second base
     */
    public Double throwToSecond(Double xPos, Double yPos) {
        Double distance = distanceFormula(xPos, yPos, 90, 90);
        return distance / armStrength;
    }

    /**
     * @param xPos the x coordinate of where the fielder throws the ball
     * @param yPos the y coordinate of where the fielder throws the ball
     * @return the time it takes for a player to throw from the landing position of the ball to third base
     */
    public Double throwToThird(Double xPos, Double yPos) {
        Double distance = distanceFormula(xPos, yPos, 0, 90);
        return distance / armStrength;
    }

    /**
     * @param xPos the x coordinate of where the fielder throws the ball
     * @param yPos the y coordinate of where the fielder throws the ball
     * @return the time it takes for a player to throw from the landing position of the ball to home plate
     */
    public Double throwToHome(Double xPos, Double yPos) {
        Double distance = distanceFormula(xPos, yPos, 0, 0);
        return distance / armStrength;
    }

    /**
     * Distance formula that computes the distance between two points
     * (x1, y1) and (x2, y2)
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public Double distanceFormula(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    /**
     * @return the speedSplits of a fielder
     */
    public double[] getSpeed() {
        return speedSplits;
    }

    /**
     * @return the position of a fielder
     */
    public String getPosition() {
      return position;
    }

    /**
     * @return the arm strength of a fielder in miles per hour
     */
    public Double getArmStrength() { return armStrength; }
}