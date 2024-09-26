package Entities;

import GameEngine.GamePanel;
import GameEngine.Render3D;

import java.awt.*;

import static java.lang.Math.abs;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH = new KeyHandler();
    double rangle;
    Render3D pov;
    int[] xy;
    public Player(GamePanel g, int x, int y, int wid, int hei) {
        super(x, y, wid, hei);
        int buff = wid/4;
        gp = g;
        solidArea = new SolidArea(x + buff, y + buff, buff*2, buff*2);
        gp.addKeyListener(keyH);
        rangle = 0;
        pov = gp.pov;
    }

    @Override
    public void update() {
        if(keyH.upPressed == true) {
            direction = "up";
        }
        else if(keyH.downPressed == true) {
            direction = "down";
        }
        else {
            direction = "none";
        }
        if (keyH.leftPressed == true) {
            rangle -= 0.05;
        } else if (keyH.rightPressed == true) {
            rangle += 0.05;
        }
        if (rangle >= Math.PI*2) {
            rangle -= Math.PI*2;
        } else if (rangle < 0) {
            rangle += Math.PI*2;
        }
        xy  = quadrant(rangle);
        collisionOn = false;
        //TODO: redo collision to handle angled movement
        //collisionOn = gp.collHandler.checkTile(this);
        //if collision false, move
        double cos = Math.cos(rangle);
        double sin = Math.sin(rangle);
        int moveY = (int) Math.round(speed*sin);
        int moveX = (int) Math.round(speed*cos);
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldX += moveX;
                    worldY += moveY;
                    solidArea.x += moveX;
                    solidArea.y += moveY;
                    break;
                case "down":
                    worldX -= moveX;
                    worldY -= moveY;
                    solidArea.x -= moveX;
                    solidArea.y -= moveY;
                    break;
            }
        }
    }
    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fillRect(worldX,worldY,width,height);
        Ray[] lengths = drawRays(g2);
        pov.walls = lengths;
    }
    private int[] quadrant(double ang) {
        //returns signs of x,y. 0 radians is directly east. pi radians is west.
        int[] ret = new int[2];
        if (ang < Math.PI/2 ) {
            ret[0] = 1;
            ret[1] = 1;
        }
        else if (ang < Math.PI ) {
            ret[0] = -1;
            ret[1] = 1;
        }
        else if (ang < 1.5*Math.PI ) {
            ret[0] = -1;
            ret[1] = -1;
        }
        else {
            ret[0] = 1;
            ret[1] = -1;
        }
        return ret;
    }
    public Ray[] drawRays(Graphics2D g2) {
        Ray[] ret = new Ray[120];
        double rad  = rangle - Math.PI/6;
        for (int i = 0; i < 120; i++) {
            if (rad >= Math.PI*2) {
                rad -= Math.PI*2;
            } else if (rad < 0) {
                rad += Math.PI*2;
            }
            ret[i] = drawRay(g2, rad);
            rad += Math.PI/360;
        }
        return ret;
    }
    public Ray drawRay(Graphics2D g2, double ang) {
        //find vertical hit
        int dof = 0;
        int gridX, gridY;
        double hxo, hyo, vxo, vyo, vx, vy, hx, hy;
        double vlen, hlen;
        double tan = Math.tan(ang);
        boolean vinv = false;
        boolean hinv = false;
        xy = quadrant(ang);

        if (ang < Math.PI/2 || ang > 1.5*Math.PI) {
            vx = worldX + gp.tileSize - worldX%gp.tileSize;
            vy = worldY + (vx-worldX)*tan;
            vxo = gp.tileSize;
            vyo = gp.tileSize*tan;
        } else {
            vx = worldX - worldX%gp.tileSize;
            vy = worldY + (vx-worldX)*tan;
            vxo = -gp.tileSize;
            vyo = -gp.tileSize*tan;
        }
        if (ang == 0 || ang == Math.PI) {vyo = 0;}
        g2.setColor(Color.red);
        while (dof < 20) {
            gridY = ((int) Math.round(vy))/gp.gen.tileSize;
            gridX = ((int) Math.round(vx))/gp.gen.tileSize;
            if(vxo < 0){
                gridX -= 1;
            }

            if (vy % gp.gen.tileSize <= 0.02 || vy % gp.gen.tileSize >= gp.gen.tileSize - 0.02) {
                gridY = ((int) Math.round(vy-1))/gp.gen.tileSize;

                if (gridY >= gp.maxScreenRow || gridY < 0 || gridX >= gp.maxScreenCol || gridX < 0 || gp.gen.world[gridX][gridY].barrier) {
                    vinv = true;
                    break;
                }
                gridY = ((int) Math.round(vy+1))/gp.gen.tileSize;
                if (gridY >= gp.maxScreenRow || gridY < 0 || gridX >= gp.maxScreenCol || gridX < 0 || gp.gen.world[gridX][gridY].barrier) {
                    vinv = true;
                    break;
                }
            }


            if (gridY >= gp.maxScreenRow || gridY < 0 || gridX >= gp.maxScreenCol || gridX < 0 || gp.gen.world[gridX][gridY].barrier) {
                break;
            }
            vx += vxo;
            vy += vyo;
            dof++;
        }
        vlen = Math.sqrt(((vx-worldX)*(vx-worldX))+((vy-worldY)*(vy-worldY)));

        //horizontal hit
        dof = 0;
        if (ang < Math.PI) { //positive, down
            hy = worldY + gp.tileSize - worldY%gp.tileSize;
            hx = worldX + (hy-worldY)/tan;
            hyo = gp.tileSize;
            hxo = gp.tileSize/tan;
        } else {
            hy = worldY - worldY%gp.tileSize;
            hx = worldX + (hy-worldY)/tan;
            hyo = -gp.tileSize;
            hxo = -gp.tileSize/tan;
        }
        if (ang == Math.PI/2 || ang == 1.5*Math.PI) {hxo = 0;}

        while (dof < 20) {
            gridY = ((int) Math.round(hy))/gp.gen.tileSize;
            gridX = ((int) Math.round(hx))/gp.gen.tileSize;

            if(hyo < 0){
                gridY -= 1;
            }
            //IF IT'S ON A VERTICAL LINE (aka vertex) check a bit above and below
            if (hx % gp.gen.tileSize <= 0.02 || hx % gp.gen.tileSize >= gp.gen.tileSize - 0.02) {
                gridX = ((int) Math.round(hx-1))/gp.gen.tileSize;

                if (gridY >= gp.maxScreenRow || gridY < 0 || gridX >= gp.maxScreenCol || gridX < 0 || gp.gen.world[gridX][gridY].barrier) {
                    hinv = true;
                    break;
                }
                gridX = ((int) Math.round(hx+1))/gp.gen.tileSize;
                if (gridY >= gp.maxScreenRow || gridY < 0 || gridX >= gp.maxScreenCol || gridX < 0 || gp.gen.world[gridX][gridY].barrier) {
                    hinv = true;
                    break;
                }
            }

            if (gridY >= gp.maxScreenRow || gridY < 0 || gridX >= gp.maxScreenCol || gridX < 0 || gp.gen.world[gridX][gridY].barrier) {
                break;
            }
            hx += hxo;
            hy += hyo;
            dof++;
        }
        hlen = Math.sqrt(((hx-worldX)*(hx-worldX))+((hy-worldY)*(hy-worldY)));
        double ret;
        boolean vert;
        boolean close = false;

        if (hlen < vlen) {
            g2.drawLine(worldX,worldY, (int) Math.round(hx),(int) Math.round(hy));
            ret = hlen;
            vert = true;
        } else {
            g2.drawLine(worldX,worldY, (int) Math.round(vx),(int) Math.round(vy));
            ret = vlen;
            vert = false;
        }
        //correct for distortion
        double ans = ret * Math.cos(rangle - ang);
        if (hinv || vinv) {
            vert = !vert;
        }
        return new Ray(ans, vert, close);
    }
}
