package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import jdk.jshell.execution.Util;

public class AAA implements Serializable {
    public static File majorDir = new File("./.gitlet");
    public static File objects = new File("./.gitlet/objects");
    public static File commits = new File("./.gitlet/objects/commits");
    public static File branch = new File("./.gitlet/objects/branches");
    public static File Blobs = new File("./.gitlet/objects/blobs");
    public static File stageForAddition = new File("./.gitlet/stageForAddition");
    public static File stageForRemoval = new File("./.gitlet/stageForRemoval");
    public static LinkedList<String> stagedForAddition;
    public static LinkedList<String> stagedForRemoval;
    public static Branch HEAD;
    public static File HEADPointer = new File("./.gitlet/HEAD");
    public static String currentBranch;
    public static File currentBranchFile = new File("./.gitlet/currentBranch");


    //The init command
    public static void init(){
        if (!majorDir.isDirectory()) {
            //make all the required dirs and files

            majorDir.mkdir();
            objects.mkdir();
            commits.mkdir();
            branch.mkdir();
            Blobs.mkdir();

            //Start and record the first empty commit
            Commit firstCommit = new Commit();
            String head = firstCommit.getCommitID();
            String fileTitle = "./.gitlet/objects/commits/" + head;
            File commitOfInit = new File(fileTitle);
            Utils.writeObject(commitOfInit, firstCommit);

            //create the default branch (master) and the HEAD
            Branch master = new Branch(head);               //Stored in branch is the title of file
            currentBranch = "master";
            HEAD = new Branch(head);
            File masterBranch = new File("./.gitlet/objects/branches/master");
            Utils.writeObject(masterBranch, master);
            Utils.writeObject(HEADPointer, HEAD);


            currentBranch = "master";
            Utils.writeContents(currentBranchFile, currentBranch);

            stagedForAddition = new LinkedList<String>();
            stagedForRemoval = new LinkedList<String>();
            Utils.writeObject(stageForAddition, stagedForAddition);
            Utils.writeObject(stageForRemoval, stagedForRemoval);
        }else{
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }




    @SuppressWarnings("unchecked")
    public static void add(String fileName){
        File fileToAdd = new File("./" + fileName);

        if (fileToAdd.isFile()){
            String content = Utils.readContentsAsString(fileToAdd);
            Blob thisBlob = new Blob(content, fileName);
            String title = thisBlob.getSHA1OfBlob();                  //This title is made from the unique title of the Blob, if the Blob are same, title should be same
            //System.out.println(title);
            //System.out.println(thisBlob.content());
            //System.out.println(thisBlob.getFilename());
            HEAD = readHEAD();
            Commit currentCommit = Commit.getCommit(HEAD);
            boolean same = currentCommit.checkWhetherTheSame(title);

            if (!same) {
                if (stageForAddition.isFile()) {
                    stagedForAddition = Utils.readObject(stageForAddition, LinkedList.class);
                }

                if (stageForAddition.isFile() && stagedForAddition != null) {
                    stagedForAddition.addLast(title);
                    Utils.writeObject(stageForAddition, stagedForAddition);
                } else {
                    stagedForAddition = new LinkedList<>();
                    stagedForAddition.addLast(title);
                    Utils.writeObject(stageForAddition, stagedForAddition);
                }

                File blobToAdd = new File("./.gitlet/objects/blobs/" + title);
                Utils.writeObject(blobToAdd, thisBlob);
            }

            stagedForRemoval = Utils.readObject(stageForRemoval, LinkedList.class);
            for(String blobName : stagedForRemoval){
                Blob currentBlob = Blob.getBlobFromSHA1(blobName);
                if (currentBlob.getFilename().equals(fileName)){
                    stagedForRemoval.remove(blobName);
                }
            }
            Utils.writeObject(stageForRemoval, stagedForRemoval);
        }else{
            System.out.println("File does not exist.");
        }
    }


    @SuppressWarnings("unchecked")
    public static void rm(String fileName){
        File fileToRemove = Utils.join("./", fileName);
        HEAD = readHEAD();
        Commit currentCommit = Commit.getCommit(HEAD);
        stagedForAddition = Utils.readObject(stageForAddition, LinkedList.class);
        stagedForRemoval = Utils.readObject(stageForRemoval, LinkedList.class);

        boolean inAddition = isStaged(fileName);
        boolean inCommit = currentCommit.whetherFileInCommit(fileName);

        if (inCommit && fileToRemove.isFile()){
            Utils.restrictedDelete(fileToRemove);
        }

        if (!inAddition && !inCommit){
            System.out.println("No reason to remove the file.");
        }

    }


    public static void commit(String message){

        if (!isCommitEmpty()) {
            //Creat a new commit with commit message
            Commit thisCommit = new Commit(message);
            String commitTitle = thisCommit.getCommitID();
            File commitFile = Utils.join(commits, commitTitle);
            Utils.writeObject(commitFile, thisCommit);


            //Find current branch and current head
            String currentBranch = Utils.readContentsAsString(readCurrentBranch());
            String thisBranchTitle = "./.gitlet/objects/branches/" + currentBranch;
            File thisBranch = new File(thisBranchTitle);
            Branch thisBranchObj = Utils.readObject(thisBranch, Branch.class);


            //Update current head and current branch
            thisBranchObj.changePointer(commitTitle);
            HEAD = readHEAD();
            HEAD.changePointer(commitTitle);
            Utils.writeObject(HEADPointer, HEAD);
            Utils.writeObject(thisBranch, thisBranchObj);

            //Refresh current stageForAddition
            Utils.writeObject(stageForAddition, new LinkedList<String>());
            Utils.writeObject(stageForRemoval, new LinkedList<String>());
        }else {
            System.out.println("No changes added to the commit.");
        }

    }


    public static void log(){
        Commit current = readHEADCommit();

        while (current.getParent() != null){
            current.printLog();
            current = current.previousCommit();
        }

        current.printLog();
    }


    public static void globalLog(){
        List<String> commitList = Utils.plainFilenamesIn(commits);
        for (String title : commitList){
            File currentCommitFile = Utils.join(commits, title);
            Commit currentCommit = Utils.readObject(currentCommitFile, Commit.class);
            currentCommit.printLog();
        }
    }



    public static void find(String commitMessage){
        List<String> commitList = Utils.plainFilenamesIn(commits);
        boolean haveSuchCommit = false;
        for (String title: commitList){
            File currentCommitFile = Utils.join(commits, title);
            Commit currentCommit = Utils.readObject(currentCommitFile, Commit.class);
            if (currentCommit.getMessage().equals(commitMessage)){
                System.out.println(currentCommit.getCommitID());
                haveSuchCommit = true;
            }
        }
        if(!haveSuchCommit){
            System.out.println("Found no commit with that message.");
        }
    }

    public static void branch(String branchName){
        File branchFileToAdd = Utils.join(branch, branchName);
        HEAD = readHEAD();
        Commit currentCommit = Commit.getCommit(HEAD);
        String commitID = currentCommit.getCommitID();
        if (!branchFileToAdd.isFile()){
            Branch branchToAdd = new Branch(commitID);
            Utils.writeObject(branchFileToAdd, branchToAdd);
        }else{
            System.out.println("A branch with that name already exists.");
        }
    }

    public static void rmBranch(String branchName){
        File branchFileToRemove = Utils.join(branch, branchName);
        HEAD = readHEAD();
        if (branchFileToRemove.isFile()){
            Branch branchToRm = Utils.readObject(branchFileToRemove, Branch.class);
            String currentBranchTitle = Utils.readContentsAsString(currentBranchFile);
            if (!currentBranchTitle.equals(branchName)){
                /**WARNING: in here I changed the file of Utils in line 74-76*/
                Utils.restrictedDelete(branchFileToRemove);
            }else{
                System.out.println("Cannot remove the current branch.");
            }
        }else{
            System.out.println("A branch with that name does not exist.");
        }
    }

    public static void status(){
        if(majorDir.isDirectory()) {
            System.out.println("=== Branches ===");
            List<String> branchesList = Utils.plainFilenamesIn(branch);
            Collections.sort(branchesList);
            String currentBranchName = Utils.readContentsAsString(currentBranchFile);
            for (String current : branchesList) {
                if (currentBranchName.equals(current)) {
                    System.out.println("*" + current);
                } else {
                    System.out.println(current);
                }
            }
            System.out.println("");


            System.out.println("=== Staged Files ===");
            LinkedList<String> listForStagedFiles = readStagedForAdditionFiles();
            for (String current : listForStagedFiles) {
                System.out.println(current);
            }
            System.out.println("");


            System.out.println("=== Removed Files ===");
            LinkedList<String> listForRemovedFiles = readStagedForRemovalFiles();
            for (String current : listForRemovedFiles) {
                System.out.println(current);
            }
            System.out.println("");


            System.out.println("=== Modifications Not Staged For Commit ===");
            System.out.println("");
            System.out.println("=== Untracked Files ===");
            System.out.println("");
        }else{
            System.out.println("Not in an initialized Gitlet directory.");
        }
    }


    public static void checkoutMostRecent(String fileName){
        Commit currentCommit = readHEADCommit();
        LinkedList<String> listOfBLobs = currentCommit.getFilePointer();

        for(String SHA1Name : listOfBLobs){
            Blob current = Blob.getBlobFromSHA1(SHA1Name);
            if (current.getFilename().equals(fileName)){
                //Del current file in the CWD and replace it with the one stored in the Blob
                File checkoutFile = getFileFromName(fileName);
                if (Utils.restrictedDelete(checkoutFile)){
                    Utils.writeContents(checkoutFile, current.content());
                    return;
                }
            }
        }
        System.out.println("File does not exist in that commit.");
    }



    public static void checkoutFromGivenCommit(String commitID, String fileName){
        if (commitID.length() == 8){
            List<String> commitList = Utils.plainFilenamesIn(commits);
            for (String thisID : commitList){
                if (thisID.substring(0,8).equals(commitID)){
                    commitID = thisID;
                    break;
                }
            }
        }
        Commit current = readHEADCommit();
        String currentID = current.getCommitID();
        while (!currentID.equals(commitID) && current.getParent() != null){
            current = current.previousCommit();
            currentID = current.getCommitID();
        }

        if (currentID.equals(commitID)){
            LinkedList<String> listOfBlobs = current.getFilePointer();
            for (String SHA1Name : listOfBlobs){
                Blob currentBlob = Blob.getBlobFromSHA1(SHA1Name);
                if (currentBlob.getFilename().equals(fileName)){
                    File checkoutfile = getFileFromName(fileName);
                    if (Utils.restrictedDelete(fileName)){
                        Utils.writeContents(checkoutfile, currentBlob.content());
                        return;
                    }
                }
            }
            System.out.println("File does not exist in that commit.");
        }else{
            System.out.println("No commit with that id exists.");
        }

    }

    public static void checkoutBranch(String branchName){
        currentBranch = Utils.readContentsAsString(currentBranchFile);
        if (!currentBranch.equals(branchName)) {
            File givenBranchFile = Utils.join(branch, branchName);
            if (givenBranchFile.isFile()) {
                Branch givenBranch = Utils.readObject(givenBranchFile, Branch.class);
                String givenCommit = givenBranch.getPointer();
                if(checkoutFromGivenCommit(givenCommit)) {
                    HEAD = givenBranch;
                    currentBranch = branchName;
                    Utils.writeObject(HEADPointer, HEAD);
                    Utils.writeContents(currentBranchFile, currentBranch);
                }
            }else{
                System.out.println("No such branch exists.");
            }
        }else{
            System.out.println("No need to checkout the current branch.");
        }
    }


    public static void reset(String commitID){

        File commitFile = Utils.join(commits, commitID);
        if (commitFile.isFile()){
            if (checkoutFromGivenCommit(commitID)) {
                Utils.writeObject(stageForRemoval, new LinkedList<String>());
                Utils.writeObject(stageForAddition, new LinkedList<String>());
                HEAD = new Branch(commitID);
                File currentBranchPointerFile = readCurrentBranch();
                String currentBranchName = Utils.readContentsAsString(currentBranchPointerFile);
                File currentBranchFile = Utils.join(branch, currentBranchName);
                Utils.writeObject(HEADPointer, HEAD);
                Utils.writeObject(currentBranchFile, HEAD);
            }
        }else{
            System.out.println("No commit with that id exists.");
        }
    }


    public static void merge(String givenBranchName){

        //Error case for non-empty staged for removal or addition
        stagedForRemoval = Utils.readObject(stageForRemoval, LinkedList.class);
        stagedForAddition = Utils.readObject(stageForAddition, LinkedList.class);
        if(!stagedForRemoval.isEmpty() || !stagedForAddition.isEmpty()){
            System.out.println("You have uncommitted changes.");
            return;
        }


        LinkedList<String> currentBranchAncestors = new LinkedList<>();
        Commit curr = readHEADCommit();
        while(curr.getParent() != null){
            currentBranchAncestors.addLast(curr.getParent());
            curr = readGivenCommit(curr.getParent());
        }
        File givenBranchFile = Utils.join(branch, givenBranchName);

        //Error case for do not have such branch
        if (!givenBranchFile.isFile()){
            System.out.println("A branch with that name does not exist.");
            return;
        }

        //Error case for given branch is the ancestor of current branch
        Branch givenBranch = Utils.readObject(givenBranchFile, Branch.class);
        String givenCommitSha1 = givenBranch.getPointer();
        if(currentBranchAncestors.contains(givenCommitSha1)){
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        }

        Commit currentCommit = readHEADCommit();
        Commit givenCommit = readGivenCommit(givenCommitSha1);


        //Error case for the same branch
        if (currentCommit.getCommitID().equals(givenCommit.getCommitID())){
            System.out.println("Cannot merge a branch with itself.");
            return;
        }



        //Get the ancestors of given branch
        curr = readGivenCommit(givenCommitSha1);
        LinkedList<String > givenBranchAncestors = new LinkedList<>();
        while(curr.getParent() != null){
            givenBranchAncestors.addLast(curr.getParent());
            curr = readGivenCommit(curr.getParent());
        }

        //Special case for given branch is the split point
        if(givenBranchAncestors.contains(currentCommit.getCommitID())){
            checkoutBranch(givenBranchName);
            System.out.println("Current branch fast-forwarded.");
            return;
        }

        //Get the latest common ancestor
        String latestCommonAncestorName = givenBranchAncestors.get(givenBranchAncestors.size()-1);

        for (int i = givenBranchAncestors.size()-1; i >= 0; i--){
            if (!currentBranchAncestors.contains(givenBranchAncestors.get(i))){
                latestCommonAncestorName = givenBranchAncestors.get(i+1);
            }
        }
        Commit splitPoint = readGivenCommit(latestCommonAncestorName);


        //get a list of all possible files' filename (i.e. files that are tracked either currentCommit or givenCommit or splitPoint
        LinkedList<String> allPossibleFileName = new LinkedList<>();

        for (String thisBlobSha1Name : currentCommit.getFilePointer()){
            Blob currentBlob = Blob.getBlobFromSHA1(thisBlobSha1Name);
            if (!allPossibleFileName.contains(currentBlob.getFilename())){
                allPossibleFileName.addLast(currentBlob.getFilename());
            }
        }
        for (String thisBlobSha1Name : givenCommit.getFilePointer()){
            Blob currentBlob = Blob.getBlobFromSHA1(thisBlobSha1Name);
            if (!allPossibleFileName.contains(currentBlob.getFilename())){
                allPossibleFileName.addLast(currentBlob.getFilename());
            }
        }
        for (String thisBlobSha1Name : splitPoint.getFilePointer()){
            Blob currentBlob = Blob.getBlobFromSHA1(thisBlobSha1Name);
            if (!allPossibleFileName.contains(currentBlob.getFilename())){
                allPossibleFileName.addLast(currentBlob.getFilename());
            }
        }


        /**if there is an untracked file in the way*/
        //TODO: fill this error case tomorrow
        boolean whetherHasUntrackedChanges = false;



        //then do the main part of merge compare
            //Firstly initialize the filePointer of MergeCommit
        LinkedList<String> filePointerOfMergeCommit = new LinkedList<>();
        boolean statusOfConflict = false;

        for (String currentFilename : allPossibleFileName){
            HashMap<String, String> mapOfCurrent = getBlobFromFilename(currentFilename, currentCommit, givenCommit, splitPoint);
            String currentBLobName = mapOfCurrent.get("current");
            String givenBlobName = mapOfCurrent.get("given");
            String splitBlobName = mapOfCurrent.get("split");

            if (currentBLobName != null && splitBlobName != null && givenBlobName != null) {
                if (currentBLobName.equals(splitBlobName) && !currentBLobName.equals(givenBlobName)) {
                    filePointerOfMergeCommit.addLast(givenBlobName);
                    writeFileFromBlob(givenBlobName);
                }
            }
            if (splitBlobName != null && currentBLobName == null && givenBlobName == null){
                File currentFile = Utils.join("./", currentFilename);
                if (currentFile.isFile()){
                    Utils.restrictedDelete(currentFile);
                }
            }
            if (splitBlobName != null && currentBLobName != null) {
                if (!splitBlobName.equals(currentBLobName) && givenBlobName == null) {
                    File currentFile = Utils.join("./", currentFilename);
                    if (currentFile.isFile()) {
                        Utils.restrictedDelete(currentFile);
                    }
                }
            }

            if (splitBlobName != null && givenBlobName != null) {
                if (splitBlobName.equals(givenBlobName) && currentBLobName == null) {
                    File currentFile = Utils.join("./", currentFilename);
                    if (currentFile.isFile()) {
                        Utils.restrictedDelete(currentFile);
                    }
                }
            }
            if (splitBlobName == null && currentBLobName == null && givenBlobName != null){
                writeFileFromBlob(givenBlobName);
            }
            if (splitBlobName == null && givenBlobName == null && currentBLobName != null){
                writeFileFromBlob(currentBLobName);
            }

            if (currentBLobName != null && splitBlobName != null && givenBlobName != null) {
                if (!splitBlobName.equals(givenBlobName) && !givenBlobName.equals(currentBLobName) && !currentBLobName.equals(splitBlobName)) {
                    String contentOfCurrentBranch = Blob.getBlobFromSHA1(currentBLobName).content();
                    String contentOfGivenBranch = Blob.getBlobFromSHA1(givenBlobName).content();
                    File targetFile = Utils.join("./", currentFilename);
                    Utils.writeContents(targetFile, "<<<<<<< HEAD\n" + contentOfCurrentBranch +
                            "\n=======\n" + contentOfGivenBranch + "\n>>>>>>>");
                    statusOfConflict = true;
                }
            }

            if (givenBlobName != null && splitBlobName != null) {
                if (givenBlobName.equals(splitBlobName) && !splitBlobName.equals(currentBLobName)) {
                    filePointerOfMergeCommit.addLast(currentBLobName);
                    writeFileFromBlob(currentBLobName);
                }
            }
            if (statusOfConflict){
                System.out.println("Encountered a merge conflict.");
            }



            String message = "Merged" + givenBranchName + "into" + Utils.readContentsAsString(currentBranchFile);

            String currentCommitSha1 = currentCommit.getCommitID();
            givenCommitSha1 = givenCommit.getCommitID();

            String currentBranchName = Utils.readContentsAsString(currentBranchFile);

            Commit mergeCommit = new MergeCommit(givenCommitSha1, currentCommitSha1, filePointerOfMergeCommit, message);
            String mergeCommitSha1ID = mergeCommit.getCommitID();

            HEAD = readHEAD();
            HEAD.changePointer(mergeCommitSha1ID);

            Utils.writeObject(currentBranchFile, HEAD);
            Utils.writeObject(HEADPointer, HEAD);



        }



    }









/**----------------------------------------Helper Methods------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/


    public static HashMap<String, String> getBlobFromFilename(String filename, Commit currentCommit, Commit givenCommit, Commit splitCommit){
        HashMap<String, String> mapToReturn = new HashMap<>();

        for (String currBlobName : currentCommit.getFilePointer()){
            Blob currBlob = Blob.getBlobFromSHA1(currBlobName);
            if (currBlob.getFilename().equals(filename)){
                mapToReturn.put("current", currBlobName);
                break;
            }
        }

        for (String currBlobName : givenCommit.getFilePointer()){
            Blob currBlob = Blob.getBlobFromSHA1(currBlobName);
            if (currBlob.getFilename().equals(filename)){
                mapToReturn.put("given", currBlobName);
                break;
            }
        }

        for (String currBlobName : splitCommit.getFilePointer()){
            Blob currBlob = Blob.getBlobFromSHA1(currBlobName);
            if (currBlob.getFilename().equals(filename)){
                mapToReturn.put("split", currBlobName);
                break;
            }
        }

        return mapToReturn;
    }


    public static void writeFileFromBlob(String givenBlobSha1Name){
        Blob givenBlob = Blob.getBlobFromSHA1(givenBlobSha1Name);
        String content = givenBlob.content();
        String fileName = givenBlob.getFilename();
        File targetFile = Utils.join("./", fileName);
        Utils.writeContents(targetFile, content);
    }


    //This method will read all the staged for addition files and input them in a return list, then sort them in the lexicographic order
    @SuppressWarnings("unchecked")
    public static LinkedList<String> readStagedForAdditionFiles(){
        stagedForAddition = Utils.readObject(stageForAddition, LinkedList.class);
        LinkedList<String> listToReturn = new LinkedList<>();
        for (String blobsSha1Name : stagedForAddition){
            Blob current = Blob.getBlobFromSHA1(blobsSha1Name);
            listToReturn.addLast(current.getFilename());
        }
        Collections.sort(listToReturn);
        return listToReturn;
    }



    //will do the commit work, if it fail to commit, return false, else, return true
    private static boolean checkoutFromGivenCommit(String sha1IDOfCommit){
        Commit givenCommit = readGivenCommit(sha1IDOfCommit);
        LinkedList<String> listOfGivenBlobs = givenCommit.getFilePointer();
        Commit currentCommit = readHEADCommit();
        LinkedList<String> listOfCurrentBlobs = currentCommit.getFilePointer();
        for (String givenBlobName : listOfGivenBlobs) {
            Blob givenBlob = Blob.getBlobFromSHA1(givenBlobName);
            String givenBlobPointedFileName = givenBlob.getFilename();
            File givenBlobPointedFile = getFileFromName(givenBlobPointedFileName);
            if (givenBlobPointedFile.isFile()) {
                if (!currentCommit.anotherWhetherFileInCommit(givenBlobPointedFileName)) {
                    String currentFileContent = Utils.readContentsAsString(givenBlobPointedFile);
                    String givenBlobContent = givenBlob.content();
                    if(!givenBlobContent.equals(currentFileContent)) {
                        System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                        return false;
                    }
                }
            }
        }
        //These steps will delete all the files that are tracked in current commit
        for (String sha1Name : listOfCurrentBlobs){
            Blob currentBlob = Blob.getBlobFromSHA1(sha1Name);
            File fileToDelete = getFileFromName(currentBlob.getFilename());
            Utils.restrictedDelete(fileToDelete);
        }
        for (String sha1Name : listOfGivenBlobs) {
            Blob currentBlob = Blob.getBlobFromSHA1(sha1Name);
            File checkoutFile = getFileFromName(currentBlob.getFilename());
            Utils.writeContents(checkoutFile, currentBlob.content());
        }
        return  true;
    }


    private static Commit readGivenCommit(String sha1IDOfCommit){
        File commitFile = Utils.join(commits, sha1IDOfCommit);
        return Utils.readObject(commitFile, Commit.class);
    }



    @SuppressWarnings("unchecked")
    public static LinkedList<String> readStagedForRemovalFiles(){
        stagedForRemoval = Utils.readObject(stageForRemoval, LinkedList.class);
        LinkedList<String> listToReturn = new LinkedList<>();
        for (String blobsSha1Name : stagedForRemoval){
            Blob current = Blob.getBlobFromSHA1(blobsSha1Name);
            listToReturn.addLast(current.getFilename());
        }

        Collections.sort(listToReturn);
        return  listToReturn;
    }




    private static File readCurrentBranch(){
        return new File("./.gitlet/currentBranch");
    }


    private static Branch readHEAD(){
        return Utils.readObject(HEADPointer, Branch.class);
    }


    private static boolean isCommitEmpty(){
        if (Utils.readObject(stageForAddition, LinkedList.class).isEmpty() && Utils.readObject(stageForRemoval,LinkedList.class).isEmpty()){
            return true;
        }
        return false;
    }

    private static Commit readHEADCommit(){
        HEAD = readHEAD();
        String title = HEAD.getPointer();
        File HEADCommitFile = Utils.join(commits, title);
        Commit HEADCommit = Utils.readObject(HEADCommitFile, Commit.class);
        return HEADCommit;
    }

    //For a file in CWD, return the file from it's name(firstly used for the checkout delete)
    private static File getFileFromName(String fileName){
        return Utils.join("./", fileName);
    }


    //This will return true if the file is in the stageForAddition and remove it and add it to
    // stageForRemoval, if it is not in the stage for addition, return false

    @SuppressWarnings("unchecked")
    private static boolean isStaged(String fileName){
        stagedForAddition = Utils.readObject(stageForAddition, LinkedList.class);
        stagedForRemoval = Utils.readObject(stageForRemoval, LinkedList.class);
        for (int i = 0; i < stagedForAddition.size(); i++){
            String currentBlobSHA1title = stagedForAddition.get(i);
            Blob currentBlob = Blob.getBlobFromSHA1(currentBlobSHA1title);
            if (currentBlob.getFilename().equals(fileName)){
                stagedForAddition.remove(i);
                Utils.writeObject(stageForAddition, stagedForAddition);
                String title = currentBlob.getSHA1OfBlob();
                return true;
            }
        }
        return false;
    }



}