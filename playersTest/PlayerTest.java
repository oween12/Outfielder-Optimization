package playersTest;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import players.Player;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private String playerName = "pete alonso";
    private String team = "mets";
    private String playerFirstName = "Pete";
    private String playerLastName = "Alonso";
    private String playerURL = "https://baseballsavant.mlb.com/savant-player/624413";
    private String teamURL = "https://baseballsavant.mlb.com/team/new-york-mets-121";

    @BeforeEach
    void setUp() throws IOException {
        this.player = new Player(playerName, team);
    }

    @Test
    void findTeamURL() {
        try {
            this.player = new Player(playerName, "yea");
            fail("findTeamURL() does not throw an IOException when called on an improper team");
        } catch (Exception ex) {
            return;
        }
    }

    @Test
    void getFullName() {
        assertEquals(player.getFullName(), playerName);
    }

    @Test
    void getFirstName() {
        assertEquals(player.getFirstName(), playerFirstName);
    }

    @Test
    void getLastName() {
        assertEquals(player.getLastName(),playerLastName);
    }

    @Test
    void capitalizeString() {
        assertEquals(player.capitalizeString("hey"), "Hey");
    }

    @Test
    void getTeam() {
        assertEquals(player.getTeam(), team);
    }

    @Test
    void getTeamURL() {
        assertEquals(player.getTeamURL(), teamURL);
    }

    @Test
    void getURL() {
        assertEquals(player.getURL(),playerURL);
    }
}