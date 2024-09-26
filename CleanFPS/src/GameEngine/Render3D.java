package GameEngine;

import Entities.Ray;

import javax.swing.*;
import java.awt.*;


public class Render3D extends JPanel {
    public Ray[] walls = new Ray[60];
    public Render3D () {
        this.setBackground(Color.GRAY);
        this.setPreferredSize(new Dimension(600, 600));
        this.setDoubleBuffered(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.darkGray);
        g2.fillRect(0,100,600,500);

        g2.setColor(Color.BLUE);
        //max magnitude ~200?? total guesstimate
        for (int i = 0; i < walls.length; i++) {
            if (walls[i] == null || (int) Math.round(walls[i].magn) == 0) {
                g2.fillRect(i*5, 100, 5, 200);
            } else {
                if (walls[i].close == true && i != 0) {
                    g2.setColor(walls[i-1].color);
                }
                else {
                    g2.setColor(walls[i].color);
                }
                g2.fillRect(i*5, 100, 5, 10000 / (int) Math.round(walls[i].magn));
            }
        }
    }
}
