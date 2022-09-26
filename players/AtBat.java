package players;
import java.lang.Math;

/**
 * AtBat class that holds all the information for a batter's at bat.
 * Has the date, pitcher, type, xPosition, yPosition, launchVelo,
 * launchAngle, and hangTime.
 */
public class AtBat {
    private String date;
    private String pitcher;
    private String type;
    private Double xPosition;
    private Double yPosition;
    private Double launchVelo;
    private Double launchAngle;
    private Double hangTime;

    /**
     * Constructor that sets the
     * @param date an at bat occurred
     * @param pitcher that was pitching
     * @param type fly_ball, line_drive, or popup
     * @param xPosition the x-coordinate of the landing position in feet
     * @param yPosition the y-coordinate of the landing position in feet
     * @param launchVelo the launch velocity in miles per hour
     * @param launchAngle the launch angle in degrees
     *
     * Savant sets the landing coordinates in an odd way.
     * Home plate's coordinates are at (125, 205).
     * Spray chart faces negative y.
     */
    public AtBat(String date, String pitcher, String type, String xPosition, String yPosition, String launchVelo, String launchAngle) {
        this.date = date;
        this.pitcher = pitcher;
        this.type = type;
        this.launchVelo = Double.parseDouble(launchVelo);
        this.launchAngle = Double.parseDouble(launchAngle);
        hangTime = calculateHangTime();
        transformPosition(Double.parseDouble(xPosition), Double.parseDouble(yPosition));
        //addNoise();
    }

    /**
     * Helper method that transforms the landing coords from Savant's weird system.
     * y-axis is 3rd base line, x-axis is 1st base line, (0, 0) is home plate.
     *
     * First shifts the data over to (0, 0).
     *
     * Then performs a linear rotation by 45 degrees clockwise.
     *
     * Then multiplies by 2.55 to turn the savant units to feet.
     *
     * @param xPosition in savant units
     * @param yPosition in savant units
     */
    private void transformPosition(Double xPosition, Double yPosition) {
        Double xShift = xPosition - 125;
        Double yShift = -(yPosition - 205);

        Double xTrans = (xShift * Math.cos(-Math.PI/4)) - (yShift * Math.sin(-Math.PI/4));
        Double yTrans = (xShift * Math.sin(-Math.PI/4)) + (yShift * Math.cos(-Math.PI/4));

        Double xCordFeet = xTrans * 2.26;
        Double yCordFeet = yTrans * 2.26;

        this.xPosition = xCordFeet;
        this.yPosition = yCordFeet;
    }


    private Double calculateHangTime() {
        if (type.equals("line_drive") || launchAngle <= 45) {
            return hangTime1();
        } else {
            return hangTime2();
        }
    }

    private Double hangTime1() {
        Double veloMS = 0.44 * launchVelo;
        Double theta = (launchAngle * 3.14) / 180;
        Double baseballMass = 0.149;
        Double forceOfGravity = 9.81;
        Double dragForce = (0.5) * (0.346) * (1.27) * (0.0044) * Math.pow(veloMS,2);
        Double accelerationForce = ((baseballMass * forceOfGravity) - dragForce) / (baseballMass);

        Double newAcceleration = Math.abs(accelerationForce);
        Double g = forceOfGravity - newAcceleration;

        Double time = (2 * veloMS * Math.sin(theta)) / g;

        return time;
    }

    private Double hangTime2() {
        Double veloMS = 0.44 * launchVelo;
        Double theta = (launchAngle * 3.14) / 180;
        Double baseballMass = 0.149;
        Double forceOfGravity = 9.81;
        Double dragForce = (0.5) * (0.346) * (1.27) * (0.0044) * Math.pow(veloMS,2);
        Double accelerationForce = ((baseballMass * forceOfGravity) - dragForce) / (baseballMass);

        Double newAcceleration = Math.abs(accelerationForce);
        Double g = forceOfGravity - newAcceleration;

        Double maxHeight = (Math.pow(veloMS, 2)) * Math.pow(Math.sin(theta), 2) / (2 * g);
        Double startHeight = 2.5;

        Double a = 0.5 * g;
        Double b = -veloMS;
        Double c = maxHeight - startHeight;
        Double d = Math.pow(b, 2) - (5 * a * c);

        if (d < 0) {
            return hangTime1();
        } else if (d == 0) {
            return (-b + Math.sqrt((Math.pow(b, 2) - (4 * a * c)))) / (2 * a);
        } else {
            return (-b + Math.sqrt((Math.pow(b, 2) - (4 * (a * c))))) / (2 * a);
        }
    }

    public void addNoise() {
        int min = -20;
        int max = 20;
        this.xPosition = this.xPosition + (int)Math.floor(Math.random() * (max - min + 1) + min);
        this.yPosition = this.yPosition + (int)Math.floor(Math.random() * (max - min + 1) + min);
    }

    public Double getHangTime() {
        return hangTime;
    }

    public Double getLaunchAngle() {
        return launchAngle;
    }

    public Double getLaunchVelo() {
        return launchVelo;
    }

    public Double getXPosition() { return xPosition;
    }

    public Double getYPosition() {
        return yPosition;
    }

    public String getDate() {
        return date;
    }

    public String getPitcher() {
        return pitcher;
    }

    public String getType() {
        return type;
    }
}