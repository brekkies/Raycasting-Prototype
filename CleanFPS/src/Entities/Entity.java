package Entities;

import java.awt.*;

public class Entity {
    public int worldX; //in raw world pixel amounts
    public int worldY; //^^^^^
    public int width;
    public int speed;
    public int height;
    public boolean collisionOn;
    public SolidArea solidArea;
    public String direction;
    public class SolidArea {
        public int x;
        public int y;
        public int w;
        public int h;
        public SolidArea(int xx, int yy, int ww, int hh) {
            x = xx;
            y = yy;
            w = ww;
            h = hh;
        }
        public void drawSolidArea(Graphics2D g2) {
            g2.setColor(Color.red);
            g2.fillRect(x,y,w,h);
        }
    }
    public Entity(int x, int y, int wid, int hei) {
        worldX = x;
        worldY = y;
        width = wid;
        height = hei;
        speed = 1;
        collisionOn = false;
        //collision-box is whole entity by default
        solidArea = new SolidArea(x, y, wid, hei);
        direction = "none";
    }
    public void update() {

    }
    public void draw() {

    }
}
