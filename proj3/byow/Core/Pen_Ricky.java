package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Pen_Ricky {

    public int x;
    public int y;
    public TETile color;

    public Pen_Ricky(int initialX, int initialY, TETile color){
        this.x = initialX;
        this.y = initialY;
        this.color = color;
    }

    public TETile[][] left(int length, TETile[][] world){
        int LENGTH = world.length, HEIGHT = world[0].length;
        if (x - length >= 0 && x < LENGTH && y >= 0 && y < HEIGHT){
            for (int i = 0; i < length; i++){
                world[x - i][y] = color;
            }
            x -= (length);
        }
        return world;
    }

    public TETile[][] right(int length, TETile[][] world){
        int LENGTH = world.length, HEIGHT = world[0].length;
        if (x + length < LENGTH && x >= 0 && y >= 0 && y < HEIGHT){
            for (int i = 0; i < length; i++){
                world[x + i][y] = color;
            }
            x += (length);
        }
        return world;
    }

    public TETile[][] up(int length, TETile[][] world){
        int LENGTH = world.length, HEIGHT = world[0].length;
        if (y + length < HEIGHT && y >= 0 && x < LENGTH && x >= 0){
            for (int i = 0; i < length; i++){
                world[x][y+i] = color;
            }
            y += (length);
        }
        return world;
    }

    public TETile[][] down(int length, TETile[][] world){
        int LENGTH = world.length, HEIGHT = world[0].length;
        if (y - length >= 0 && y < HEIGHT && x >= 0 && x < LENGTH){
            for (int i = 0; i < length; i++){
                world[x][y-i] = color;
            }
            y -= (length);
        }
        return world;
    }


    //Draw a square with given length and height on the world. Normally the start pen point will be the bottom-left conor of the
    //square but if the square is close to the edge, try best to draw the square
    public TETile[][] square(int length, int height, TETile[][] world){
        int LENGTH = world.length, HEIGHT = world[0].length;
        if ( y + height < HEIGHT && x + length < LENGTH){
            for (int x = 0; x < length; x++){
                for (int y = 0; y < height; y++){
                    world[this.x + x][this.y + y] = color;
                }
            }
        }else if ( y - height >= 0 && x - length >= 0) {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    world[this.x - x][this.y - y] = color;
                }

            }
        }else if ( y + height < HEIGHT && x - length >= 0) {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    world[this.x - x][this.y + y] = color;
                }

            }
        }else if ( y - height >= 0 && x + length < HEIGHT) {
            for (int x = 0; x < length; x++) {
                for (int y = 0; y < height; y++) {
                    world[this.x + x][this.y - y] = color;
                }

            }
        }
        return world;
    }


    //change the pen position to the designed position and make a dot on the point with the current pen's "color"
    public TETile[][] dot(int x, int y, TETile[][] world){
        if (x >= 0 && x < world.length && y >= 0 && y < world[0].length) {
            this.x = x;
            this.y = y;
            world[x][y] = this.color;
        }
        return world;
    }

}
