import java.awt.*;
import java.text.DecimalFormat;

public class Node implements Comparable<Node> {

    Point location;
    double gScore;
    double hScore;
    double fScore;
    Node parentNode;

    public Node(Point location, double gScore, double hScore, Node parent) {
        this.location = location;
        this.gScore = gScore;
        this.hScore = hScore;
        this.fScore = gScore + hScore;
        parentNode = parent;
    }

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

    public boolean equals(Object that) {
        if(this == that) return true;
        if(that == null) return false;
        if (this.getClass() != that.getClass()) return false;

        Node thatNode = (Node) that;
        return this.location.equals(thatNode.location);
    }

    public int hashCode() {
        return location.x + location.y * 2;
    }

    public String toString() {
        return "Node at (" + location.getX() + ", " + location.getY() + ") " +
                "with fScore = " + (new DecimalFormat("0.00")).format(fScore) + ", " +
                "gScore = " + (new DecimalFormat("0.00")).format(gScore) + ", " +
                "hScore = " + (new DecimalFormat("0.00")).format(hScore);
    }


}
