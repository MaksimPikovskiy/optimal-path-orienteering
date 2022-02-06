import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * aStarSearch is a class which uses A* search algorithm to find an optimal path between two points.
 *
 * It uses a formula f(n) = g(n) + h(n), where g(n) is a cost of a path from one {@linkplain Node} to another and
 * h(n) is a cost of a path from next {@linkplain Node} to goal {@linkplain Node}.
 *
 * @author <a href='mailto:mp8671@rit.edu'>Maksim Pikovskiy</a>
 */
public class aStarSearch {

    // Data variables that lab1 provides.
    private final BufferedImage terrainMap;
    private final List<Terrain> terrains;
    private final double[][] elevationArray;
    private final Point startPoint;
    private final Point goalPoint;

    // Variables representing the dimensions x and y of each Node.
    public static final double LONGITUDE = 10.29;
    public static final double LATITUDE = 7.55;

    // Limits around the map so that search algorithm does not search off the map
    // (and result in error).
    private static final int NORTH_LIMIT = 498;
    private static final int SOUTH_LIMIT = 1;
    private static final int EAST_LIMIT = 393;
    private static final int WEST_LIMIT = 1;

    /**
     * Constructor for {@linkplain aStarSearch} with values retrieved by {@linkplain lab1}.
     *
     * @param terrainMap the terrain image that has specified colors correlated to "terrains" variable.
     * @param terrains the list of terrains with their respective names, colors, and modifiers.
     * @param elevationArray 2D array which has elevation of each pixel on the terrain map.
     * @param startPoint the {@link Point} where the search begins.
     * @param goalPoint the {@link Point} which search finds a path to.
     */
    public aStarSearch(BufferedImage terrainMap, List<Terrain> terrains, double[][] elevationArray,
                       Point startPoint, Point goalPoint) {
        this.terrainMap = terrainMap;
        this.terrains = terrains;
        this.elevationArray = elevationArray;
        this.startPoint = startPoint;
        this.goalPoint = goalPoint;
    }

    /**
     * Finds an optimal path from {@linkplain Point start point} to {@linkplain Point goal point}.
     *
     * Searches neighbors in 4 directions: North, South, East, West.
     * Once a {@linkplain Node goal node} was found, it creates an {@linkplain ArrayList} that contains
     * a path from {@linkplain Point start point} to {@linkplain Point goal point}.
     *
     * @return {@linkplain ArrayList} containing a path from {@linkplain Point start point} to
     *         {@linkplain Point goal point}.
     */
    public ArrayList<Node> findPath() {
        PriorityQueue<Node> toExplore = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        Set<Node> inQ = new HashSet<>();

        Node start = new Node(startPoint, 0, 0, null);
        toExplore.add(start);

        Point nextPoint;
        Node goal = null;
        while(!toExplore.isEmpty()) {
            // Remove node from queue so we do explore it again.
            Node curr = toExplore.remove();
            inQ.remove(curr);
            // Check if the currently selected Node is in visited.
            // If true, skip the node and go to next one in toExplore.
            if(visited.contains(curr)) {
                continue;
            }

            // Get the 4 neighbors of the current node.
            if(curr.location.y <= NORTH_LIMIT) {
                nextPoint = new Point(curr.location.x, curr.location.y + 1);
                Node temp = getNewNode(curr, nextPoint);

                // If node is goal, break entire loop.
                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                // Check whether the neighbor is visited.
                // If not, check if it is in queue to explore.
                // If not, add to the queue.
                // If it is in queue, check if it's fScore is smaller.
                // If it is, add to queue. Otherwise, scrap it.
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

                // If node is goal, break entire loop.
                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                // Check whether the neighbor is visited.
                // If not, check if it is in queue to explore.
                // If not, add to the queue.
                // If it is in queue, check if it's fScore is smaller.
                // If it is, add to queue. Otherwise, scrap it.
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

                // If node is goal, break entire loop.
                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                // Check whether the neighbor is visited.
                // If not, check if it is in queue to explore.
                // If not, add to the queue.
                // If it is in queue, check if it's fScore is smaller.
                // If it is, add to queue. Otherwise, scrap it.
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

                // If node is goal, break entire loop.
                if(temp.location.equals(goalPoint)) {
                    goal = temp;
                    break;
                }

                // Check whether the neighbor is visited.
                // If not, check if it is in queue to explore.
                // If not, add to the queue.
                // If it is in queue, check if it's fScore is smaller.
                // If it is, add to queue. Otherwise, scrap it.
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

            // Add explored node to visited
            // so it does not get checked again.
            visited.add(curr);
        }

        // Once goal node was found, build the array by
        // retrieving parent node of each node and adding it
        // to the front of the array.
        ArrayList<Node> solution = new ArrayList<>();
        while(goal != null) {
            solution.add(0, goal);
            goal = goal.parentNode;
        }

        return solution;
    }

    /**
     * Calculates the Euclidean distance between two {@linkplain Point points} in 3D.
     *
     * @param point1 one {@link Point point} to use for distance calculation.
     * @param point2 another {@link Point point} to use for distance calculation.
     *
     * @return returns the distance between two given {@link Point points}.
     */
    private double calculateDistance(Point point1, Point point2) {

        double deltaX = LONGITUDE * (point1.x - point2.x);
        double distX = Math.pow(Math.abs(deltaX), 2);

        double deltaY = LATITUDE * (point1.y - point2.y);
        double distY = Math.pow(Math.abs(deltaY), 2);

        double deltaZ = elevationArray[point1.x][point1.y]
                - elevationArray[point2.x][point2.y];
        double distZ = Math.pow(Math.abs(deltaZ), 2);

        return Math.sqrt(distX + distY + distZ);
    }

    /**
     * Calculates the cost of the path using distance between two {@linkplain Point points}.
     * This it is the cost of the path from one {@linkplain Point point} to its neighbor.
     *
     * To calculate the cost, it retrieves the distance between two {@linkplain Point points},
     * divides the distance into two halves, and divides one half by a terrain modifier of one
     * {@linkplain Point point} and divides the other half by a terrain modifier of the
     * neighbor of the {@linkplain Point point}.
     *
     * @param point1 {@link Point point} from which to calculate the cost of the path.
     * @param point2 neighbor of the {@link Point first point}, point1
     *
     * @return the cost of the path between two {@linkplain Point points}.
     */
    private double costFunction(Point point1, Point point2) {
        double dist = calculateDistance(point1, point2);

        double TerrainMod1 = getTerrainModifier(point1);
        double TerrainMod2 = getTerrainModifier(point2);

        return (dist / 2) / TerrainMod1 + (dist / 2) / TerrainMod2;
    }

    /**
     * Calculates the heuristic cost of the path using distance between two {@linkplain Point points}.
     * This is the cost of the path from one {@linkplain Point point} to the {@linkplain Point goal point}.
     * It used to calculate which node is best to go on for the most optimal path.
     *
     * To calculate the cost, it retrieves the distance between one {@linkplain Point point} and
     * {@linkplain Point goal point}, retrieves the best terrain modifier (usually 1), and divides
     * the distance by the best terrain modifier to get the heuristic.
     *
     * @param point1 {@linkplain Point point} to calculate the distance from.
     * @param point2 {@linkplain Point goal point}
     *
     * @return the heuristic cost of the path from one {@linkplain Point point}
     *         to the {@linkplain Point goal point}.
     */
    private double heuristicFunction(Point point1, Point point2) {
        double dist = calculateDistance(point1, point2);

        double bestTerrainMod = 0;
        for (Terrain terrain : terrains)
            bestTerrainMod = Math.max(bestTerrainMod, terrain.modifier);

        return dist / bestTerrainMod; // ( * 1.5 ) <- multiply by this if all terrain modifiers is 1
    }

    /**
     * Gets the cost of path and heuristic for the {@linkplain Node}
     * and creates a new {@linkplain Node} with the specific values:
     *      - the new location,
     *      - its gScore + the gScore of the previous {@linkplain Node} (parent),
     *      - its hScore (cost from it to {@linkplain Point goal point},
     *      - the {@linkplain Node parent node}.
     *
     *
     * @param curr the {@link Node} that is getting explored in findPath().
     * @param nextPoint the {@link Point} of the {@link Node new node}.
     *
     * @return a new {@link Node} that has new location, new total cost of the path,
     *         its hScore, and the {@linkplain Node parent node}.
     */
    private Node getNewNode(Node curr, Point nextPoint) {
        double gScoreTemp = costFunction(curr.location, nextPoint);
        double hScoreTemp = heuristicFunction(nextPoint, goalPoint);

        return new Node(nextPoint, curr.gScore + gScoreTemp, hScoreTemp, curr);
    }

    /**
     * Gets the terrain modifier based on the x and y coordinates ({@linkplain Point}).
     *
     * To get the terrain modifier, it gets the {@linkplain Color} of the terrain
     * on the terrain map, and then compares it to the color arrays of all terrains
     * in the terrain list to get the terrain modifier.
     *
     * @param point the {@linkplain Point} to get the terrain modifier for.
     *
     * @return the terrain modifier of the {@linkplain Point}.
     *         if the {@linkplain Color} for the {@linkplain Point} is not present,
     *         return a low modifier number (0.0001).
     */
    private double getTerrainModifier(Point point) {
        Color color = new Color(terrainMap.getRGB(point.x, point.y));
        int[] valuesRGB = new int[]{color.getRed(), color.getGreen(), color.getBlue()};

        for(Terrain terr : terrains)
            if(Arrays.equals(terr.color, valuesRGB))
                return terr.modifier;

        return 0.0001;
    }

}
