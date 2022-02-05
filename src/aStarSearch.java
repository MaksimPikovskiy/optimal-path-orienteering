import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class aStarSearch {

    private final BufferedImage terrainMap;
    private final List<Terrain> terrains;
    private final double[][] elevationArray;
    private final Point startPoint;
    private final Point goalPoint;

    public static final double LONGITUDE = 10.29;
    public static final double LATITUDE = 7.55;

    private static final int NORTH_LIMIT = 498;
    private static final int SOUTH_LIMIT = 1;
    private static final int EAST_LIMIT = 393;
    private static final int WEST_LIMIT = 1;

    public aStarSearch(BufferedImage terrainMap, List<Terrain> terrains, double[][] elevationArray,
                       Point startPoint, Point goalPoint) {
        this.terrainMap = terrainMap;
        this.terrains = terrains;
        this.elevationArray = elevationArray;
        this.startPoint = startPoint;
        this.goalPoint = goalPoint;
    }

    // from StackOverflow for testing/visuals
    public double reverseNumber(double num, double min, double max) {
        return (max + min) - num;
    }

    public ArrayList<Node> findPath() {
        PriorityQueue<Node> toExplore = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        Set<Node> inQ = new HashSet<>();

        Node start = new Node(startPoint, 0, 0, null);
        toExplore.add(start);

        StdDraw.setCanvasSize(400, 500);
        StdDraw.setXscale(0,400);
        StdDraw.setYscale(0,500);
        StdDraw.clear();

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(startPoint.getX(),
                reverseNumber(startPoint.getY(), 0, 500), 1);
        StdDraw.filledCircle(goalPoint.getX(),
                reverseNumber(goalPoint.getY(), 0, 500), 1);

        Point nextPoint;
        Node goal = null;
        while(!toExplore.isEmpty()) {
            Node curr = toExplore.remove();
            inQ.remove(curr);
            if(visited.contains(curr)) {
                continue;
            }
            visited.add(curr);

            StdDraw.setPenRadius(0.0);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.point(curr.location.getX(),
                    reverseNumber(curr.location.getY(), 0, 500));

            if(curr.location.y <= NORTH_LIMIT) {
                nextPoint = new Point(curr.location.x, curr.location.y + 1);
                Node temp = getNewNode(curr, nextPoint);

                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                if(!visited.contains(temp)) {
                    if (!inQ.contains(temp)) {
                        toExplore.add(temp);
                        inQ.add(temp);
                    } else {
                        for(Node node : inQ) {
                            if(node.fScore > temp.fScore) {
                                toExplore.add(temp);
                                inQ.add(temp);
                                break;
                            }
                        }
                    }
                }
            }
            if(curr.location.y >= SOUTH_LIMIT) {
                nextPoint = new Point(curr.location.x, curr.location.y - 1);
                Node temp = getNewNode(curr, nextPoint);

                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                if(!visited.contains(temp)) {
                    if (!inQ.contains(temp)) {
                        toExplore.add(temp);
                        inQ.add(temp);
                    } else {
                        for(Node node : inQ) {
                            if(node.fScore > temp.fScore) {
                                toExplore.add(temp);
                                inQ.add(temp);
                                break;
                            }
                        }
                    }
                }
            }
            if(curr.location.x <= EAST_LIMIT) {
                nextPoint = new Point(curr.location.x + 1, curr.location.y);
                Node temp = getNewNode(curr, nextPoint);

                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                if(!visited.contains(temp)) {
                    if (!inQ.contains(temp)) {
                        toExplore.add(temp);
                        inQ.add(temp);
                    } else {
                        for(Node node : inQ) {
                            if(node.fScore > temp.fScore) {
                                toExplore.add(temp);
                                inQ.add(temp);
                                break;
                            }
                        }
                    }
                }
            }
            if(curr.location.x >= WEST_LIMIT) {
                nextPoint = new Point(curr.location.x - 1, curr.location.y);
                Node temp = getNewNode(curr, nextPoint);

                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                if(!visited.contains(temp)) {
                    if (!inQ.contains(temp)) {
                        toExplore.add(temp);
                        inQ.add(temp);
                    } else {
                        for(Node node : inQ) {
                            if(node.fScore > temp.fScore) {
                                toExplore.add(temp);
                                inQ.add(temp);
                                break;
                            }
                        }
                    }
                }
            }

            System.out.println(curr);
            StdDraw.show();
        }

        ArrayList<Node> solution = new ArrayList<>();
        while(goal != null) {
            solution.add(0, goal);
            goal = goal.parentNode;
        }
        return solution;
    }

    public double calculateDistance(Point point1, Point point2) {

        double deltaX = LONGITUDE * (point1.x - point2.x);
        double distX = Math.pow(Math.abs(deltaX), 2);

        double deltaY = LATITUDE * (point1.y - point2.y);
        double distY = Math.pow(Math.abs(deltaY), 2);

        double deltaZ = elevationArray[point1.x][point1.y]
                - elevationArray[point2.x][point2.y];
        double distZ = Math.pow(Math.abs(deltaZ), 2);

        return Math.sqrt(distX + distY + distZ);
    }

    private double costFunction(Point point1, Point point2) {
        //double dist = 1;
        double dist = calculateDistance(point1, point2);

        double TerrainMod1 = getTerrainModifier(point1);
        double TerrainMod2 = getTerrainModifier(point2);

        return (dist / 2) / TerrainMod1 + (dist / 2) / TerrainMod2;
    }

    private double heuristicFunction(Point point1, Point point2) {
        double dist = calculateDistance(point1, point2);

        double bestTerrainMod = 1; //change to zero after testing
//        for (Terrain terrain : terrains)
//            bestTerrainMod = Math.max(bestTerrainMod, terrain.modifier);

        return dist / bestTerrainMod; //increase differences between heuristics?* 100
        //return 1;
    }

    private Node getNewNode(Node curr, Point nextPoint) {
        double gScoreTemp = costFunction(curr.location, nextPoint);
        double hScoreTemp = heuristicFunction(nextPoint, goalPoint);

        return new Node(nextPoint, curr.gScore + gScoreTemp, hScoreTemp, curr);
    }

    private double getTerrainModifier(Point point) {
        Color color = new Color(terrainMap.getRGB(point.x, point.y));
        int[] valuesRGB = new int[]{color.getRed(), color.getGreen(), color.getBlue()};

//        for(Terrain terr : terrains)
//            if(Arrays.equals(terr.color, valuesRGB))
//                return terr.modifier;
//
//        return 0.0001;
        return 1;
    }
}
