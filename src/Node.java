import java.awt.*;

public class Node {

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

    public boolean equals(Object that) {
        if(this == that) return true;
        if(that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Node thatNode = (Node) that;
        return (this.parentNode == thatNode.parentNode) &&
                (this.location == thatNode.location);
    }


}
