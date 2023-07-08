package byow.Core;

import java.util.*;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

class WorldGuo {
        

    public static final int WIDTH = Engine.WIDTH;
    public static final int HEIGHT = Engine.HEIGHT;
    public static final int MAXROOM_HEIGHT = 8;
    public static final int MAXROOM_WIDTH = 8;

    private int size = 0;
    public TETile[][]world;
    private Random r;
    public LinkedList<RoomGuo> rooms;

    public WorldGuo(long seed){
        world = new TETile[WIDTH][HEIGHT];
        r = new Random(seed);
        rooms = new LinkedList<>();
        addEmptySpace();
    }

    public void addRoom(RoomGuo room){
        rooms.add(room);
        putRoomToWorld(room);
    }

    private void putRoomToWorld(RoomGuo room){
        for (int i = room.bottomLeft.x; i < room.bottomRight.x + 1; i++) {
            for (int j = room.bottomLeft.y; j <room.topLeft.y + 1 ; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
     }

    public void connectRoom(RoomGuo newRoom){
        RoomGuo latestRoom = rooms.getLast();
        int xDisplacement = latestRoom.getXDisplacement(newRoom);
        int yDispacement = latestRoom.getYDisplacement(newRoom);
        if (xDisplacement >= 0) { //Right
            for (int i = latestRoom.getMiddle().x; i < newRoom.getMiddle().x; i++) {
                world[i][latestRoom.getMiddle().y] = Tileset.FLOOR;
            }
        }else{ //left
            for (int i = latestRoom.getMiddle().x; i >= newRoom.getMiddle().x; i--) {
                world[i][latestRoom.getMiddle().y] = Tileset.FLOOR;
            }
        }
        if (yDispacement>=0) { //up
            for (int j = latestRoom.getMiddle().y; j < newRoom.getMiddle().y; j++) {
                world[latestRoom.getMiddle().x][j] = Tileset.FLOOR;
            }
        }else{ //down
            for (int j = latestRoom.getMiddle().y; j >= newRoom.getMiddle().y; j--) {
                world[latestRoom.getMiddle().x][j] = Tileset.FLOOR;
            }
        }

    }


    public RoomGuo createRoom(){
        int width = RandomUtils.uniform(r,3,MAXROOM_WIDTH + 1);
        int height = RandomUtils.uniform(r,3,MAXROOM_HEIGHT + 1);
        int x = RandomUtils.uniform(r,1,WIDTH-1);
        int y= RandomUtils.uniform(r,1,HEIGHT-1);
        PointGuo bottomLeft = new PointGuo(x,y);
        RoomGuo room = new RoomGuo(width ,height,bottomLeft);
        return room;
    }

    public void addWalls(){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (world[i][j].equals(Tileset.NOTHING)) {
                    world[i][j] = Tileset.WALL;

                }
            }
        }
    }
    public void addEmptySpace() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (world[i][j] == null) {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }
}


