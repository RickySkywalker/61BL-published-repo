package gitlet;

import java.io.Serializable;
/** What branch store is the whole title of the commit title*/


public class Branch implements Serializable {
    private String pointer;

    public Branch(String title){
        this.pointer = title;
    }

    public void changePointer(String newPoint){
        this.pointer = newPoint;
    }

    public String getPointer(){
        return this.pointer;
    }

}
