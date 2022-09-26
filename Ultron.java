import java.io.IOException;
import java.lang.*;
import java.util.PriorityQueue;
import java.util.Scanner;
import players.*;

/**
 * This code takes in a few parameters from a user
 * and produces the optimal position of outfielders.
 * The home team is the defending team with corresponding
 * outfielders and a pitcher.
 * The away team is the offensive team that is up to bat.
 */
public class Ultron {
  private static String defensiveTeamName;
  private static String offensiveTeamName;
  private static Pitcher pitcher;
  private static Fielder leftFielder;
  private static Fielder centerFielder;
  private static Fielder rightFielder;
  private static Batter batter;

  //All of these values are in feet
  private static Double shift = 15.0;
  private static Double step = 5.0;
  private static Double lfX = 90.0;
  private static Double lfY = 260.0;
  private static Double cfX = 200.0;
  private static Double cfY = 200.0;
  private static Double rfX = 260.0;
  private static Double rfY = 90.0;

  private static Double bigReactionTime = 1.5;
  private static Double smallReactionTime = 0.5;

  private static final double mathCounter = Math.pow(((2 * shift) / step) + 1, 6);
  private static int totalBats = 0;
  private static double approxRuntime = 0.03 + (0.000001 * mathCounter) + (0.004 * totalBats);

  private static Position best;

  public static void main(String[] args) throws IOException {
    System.out.println("# of Positions Tested: " + mathCounter);
    setData();
    System.out.println("# of at bats: " + totalBats);
    findBest();
    System.out.println(best);
  }

  /**
   * Sets the data manually
   *
   * @throws IOException for invalid input
   */
  private static void setData() throws IOException {
    defensiveTeamName = "mets";
    offensiveTeamName = "mets";
    pitcher = new Pitcher("jacob degrom", defensiveTeamName);
    leftFielder = new Fielder("LF");
    centerFielder = new Fielder("CF");
    rightFielder = new Fielder("RF");
    batter = new Batter("pete alonso", offensiveTeamName, "alonsoR.csv");
    totalBats = batter.getAtBats().size();
  }

  /**
   * Creates a search square around each fielder
   * The square is centered around base coordinates (LeftBaseX, LeftBaseY)
   * The square then expanded by looking to the left and up a set amount (boxShift)
   * <p>
   * Sends each coordinate possibility to computeScore
   */
  private static void findBest() throws IOException {
    for (double i = lfX - shift; i <= lfX + shift; i+=step) {
      for (double j = lfY - shift; j <= lfY + shift; j+=step) {
        for (double k = cfX - shift; k <= cfX + shift; k+=step) {
          for (double l = cfY - shift; l <= cfY + shift; l+=step) {
            for (double m = rfX - shift; m <= rfX + shift; m+=step) {
              for (double n = rfY - shift; n <= rfY + shift; n+=step) {
                Position position = new Position(i, j, k, l, m, n, batter, leftFielder, centerFielder, rightFielder, pitcher, bigReactionTime, smallReactionTime);
                if (best == null || best.getScore() > position.getScore()) {
                  best = position;
                }
              }
            }
          }
        }
      }
    }
  }


  /**
   * Collects and sets data from the user
   *
   * @param args user input
   * @throws IOException for invalid input

  private static void collectUserData(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter the name of the defensive team: ");
    defensiveTeamName = sc.nextLine().toLowerCase();
    System.out.print("Enter the name of the offensive team: ");
    offensiveTeamName = sc.nextLine().toLowerCase();
    System.out.print("Enter the first and last name of the pitcher (Ex: 'john doe'): ");
    pitcher = new Pitcher(sc.nextLine().toLowerCase(), defensiveTeamName);
    System.out.print("Enter the first and last name of the left fielder (Ex: 'john doe'): ");
    leftFielder = new Fielder(sc.nextLine().toLowerCase(), defensiveTeamName, "LF");
    System.out.print("Enter the first and last name of the center fielder (Ex: 'john doe'): ");
    centerFielder = new Fielder(sc.nextLine().toLowerCase(), defensiveTeamName, "CF");
    System.out.print("Enter the first and last name of the right fielder (Ex: 'john doe'): ");
    rightFielder = new Fielder(sc.nextLine().toLowerCase(), defensiveTeamName, "RF");
    System.out.print("Enter the name of the batter (Ex: 'john doe'): ");
    String batterName = sc.nextLine().toLowerCase();
    System.out.print("Enter the name of the data file for a batter: ");
    batter = new Batter(batterName, offensiveTeamName, sc.nextLine());
    totalBats = batter.getAtBats().size();
  }
  */
}