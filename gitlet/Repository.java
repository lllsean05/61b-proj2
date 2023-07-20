package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */
    static void restoreFileFromHeadCommit(String fileName) {
        // TODO: Implement this method
        // Get the head commit
        File headCommitFile = new File(Repository.GITLET_DIR, "head_commit");
        if (!headCommitFile.exists()) {
            System.out.println("No head commit.");
            return;
        }

        Commit headCommit = Utils.readObject(headCommitFile, Commit.class);

        if (!headCommit.contains(fileName)) {
            System.out.println("File does not exist");
            return;
        }

        byte[] fileContents = headCommit.getFileHash(fileName).getBytes();
        File outputFile = new File(fileName);
        Utils.writeContents(outputFile, fileContents);
    }

     static void restoreFileFromCommit(String commitId, String fileName) {
        // TODO: Implement this method
        // Get the specific commit
        File commitFile = new File(Repository.GITLET_DIR, "commits/" + commitId);
        if (!commitFile.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }

        Commit commit = Utils.readObject(commitFile, Commit.class);

        if (!commit.contains(fileName)) {
            System.out.println("File does not exist");
            return;
        }

        byte[] fileContents = commit.getFileHash(fileName).getBytes();
        File outputFile = new File(fileName);
        Utils.writeContents(outputFile, fileContents);
    }

}
