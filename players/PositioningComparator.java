/**
package players;

import java.util.Comparator;

public class PositioningComparator implements Comparator<Position> {

    @Override
    public int compare(Position p1, Position p2) {
        Double difference = p1.computeScore() - p2.computeScore();
        if (difference > 0) {
            return 1;
        } else if (difference == 0) {
            return 0;
        } else {
            return -1;
        }
    }

}
**/