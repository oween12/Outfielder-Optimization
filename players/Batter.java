package players;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Batter class that extends the Fielder class.
 * Has an ArrayList of AtBats.
 * Has a dataFile name that is a csv file with the following conditions.
 *
 * Search Savant parameters:
 * Season Type : Regular Season
 * Batted Ball Location : Left Field, Center Field, and Right Field
 * Season : 2019, 2020, 2021
 * Player Type : Batter
 * Batters : Batter Name
 * Batted Ball Type : Fly Ball, Pop Up, Line Drive
 *
 * Formatted CSV:
 * Find and replace all "" -> "NA"
 * Find and replace all "," -> ""
 */
public class Batter extends Fielder {
    private ArrayList<AtBat> atBats;
    private String dataFile;

    /**
     * Constructor that uses the constructor for a Fielder.
     * Sets the dataFile and creates the atBats ArrayList.
     *
     * @param name the name of the batter
     * @param team the team of the batter
     * @param dataFile the name of the CSV data file for the batter
     * @throws IOException if there is a problem with the data file
     */
    public Batter(String name, String team, String dataFile) throws IOException {
        super(name, team, "B");
        this.dataFile = dataFile;
        atBats = new ArrayList<AtBat>();
        createAtBats();
    }

    /**
     * Opens the file with a BufferedReader.
     * Parses the CSV by each row which is the data for an at bat.
     * Parses each row by the commas and reads it into an array.
     * Certain indices of each array correspond to certain data values.
     * If any of the data values are missing the at bat is not added.
     *
     * @throws IOException
     */
    private void createAtBats() throws IOException {
        String path = "/Users/owen/Desktop/savant_data/" + dataFile;
        BufferedReader csvReader = new BufferedReader(new FileReader(path));

        String atBat = csvReader.readLine();
        while ((atBat = csvReader.readLine()) != null) {
            String[] data = atBat.split(",");
            String date = data[1];
            String pitcher = data[7];
            String type = data[23];
            String posX = data[37];
            String posY = data[38];
            String launchVelo = data[53];
            String launchAngle = data[54];

            if (date.equals("NA") || pitcher.equals("NA") || type.equals("NA") || posX.equals("NA")
            || posY.equals("NA") || launchVelo.equals("NA") || launchAngle.equals("NA")) {
            } else {
                Double distance = Math.sqrt(Math.pow(Double.parseDouble(posX), 2) + Math.pow(Double.parseDouble(posY), 2));
                if (distance * 2.26 >= 125) {
                    AtBat curAtBat = new AtBat(date, pitcher, type, posX, posY, launchVelo, launchAngle);
                    atBats.add(curAtBat);
                }
            }
        }
    }

    /**
     * @return the ArrayList of atBats for a player.
     */
    public ArrayList<AtBat> getAtBats() {
        return this.atBats;
    }
}