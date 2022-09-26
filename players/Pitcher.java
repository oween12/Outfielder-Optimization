package players;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Pitcher class that has the handedness and category of a pitcher
 * Pitchers are in categories based off of who they are like
 * 0 - basic pitcher with no grouping
 */
public class Pitcher extends Player {
    private final char handedness;
    private final int category;

    /**
     * Constructor for a pitcher.
     * Uses the constructor for a player then sets the handedness and category.
     *
     * @param name the full name of the pitcher
     * @param team the team of the pitcher
     */
    public Pitcher(String name, String team) throws IOException {
        super(name, team);
        handedness = findHandedness();
        category = findCategory();
    }

    /**
     * Finds the handedness of a pitcher off of Savant.
     *
     * @return the handedness of a pitcher 'R' or 'L'
     */
    public char findHandedness() throws IOException {
        Document doc = Jsoup.connect(getURL()).get();
        Element bio = doc.select("div.bio").first();
        String bioString = bio.toString();
        int index = bioString.indexOf("B/T: ");
        return bioString.charAt(index + 7);
    }

    /**
     * Finds the category of a pitcher based off who they are like.
     *
     * @return the category of a pitcher
     */
    public int findCategory() {
      return 0;
    }

    /**
     * @return the handedness of a pitcher
     */
    public char getHandedness() {
        return handedness;
    }

    /**
     * @return the category of a pitcher
     */
    public int getCategory() {
        return category;
    }
}
