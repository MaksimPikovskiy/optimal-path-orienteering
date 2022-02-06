/**
 * Terrain is a data structure for terrain representation, correlating to terrain pixels on the terrain image.
 *
 * It has a name, color, and modifier (how good the terrain is to walk on; or speed on this terrain).
 *
 * The terrain modifier is represented as this:
 *      - Lower value of modifier means the terrain is worse (slower speed).
 *      - Higher value of modifier means the terrain is better (faster speed).
 *
 * @author <a href='mailto:mp8671@rit.edu'>Maksim Pikovskiy</a>
 */
public class Terrain {

    String name;
    int[] color;
    double modifier;

    /**
     * Constructor for the {@linkplain Terrain} with given values.
     *
     * @param name the name of the terrain.
     * @param color the color of the terrain.
     * @param modifier the modifier of the terrain (lower means worse, higher means better).
     */
    public Terrain(String name, int[] color, double modifier) {
        this.name = name;
        this.color = color;
        this.modifier = modifier;
    }

}
