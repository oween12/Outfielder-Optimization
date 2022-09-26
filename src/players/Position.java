package players;

import java.io.IOException;
import java.util.ArrayList;

public class Position {
    private final Batter batter;
    private final Fielder leftFielder;
    private final Fielder centerFielder;
    private final Fielder rightFielder;
    private final Pitcher pitcher;

    private double score;

    private double LFX;
    private double LFY;
    private double CFX;
    private double CFY;
    private double RFX;
    private double RFY;

    private double bigReactionTime;
    private double smallReactionTime;

    private int outs;
    private int singles;
    private int doubles;
    private int triples;
    private int homeruns;

    public Position(Double lfx, Double lfy, Double cfx, Double cfy, Double rfx, Double rfy, Batter B, Fielder LF, Fielder CF, Fielder RF, Pitcher P, Double bigReactionTime, Double smallReactionTime) throws IOException {
        batter = B;
        leftFielder = LF;
        centerFielder = CF;
        rightFielder = RF;
        pitcher = P;
        LFX = lfx;
        LFY = lfy;
        CFX = cfx;
        CFY = cfy;
        RFX = rfx;
        RFY = rfy;
        this.bigReactionTime = bigReactionTime;
        this.smallReactionTime = smallReactionTime;
        score = computeScore();
    }

    /**
     * Takes a set of coordinates for outfielders and computes the score
     *
     * Iterates through each at bat for the batter and adds to the score
     * according to the expected outcome
     */
    public Double computeScore() {
        ArrayList<AtBat> atBats = batter.getAtBats();
        Double score = 0.0;

        for (int i = 0; i < atBats.size(); i++) {
            AtBat curAtBat = atBats.get(i);
            Fielder fielder = determineFielder(curAtBat);
            String outcome = "out";

            if (fielder.getPosition() == "LF") {
                outcome = determineOutcome(fielder, curAtBat, LFX, LFY);
            } else if (fielder.getPosition() == "CF") {
                outcome = determineOutcome(fielder, curAtBat, CFX, CFY);
            } else {
                outcome = determineOutcome(fielder, curAtBat, RFX, RFY);
            }

            score += scoreToAdd(outcome, curAtBat);
        }
        return score;
    }

    /**
     * Determines which fielder should field the ball for each at bat
     * Depends on the positioning of the fielders and their individual speeds
     * Computes the time it would take each fielder to get to the ball
     *
     * @return the optimal fielder
     */
    public Fielder determineFielder(AtBat curAtBat) {
        Double xPos = curAtBat.getXPosition();
        Double yPos = curAtBat.getYPosition();

        Double LFDistance = distanceFormula(LFX, LFY, xPos, yPos);
        Double CFDistance = distanceFormula(CFX, CFY, xPos, yPos);
        Double RFDistance = distanceFormula(RFX, RFY, xPos, yPos);

        Double LFTime = leftFielder.timeAfterDistance(LFDistance);
        Double CFTime = centerFielder.timeAfterDistance(CFDistance);
        Double RFTime = rightFielder.timeAfterDistance(RFDistance);

        if (CFTime <= LFTime && CFTime <= RFTime) {
            return centerFielder;
        } else if (LFTime < RFTime) {
            return leftFielder;
        } else {
            return rightFielder;
        }
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
     * Determines the outcome of an at bat based on which fielder is fielding it,
     * Where the ball is, and where the fielder is
     *
     * @param fielder
     * @param curAtBat
     * @return out, single, double, triple, or homerun
     */
    public String determineOutcome(Fielder fielder, AtBat curAtBat, Double FX, Double FY) {
        Double distance = distanceFormula(FX, FY, curAtBat.getXPosition(), curAtBat.getYPosition());
        Double reactionTime = determineReactionTime(FX, FY, curAtBat);
        Double hangTime = curAtBat.getHangTime();
        Double timeToGetThere = fielder.timeAfterDistance(distance) + reactionTime;
        Double throwSecond = fielder.throwToSecond(curAtBat.getXPosition(), curAtBat.getYPosition());
        Double throwThird = fielder.throwToThird(curAtBat.getXPosition(), curAtBat.getYPosition());
        Double throwHome = fielder.throwToHome(curAtBat.getXPosition(), curAtBat.getYPosition());

        if (timeToGetThere <= hangTime) {
            outs++;
            return "out";
        } else if (timeToGetThere + throwSecond < 8.0) {
            singles++;
            return "single";
        } else if (timeToGetThere + throwThird < 12.0) {
            doubles++;
            return "double";
        } else if (timeToGetThere + throwHome < 16.0) {
            triples++;
            return "triple";
        } else {
            homeruns++;
            return "homerun";
        }
    }

    /**
     * Determines how much score to add to the positioning
     * Based on wOBA linear weights
     * <p>
     * Depending on the date, it will be weighted differently
     * Depending on the pitcher, it will be weighted differently
     *
     * @param outcome
     * @param curAtBat
     */
    public double scoreToAdd(String outcome, AtBat curAtBat) {
        double weighting = determineWeighting(curAtBat);

        if (outcome.equals("single")) {
            return weighting * 0.87;
        } else if (outcome.equals("double")) {
            return weighting * 1.27;
        } else if (outcome.equals("triple")) {
            return weighting * 1.62;
        } else if (outcome.equals("homerun")) {
            return weighting * 2.1;
        } else {
            return 0;
        }
    }

    public double determineWeighting(AtBat curAtBat) {
        return determineDateWeighting(curAtBat);
    }

    public double determineDateWeighting(AtBat curAtBat) {
        String date = curAtBat.getDate();
        String month = date.substring(0, date.indexOf("/"));
        String day = date.substring(date.indexOf("/") + 1);
        day = day.substring(0, day.indexOf("/"));
        String year = date.substring(date.indexOf("/") + 1);
        year = year.substring(year.indexOf("/") + 1);

        if (year.equals("19")) {
            return 0.35;
        } else if (year.equals("20")) {
            return 0.7;
        } else {
            return 1.0;
        }
    }

    public double determineReactionTime(Double FX, Double FY, AtBat curAtBat) {
        Double fielderAngle = Math.atan(FY/FX);
        Double ballAngle = Math.tan(curAtBat.getYPosition()/curAtBat.getXPosition());
        Double difference = Math.abs(fielderAngle - ballAngle);

        if (difference <= 0.0872665) {
            return bigReactionTime;
        } else {
            return smallReactionTime;
        }
    }

    public String toString() {
        return "Left Fielder (" + LFX + ", " + LFY + ") \n"
                + "Center Fielder (" + CFX + ", " + CFY + ") \n"
                + "Right Fielder (" + RFX + ", " + RFY + ") \n"
                + "Score: " + score;
    }

    /**
     * @return the number of outs
     */
    public int getOuts() { return outs; }

    /**
     * @return the number of singles
     */
    public int getSingles() { return singles; }

    /**
     * @return the number of doubles
     */
    public int getDoubles() { return doubles; }

    /**
     * @return the number of triples
     */
    public int getTriples() { return triples; }

    /**
     * @return the number of homeruns
     */
    public int getHomeruns() { return homeruns; }

    /**
     * @return the score
     */
    public double getScore() { return score; }
}
