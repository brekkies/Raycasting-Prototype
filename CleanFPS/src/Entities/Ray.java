package Entities;

import java.awt.*;

public class Ray {
    public boolean vert;
    public double magn;
    public boolean close;
    public Color color;
    public Ray (double mag, boolean vertical, boolean cl) {
        magn = mag;
        vert = vertical;
        close = cl;
        if (vert) {
            this.color = Color.getColor("Blue", 50);
        } else {
            this.color = Color.blue;
        }
    }
}
