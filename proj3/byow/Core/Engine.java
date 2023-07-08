package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;



import java.io.File;

import java.awt.*;
import java.util.Locale;


public class Engine {

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int setUpFrameWidth = 40 * 16;
    public static final int setUpFrameHeight = 50 * 16;


    //Instance variables from Vincent
    public static final int MAX_ATTEMPTS = 100;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

        String input = setUpSpace();
        char[] inputArray = input.toCharArray();
        int indexOfN = input.indexOf("n");
        char worldStatus = 'r';
        for (int i = 0; i < indexOfN; i++){
            char curr = inputArray[i];
            if (curr == 'r' || curr == 'v'){
                worldStatus = curr;
            }
        }

        TETile[][] world;

        if (worldStatus == 'r') {
            world = CreateWorld_Ricky.createNewWorld(input);
        }else{
            world = anotherInteractWithInputString(input);
        }


        TERenderer ter = new TERenderer();
        ter.initialize(world.length, world[0].length);

        Player_Ricky player = generatePlayer(world);
        world = putPlayer(world, player);

        //restore prevGameStatus;
        if (input.indexOf('s')<input.length()-1) {
            int startIndex = input.indexOf('s') +1;
            for (int i = startIndex; i < input.length(); i++) {
                char curr = input.charAt(i);
                if (curr == 'w'){
                    world = player.up(world);
                }else if (curr == 'a'){
                    world = player.left(world);
                }else if (curr == 's'){
                    world = player.down(world);
                }else if (curr == 'd') {
                    world = player.right(world);
                }
            }
        }
        ter.renderFrame(world);


        boolean statusOfQuit = false;

        while(true){

            if (StdDraw.isMousePressed()) {
                int mouseX = (int) StdDraw.mouseX();
                int mouseY = (int)StdDraw.mouseY();

                displayInfo(mouseX,mouseY,world);
            }



            if (StdDraw.hasNextKeyTyped()){
                char curr = StdDraw.nextKeyTyped();
                System.out.println(curr);
                input += curr;

                if(!statusOfQuit) {
                    if (curr == 'w') {
                        world = player.up(world);
                    }
                    else if (curr == 'a') {
                        world = player.left(world);
                    }
                    else if (curr == 's') {
                        world = player.down(world);
                    }
                    else if (curr == 'd') {
                        world = player.right(world);
                    }
                    else if (curr == ':' || curr == ';') {
                        statusOfQuit = true;
                    }
                }

                if (statusOfQuit && curr == 'q'){
                    File save = new File("./save.txt");
                    Utils.writeContents(save, input);
                    System.exit(0);
                    return;
                }
                ter.renderFrame(world);
            }
        }
    }


    public static Player_Ricky generatePlayer(TETile[][] world){
        for (int x = 0; x < world.length; x++){
            for (int y = 0; y < world[0].length; y++){
                if (world[x][y].equals(Tileset.FLOOR)){
                    return new Player_Ricky(x, y);
                }
            }
        }
        System.out.println("Error");
        return null;
    }

    public static TETile[][] putPlayer(TETile[][] world, Player_Ricky player){
        world[player.x][player.y] = player.playerShape;
        return world;
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {

        input = input.toLowerCase(Locale.ROOT);

        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.


        return InteractInput_Ricky.interactInput(input);
    }


    public static TETile[][] anotherInteractWithInput(String input){
        Engine engine = new Engine();
        return engine.interactWithInputString(input);
    }

    public TETile[][] anotherInteractWithInputString(String input){

        WorldGuo w = new WorldGuo(getSeed(input));
        int attempt = 0;
        while(attempt < MAX_ATTEMPTS){
            RoomGuo newRoom = w.createRoom();
            if (newRoom.isValid(w.world)) {
                if (attempt == 0) {
                    w.addRoom(newRoom);
                }else{
                    TETile[][] current = w.world;
                    w.connectRoom(newRoom);
                    w.addRoom(newRoom);
                }
            }
            attempt++;
        }
        w.addWalls();
        w.world = CreateWorld_Ricky.cleanExceedWall(w.world);
        return w.world;
    }


    public static String setUpSpace(){
        
        StdDraw.setCanvasSize(setUpFrameWidth, setUpFrameHeight);

        StdDraw.setXscale(0, setUpFrameWidth);
        StdDraw.setYscale(0, setUpFrameHeight);

        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        Font fontBig = new Font("Monaco", Font.BOLD, 40);
        Font fontMini = new Font("Monaco", Font.BOLD, 10);
        StdDraw.setFont(fontBig);
        String title = "CS61BL: THE GAME", newGame = "New Game (N)", loadGame = "Load Game (L)", quit = "Quit(Q)",
                chooseWorld = "Choose World (R: normal world; V: square world)",
                replay = "Replay(P)";
        StdDraw.text(setUpFrameWidth/2, (setUpFrameHeight/6) * 5, title);
        Font fontSmall = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 20, loadGame);
        StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 40, newGame);
        StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2, chooseWorld);
        StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 - 20, quit);
        StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2-40, replay);
        StdDraw.setFont(fontMini);
        StdDraw.text(setUpFrameWidth/2, 5, "World status: normal");
        StdDraw.setFont(fontSmall);
        StdDraw.show();

        String input = null;
        int indexOfN = 0;
        char worldStatus = 'r';

        while(true){
            if (StdDraw.hasNextKeyTyped()){
                char curr = StdDraw.nextKeyTyped();
                System.out.println(curr);
                if (input == null){
                    input = Character.toString(curr);
                }else{
                    input += curr;
                }


                if (curr == 'q'){
                    return null;
                }else if (curr == 'n'){
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 -55, "Enter Seed");
                    indexOfN = input.length()-1;
                    StdDraw.show();
                }else if (curr == 's') {
                    return input;
                }
                else if (curr == 'l'){
                    File target = new File("./save.txt");
                    String temp = Utils.readContentsAsString(target);
                    String ans = "";
                    for (int i = 0; i < temp.length(); i++) {
                        if (temp.charAt(i)!=':' && temp.charAt(i) != ';' && temp.charAt(i)!='q') {
                            ans+=temp.charAt(i);
                        }
                    }
                    return ans;
                }else if(curr == 'p'){
                    replay();
                }else if (curr != 'r' && curr != 'v'){
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(fontBig);
                    StdDraw.text(setUpFrameWidth/2, (setUpFrameHeight/6) * 5, title);
                    StdDraw.setFont(fontSmall);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 20, loadGame);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 40, newGame);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2, chooseWorld);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 - 20, quit);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 -55, "Enter Seed");
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 -75, input.substring(indexOfN + 1));
                }else{
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(fontBig);
                    StdDraw.text(setUpFrameWidth/2, (setUpFrameHeight/6) * 5, title);
                    StdDraw.setFont(fontSmall);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 20, loadGame);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 40, newGame);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2, chooseWorld);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 - 20, quit);

                    worldStatus = curr;
                    StdDraw.setFont(fontMini);
                    if (worldStatus == 'r'){
                        StdDraw.text(setUpFrameWidth/2, 5, "World status: normal");
                    }else if (worldStatus == 'v'){
                        StdDraw.text(setUpFrameWidth/2, 5, "World status: square");
                    }
                    StdDraw.setFont(fontSmall);
                }

                StdDraw.show();
            }
        }

        /*
        while(true){
            if (StdDraw.hasNextKeyTyped()){
                char curr = StdDraw.nextKeyTyped();
                System.out.println(curr);
                if (curr == 'q'){
                    return null;
                }else if (curr == 'n'){
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 -45, "Enter Seed");
                    input = "n";
                    StdDraw.show();
                }else if (curr == 's'){
                    input += "s";
                    return input;
                }else{
                    input += curr;
                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(fontBig);
                    StdDraw.text(setUpFrameWidth/2, (setUpFrameHeight/6) * 5, title);
                    StdDraw.setFont(fontSmall);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2, loadGame);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 + 25, newGame);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 - 25, quit);
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 -45, "Enter Seed");
                    StdDraw.text(setUpFrameWidth/2, setUpFrameHeight/2 -65, input.substring(1));
                }
            }
        }*/
    }



    public long getSeed(String input){
        return Long.parseLong(input.substring(input.indexOf('n')+1,input.indexOf('s') - 1));
    }



    public static void main(String[] args) {
        Engine engine = new Engine();
        TERenderer world = new TERenderer();
        world.initialize(WIDTH,HEIGHT);
        TETile[][] finalWorldFrame = engine.anotherInteractWithInputString("n110s");
        world.renderFrame(finalWorldFrame);

    }

    public static boolean checkRange(TETile[][] world, int x, int y){
        return (x >= 0 && x < world.length && y >= 0 && y < world[0].length);
    }


    private void displayInfo(int mouseX, int mouseY, TETile[][] world){
        StdDraw.clear(Color.BLACK);
        ter.renderFrame(world);
        if (world[mouseX][mouseY].equals(Tileset.WALL)) {
            StdDraw.setPenColor(Color.white);
            StdDraw.text(5,HEIGHT+18,"Locked Door");
            StdDraw.show();
        }else if (world[mouseX][mouseY].equals(Tileset.FLOOR)){
            StdDraw.setPenColor(Color.white);
            StdDraw.text(5,HEIGHT+18,"Floor");
            StdDraw.show();
        }else{
            StdDraw.setPenColor(Color.white);
            StdDraw.text(5,HEIGHT+18,"Unused Space");
            StdDraw.show();

        }

    }


    private static void replay(){
        File save = new File("./save.txt");
        String input = Utils.readContentsAsString(save);

        char[] inputArray = input.toCharArray();
        int indexOfN = input.indexOf("n");
        char worldStatus = 'r';
        for (int i = 0; i < indexOfN; i++){
            char curr = inputArray[i];
            if (curr == 'r' || curr == 'v'){
                worldStatus = curr;
            }
        }

        TETile[][] world;

        if (worldStatus == 'r') {
            world = CreateWorld_Ricky.createNewWorld(input);
        }else{
            world = anotherInteractWithInput(input);
        }


        TERenderer ter = new TERenderer();
        ter.initialize(world.length, world[0].length);

        Player_Ricky player = generatePlayer(world);
        world = putPlayer(world, player);

        //restore prevGameStatus;
        if (input.indexOf('s')<input.length()-1) {
            int startIndex = input.indexOf('s') +1;
            for (int i = startIndex; i < input.length(); i++) {
                char curr = input.charAt(i);
                if (curr == 'w'){
                    world = player.up(world);
                }else if (curr == 'a'){
                    world = player.left(world);
                }else if (curr == 's'){
                    world = player.down(world);
                }else if (curr == 'd') {
                    world = player.right(world);
                }
                ter.renderFrame(world);
                StdDraw.pause(100);
            }


        }


    }






}
