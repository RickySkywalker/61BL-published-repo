package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import org.junit.Test;

import static org.junit.Assert.*;

public class Player_RickyTest {

    @Test
    public void test1(){
        TETile[][] world = CreateWorld_Ricky.createNewWorld("n455857754086099036s");
        TERenderer ter = new TERenderer();
        ter.initialize(100, 50);

        ter.renderFrame(world);
    }
}