import java.awt.*;

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
            if(this.hScore < that.hScore) return -1;
            else if(this.hScore > that.hScore) return 1;
            else return 0;
        }
    }

    public String toString() {
        return "Node at (" + location.getX() + ", " + location.getY() + ") with fScore = " + fScore;
    }


}
