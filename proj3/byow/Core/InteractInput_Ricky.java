package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.io.File;

public class InteractInput_Ricky {

    public static TETile[][] interactInput(String input){

        TETile[][] world;

        if (input.charAt(0) != 'l') {

            world = CreateWorld_Ricky.createNewWorld(input);

            boolean statusOfQuit = false;

            Player_Ricky player = Engine.generatePlayer(world);
            world = Engine.putPlayer(world, player);


            for (int i = input.indexOf('s') + 1; i < input.length(); i++) {
                char curr = input.charAt(i);
                if (!statusOfQuit) {
                    if (curr == 'w') {
                        world = player.up(world);
                    } else if (curr == 'a') {
                        world = player.left(world);
                    } else if (curr == 's') {
                        world = player.down(world);
                    } else if (curr == 'd') {
                        world = player.right(world);
                    } else if (curr == ':' || curr == ';') {
                        statusOfQuit = true;
                    }
                }

                if (statusOfQuit && curr == 'q') {
                    File save = new File("./save.txt");
                    Utils.writeContents(save, input);
                    return world;
                }
            }
        }else{
            File save = new File("./save.txt");
            String saveContents = Utils.readContentsAsString(save);

            String newContent = "";

            for (int i = 0; i < saveContents.length(); i++){
                char curr = saveContents.charAt(i);
                if (curr != ':' && curr != ';' && curr != 'q'){
                    newContent += curr;
                }
            }


            char[] newContentArray = newContent.toCharArray();
            int indexOfN = newContent.indexOf("n");
            world = CreateWorld_Ricky.createNewWorld(newContent);

            Player_Ricky player = Engine.generatePlayer(world);
            world = Engine.putPlayer(world, player);

            if (newContent.indexOf('s') < newContent.length() - 1){
                int startIndex = newContent.indexOf('s') + 1;
                for (int i = startIndex; i < newContent.length(); i++){
                    char curr = newContent.charAt(i);
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

            boolean statusOfQuit = false;

            for (int i = 1; i < input.length(); i++) {
                char curr = input.charAt(i);
                if (!statusOfQuit) {
                    if (curr == 'w') {
                        world = player.up(world);
                    } else if (curr == 'a') {
                        world = player.left(world);
                    } else if (curr == 's') {
                        world = player.down(world);
                    } else if (curr == 'd') {
                        world = player.right(world);
                    } else if (curr == ':' || curr == ';') {
                        statusOfQuit = true;
                    }
                }

                if (statusOfQuit && curr == 'q') {
                    Utils.writeContents(save, newContent + input);
                    return world;
                }
            }

        }
        return world;

        /*
        char[] inputArray = input.toCharArray();
        String refinedInput = "";

        for (char curr : inputArray){
            if (curr != ':' && curr != ';' && curr != 'q' ){
                refinedInput += curr;
            }
        }

        int indexOfN = refinedInput.indexOf('n');


        Player_Ricky player = Engine.generatePlayer(world);
        world = Engine.putPlayer(world, player);

        if (refinedInput.indexOf('s') < refinedInput.length() - 1){
            int startIndex = refinedInput.indexOf('s') + 1;
            for (int i = startIndex; i < refinedInput.length(); i++){
                char curr = refinedInput.charAt(i);
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
        }*/
    }
}
