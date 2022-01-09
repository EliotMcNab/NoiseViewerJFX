package app.noiseviewerjfx.utilities.io.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

    /**
     * Reads a text file
     * @param fileToRead (File): the file we are reading from
     * @return (String): the contents of the text file
     */
    public static String readText(File fileToRead) {

        // checks that the file exists
        if (!fileToRead.exists()) {
            return "";
        }

        // tries to get a reader for the file
        Scanner scanner = null;

        try {
            scanner = new Scanner(new FileInputStream(fileToRead));
        } catch (FileNotFoundException e) {
            // aborts reading if the file cannot be opened
            return "";
        }

        // will contain the file's text
        StringBuilder stringBuilder = new StringBuilder();

        // saves every line in the file
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine() + "\n");
        }

        // closes the file
        scanner.close();

        // returns the final string contents of the file
        return stringBuilder.toString();

    }

}
