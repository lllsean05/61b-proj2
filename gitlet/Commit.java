package gitlet;


// TODO: any imports you need here
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.io.File;
import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    private String message;/* The message of this Commit. */

    private String parentID;/* The parentID of this Commit. */

    private Date timestamp;/* The record time of this Commit. */

    private HashMap<String, String> fileSnapshot;/*The snapshot of this Commit. */

    private String commitID;/* The commitID of this Commit. */



    public Commit(String message, String parentID, HashMap<String, String> fileSnapshot) {
        this.message = message;
        this.timestamp = new Date();
        this.fileSnapshot = fileSnapshot;
        String safeParentID = parentID != null ? parentID : "";
        this.commitID = Utils.sha1(message, timestamp.toString(), safeParentID, mapToString(fileSnapshot));
    }
    private String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        return sb.toString();
    }
    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public boolean contains(String fileName) {
        return fileSnapshot.containsKey(fileName);
    }

    public String getFileHash(String fileName) {
        return fileSnapshot.get(fileName);
    }
    public String getCommitID() {
        return commitID;
    }

    public String getParentID() {
        return parentID;
    }

    public HashMap<String, String> getFileSnapshot() {
        return fileSnapshot;
    }

    public byte[] fileContents(String fileName) {
        return Utils.readContents(new File(fileName));
    }

    /* TODO: fill in the rest of this class. */
    public static class Gitlet implements Serializable {
        private Commit currentCommit;
        HashMap<String, String> stagedFiles;
        private File gitletDir;

        public Gitlet() {
            gitletDir = Utils.join(Repository.GITLET_DIR, ".gitlet");
            if(!gitletDir.exists()) {
                gitletDir.mkdir();
            }
            File commitFile = Utils.join(gitletDir.getPath(), "currentCommit");
            if(commitFile.exists()) {
                currentCommit = Utils.readObject(commitFile, Commit.class);
            } else {
                HashMap<String, String> initialSnapshot = new HashMap<>();
                currentCommit = new Commit("initial commit", null, initialSnapshot);
                Utils.writeObject(commitFile, currentCommit);
            }
            File stagedFile = Utils.join(gitletDir.getPath(), "stagedFiles");
            if(stagedFile.exists()) {
                stagedFiles = Utils.readObject(stagedFile, HashMap.class);
            } else {
                stagedFiles = new HashMap<>();
                Utils.writeObject(stagedFile, stagedFiles);
            }
        }

        public void commit(String message) {
            System.out.println("Committing with staged files: " + stagedFiles.keySet());
            if (message == null || message.equals("")) {
                System.out.println("Please enter a commit message!");
                return;
            }
            if (stagedFiles.isEmpty()) {
                System.out.println("No change to the commit.commit side debug");
                return;
            }


            String parentID = currentCommit.getCommitID();
            HashMap<String, String> fileSnapshot = new HashMap<>(currentCommit.getFileSnapshot());
            for (Map.Entry<String, String> entry : stagedFiles.entrySet()) {
                fileSnapshot.put(entry.getKey(), entry.getValue());
            }
            Commit newCommit = new Commit(message, parentID, fileSnapshot);
            Utils.writeObject(Utils.join(gitletDir.getPath(), newCommit.getCommitID()), newCommit);
            Utils.writeObject(Utils.join(gitletDir.getPath(), "currentCommit"), newCommit);
            currentCommit = newCommit; //I update the created commit to the new commit
            stagedFiles.clear();
            Utils.writeObject(Utils.join(gitletDir.getPath(), "stagedFiles"), stagedFiles);
        }
        public Commit getCommitFromID(String commitID) {
            if (commitID == null) {
                return null;
            }
            File commitFile = Utils.join(gitletDir.getPath(), commitID);
            if (commitFile.exists()) {
                return Utils.readObject(commitFile, Commit.class);
            } else {
                return null;
            }
        }
        public Commit getCurrentCommit() {
            return Utils.readObject(Utils.join(gitletDir.getPath(), "currentCommit"), Commit.class);
        }
        public Commit getParentCommit() {
            String parentID = this.currentCommit.getParentID();
            return getCommitFromID(parentID);
        }
        public void stageFile(String fileName, byte[] fileContents) {
            stagedFiles.put(fileName, Arrays.toString(fileContents));
        }

        public void unstageFile(String fileName) {
            stagedFiles.remove(fileName);
        }

    }

}

