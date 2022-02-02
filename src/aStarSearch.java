import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class aStarSearch {

    private final BufferedImage terrainMap;
    private final List<Terrain> terrains;
    private final double[][] elevationArray;
    private final Point startPoint;
    private final Point goalPoint;

    private static final double LONGITUDE = 10.29;
    private static final double LATITUDE = 7.55;

    public aStarSearch(BufferedImage terrainMap, List<Terrain> terrains, double[][] elevationArray,
                       Point startPoint, Point goalPoint) {
        this.terrainMap = terrainMap;
        this.terrains = terrains;
        this.elevationArray = elevationArray;
        this.startPoint = startPoint;
        this.goalPoint = goalPoint;
    }

    public ArrayList<Node> findPath() {
        double test = elevationArray[1][1];

        PriorityQueue<Node> generatedNodes = new PriorityQueue<>();
        List<Node> searchedNodes = new ArrayList<>();

        Node start = new Node(startPoint, 0, 0, null);
        generatedNodes.add(start);

        Node goal = null;
        while(!generatedNodes.isEmpty()) {
            Node curr = generatedNodes.remove();

        }

        ArrayList<Node> solution = new ArrayList<>();
        while(goal != null) {
            solution.add(goal);
            goal = goal.parentNode;
        }
        return solution;
    }

    private double costFunction(Point point1, Point point2) {

        double deltaX = LONGITUDE * (point1.x - point2.y);
        double distX = Math.pow(Math.abs(deltaX), 2);

        double deltaY = LATITUDE * (point1.y - point2.y);
        double distY = Math.pow(Math.abs(deltaY), 2);

        double deltaZ = elevationArray[point1.x][point1.y]
                - elevationArray[point2.x][point2.y];
        double distZ = Math.pow(Math.abs(deltaZ), 2);

        double dist = Math.sqrt(distX + distY + distZ);

        double TerrainMod1 = getTerrainModifier(point1);
        double TerrainMod2 = getTerrainModifier(point2);

        return TerrainMod1 * (dist / 2) + TerrainMod2 * (dist / 2);
    }

    private double heuristicFunction(Point point1, Point point2) {
        double dist = costFunction(point1, point2);

        double bestTerrainMod = 0;
        for (Terrain terrain : terrains)
            if (bestTerrainMod < terrain.modifier)
                bestTerrainMod = terrain.modifier;

        return dist / bestTerrainMod;
    }

    private double getTerrainModifier(Point point) {
        int color = terrainMap.getRGB(point.x, point.y);
        int[] valuesRGB = new int[]{((color & 0xff0000) >> 16), (color & 0xff00) >> 8, color & 0xff};

        for(Terrain terr : terrains)
            if(terr.color == valuesRGB)
                return terr.modifier;

        return 0.000000001;
    }
}
