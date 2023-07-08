package byow.Core;

import byow.TileEngine.TETile;
import org.junit.Test;



import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

import static org.junit.Assert.*;

public class CreateWorld_RickyTest {

    @Test
    public void test1(){
        CreateWorld_Ricky.createNewWorld("n5197880843569031643s");
    }


    @Test
    public void test2(){
        TETile[][] world = CreateWorld_Ricky.createNewWorld("n455857754086099036s");
        /*TERenderer ter = new TERenderer();
        ter.initialize(100, 50);
        ter.renderFrame(world);
        StdDraw.pause(50000);
         */
    }

}