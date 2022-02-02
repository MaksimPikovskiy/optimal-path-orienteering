public class Terrain {
    String name;
    int[] color = new int[3];
    int modifier;

    public Terrain(String name, int[] color, int modifier) {
        this.name = name;
        this.color = color;
        this.modifier = modifier;
    }
}
