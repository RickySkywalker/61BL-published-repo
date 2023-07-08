package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.LinkedList;
import java.util.Random;

public class CreateWorld_Ricky {
    public static final int LENGTH = 100;
    public static final int HEIGHT = 50;
    public static int steps = 300;


    public static TETile[][] createNewWorld(String input){

        /**First attempt, trying to create the world in the way of a circle*/
        long root = getRoot(input);
        Random RANDOM = new Random(root);

        //Initial the world
        TETile[][] world = new TETile[LENGTH][HEIGHT];
        for (int x = 0; x < LENGTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                world[x][y] = Tileset.WALL;
            }
        }
        //Marks the point where the current pen is, wherever the pen goes, it will draw wall
        int[] penCor = new int[] {3, 3};
        Pen_Ricky pen = new Pen_Ricky(penCor[0], penCor[1], Tileset.FLOOR);          //Initialize the pen

        //used to judge the factor of relative x and y position, if the xFactor is close to 1, then it has large chance to go left, if is close to 0, then it
        //has large chance to go right
        double xFactor = ((double) penCor[0])/((double) LENGTH), yFactor = ((double) penCor[1])/(double) HEIGHT;



        int decisionFactorLD = Math.abs(RANDOM.nextInt())%3;
        if (decisionFactorLD <= 1) {
            int squareLength = 7 + Math.abs(RANDOM.nextInt())%20;
            int squareHeight = 5 + Math.abs(RANDOM.nextInt())%15;
            world = pen.square(squareLength, squareHeight, world);

            for (int i = 0; i < steps; i++) {
                int decisionFactor = Math.abs(RANDOM.nextInt()) % 2;
                double subDecisionFactor = (Math.abs(RANDOM.nextInt()) % 100) / 100.0;
                int length = Math.abs(RANDOM.nextInt()) % 5;
                if (decisionFactor == 1) {               //when factor is 1, the pen will go in right/left direction
                    if (subDecisionFactor < xFactor) {
                        world = pen.left(length, world);
                    } else {
                        world = pen.right(length, world);
                    }
                } else {                                  //when factor is 0, the pen will go in up/down direction
                    if (subDecisionFactor < yFactor) {
                        world = pen.down(length, world);
                    } else {
                        world = pen.up(length, world);
                    }
                }
                xFactor = ((double) pen.x) / LENGTH;
                yFactor = ((double) pen.y) / HEIGHT;
            }
        }



        pen.x = LENGTH-3;
        pen.y = HEIGHT-3;
        int decisionFactorRU = Math.abs(RANDOM.nextInt())%3;

        if (decisionFactorRU <= 1) {

            int squareLength = 7 + Math.abs(RANDOM.nextInt())%20;
            int squareHeight = 5 + Math.abs(RANDOM.nextInt())%15;
            world = pen.square(squareLength, squareHeight, world);

            for (int i = 0; i < steps; i++) {
                int decisionFactor = Math.abs(RANDOM.nextInt()) % 2;
                double subDecisionFactor = (Math.abs(RANDOM.nextInt()) % 100) / 100.0;
                int length = Math.abs(RANDOM.nextInt()) % 5;
                if (decisionFactor == 1) {               //when factor is 1, the pen will go in right/left direction
                    if (subDecisionFactor < xFactor) {
                        world = pen.left(length, world);
                    } else {
                        world = pen.right(length, world);
                    }
                } else {                                  //when factor is 0, the pen will go in up/down direction
                    if (subDecisionFactor < yFactor) {
                        world = pen.down(length, world);
                    } else {
                        world = pen.up(length, world);
                    }
                }
                xFactor = ((double) pen.x) / LENGTH;
                yFactor = ((double) pen.y) / HEIGHT;
            }
        }


        pen.x = 3;
        pen.y = HEIGHT-3;
        int decisionFactorLU = Math.abs(RANDOM.nextInt())%3;


        if (decisionFactorLU <= 1) {

            int squareLength = 7 + Math.abs(RANDOM.nextInt())%20;
            int squareHeight = 5 + Math.abs(RANDOM.nextInt())%15;
            world = pen.square(squareLength, squareHeight, world);

            for (int i = 0; i < steps; i++) {
                int decisionFactor = Math.abs(RANDOM.nextInt()) % 2;
                double subDecisionFactor = (Math.abs(RANDOM.nextInt()) % 100) / 100.0;
                int length = Math.abs(RANDOM.nextInt()) % 5;
                if (decisionFactor == 1) {               //when factor is 1, the pen will go in right/left direction
                    if (subDecisionFactor < xFactor) {
                        world = pen.left(length, world);
                    } else {
                        world = pen.right(length, world);
                    }
                } else {                                  //when factor is 0, the pen will go in up/down direction
                    if (subDecisionFactor < yFactor) {
                        world = pen.down(length, world);
                    } else {
                        world = pen.up(length, world);
                    }
                }
                xFactor = ((double) pen.x) / LENGTH;
                yFactor = ((double) pen.y) / HEIGHT;

            }
        }



        pen.x = LENGTH-3;
        pen.y = 3;
        int decisionFactorRD = Math.abs(RANDOM.nextInt())%3;

        if (decisionFactorRD <= 1 ||
                (decisionFactorLD > 1 && decisionFactorLU > 1 && decisionFactorRU > 1)) {

            int squareLength = 7 + Math.abs(RANDOM.nextInt())%20;
            int squareHeight = 5 + Math.abs(RANDOM.nextInt())%15;
            world = pen.square(squareLength, squareHeight, world);

            for (int i = 0; i < steps; i++) {
                int decisionFactor = Math.abs(RANDOM.nextInt()) % 2;
                double subDecisionFactor = (Math.abs(RANDOM.nextInt()) % 100) / 100.0;
                int length = Math.abs(RANDOM.nextInt()) % 5;
                if (decisionFactor == 1) {               //when factor is 1, the pen will go in right/left direction
                    if (subDecisionFactor < xFactor) {
                        world = pen.left(length, world);
                    } else {
                        world = pen.right(length, world);
                    }
                } else {                                  //when factor is 0, the pen will go in up/down direction
                    if (subDecisionFactor < yFactor) {
                        world = pen.down(length, world);
                    } else {
                        world = pen.up(length, world);
                    }
                }
                xFactor = ((double) pen.x) / LENGTH;
                yFactor = ((double) pen.y) / HEIGHT;
            }
        }

        System.out.println("Finished");
        world = cleanExceedWall(world);


        for (int y = 0; y < world[0].length; y++){
            System.out.println(world[0][y].character());
            for (int x = 1; x < world.length; x++){
                System.out.print(world[x][y].character() + " ");
            }
        }


        return world;
    }



    public static long getRoot(String input){

        char[] inputArray = input.toCharArray();
        String root;
        boolean countStatus = false;
        int indexOfN = 0;

        for (int i = 0; i < inputArray.length; i++){
            if (inputArray[i] == 'n'){
                indexOfN = i;
                break;
            }
        }

        root = "";

        char[] numbers = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        LinkedList<Character> numbersList = new LinkedList<>();
        for (char curr : numbers){
            numbersList.addLast(curr);
        }

        for (int i = indexOfN+1; i < inputArray.length; i++){
            char curr = inputArray[i];
            if (numbersList.contains(curr)){
                root += Character.toString(curr);
            }else{
                break;
            }
        }
        /*
        String root = Character.toString(inputArray[1]);
        for (int i = 2; i < inputArray.length; i++){
            char curr = inputArray[i];
            if(curr != 's'){
                root += curr;
            }else{
                break;
            }
        }*/

        if (!root.equals("")) {
            return Long.parseLong(root);
        }else{
            return 1415926;
        }
    }


    public static TETile[][] cleanExceedWall(TETile[][] world){

        for (int x = 0; x < world.length; x++){
            for (int y = 0; y < world[0].length; y++){
                if (world[x][y].equals(Tileset.WALL)){
                    boolean status = true;
                    for(int x1 = -1; x1 <= 1; x1++){
                        for (int y1 = -1; y1 <= 1; y1++){
                            if(x + x1 >=0 && x + x1 < world.length && y + y1 >= 0 && y + y1 < world[0].length){
                                if (world[x+x1][y+y1].equals(Tileset.FLOOR)){
                                    status = false;
                                }
                            }
                        }
                    }
                    if (status){
                        world[x][y] = Tileset.NOTHING;
                    }
                }
            }
        }
        return world;
    }
}
