package gitlet;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import gitlet.Commit;
/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */

public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length == 0){
            System.out.print("the input is empty!");
            return;
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                if(Repository.GITLET_DIR.exists()){
                    System.out.print("A Gitlet system already exists in current directory.");
                } else{
                    Repository.GITLET_DIR.mkdir();
                }
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                String fileName = args[1];
                File file = new File(fileName);
                if(!file.exists()){
                    System.out.println("File does not exist.");
                    System.exit(0);
                }

                // Load current commit
                Commit.Gitlet gitlet2 = new Commit.Gitlet();
                Commit currentCommit = gitlet2.getCurrentCommit();

                // Read file contents
                byte[] fileContents = Utils.readContents(file);

                // Check if the file is identical to the version in the current commit
                if (currentCommit != null && Arrays.equals(currentCommit.fileContents(fileName), fileContents)) {
                    // The file is identical to the version in the current commit, remove it from the staging area
                    gitlet2.unstageFile(fileName);
                } else {
                    // The file is not identical to the version in the current commit, add it to the staging area
                    gitlet2.stageFile(fileName, fileContents);
                }
                break;

            // TODO: FILL THE REST IN
            case "commit":
                Commit.Gitlet gitlet = new Commit.Gitlet();
                // TODO: handle the `commit [message]` command
                if (args.length != 2) {
                    System.out.println("Incorrect operands.");
                    return;
                }
                String message = args[1];
                // Assume `gitlet` is an instance of your `Gitlet` class
                gitlet.commit(message);
                break;

            case "restore":
                if (args.length < 3) {
                    System.out.println("Incorrect operands.");
                    return;
                }

                if (args[1].equals("--")) {
                    String fileName2 = args[2];
                    Repository.restoreFileFromHeadCommit(fileName2);
                } else {
                    String commitId = args[1];
                    String fileName3 = args[3];
                    Repository.restoreFileFromCommit(commitId, fileName3);
                }
                break;

            case "log":
                Commit.Gitlet gitlet3 = new Commit.Gitlet();
                Commit currentCommit0 = gitlet3.getCurrentCommit();

                while (currentCommit0 != null) {
                    System.out.println("===");
                    System.out.println("commit " + currentCommit0.getCommitID());
                    System.out.println("Date: " + currentCommit0.getTimestamp());
                    System.out.println(currentCommit0.getMessage());
                    System.out.println();

                    currentCommit0 = gitlet3.getParentCommit();
                }
                break;
        }
    }
}
