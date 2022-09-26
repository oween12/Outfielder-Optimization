package playersTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import players.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class positionTest {
    private Position position;
    private final Batter batter = new Batter("pete alonso", "mets", "alonsoR.csv");
    private final Fielder leftFielder = new Fielder("LF");
    private final Fielder centerFielder = new Fielder("CF");
    private final Fielder rightFielder = new Fielder("RF");
    private final Pitcher pitcher = new Pitcher("jacob degrom", "mets");
    private Double SCORE;


    positionTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
        Double LFX = 65.0;
        Double LFY = 245.0;
        Double CFX = 191.0;
        Double CFY = 216.0;
        Double RFX = 270.0;
        Double RFY = 105.0;
        position = new Position(LFX, LFY, CFX, CFY, RFX, RFY, batter, leftFielder, centerFielder, rightFielder, pitcher, 0.5, 1.5);
    }

    @Test
    void computeScoreWorks() {

    }

    @Test
    void determineFielderWorks() {
        AtBat bat = new AtBat("6/12/19", "jacob degrom", "line_drive", "50", "100", "10", "10");
        assertEquals(position.determineFielder(bat), leftFielder);
        AtBat bat2 = new AtBat("6/12/19", "jacob degrom", "line_drive", "120", "65", "10", "10");
        assertEquals(position.determineFielder(bat2), centerFielder);
        AtBat bat3 = new AtBat("6/12/19", "jacob degrom", "line_drive", "175", "90", "10", "10");
        assertEquals(position.determineFielder(bat3), rightFielder);
    }

    @Test
    void distanceFormulaWorks() {
        assertEquals(position.distanceFormula(0, 0, 0, 4), 4);
        assertEquals(position.distanceFormula(0, 0, 4, 4), Math.sqrt(32));
        assertEquals(position.distanceFormula(0, 0, 10, 10), Math.sqrt(200));
    }

    @Test
    void determineOutcomeWorks() {

    }

    @Test
    void scoreToAddWorks() {
        AtBat bat = new AtBat("6/12/19", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.scoreToAdd("single", bat), 0.35 * 0.87);
        assertEquals(position.scoreToAdd("double", bat), 0.35 * 1.27);
        assertEquals(position.scoreToAdd("triple", bat), 0.35 * 1.62);
    }

    @Test
    void determineWeightingWorks() {
        AtBat bat = new AtBat("6/12/19", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.determineWeighting(bat), 0.35);
        AtBat bat2 = new AtBat("6/12/20", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.determineWeighting(bat2), 0.7);
        AtBat bat3 = new AtBat("6/12/21", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.determineWeighting(bat3), 1.0);
    }

    @Test
    void determineDateWeightingWorks() {
        AtBat bat = new AtBat("6/12/19", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.determineDateWeighting(bat), 0.35);
        AtBat bat2 = new AtBat("6/12/20", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.determineDateWeighting(bat2), 0.7);
        AtBat bat3 = new AtBat("6/12/21", "jacob degrom", "line_drive", "90", "90", "10", "10");
        assertEquals(position.determineDateWeighting(bat3), 1.0);
    }

    @Test
    void determineReactionTimeWorks() {

    }

    @Test
    void tallyOutcomes() {
        ArrayList<AtBat> atBats = batter.getAtBats();
        System.out.println("Number of at bats: " + atBats.size());
        System.out.println("Number of outs: " + position.getOuts());
        System.out.println("Number of singles: " + position.getSingles());
        System.out.println("Number of doubles: " + position.getDoubles());
        System.out.println("Number of triples: " + position.getTriples());
        System.out.println("Number of homeruns: " + position.getHomeruns());
        System.out.println("Score: " + this.SCORE);
    }
}