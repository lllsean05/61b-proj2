# Gitlet Design Document

**Name**:
Song Li
## Classes and Data Structures
Commit, 
Main,
Repository,
Utils,
DumpObj
### Class 1
Commit
#### Fields

1. Field 1
2. Field 2


### Class 2
Main
#### Fields

1. main(String[] args)    checks whether the args array is empty. If it is, the system outputs an error message and returns.
2. init    initializes a new Gitlet repository in the current directory.
3. add    stages the file specified by the file's name for the next commit. If the file does not exist, prints error message and exit. I am struggled about the check current commit part
4. restore   restores the specified file from the current commit or the commit I want
5. log  shows information about each commit and its history

### Class 3
Repository
#### Fields

1. restoreFileFromHeadCommit  check if there is head commit and then check if the file exists in the commit. If not print Error message. If exists, get the content of the file and create a new file put the old content in the new file.
2. restoreFileFromCommit  basically the same process except we get to the commit by entering its commit ID and then get the content and write it in the new file

### Class 4
Utils
#### Fields
This is given by the skeleton but I want to write it to deepen my understanding

1. sha1(Object... vals): Computes the SHA-1 hash of vals.
2. sha1(List<Object> vals): Overloaded method that computes the SHA-1 hash of a list of objects.
3. restrictedDelete(File file): Deletes a file if it exists and is not a directory. 
4. restrictedDelete(String file): Overloaded method that deletes a file by its name.
5. readContents(File file): Reads the entire contents of a file and returns it as a byte array.
6. readContentsAsString(File file): Reads the entire contents of a file and returns it as a String.
7. writeContents(File file, Object... contents): Writes the contents to a file, creating or overwriting it as needed.
8. readObject(File file, Class<T> expectedClass): Reads an object of type T from a file and casts it to the expected class.
9. writeObject(File file, Serializable obj): Writes a serializable object to a file.
10. plainFilenamesIn(File dir): Returns a list of the names of all plain files in the directory
11. plainFilenamesIn(String dir): Overloaded method that takes a directory name as a string.
12. join(String first, String... others): Returns the first and other strings into a File designator.
13. join(File first, String... others): Overloaded method that takes a File and other strings
14. serialize(Serializable obj): Returns a byte array containing the serialized contents of the object.
15. error(String msg, Object... args): Returns a GitletException whose message is arguments.
16. message(String msg, Object... args): Prints a message to the console.

### Class 5
DumpObj
#### Fields

1. Field 1
2. Field 2


## Algorithms

## Persistence

