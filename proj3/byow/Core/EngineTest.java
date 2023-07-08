package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import static org.junit.Assert.*;

public class EngineTest {

    @Test
    public void test1(){
        TETile[][] world = CreateWorld_Ricky.createNewWorld("n1415926s");
        TERenderer ter = new TERenderer();
        ter.initialize(CreateWorld_Ricky.LENGTH, CreateWorld_Ricky.HEIGHT);
        Player_Ricky player = Engine.generatePlayer(world);
        world = Engine.putPlayer(world, player);
        ter.renderFrame(world);
        player.printCoordinate();
        StdDraw.pause(1000);

        world = player.up(world);
        ter.renderFrame(world);
        player.printCoordinate();
        StdDraw.pause(500);
        world = player.up(world);
        ter.renderFrame(world);
        player.printCoordinate();
        StdDraw.pause(500);
        world = player.up(world);
        ter.renderFrame(world);
        player.printCoordinate();
        StdDraw.pause(100);


    }

    @Test
    public void test2(){
        Engine engine = new Engine();
        TETile[][] world = engine.interactWithInputString("n1415926sdddddddddddddddddddssss:q");
        world = engine.interactWithInputString("lddddddddddwddssssddddddddddsss:q");
        TERenderer ter = new TERenderer();
        ter.initialize(world.length, world[0].length);

        ter.renderFrame(world);
        StdDraw.pause(5000000);
    }

}