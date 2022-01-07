package app.noiseviewerjfx.io.write;

import app.noiseviewerjfx.io.FileType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class FileWriter {

    /**
     * Tries to write at the specified path
     * @param fileToWriteTo (File): the file we are writing to
     * @param toWrite (String): the text to write into the file
     * @param overWrite (boolean): whether to append to the file or overwrite it if it exists
     * @return
     */
    public static boolean writeText(File fileToWriteTo, String toWrite, FileType fileType, boolean overWrite) {

        // whether an error occurs while writing to the file
        boolean errorHappens = false;

        // used to write to the file
        PrintWriter textWriter = null;

        // the path of the file we are writing to
        final String filePath;

        // adds the file extension to the file type if necessary
        if (fileToWriteTo.getAbsolutePath().contains(fileType.extension())) {
            filePath = fileToWriteTo.getAbsolutePath();
        } else {
            filePath = fileToWriteTo.getAbsolutePath() + fileType.extension();
        }

        // tries to write to the file
        try {

            // if the file exists and the writing mode is set to "append"...
            if (fileToWriteTo.exists() && overWrite) {
                // ...sets will start writing at the end of the file
                textWriter = new PrintWriter(new FileOutputStream(filePath));
            } else {
                // ...otherwise, overwrites the file if it exists
                textWriter = new PrintWriter(new FileOutputStream(filePath, true));
            }

        } catch (FileNotFoundException e) {
            // the file could not be opened
            errorHappens = true;
        }

        // writes every character that needs to be written
        for (int i = 0; i < toWrite.length(); i++) {
            textWriter.write(toWrite.charAt(i));
        }

        // closes the writing stream
        textWriter.close();

        // writing to the file was successful
        return !errorHappens;

    }

    /**
     * Tries to write at the specified path
     * @param fileToWriteTo (File): the file we are writing to
     * @param toWrite (String): the text to write into the file
     * @return
     */
    public static boolean writeText(File fileToWriteTo, String toWrite, FileType fileType) {
        return writeText(fileToWriteTo, toWrite, fileType, true);
    }

    /**
     * Tries to erase the contents of a text file
     * @param filePath (String): path of the file we want to erase the contents of
     * @return
     */
    public static boolean erase(String filePath) {

        // the file to be erased
        File toErase = new File(filePath);

        // tries to get a writer associated to the file
        PrintWriter eraser = null;

        try {
            eraser = new PrintWriter(toErase);
        } catch (FileNotFoundException e) {
            // error opening file
            return false;
        }

        // erases the content of the file by overwriting its contents
        eraser.write("");
        eraser.close();

        // erasing was successful
        return true;

    }

}
