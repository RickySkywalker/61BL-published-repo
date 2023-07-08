package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;
    private static LinkedList<TETile> lsOfTiles = new LinkedList<>();

    private static final Random RANDOM = new Random(1415926);


    public static TETile[][] drawPlus(int size, int beginX, int beginY, TETile[][] world){

        boolean status = true;

        int thisTileIndex = RANDOM.nextInt(7);
        TETile thisTile = lsOfTiles.get(thisTileIndex);

        if (size%2 == 1){
            for (int x = 0; x < 3 * size; x++){
                for (int y = 0; y < size; y++){
                    int x0 = beginX - size/2 - size;
                    int y0 = beginY - size/2;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        if (!world[x0 + x][y0 + y].equals(Tileset.NOTHING)) {
                            status = false;
                        }
                    }
                }
            }

            for (int y = 0; y < 3 * size; y++){
                for (int x = 0; x < size; x++){
                    int x0 = beginX - size/2;
                    int y0 = beginY - size - size/2;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        if (!world[x0 + x][y0 + y].equals(Tileset.NOTHING)) {
                            status = false;
                        }
                    }
                }
            }
        }else{
            for (int x = 0; x < 3 * size; x++){
                for (int y = 0; y < size; y++){
                    int x0 = beginX - size/2 - size + 1;
                    int y0 = beginY - size/2 + 1;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        if (!world[x0 + x][y0 + y].equals(Tileset.NOTHING)) {
                            status = false;
                        }
                    }
                }
            }

            for (int y = 0; y < 3 * size; y++){
                for (int x = 0; x < size; x++){
                    int x0 = beginX - size/2 + 1;
                    int y0 = beginY - size - size/2 + 1;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        if (!world[x0 + x][y0 + y].equals(Tileset.NOTHING)) {
                            status = false;
                        }
                    }
                }
            }
        }

        if (!status){
            return world;
        }


        if (size%2 == 1){
            for (int x = 0; x < 3 * size; x++){
                for (int y = 0; y < size; y++){
                    int x0 = beginX - size/2 - size;
                    int y0 = beginY - size/2;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        world[x0 + x][y0 + y] = thisTile;
                    }
                }
            }

            for (int y = 0; y < 3 * size; y++){
                for (int x = 0; x < size; x++){
                    int x0 = beginX - size/2;
                    int y0 = beginY - size - size/2;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        world[x0 + x][y0 + y] = thisTile;
                    }
                }
            }
        }else{
            for (int x = 0; x < 3 * size; x++){
                for (int y = 0; y < size; y++){
                    int x0 = beginX - size/2 - size + 1;
                    int y0 = beginY - size/2 + 1;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        world[x0 + x][y0 + y] = thisTile;
                    }
                }
            }

            for (int y = 0; y < 3 * size; y++){
                for (int x = 0; x < size; x++){
                    int x0 = beginX - size/2 + 1;
                    int y0 = beginY - size - size/2 + 1;
                    if (x0+x >= 0 && y0 + y >= 0 && x0 + x < 100 && y0 + y < 100) {
                        world[x0 + x][y0 + y] = thisTile;
                    }
                }
            }
        }
        return world;
    }

   public static void main(String[] args){

        lsOfTiles.addLast(Tileset.AVATAR);
        lsOfTiles.addLast(Tileset.GRASS);
        lsOfTiles.addLast(Tileset.WATER);
        lsOfTiles.addLast(Tileset.FLOWER);
        lsOfTiles.addLast(Tileset.SAND);
        lsOfTiles.addLast(Tileset.MOUNTAIN);
        lsOfTiles.addLast(Tileset.TREE);

        int lengthOfLs = lsOfTiles.size();

        Object[] a = new Object[8];

       TERenderer ter = new TERenderer();
       ter.initialize(WIDTH, HEIGHT);

       TETile[][] world = new TETile[WIDTH][HEIGHT];
       for (int x = 0; x < WIDTH; x += 1) {
           for (int y = 0; y < HEIGHT; y += 1) {
               world[x][y] = Tileset.NOTHING;
           }
       }

       for (int x = 0; x < WIDTH; x++){
           for (int y = 0; y < HEIGHT; y++){
               int factor = RANDOM.nextInt(3);
               if (factor == 0){
                   world = drawPlus(1, x, y, world);
               }
           }
       }

       ter.renderFrame(world);

   }
}
