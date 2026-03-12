import java.util.ArrayList;
import java.util.List;

public class BookingSystem {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("ERROR: This program works exactly with two command line arguments, " +
                    "the first one is the path to the input file whereas the second one is the path to the output file. " +
                    "Sample usage can be as follows: \"java8 BookingSystem input.txt output.txt\". Program is going to terminate!");
            return;
        }

        String inputVoyage = args[0];
        String outputVoyage = args[1];

        if (!FileInput.checkInputFile(inputVoyage)) {
            return;
        }

        if (!FileOutput.checkOutputFile(outputVoyage)) {
            return;
        }

        List<Vehicle> vehicles = new ArrayList<>();
        FileInput.processInputFile(inputVoyage, outputVoyage, vehicles);
    }
}
