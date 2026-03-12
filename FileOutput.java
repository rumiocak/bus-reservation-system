import java.io.*;

public class FileOutput {
    /**
     * This method writes given content to file at given path.
     *
     * @param path    Path for the file content is going to be written.
     * @param content Content that is going to be written to file.
     * @param append  Append status, true if wanted to append to file if it exists, false if wanted to create file from zero.
     * @param newLine True if wanted to append a new line after content, false if vice versa.
     */
    public static void writeToFile(String path, String content, boolean append, boolean newLine) {
        PrintStream ps = null;
        try {
            ps = new PrintStream(new FileOutputStream(path, append));
            ps.print(content + (newLine ? "\n" : ""));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { // Flushes all the content and closes the stream if it has been successfully created.
                ps.flush();
                ps.close();
            }
        }
    }

    /**
     * Checks if the output file can be created or written to.
     *
     * @param outputFilePath The path of the output file to be checked.
     * @return               True if the output file can be created or written to, false otherwise.
     */
    public static boolean checkOutputFile(String outputFilePath) {
        File outputFile = new File(outputFilePath);
        boolean createNewFileFailed = false; // Flag to check error status.

        if (!outputFile.exists() && outputFilePath.endsWith(".txt")) {
            try {
                if (!outputFile.createNewFile()) {
                    createNewFileFailed = true;
                }
            } catch (IOException e) {
                createNewFileFailed = true;
            }
        }

        if (createNewFileFailed || !outputFile.canWrite()) {
            System.out.println("ERROR: This program cannot write to the \"" + outputFilePath + "\", please check the permissions to write that directory. Program is going to terminate!");
            return false;
        }

        return true;
    }
}