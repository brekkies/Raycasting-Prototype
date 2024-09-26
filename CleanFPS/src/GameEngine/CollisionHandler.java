package GameEngine;

import Entities.Entity;
import GameEngine.GamePanel;
import World.Tile;

import java.awt.*;

public class CollisionHandler {
    public GamePanel gp;
    public CollisionHandler(GamePanel g) {
        gp = g;
    }

    public boolean checkTile(Entity entity) {
        int leftx = entity.solidArea.x;
        int rightx = entity.solidArea.x + entity.solidArea.w;
        int topy = entity.solidArea.y;
        int boty = entity.solidArea.y + entity.solidArea.h;


        //collisioncases for each of the two tiles the entity is running into
        Tile tile1 = null;
        Tile tile2 = null;
        switch (entity.direction) {
            case "up":
                tile1 = gp.gen.world[leftx/gp.tileSize][(topy-entity.speed)/gp.tileSize];
                tile2 = gp.gen.world[rightx/gp.tileSize][(topy-entity.speed)/gp.tileSize];
                break;
            case "down":
                tile1 = gp.gen.world[leftx/gp.tileSize][(boty+entity.speed)/gp.tileSize];
                tile2 = gp.gen.world[rightx/gp.tileSize][(boty+entity.speed)/gp.tileSize];
                break;
                //LEFT RIGHT NOT NEEDED
            /*case "left":
                tile1 = gp.gen.world[(leftx - entity.speed)/gp.tileSize][topy/gp.tileSize];
                tile2 = gp.gen.world[(leftx - entity.speed)/gp.tileSize][boty/gp.tileSize];
                break;
            case "right":
                tile1 = gp.gen.world[(rightx + entity.speed)/gp.tileSize][topy/gp.tileSize];
                tile2 = gp.gen.world[(rightx + entity.speed)/gp.tileSize][boty/gp.tileSize];
                break; */
        }
        if (tile1 != null && tile2 != null) {
            if (tile1.barrier || tile2.barrier) {
                return true;
            }
        }

        return false;
    }
}
