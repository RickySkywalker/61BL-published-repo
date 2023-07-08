package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Ricky
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */



    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length != 0) {
            String firstArg = args[0];
            switch (firstArg) {
                case "init":
                    // TODO: handle the `init` command
                    AAA.init();
                    break;
                case "add":
                    // TODO: handle the `add [filename]` command
                    AAA.add(args[1]);
                    break;
                // TODO: FILL THE REST IN
                case "commit":
                    if (args.length == 2 && !args[1].equals("")) {
                        AAA.commit(args[1]);
                    } else {
                        System.out.println("Please enter a commit message.");
                    }
                    break;

                case "log":
                    AAA.log();
                    break;

                case "checkout":
                    if (args.length == 3) {
                        if (args[1].equals("--")) {
                            AAA.checkoutMostRecent(args[2]);
                        } else {
                            System.out.println("Incorrect operands.");
                        }
                    } else if (args.length == 4) {
                        if (args[2].equals("--")) {
                            AAA.checkoutFromGivenCommit(args[1], args[3]);
                        } else {
                            System.out.println("Incorrect operands.");
                        }
                    } else if (args.length == 2) {
                        AAA.checkoutBranch(args[1]);
                    }
                    break;


                //After checkpoint
                case "rm":
                    AAA.rm(args[1]);
                    break;
                case "global-log":
                    AAA.globalLog();
                    break;
                case "find":
                    AAA.find(args[1]);
                    break;
                case "branch":
                    AAA.branch(args[1]);
                    break;
                case "rm-branch":
                    AAA.rmBranch(args[1]);
                    break;
                case "status":
                    AAA.status();
                    break;
                case "reset":
                    AAA.reset(args[1]);
                    break;
                case "merge":
                    AAA.merge(args[1]);
                    break;
                default:
                    System.out.println("No command with that name exists.");
            }
        }else{
            System.out.println("Please enter a command.");
        }

        return;
    }
}
