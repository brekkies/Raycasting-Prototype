package World;

import java.awt.*;

public class Tile {
    public int type;
    private final Color[] cols = new Color[9];
    public Color color;
    public boolean barrier;
    public Tile (int t) {
        cols[0] = Color.black;
        cols[1] = Color.gray;
        cols[2] = Color.darkGray;
        type = t;
        color = cols[type];
        barrier = type == 2;
    }
}
