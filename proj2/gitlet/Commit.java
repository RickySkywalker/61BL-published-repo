package gitlet;

// TODO: any imports you need here
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static gitlet.AAA.*;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *  Things in the class: 1. message (String) the commit message;
 *                       2. timeStamp (Date) the time at which the commit is made;
 *                       3. filePointer: (LinkedList<String>) SHA-1 title of the add Blobs of the files
 *                       4. parent: (String) SHA-1 title for the last commit;
 *
 *  @author Ricky
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private LinkedList<String> filePointer;
    private String parent;
    private Date timeStamp;

    /* TODO: fill in the rest of this class. */
    public Commit(){
        this.message = "initial commit";
        this.filePointer = new LinkedList<String>();
        this.timeStamp = new Date();
        this.parent = null;
    }


    @SuppressWarnings("unchecked")
    public Commit(String message){

        this.message = message;
        //in commit the file pointer simply store the list of SHA1 code of the Blobs
        this.filePointer = Utils.readObject(stageForAddition, LinkedList.class);
        this.timeStamp = new Date();
        this.parent = Utils.readObject(HEADPointer, Branch.class).getPointer();
    }

    public String getMessage(){
        return this.message;
    }

    public LinkedList<String> getFilePointer(){
        return filePointer;
    }

    public String getParent(){
        return parent;
    }

    public String getDate(){
        return timeStamp.toString();
    }


    public static Commit getCommit(Branch thisBranchHead){
        String branchPointed = thisBranchHead.getPointer();
        File commitOfBranch = Utils.join(commits, branchPointed);
        Commit thingsToReturn = Utils.readObject(commitOfBranch, Commit.class);
        return thingsToReturn;
    }

    public boolean checkWhetherTheSame(String titleOfBlob){
        if (filePointer != null) {
            for (String i : filePointer) {
                if (i.equals(titleOfBlob)) {
                    return true;
                }
            }
        }else{
            return false;
        }
        return false;
    }


    public Commit previousCommit(){
        File previous = Utils.join(commits, parent);
        Commit previousCommit = Utils.readObject(previous, Commit.class);
        return previousCommit;
    }

    public String getCommitID(){
        if (this.getFilePointer() != null) {
            return Utils.sha1(this.getDate() + this.getFilePointer().toString() + this.getMessage());
        }else {
            return  Utils.sha1(this.getDate() + this.getMessage());
        }
    }

    public String getDateInCertainFormat(){
        SimpleDateFormat f = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy -0800");
        return f.format(timeStamp);
    }

    //This helper method will check whether the file in "this" commit, it will only check the name of the file,but not check the content
    //and write it in the stage for removal
    @SuppressWarnings("unchecked")
    public boolean whetherFileInCommit(String fileName){
        LinkedList<String> thisCommitPointer = filePointer;
        stagedForRemoval = Utils.readObject(stageForRemoval, LinkedList.class);
        for (String SHA1NameOfBlobs : thisCommitPointer){
            Blob currentBlob = Blob.getBlobFromSHA1(SHA1NameOfBlobs);
            if (currentBlob.getFilename().equals(fileName)){
                stagedForRemoval.addLast(currentBlob.getSHA1OfBlob());
                Utils.writeObject(stageForRemoval, stagedForRemoval);
                return true;
            }
        }
        return false;
    }


    //return true if this commit has tracked that file of given filename
    public boolean anotherWhetherFileInCommit(String fileName){
        LinkedList<String> thisCommitPointer = this.filePointer;
        for (String SHA1NameOfBlobs : thisCommitPointer){
            Blob currentBlob = Blob.getBlobFromSHA1(SHA1NameOfBlobs);
            if (currentBlob.getFilename().equals(fileName)){
                return true;
            }
        }
        return false;
    }


    public void printLog(){
        String filename = this.getCommitID();
        System.out.println("===");
        System.out.println("commit " + filename);
        System.out.println("Date: " + this.getDateInCertainFormat());
        System.out.println(this.getMessage());
        System.out.println("");
    }

}
