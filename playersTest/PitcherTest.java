package playersTest;

import players.Pitcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PitcherTest {

    Pitcher pitcher;
    private String playerName = "jacob degrom";
    private String team = "mets";
    private char handedness = 'R';
    private int category = 0;

    @BeforeEach
    void setUp() throws IOException {
        this.pitcher = new Pitcher(playerName, team);
    }

    @Test
    void findCategory() {
        assertEquals(pitcher.getCategory(), category);
    }

    @Test
    void getHandedness() {
        assertEquals(pitcher.getHandedness(), handedness);
    }
}