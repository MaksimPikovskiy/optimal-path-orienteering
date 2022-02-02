import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class lab1 {

    public static void usage() {
        System.err.println("Program should accept 4 arguments, " +
                "in order: terrain-image, elevation-file, path-file, output-image-filename." +
                "\n Example: $ java lab1.java terrain.png mpp.txt red.txt redOut.png");
    }

    public static void main(String[] args) {

        List<Terrain> terrains = new LinkedList<>();

        terrains.add(new Terrain("Open land", new int[]{248, 148, 18}, 1));
        terrains.add(new Terrain("Rough meadow", new int[]{255, 192, 0}, 1));
        terrains.add(new Terrain("Easy movement forest", new int[]{255, 255, 255}, 1));
        terrains.add(new Terrain("Slow run forest", new int[]{2, 208, 60}, 1));
        terrains.add(new Terrain("Walk forest", new int[]{2, 136, 40}, 1));
        terrains.add(new Terrain("Impassible vegetation", new int[]{5, 73, 24}, 1));
        terrains.add(new Terrain("Lake/Swamp/Marsh", new int[]{0, 0, 255}, 1));
        terrains.add(new Terrain("Paved road", new int[]{71, 51, 3}, 1));
        terrains.add(new Terrain("Footpath", new int[]{0, 0, 0}, 1));
        terrains.add(new Terrain("Out of bounds", new int[]{205, 0, 101}, 1));




        if(args.length != 4) {
            System.err.println("Error: Incorrect number of arguments");
            usage();
        }

        BufferedImage terrainMap = null;
        String allElevations = null;
        String pointsToVisit = null;
        try {
            terrainMap = ImageIO.read(new File(args[0]));

            Path filePathElevations = Paths.get(args[1]);
            allElevations = Files.readString(filePathElevations);

            Path filePathPath = Paths.get(args[2]);
            pointsToVisit = Files.readString(filePathPath);
        } catch (IOException e) {
            System.err.println(e);
        }

        assert allElevations != null;
        String[] tempElevationArray = allElevations.split("\\s+");

        for(int i = 0; i < tempElevationArray.length; i++)
            tempElevationArray[i] = tempElevationArray[i].substring(0, tempElevationArray[i].length() - 5);

        double[] tempElevationArray2 = Arrays.stream(tempElevationArray).mapToDouble(Double::parseDouble).toArray();

        double[][] elevationArray = new double[400][500];
        for(int col = 0; col < 500; col++) {
            for (int row = 0; row < 400; row++) {
                elevationArray[row][col] = tempElevationArray2[row + (col * 400)];
            }
        }

        String[] pointsArray = pointsToVisit.split("\\s+");

        List<Point> destinations = new ArrayList<>();
        for(int i = 0; i < (pointsArray.length); i+=2) {
            int x = Integer.parseInt(pointsArray[i]);
            int y = Integer.parseInt(pointsArray[i + 1]);
            destinations.add(new Point(x, y));
        }

        List<Node> solutionPath = new ArrayList<>();

        for(int i = 0; i < pointsArray.length - 1; i++) {
            solutionPath.addAll(aStarSearch(terrainMap, terrains, elevationArray,
                    pointsArray[i], pointsArray[i + 1]));
        }

        for(Point a: destinations)
            System.out.println(a);


    }
}
