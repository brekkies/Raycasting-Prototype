import GameEngine.GamePanel;
import GameEngine.Render3D;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        window.setTitle("mygame");
        Render3D rend = new Render3D();
        GamePanel gamePanel = new GamePanel(rend);
        window.add(gamePanel);
        window.add(rend);
        window.pack();
        window.setVisible(true);
        gamePanel.startGameThread();



    }
}