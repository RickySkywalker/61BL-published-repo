package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player_Ricky {
    public int x;
    public int y;
    public TETile playerShape;


    public Player_Ricky(int x, int y){
        this.x = x;
        this.y = y;
        this.playerShape = Tileset.AVATAR;
    }


    public Player_Ricky(int x, int y, TETile shape){
        this.x = x;
        this.y = y;
        this.playerShape = shape;
    }

    public Player_Ricky putPlayer(TETile[][] world){
        if(Engine.checkRange(world, x, y)){
            if(!checkWall(world, x, y)){
                for (int x = 0; x < world.length; x++){
                    for (int y = 0; y < world[0].length; y++){
                        if (world[x][y].equals(Tileset.FLOOR)){
                            Player_Ricky player = new Player_Ricky(x, y);
                            return player;
                        }
                    }
                }
                world[x][y] = playerShape;
            }else{
                System.out.println("Given location is a wall");
            }
        }else{
            System.out.println("Out of range");
        }

        return null;
    }

    public TETile[][] up(TETile[][] world){
        if (Engine.checkRange(world, x, y+1)){
            if (!checkWall(world, x, y+1)){
                world[x][y] = Tileset.FLOOR;
                y++;
                world[x][y] = playerShape;
            }
        }
        return world;
    }

    public TETile[][] down(TETile[][] world){
        if (Engine.checkRange(world, x, y - 1)){
            if (!checkWall(world, x, y-1)){
                world[x][y] = Tileset.FLOOR;
                y--;
                world[x][y] = playerShape;
            }
        }

        return world;
    }

    public TETile[][] left(TETile[][] world){
        if (Engine.checkRange(world, x - 1, y)){
            if (!checkWall(world, x-1, y)){
                world[x][y] = Tileset.FLOOR;
                x--;
                world[x][y] = playerShape;
            }
        }
        return world;
    }

    public TETile[][] right(TETile[][] world){
        if (Engine.checkRange(world, x + 1, y)){
            if (!checkWall(world, x + 1, y)){
                world[x][y] = Tileset.FLOOR;
                x++;
                world[x][y] = playerShape;
            }
        }

        return world;
    }


    public void printCoordinate(){
        System.out.println("(" + x + ", " + y + ")");
    }

    public static boolean checkWall(TETile[][] world, int x, int y){
        return world[x][y].equals(Tileset.WALL);
    }


    
}
