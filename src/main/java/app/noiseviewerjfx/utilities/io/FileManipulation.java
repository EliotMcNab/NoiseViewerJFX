package app.noiseviewerjfx.utilities.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileManipulation {

    /**
     * Deletes a file at the specified path
     * @param filePath (String): the path at which the file is located
     * @return (boolean): whether the deletion was successful
     */
    public static boolean delete(String filePath) {

        // the file to delete
        File toDelete = new File(filePath);

        // tries to delete the file
        return toDelete.delete();

    }

    /**
     * Creates a copy of file
     * @param origin (String): path to the file to be copied
     * @param destination (String): path to which the file will be copied
     * @param overwrite (boolean): whether to overwrite the destination file if it already exists
     * @return (boolean): whether the copy was successful
     */
    public static boolean copy(String origin, String destination, boolean overwrite) {

        // the file to copy
        File originFile = new File(origin);

        // the file to be copied to
        File destinationFile = new File(destination);

        // checks that the origin file exists
        if (!originFile.exists()) {
            return false;
        }

        // checks that a file does not already exist at the specified destination
        if (destinationFile.exists() && !overwrite) {
            // if it was not specified that the output file should be replaced, aborts copy
            return false;
        }

        // copies the contents of the original file to the specified destination
        FileChannel originChannel = null;
        FileChannel destinationChannel = null;

        try {

            originChannel = new FileInputStream(originFile).getChannel();
            destinationChannel = new FileOutputStream(destinationFile).getChannel();
            destinationChannel.transferFrom(originChannel, 0, originChannel.size());

            originChannel.close();
            destinationChannel.close();

        } catch (IOException e) {
            // the file could not be copied
            return false;
        }

        // file copy successful
        return true;
    }

    /**
     * Creates a copy of file
     * @param origin (String): path to the file to be copied
     * @param destination (String): path to which the file will be copied
     * @return (boolean): whether the copy was successful
     */
    public static boolean copy(String origin, String destination) {
        return copy(origin, destination, false);
    }

    /**
     * Cuts a file
     * @param origin (String): path to the file being copied
     * @param destination (String): path to where the file is being copied
     * @param overwrite (boolean): whether it is allowed to overwrite the destination file
     * @return (boolean): whether the cut was successful
     */
    public static boolean cut(String origin, String destination, boolean overwrite) {

        // the original file
        File originalFile = new File(origin);

        // the copy of the original file
        File destinationFile = new File(destination);

        // checks that the cut is possible
        if (destinationFile.exists() && !overwrite || !originalFile.exists()) {
            return false;
        }

        // tries to cut
        return copy(origin, destination) && delete(origin);
    }

    /**
     * Cuts a file
     * @param origin (String): path to the file being copied
     * @param destination (String): path to where the file is being copied
     * @return (boolean): whether the cut was successful
     */
    public static boolean cut(String origin, String destination) {
        return cut(origin, destination, false);
    }

}
