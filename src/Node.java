import java.awt.*;
import java.text.DecimalFormat;

/**
 * Node is a data structure class that represents a pixel on the terrain image.
 *
 * It has:
 *      -location (x, y) which corresponds to the pixel on the terrain image (represented in {@linkplain Point}.
 *      -gScore, hScore, fScore for {@linkplain aStarSearch} calculation -> g(n), h(n), f(n), respectively.
 *      -parentNode which is the {@linkplain Node} from which we got to this {@linkplain Node}.
 *
 * @author <a href='mailto:mp8671@rit.edu'>Maksim Pikovskiy</a>
 */
public class Node implements Comparable<Node> {

    Point location;
    double gScore;
    double hScore;
    double fScore;
    Node parentNode;

    /**
     * Constructs the {@linkplain Node} with given values.
     *
     * @param location {@link Point} which represents the x and y coordinates of the {@link Node}.
     * @param gScore g(n) of the {@link Node}, which is the cost of the path from parentNode to this {@link Node}.
     * @param hScore h(n) of the {@link Node}, which is the cost of the path from this {@link Node} to goal {@link Node}.
     * @param parent the {@link Node} that precedes this {@link Node} (from which aStarSearch came from).
     */
    public Node(Point location, double gScore, double hScore, Node parent) {
        this.location = location;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = gScore + hScore;
        parentNode = parent;
    }

    /**
     * Compares two {@linkplain Node Nodes} to each other based on their fScores.
     *
     * Used for {@linkplain java.util.PriorityQueue} in {@linkplain aStarSearch}.
     *
     * @param that {@link Object}, specifically another {@link Node}, that will be compared to this {@link Node}.
     * @return returns whether this {@link Node node's} fScore is larger or smaller than {@link Node compared Node}.
     */
    public int compareTo(Node that) {
        if(this.fScore < that.fScore) return -1;
        else if(this.fScore > that.fScore) return 1;
        else {
            int diffX = this.location.x - that.location.x;
            int diffY = this.location.y - that.location.y;

            if (diffX == 0) return diffY;
            else return diffX;
        }
    }

    /**
     * Compares two {@linkplain Node Nodes} to each other based on their {@linkplain Point locations}.
     *
     * It used for contains() function of {@linkplain List} and {@linkplain java.util.Set} in {@linkplain aStarSearch}.
     *
     * @param that {@link Object}, specifically another {@link Node}, that will be compared to this {@link Node}.
     * @return true if two {@link Node Nodes} are identical,
     *         false otherwise.
     */
    public boolean equals(Object that) {
        if(this == that) return true;
        if(that == null) return false;
        if (this.getClass() != that.getClass()) return false;

        Node thatNode = (Node) that;
        return this.location.equals(thatNode.location);
    }

    /**
     * Makes and returns a hashCode for this {@linkplain Node}.
     *
     * @return the hashCode of this {@link Node}.
     */
    public int hashCode() {
        return location.x + location.y * 2;
    }

    /**
     * Prints out a toString representation of this {@linkplain Node}.
     *
     * Format: Node at ([x], [y]) with fScore = [fScore], gScore = [gScore], hScore = [hScore]
     * (used for testing purposes)
     *
     * @return String representation of this {@linkplain Node}.
     */
    public String toString() {
        return "Node at (" + location.getX() + ", " + location.getY() + ") " +
                "with fScore = " + (new DecimalFormat("0.00")).format(fScore) + ", " +
                "gScore = " + (new DecimalFormat("0.00")).format(gScore) + ", " +
                "hScore = " + (new DecimalFormat("0.00")).format(hScore);
    }

}
