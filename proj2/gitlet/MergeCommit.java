package gitlet;

import java.util.Date;
import java.util.LinkedList;

public class MergeCommit extends Commit{

    private String message;
    private LinkedList<String> filePointer;
    private String firstParent;
    private String lastParent;
    private Date timeStamp;

    public MergeCommit(String givenCommitSha1, String currentCommitSha1, LinkedList<String> filePointer, String message){
        this.message = message;
        this.firstParent = currentCommitSha1;
        this.lastParent = givenCommitSha1;
        this.filePointer = filePointer;
        this.timeStamp = new Date();
    }

    @Override
    public void printLog(){
        String fileName = this.getCommitID();
        String firstParentHead = this.firstParent.substring(0, 8);
        String lastParentHead = this.lastParent.substring(0, 8);

        System.out.println("===");
        System.out.println("commit " + fileName);
        System.out.println("Merge: " + firstParentHead + " " + lastParentHead);
        System.out.println("Date: " + this.getDateInCertainFormat());
        System.out.println(message);
        System.out.println("");
    }
}
