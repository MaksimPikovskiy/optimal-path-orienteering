

public class Node {

    int x;
    int y;
    double g;
    double h;
    double f;
    Node parentNode;

    public Node(int x, int y, double g, double h, Node parent) {
        this.x = x;
        this.y = y;
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
                (this.x == thatNode.x) &&
                (this.y == thatNode.y);
    }


}
