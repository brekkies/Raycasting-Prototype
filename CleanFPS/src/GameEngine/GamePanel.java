package GameEngine;

import Entities.Player;
import World.WorldGenerator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 1;
    public final int tileSize = originalTileSize * scale; //16x16 tile
    public final int maxScreenCol = 24;
    public final int maxScreenRow = 18;
    final int screenWidth = tileSize * maxScreenCol; //??? pix
    final int screenHeight = tileSize * maxScreenRow; //??? pix
    public final CollisionHandler collHandler;
    Thread gameThread;
    public WorldGenerator gen;
    Player avatar;

    //set player's default position

    int aX = 100;
    int aY = 100;
    public Render3D pov;
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //calls the run method
    }
    @Override
    public void run() {
        int FPS = 60;
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {

            update(); //updates variables and positions

            repaint(); //draws the updated stuff
            pov.repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public GamePanel(Render3D p) {
        pov = p;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.add(new JLabel("2D GAME"));
        gen = new WorldGenerator(tileSize, maxScreenCol, maxScreenRow);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        collHandler = new CollisionHandler(this);
        avatar = new Player(this, aX, aY, 10, 10);

    }
    public void update() {
        avatar.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        gen.drawWorld(g2);
        avatar.draw(g2);
        g2.dispose();
    }
}

