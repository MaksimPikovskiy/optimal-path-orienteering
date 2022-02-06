import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This is the main class for initiating Summer Orienteering.
 *
 * It initiates the terrains with their respective colors and modifiers,
 * Checks for valid number of arguments.
 *
 * Using those arguments, it reads from the provided terrain image to check for colors,
 * converts the elevations file into 2D array, reads from the provided path file and
 * calls on {@linkplain aStarSearch} to find a path from one point to another.
 *
 * After {@linkplain aStarSearch} has finished finding paths between all provided points,
 * This class prints the total distance of the path (and writes it into output folder in
 * the directory), and draws a path on the provided terrain map and saves it as separate
 * image (specified as last argument).
 *
 * It also calculates the time taken to find the path(s)
 *
 * @author <a href='mailto:mp8671@rit.edu'>Maksim Pikovskiy</a>
 */
public class lab1 {

    // Path for the output folder
    private static final String outputPath = "output/";

    /**
     * Prints the message on how to use the program.
     * Only prints when incorrect number of arguments are given.
     */
    public static void usage() {
        System.err.println("Program should accept 4 arguments, " +
                "in order: terrain-image, elevation-file, path-file, output-image-filename." +
                "\n Example: $ java lab1.java terrain.png mpp.txt red.txt redOut.png");
    }

    /**
     * Main body of the program.
     *
     * Retrieves all the required information needed to start an {@linkplain aStarSearch}.
     *
     * @param args required 4 arguments for the {@link aStarSearch} algorithm.
     */
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        // Terrain Table, based on the one provided for the assignment
        List<Terrain> terrains = new ArrayList<>();

        // Values for the "elevation" test case
//        terrains.add(new Terrain("Paths", new int[]{255, 255, 255}, 1));
//        terrains.add(new Terrain("Mountains", new int[]{0, 0, 0}, 0.001));

        // Main Terrain Table
        terrains.add(new Terrain("Open land", new int[]{248, 148, 18}, 1));
        terrains.add(new Terrain("Rough meadow", new int[]{255, 192, 0}, 0.45));
        terrains.add(new Terrain("Easy movement forest", new int[]{255, 255, 255}, 0.8));
        terrains.add(new Terrain("Slow run forest", new int[]{2, 208, 60}, 0.7));
        terrains.add(new Terrain("Walk forest", new int[]{2, 136, 40}, 0.6));
        terrains.add(new Terrain("Impassible vegetation", new int[]{5, 73, 24}, 0.001));
        terrains.add(new Terrain("Lake/Swamp/Marsh", new int[]{0, 0, 255}, 0.001));
        terrains.add(new Terrain("Paved road", new int[]{71, 51, 3}, 1));
        terrains.add(new Terrain("Footpath", new int[]{0, 0, 0}, 0.9));
        terrains.add(new Terrain("Out of bounds", new int[]{205, 0, 101}, 0.00001));


        if(args.length != 4) {
            System.err.println("Error: Incorrect number of arguments");
            usage();
            System.exit(0);
        }

        // Checks all required files if they exist and retrieves them.
        // Otherwise, gives an error with Exception message.
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

        // Asserts that there are elevations in the elevation files.
        // Then splits the entire String into individual elevation numbers.
        assert allElevations != null;
        String[] tempElevationArray = allElevations.strip().split("\\s+");

        // Removes the scientific notation of each elevation number (last 5 numbers/letters).
        for(int i = 0; i < tempElevationArray.length; i++) {
            if(tempElevationArray[i].length() > 0) {
                tempElevationArray[i] = tempElevationArray[i].substring(0, tempElevationArray[i].length() - 5);
            }
        }

        // Converts the elevation array from String to double.
        double[] tempElevationArray2 = Arrays.stream(tempElevationArray).mapToDouble(Double::parseDouble).toArray();

        // Converts the 1D array to corresponding 2D array, where dimensions match the dimensions of the terrain image.
        double[][] elevationArray = new double[400][500];
        for(int col = 0; col < 500; col++) {
            for (int row = 0; row < 400; row++) {
                elevationArray[row][col] = tempElevationArray2[row + (col * 400)];
            }
        }

        // Splits the entire String into individual coordinate numbers.
        assert pointsToVisit != null;
        String[] pointsArray = pointsToVisit.split("\\s+");

        // Converts the String array into Point List to iterate over for aStarSearch.
        List<Point> destinations = new ArrayList<>();
        for(int i = 0; i < (pointsArray.length); i+=2) {
            int x = Integer.parseInt(pointsArray[i]);
            int y = Integer.parseInt(pointsArray[i + 1]);
            destinations.add(new Point(x, y));
        }

        List<Node> solutionPath = new ArrayList<>();

        // Iterate over the pointsToVisit and find a path between them.
        for(int i = 0; i < destinations.size() - 1; i++) {
            aStarSearch search = new aStarSearch(terrainMap, terrains, elevationArray,
                    destinations.get(i), destinations.get(i + 1));
            solutionPath.addAll(search.findPath());
        }

        // Calculate the total distance traveled.
        double sum = 0;
        for(int i = 0; i < solutionPath.size() - 1; i++) {
            Point point1 = solutionPath.get(i).location;
            Point point2 = solutionPath.get(i + 1).location;

            if(point1.y == point2.y) sum += aStarSearch.LONGITUDE;
            else if(point1.x == point2.x) sum += aStarSearch.LATITUDE;
        }

        String distText = "Total Distance: " + sum + " m";

        System.out.println(distText);

        // Color in visited nodes on the terrain image.
        for(Node node: solutionPath) {
            terrainMap.setRGB(node.location.x, node.location.y, new Color(255, 0, 0, 255).getRGB());
        }

        // Save the terrain image with the optimal path(s) colored in
        File totalDistanceFile = new File(outputPath + "totalDistance.txt");
        File terrainWithPathFile = new File(args[3]);
        try {
            ImageIO.write(terrainMap, "PNG", terrainWithPathFile);

            FileWriter fileWriter = new FileWriter(totalDistanceFile, false);
            fileWriter.write(distText);
            fileWriter.close();
        } catch(IOException e) {
            System.err.println(e);
        }

        // Print total time taken.
        long endTime   = System.nanoTime();
        long totalTime = TimeUnit.NANOSECONDS.toSeconds(endTime - startTime);
        System.out.println("Time Taken: " + totalTime + " seconds");
    }

}
