import java.awt.*;

public class Node implements Comparable<Node> {

    Point location;
    double g;
    double h;
    double f;
    Node parentNode;

    public Node(Point location, double g, double h, Node parent) {
        this.location = location;
        this.g = g;
        this.h = h;
        this.f = g + h;
        parentNode = parent;
    }

    public int compareTo(Node that) {
        if(this.f < that.f) return -1;
        else if(this.f > that.f) return 1;
        else {
            if(this.h < that.h) return -1;
            else if(this.h > that.h) return 1;
            else return 0;
        }
    }

    public boolean equals(Object that) {
        if(this == that) return true;
        if(that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Node thatNode = (Node) that;
        return (this.parentNode == thatNode.parentNode) &&
                (this.location == thatNode.location);
    }

    public String toString() {
        return "Node at (" + location.getX() + ", " + location.getY() + ")";
    }


}
