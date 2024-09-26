package World;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
    public class WorldGenerator {
        File source = new File("C:\\Users\\blake\\PERSONAL PROJECTS\\CleanFPS\\src\\source.txt");
        public int tileSize;
        public int tilesWide;
        int tilesTall;
        public Tile[][] world;
        public WorldGenerator(int ts, int tw, int tt) {
            tileSize = ts;
            tilesWide = tw;
            tilesTall = tt;
            world = fromSource(tw, tt);
        }
        public Tile[][] fromSource(int w, int h) {
            Tile[][] out = new Tile[w][h];

            //create blank world
            for (int x = 0; x < w; x++){
                for (int y = 0; y < h; y++) {
                    out[x][y] = new Tile(0);
                }
            }
            //create scanner
            Scanner myReader;
            try {
                myReader = new Scanner(source);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            //generate world from source file
            int rown = 0;
            while (myReader.hasNextLine() && rown < h) {
                String row = myReader.nextLine();
                if (row.length() > w) {
                    System.out.println("ERROR!!!! row is too long for world");
                }
                for (int x = 0; x < row.length(); x++) {
                    Tile get = new Tile(Character.getNumericValue(row.charAt(x)));
                    out[x][rown] = get;
                }
                rown++;
            }
            myReader.close();
            return out;
        }

        public void drawWorld(Graphics2D g2) {
            for (int x = 0; x < tilesWide; x++) {
                for (int y = 0; y <tilesTall; y++) {
                    g2.setColor(world[x][y].color);
                    g2.fillRect(x*tileSize,y*tileSize,tileSize-1,tileSize-1);
                }
            }
        }
    }
