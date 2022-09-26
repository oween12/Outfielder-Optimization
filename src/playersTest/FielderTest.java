package playersTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import players.Fielder;

import static org.junit.jupiter.api.Assertions.*;

class FielderTest {

    private Fielder fielder;
    private String playerName = "dominic smith";
    private String team = "mets";
    private String position = "LF";
    private Double[] speed = new Double[]{0.00, 0.56, 0.89, 1.17, 1.42, 1.66, 1.88, 2.09, 2.30, 2.50, 2.69, 2.88, 3.07, 3.26, 3.44, 3.63, 3.82, 4.00, 4.22};
    //      positions in feet               0     5    10    15    20    25    30    35    40    45    50    55    60    65    70    75    80    85    90

    @BeforeEach
    void setUp() throws IOException {
        this.fielder = new Fielder(playerName, team, position);
    }

    @Test
    void distanceAfterTime() {
        assertEquals(fielder.distanceAfterTime(speed[18]), 90);
        assertEquals(fielder.distanceAfterTime(speed[17]), 85);
        assertEquals(fielder.distanceAfterTime(speed[16]), 80);
    }

    @Test
    void distanceAfterTime2() {
        System.out.println(fielder.distanceAfterTime(0));
        System.out.println(fielder.distanceAfterTime(0.1));
        System.out.println(fielder.distanceAfterTime(.5));
        System.out.println(fielder.distanceAfterTime(.75));
        System.out.println(fielder.distanceAfterTime(1));
        System.out.println(fielder.distanceAfterTime(1.2));
        System.out.println(fielder.distanceAfterTime(1.8));
        System.out.println(fielder.distanceAfterTime(3.0));
    }

    @Test
    void timeAfterDistance() {
        assertEquals(fielder.timeAfterDistance(90), speed[18]);
        assertEquals(fielder.timeAfterDistance(85), speed[17]);
        assertEquals(fielder.timeAfterDistance(80), speed[16]);
    }

    @Test
    void timeAfterDistance2() {
        System.out.println(fielder.timeAfterDistance(0));
        System.out.println(fielder.timeAfterDistance(3));
        System.out.println(fielder.timeAfterDistance(8));
        System.out.println(fielder.timeAfterDistance(30));
        System.out.println(fielder.timeAfterDistance(87));
        System.out.println(fielder.timeAfterDistance(105));
        System.out.println(fielder.timeAfterDistance(250));
    }

    @Test
    void getSpeed() {
        double[] speeds = fielder.getSpeed();
        for (int i = 0; i < 19; i++) {
            assertEquals(speeds[i], speed[i]);
        }
    }

    @Test
    void getPosition() {
        assertEquals(fielder.getPosition(), position);
    }

    @Test
    void throwToSecond() {
        assertEquals(fielder.throwToSecond(180.0, 180.0) , Math.sqrt(16200) / (90 * 1.467));
        assertEquals(fielder.throwToSecond(90.0, 100.0), 10 / (90 * 1.467));
        assertEquals(fielder.throwToSecond(100.0, 130.0), Math.sqrt(1700) / (90 * 1.467));
    }

    @Test
    void throwToThird() {
        assertEquals(fielder.throwToThird(180.0, 180.0) , Math.sqrt(40500)  / (90 * 1.467));
        assertEquals(fielder.throwToThird(90.0, 100.0), Math.sqrt(8200) / (90 * 1.467));
        assertEquals(fielder.throwToThird(100.0, 130.0), Math.sqrt(11600) / (90 * 1.467));
    }

    @Test
    void throwToHome() {
        assertEquals(fielder.throwToHome(180.0, 180.0) , Math.sqrt(64800) / (90 * 1.467));
        assertEquals(fielder.throwToHome(90.0, 100.0), Math.sqrt(18100) / (90 * 1.467));
        assertEquals(fielder.throwToHome(100.0, 130.0), Math.sqrt(26900) / (90 * 1.467));
    }

    @Test
    void throwTests() {
        assertTrue(fielder.throwToSecond(90.0, 90.0) < fielder.throwToThird(90.0, 90.0));
        assertTrue(fielder.throwToHome(100.0, 100.0) > fielder.throwToSecond(100.0, 100.0));
        assertTrue(fielder.throwToSecond(200.0, 90.0) < fielder.throwToThird(200.0, 90.0));
    }
}