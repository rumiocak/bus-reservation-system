import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileInput {
    /**
     * Reads the file at the given path and returns contents of it in a string array.
     *
     * @param path              Path to the file that is going to be read.
     * @param discardEmptyLines If true, discards empty lines with respect to trim; else, it takes all the lines from the file.
     * @param trim              Trim status; if true, trims (strip in Python) each line; else, it leaves each line as-is.
     * @return Contents of the file as a string array, returns null if there is not such a file or this program does not have sufficient permissions to read that file.
     */
    public static String[] readFile(String path, boolean discardEmptyLines, boolean trim) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path)); //Gets the content of file to the list.
            if (discardEmptyLines) { // Removes the lines that are empty with respect to trim.
                lines.removeIf(line -> line.trim().equals(""));
            }
            if (trim) { // Trims each line.
                lines.replaceAll(String::trim);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) { // Returns null if there is no such a file.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Processes the input file, executing commands and writing results to the output file.
     *
     * @param inputFile  The path of the input file to be processed.
     * @param outputFile The path of the output file where the results will be written.
     * @param vehicles   A list of Vehicle objects to be used for processing commands.
     */
    public static void processInputFile(String inputFile, String outputFile, List<Vehicle> vehicles) {
        String[] input = FileInput.readFile(inputFile, true, true);
        String[] zReportLine = {"Z_REPORT"};

        // Check if input file is empty
        if (input.length == 0) {
            Command.processCommand(outputFile, zReportLine, vehicles);
            removeLastNewline(outputFile);
            return;
        }

        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            String[] fields = line.split("\t");

            FileOutput.writeToFile(outputFile, "COMMAND: " + line, true, true);
            Command.processCommand(outputFile, fields, vehicles);

            // Check if it is the last line and the last line is not Z_REPORT
            if (i == input.length - 1 && !fields[0].equals("Z_REPORT")) {
                Command.processCommand(outputFile, zReportLine, vehicles);
            }
        }
        removeLastNewline(outputFile);
    }


    /**
     * Checks if the input file exists and has a ".txt" extension.
     *
     * @param inputFilePath The path of the input file to be checked.
     * @return              True if the input file exists and has a ".txt" extension, false otherwise.
     */
    public static boolean checkInputFile(String inputFilePath) {
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists() || !inputFilePath.endsWith(".txt")) {
            System.out.println("ERROR: This program cannot read from the \"" + inputFilePath + "\", " +
                    "either this program does not have read permission to read that file or file does not exist. Program is going to terminate!");
            return false;
        }
        return true;
    }


    /**
     * Removes the last newline character from the end of a file.
     *
     * @param filePath The path to the file from which the last newline character will be removed.
     */
    public static void removeLastNewline(String filePath) {
        try {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long length = file.length();
            if (length > 0) {
                file.setLength(length - 1);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}