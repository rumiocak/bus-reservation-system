import java.util.*;
public class Command {
    /**
     * Processes the command specified in the command line.
     *
     * @param filePath     The file path where the command results will be written.
     * @param commandLine  An array containing the command and its arguments.
     * @param vehicles     A list of vehicles used in the command operations.
     */
    public static void processCommand(String filePath, String[] commandLine, List<Vehicle> vehicles) {
        String command = commandLine[0];

        switch (command) {
            case "INIT_VOYAGE":
                InitVoyage.initVoyage(filePath, commandLine, vehicles);
                break;
            case "SELL_TICKET":
                TicketOperations.sellTicket(filePath, commandLine, vehicles);
                break;
            case "REFUND_TICKET":
                TicketOperations.refundTicket(filePath, commandLine, vehicles);
                break;
            case "PRINT_VOYAGE":
                PrintVoyage.printVoyage(filePath, commandLine, vehicles);
                break;
            case "CANCEL_VOYAGE":
                CancelVoyage.cancelVoyage(filePath, commandLine, vehicles);
                break;
            case "Z_REPORT":
                ZReport.zReport(filePath, commandLine, vehicles);
                break;
            default:
                writeErrorMessage(filePath, "There is no command namely " + command +"!");
        }
    }

    /**
     * Checks if the given voyage ID is valid.
     *
     * @param filePath   The file path where error messages will be written.
     * @param voyageId   The ID of the voyage to be validated.
     * @return           True if the voyage ID is valid (positive integer), false otherwise.
     */
    private static boolean isValidVoyageId(String filePath, int voyageId){
        if (voyageId <= 0) {
            writeErrorMessage(filePath, voyageId + " is not a positive integer, ID of a voyage must be a positive integer!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given voyage ID is unique.
     *
     * @param filePath   The file path where error messages will be written.
     * @param voyageId   The ID of the voyage to be checked for uniqueness.
     * @param vehicles   The list of vehicles to search for the voyage ID.
     * @return           True if the voyage ID is unique, false otherwise.
     */
    private static boolean isUniqueVoyageId(String filePath, int voyageId, List<Vehicle> vehicles){
        Vehicle selectedVoyage = findVoyageById(voyageId, vehicles);
        if (selectedVoyage != null) {
            writeErrorMessage(filePath, "There is already a voyage with ID of " + voyageId + "!");
            return false;
            }
        return true;
    }

    /**
     * Checks if a voyage with the given ID exists.
     *
     * @param filePath   The file path where error messages will be written.
     * @param voyageId   The ID of the voyage to be checked.
     * @param vehicles   The list of vehicles to search for the voyage ID.
     * @return           True if a voyage with the given ID exists, false otherwise.
     */
    private static boolean isVoyageFound(String filePath, int voyageId, List<Vehicle> vehicles){
        Vehicle selectedVoyage = findVoyageById(voyageId, vehicles);
        if (selectedVoyage == null) {
            writeErrorMessage(filePath, "There is no voyage with ID of " + voyageId + "!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given number of seat rows is valid.
     *
     * @param filePath   The file path where error messages will be written.
     * @param seatRows   The number of seat rows to be checked.
     * @return           True if the number of seat rows is valid (positive integer), false otherwise.
     */
    private static boolean isValidSeatRows(String filePath, int seatRows){
        if (seatRows <= 0) {
            writeErrorMessage(filePath, seatRows + " is not a positive integer, number of seat rows of a voyage must be a positive integer!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given standard seat price is valid.
     *
     * @param filePath          The file path where error messages will be written.
     * @param commandLine       The command line arguments.
     * @param standardSeatPrice The standard seat price to be checked.
     * @return                  True if the standard seat price is valid (positive number), false otherwise.
     */
    private static boolean isValidStandardSeatPrice(String filePath, String[] commandLine, float standardSeatPrice){
        if (standardSeatPrice <= 0) {
            writeErrorMessage(filePath, commandLine[6] + " is not a positive number, price must be a positive number!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given refund cut is valid.
     *
     * @param filePath   The file path where error messages will be written.
     * @param refundCut  The refund cut to be checked.
     * @return           True if the refund cut is valid (in range of [0, 100]),  false otherwise.
     */
    private static boolean isValidRefundCut(String filePath, int refundCut) {
        if (refundCut < 0 || refundCut > 100) {
            writeErrorMessage(filePath, refundCut + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given premium fee is valid.
     *
     * @param filePath   The file path file where error messages will be written.
     * @param premiumFee The premium fee to be checked.
     * @return           True if the premium fee is valid (non-negative integer), false otherwise.
     */
    private static boolean isValidPremiumFee(String filePath, int premiumFee){
        if (premiumFee < 0) {
            writeErrorMessage(filePath, premiumFee + " is not a non-negative integer, premium fee must be a non-negative integer!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the given list of seat numbers is valid.
     *
     * @param filePath    The file path where error messages will be written.
     * @param seatNumbers The list of seat numbers to be checked.
     * @return            True if all seat numbers are valid (positive integer), false otherwise.
     */
    private static boolean isValidSeatNumbers(String filePath, List<Integer> seatNumbers) {
        for (int seatNumber : seatNumbers) {
            if (seatNumber <= 0) {
                writeErrorMessage(filePath, seatNumber + " is not a positive integer, seat number must be a positive integer!");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the seat number is within the range of the number of seats in a vehicle.
     *
     * @param seatNumber The seat number to be checked.
     * @param vehicle    The vehicle for which the seat number is being checked.
     * @return           True if the seat number is within the valid range, false otherwise.
     */
    private static boolean isSeatNumberWithinRange(int seatNumber, Vehicle vehicle) {
        return seatNumber <= vehicle.availableSeats.length;
    }

    /**
     * Parses the given string value into an integer, performing error handling if parsing fails.
     *
     * @param filePath    The file path where error messages will be written.
     * @param value       The string value to be parsed into an integer.
     * @param field       The field associated with the value being parsed ("voyageId", "seatRows", "refundCut", "premiumFee" or "seatNumber").
     * @param commandName The name of the command associated with the ErroneousCommandUsage error.
     * @return            The parsed integer value if successful, or null if parsing fails.
     */
    private static Integer parseInteger(String filePath, String value, String field, String commandName) {
        // Checks if the command line arguments are separated by whitespace instead of tabs, indicating an incorrect command format.
        // If the value string remains unchanged after trimming (i.e., it does not contain whitespace),
        // the command format is considered correct.
        // If before and after not equal, an error message is written and null is returned.
        String trimmedValue = value.trim();
        if (!value.equals(trimmedValue)) {
            writeErroneousCommandUsage(filePath, commandName);
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            switch (field) {
                case "voyageId":
                    writeErrorMessage(filePath, value + " is not a positive integer, ID of a voyage must be a positive integer!");
                    break;
                case "seatRows":
                    writeErrorMessage(filePath, value + " is not a positive integer, number of seat rows of a voyage must be a positive integer!");
                    break;
                case "refundCut":
                    writeErrorMessage(filePath, value + " is not an integer that is in range of [0, 100], refund cut must be an integer that is in range of [0, 100]!");
                    break;
                case "premiumFee":
                    writeErrorMessage(filePath, value + " is not a non-negative integer, premium fee must be a non-negative integer!");
                    break;
                case "seatNumber":
                    writeErrorMessage(filePath, value + " is not a positive integer, seat number must be a positive integer!");
            }
            return null;
        }
    }

    /**
     * Finds a vehicle by its voyage ID in the list of vehicles.
     *
     * @param voyageId The ID of the voyage to search for.
     * @param vehicles The list of vehicles to search within.
     * @return         The vehicle with the specified voyage ID, or null if no such vehicle is found.
     */
    private static Vehicle findVoyageById(int voyageId, List<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVoyageId() == voyageId) return vehicle;
        }
        return null;
    }

    /**
     * Writes an error message to the specified output file.
     *
     * @param filePath   The path of the output file to write the error message to.
     * @param message    The error message to write.
     */
    private static void writeErrorMessage(String filePath, String message) {
        FileOutput.writeToFile(filePath, "ERROR: " + message, true, true);
    }

    /**
     * Writes an error message for erroneous command usage to the specified output file.
     *
     * @param filePath    The path of the output file to write the error message to.
     * @param commandName The name of the command for which the erroneous usage occurred.
     */
    private static void writeErroneousCommandUsage(String filePath, String commandName) {
        writeErrorMessage(filePath, "Erroneous usage of \"" + commandName + "\" command!");
    }

    /**
     * Writes the provided content to the specified output file with a newline appended.
     *
     * @param filePath   The path of the output file to write the content to.
     * @param content    The content to write to the file.
     */
    private static void writeWithNewLine(String filePath, String content) {
        FileOutput.writeToFile(filePath, content, true, true);
    }

    public static class InitVoyage {
        /**
         * Executes the INIT_VOYAGE command that initializes a new voyage based on the provided command line arguments.
         *
         * @param filePath       The file path where the output of the command will be written.
         * @param commandLine    The command line arguments specifying the details of the voyage.
         * @param vehicles       The list of vehicles to which the new voyage will be added.
         */
        private static void initVoyage(String filePath, String[] commandLine, List<Vehicle> vehicles) {
            if (!isValidCommandLineLength(filePath, commandLine[1], commandLine.length)) return;

            Integer voyageId = parseInteger(filePath, commandLine[2], "voyageId", "INIT_VOYAGE");
            if (voyageId == null) return;

            String voyageFrom = commandLine[3];
            String voyageTo = commandLine[4];

            Integer seatRows = parseInteger(filePath, commandLine[5], "seatRows", "INIT_VOYAGE");
            if (seatRows == null) return;

            Float standardSeatPrice = parseFloatSeatPrice(filePath, commandLine[6]);
            if (standardSeatPrice == null) return;

            // Check if the fields are valid after successfully parsing them.
            if (!isValidVoyageId(filePath, voyageId) ||
                    !isUniqueVoyageId(filePath, voyageId, vehicles) ||
                    !isValidSeatRows(filePath, seatRows) ||
                    !isValidStandardSeatPrice(filePath, commandLine, standardSeatPrice)) {
                return;
            }

            switch (commandLine[1]) {
                case "Minibus":
                    createMinibus(filePath, vehicles, voyageId, voyageFrom, voyageTo, seatRows, standardSeatPrice);
                    break;
                case "Standard":
                    createStandardBus(filePath, commandLine, vehicles, voyageId, voyageFrom, voyageTo, seatRows, standardSeatPrice);
                    break;
                case "Premium":
                    createPremiumBus(filePath, commandLine, vehicles, voyageId, voyageFrom, voyageTo, seatRows, standardSeatPrice);
                    break;
                default:
                    writeErroneousCommandUsage(filePath, "Invalid vehicle type.");
            }
        }

        /**
         * Checks if the length of the command line arguments matches the expected length based on the vehicle type.
         *
         * @param filePath           The path of the output file to write error messages.
         * @param vehicleType        The type of vehicle for which the command line length is being validated.
         * @param commandLineLength  The length of the provided command line arguments.
         * @return                   True if the command line length matches the expected length, otherwise false.
         */
        private static boolean isValidCommandLineLength(String filePath, String vehicleType, int commandLineLength) {
            int expectedLength;
            switch (vehicleType) {
                case "Minibus":
                    expectedLength = 7;
                    break;
                case "Standard":
                    expectedLength = 8;
                    break;
                case "Premium":
                    expectedLength = 9;
                    break;
                default:
                    expectedLength = 0;
                    break;
            }

            if (commandLineLength != expectedLength) {
                writeErroneousCommandUsage(filePath, "INIT_VOYAGE");
                return false;
            }
            return true;
        }

        /**
         * Parses the string representation of a seat price to a Float value.
         *
         * @param filePath   The file path where error messages will be written.
         * @param value      The string representation of the seat price to be parsed.
         * @return           A Float representing the parsed seat price if successful, or null if parsing fails.
         */
        private static Float parseFloatSeatPrice(String filePath, String value) {
            // Checks if the command line arguments are separated by whitespace instead of tabs, indicating an incorrect command format.
            // If the value string remains unchanged after trimming (i.e., it does not contain whitespace),
            // the command format is considered correct.
            // If before and after not equal, an error message is written and null is returned.
            String trimmedValue = value.trim();
            if (!value.equals(trimmedValue)) {
                writeErroneousCommandUsage(filePath, "INIT_VOYAGE");
                return null;
            }
            try {
                return Float.parseFloat(value);
            } catch (NumberFormatException e) {
                writeErrorMessage(filePath, value + " is not a positive number, price must be a positive number!");
                return null;
            }
        }

        /**
         * Creates a Minibus instance with the provided details and adds it to the list of vehicles.
         *
         * @param filePath          The path of the output file to write initialized voyage will be written.
         * @param vehicles          The list of vehicles to which the Minibus instance will be added.
         * @param voyageId          The ID of the voyage for the Minibus.
         * @param voyageFrom        The starting location of the voyage.
         * @param voyageTo          The destination of the voyage.
         * @param seatRows          The number of seat rows for the Minibus.
         * @param standardSeatPrice The standard seat price for the Minibus.
         */
        private static void createMinibus(String filePath, List<Vehicle> vehicles, int voyageId, String voyageFrom, String voyageTo, int seatRows, float standardSeatPrice) {
            Minibus minibus = new Minibus(filePath, voyageId, voyageFrom, voyageTo, seatRows, standardSeatPrice);
            vehicles.add(minibus);
        }

        /**
         * Creates a StandardBus instance with the provided details and adds it to the list of vehicles.
         *
         * @param filePath          The path of the output file to write initialized voyage will be written.
         * @param commandLine       The command line arguments containing information about the StandardBus.
         * @param vehicles          The list of vehicles to which the StandardBus instance will be added.
         * @param voyageId          The ID of the voyage for the StandardBus.
         * @param voyageFrom        The starting location of the voyage.
         * @param voyageTo          The destination of the voyage.
         * @param seatRows          The number of seat rows for the StandardBus.
         * @param standardSeatPrice The standard seat price for the StandardBus.
         */
        private static void createStandardBus(String filePath, String[] commandLine, List<Vehicle> vehicles, int voyageId, String voyageFrom, String voyageTo, int seatRows, float standardSeatPrice) {
            Integer refundCut = parseInteger(filePath, commandLine[7], "refundCut", "INIT_VOYAGE");
            if (refundCut == null || !isValidRefundCut(filePath, refundCut)) return;

            StandardBus standard = new StandardBus(filePath, voyageId, voyageFrom, voyageTo, seatRows, standardSeatPrice, refundCut);
            vehicles.add(standard);
        }

        /**
         * Creates a PremiumBus instance with the provided details and adds it to the list of vehicles.
         *
         * @param filePath          The path of the output file to write initialized voyage will be written.
         * @param commandLine       The command line arguments containing information about the PremiumBus.
         * @param vehicles          The list of vehicles to which the PremiumBus instance will be added.
         * @param voyageId          The ID of the voyage for the PremiumBus.
         * @param voyageFrom        The starting location of the voyage.
         * @param voyageTo          The destination of the voyage.
         * @param seatRows          The number of seat rows for the PremiumBus.
         * @param standardSeatPrice The standard seat price for the PremiumBus.
         */
        private static void createPremiumBus(String filePath, String[] commandLine, List<Vehicle> vehicles, int voyageId, String voyageFrom, String voyageTo, int seatRows, float standardSeatPrice) {
            Integer refundCut = parseInteger(filePath, commandLine[7], "refundCut", "INIT_VOYAGE");
            Integer premiumFee = parseInteger(filePath, commandLine[8], "premiumFee", "INIT_VOYAGE");

            // Check whether the fields have been parsed and whether the parsed values are valid.
            if (refundCut == null || premiumFee == null || !isValidRefundCut(filePath, refundCut) || !isValidPremiumFee(filePath, premiumFee)) {
                return;
            }

            PremiumBus premium = new PremiumBus(filePath, voyageId, voyageFrom, voyageTo, seatRows, standardSeatPrice, refundCut, premiumFee);
            vehicles.add(premium);
        }
    }

    private static class TicketOperations {
        /**
         * Executes the SELL_TICKET command that initializes the process of selling a ticket for a voyage.
         *
         * @param filePath     The file path where the output of the command will be written.
         * @param commandLine  The command line arguments containing information about the ticket selling operation.
         * @param vehicles     The list of vehicles involved in the ticket operation.
         */
        private static void sellTicket(String filePath, String[] commandLine, List<Vehicle> vehicles) {
            processTicketOperation(filePath, commandLine, vehicles, "SELL_TICKET", false);
        }

        /**
         * Executes the REFUND_TICKET command that initializes the process of refunding a ticket for a voyage.
         *
         * @param filePath     The file path where the output of the command will be written.
         * @param commandLine  The command line arguments containing information about the ticket refund operation.
         * @param vehicles     The list of vehicles involved in the ticket operation.
         */
        private static void refundTicket(String filePath, String[] commandLine, List<Vehicle> vehicles) {
            processTicketOperation(filePath, commandLine, vehicles, "REFUND_TICKET", true);
        }

        /**
         * Processes the ticket operation for a voyage, such as selling or refunding tickets based on the provided command line arguments.
         *
         * @param filePath          The path of the output file to write output.
         * @param commandLine       The command line arguments containing information about the ticket operation.
         * @param vehicles          The list of vehicles involved in the ticket operation.
         * @param operationName     The name of the ticket operation (SELL_TICKET or REFUND_TICKET).
         * @param isRefundOperation A boolean indicating whether the operation is a refund operation or not.
         */
        private static void processTicketOperation(String filePath, String[] commandLine, List<Vehicle> vehicles, String operationName, boolean isRefundOperation) {
            if (commandLine.length != 3) {
                writeErroneousCommandUsage(filePath, operationName);
                return;
            }

            Integer voyageId = parseInteger(filePath, commandLine[1], "voyageId", operationName);
            if (voyageId == null) return;


            String seatNumbersString = commandLine[2];
            List<Integer> seatNumbers = parseSeatNumbers(filePath, seatNumbersString, operationName);
            if (seatNumbers.isEmpty()) return;

            if (!isValidVoyageId(filePath, voyageId) ||
                    !isValidSeatNumbers(filePath, seatNumbers) ||
                    !isVoyageFound(filePath, voyageId, vehicles)) return;

            Vehicle selectedVoyage = findVoyageById(voyageId, vehicles);
            if (isRefundOperation && selectedVoyage instanceof Minibus) {
                writeErrorMessage(filePath, "Minibus tickets are not refundable!");
                return;
            }

            if (isRefundOperation) {
                processRefundOperation(filePath, seatNumbers, selectedVoyage, voyageId);
            } else {
                processSellOperation(filePath, seatNumbers, selectedVoyage, voyageId);
            }
        }

        /**
         * Processes the sale of tickets for a selected voyage.
         *
         * @param filePath        The path of the output file to write output.
         * @param seatNumbers     The list of seat numbers to be sold.
         * @param selectedVoyage  The selected voyage for ticket sales.
         * @param voyageId        The ID of the voyage.
         */
        private static void processSellOperation(String filePath, List<Integer> seatNumbers, Vehicle selectedVoyage, int voyageId) {
            float ticketRevenue = 0;
            boolean allSeatsAvailable = true;

            for (int seatNumber : seatNumbers) {
                if (!isSeatNumberWithinRange(seatNumber, selectedVoyage)) {
                    writeErrorMessage(filePath, "There is no such a seat!");
                    return;
                } else if (!selectedVoyage.isSeatAvailable(seatNumber)) {
                    allSeatsAvailable = false;
                    break;
                }
            }

            if (!allSeatsAvailable) {
                writeErrorMessage(filePath, "One or more seats already sold!");
                return;
            }

            for (int seatNumber : seatNumbers) {
                ticketRevenue += getSeatPriceForTransaction(selectedVoyage, seatNumber);
                selectedVoyage.markSeatAsSold(seatNumber);
            }

            updateRevenueAndWriteMessage(filePath, selectedVoyage, voyageId, ticketRevenue, seatNumbers);
        }

        /**
         * Processes the refund of tickets for a selected voyage.
         *
         * @param filePath        The path of the output file to write command output.
         * @param seatNumbers     The list of seat numbers to be refunded.
         * @param selectedVoyage  The selected voyage for ticket refunds.
         * @param voyageId        The ID of the voyage.
         */
        private static void processRefundOperation(String filePath, List<Integer> seatNumbers, Vehicle selectedVoyage, int voyageId) {
            float refundAmount = 0;
            boolean allSeatsSold = true;

            for (int seatNumber : seatNumbers) {
                if (!isSeatNumberWithinRange(seatNumber, selectedVoyage)) {
                    writeErrorMessage(filePath, "There is no such a seat!");
                    return;
                }

                if (selectedVoyage.isSeatAvailable(seatNumber)) {
                    allSeatsSold = false;
                    break;
                }
            }

            if (!allSeatsSold) {
                writeErrorMessage(filePath, "One or more seats are already empty!");
                return;
            }

            for (int seatNumber : seatNumbers) {
                refundAmount += getRefundedSeatPriceForTransaction(selectedVoyage, seatNumber);
                selectedVoyage.markSeatAsAvailable(seatNumber);
            }

            updateRevenueAndWriteMessage(filePath, selectedVoyage, voyageId, -refundAmount, seatNumbers);
        }

        /**
         * Parses the seat numbers provided as a string and returns them as a list of integers.
         *
         * @param filePath          The file path for error logging.
         * @param seatNumbersString The string containing seat numbers separated by underscores.
         * @param operationName     The name of the operation being performed.
         * @return                  A list of parsed seat numbers if parsing is successful and seat numbers are unique, otherwise an empty list.
         */
        private static List<Integer> parseSeatNumbers(String filePath, String seatNumbersString, String operationName) {
            List<Integer> seatNumbers = new ArrayList<>();

            String[] seatNumberStrings = seatNumbersString.split("_");

            for (String seatNumberString : seatNumberStrings) {
                Integer seatNumber = parseInteger(filePath, seatNumberString, "seatNumber", operationName);
                if (seatNumber == null) {
                    return Collections.emptyList();
                }
                seatNumbers.add(seatNumber);
            }
            return seatNumbers;
        }

        /**
         * Gets the seat price for a transaction based on the selected voyage and seat number.
         *
         * @param selectedVoyage  The selected voyage.
         * @param seatNumber      The seat number.
         * @return                The seat price for the transaction.
         */
        private static float getSeatPriceForTransaction(Vehicle selectedVoyage, int seatNumber) {
            if (selectedVoyage instanceof PremiumBus && ((PremiumBus) selectedVoyage).isSeatPremium(seatNumber)) {
                return ((PremiumBus) selectedVoyage).getPremiumSeatPrice();
            } else {
                return selectedVoyage.getStandardSeatPrice();
            }
        }

        /**
         * Gets the refunded seat price for a transaction based on the selected voyage and seat number.
         *
         * @param selectedVoyage  The selected voyage.
         * @param seatNumber      The seat number.
         * @return                The refunded seat price for the transaction.
         */
        private static float getRefundedSeatPriceForTransaction(Vehicle selectedVoyage, int seatNumber) {
            if (selectedVoyage instanceof PremiumBus && ((PremiumBus) selectedVoyage).isSeatPremium(seatNumber)) {
                return ((PremiumBus) selectedVoyage).getRefundedPremiumSeatPrice();
            } else if (selectedVoyage instanceof PremiumBus && !((PremiumBus) selectedVoyage).isSeatPremium(seatNumber)) {
                return ((PremiumBus) selectedVoyage).getRefundedStandardSeatPrice();
            } else {
                return ((StandardBus) selectedVoyage).getRefundedStandardSeatPrice();
            }
        }

        /**
         * Updates the revenue of the selected voyage and writes a message about the transaction to the output file.
         *
         * @param filePath        The path of the output file to write the transaction message.
         * @param selectedVoyage  The selected voyage.
         * @param voyageId        The ID of the voyage.
         * @param amount          The amount of revenue change (positive for ticket sales, negative for refunds).
         * @param seatNumbers     The list of seat numbers involved in the transaction.
         */
        private static void updateRevenueAndWriteMessage(String filePath, Vehicle selectedVoyage, int voyageId, float amount, List<Integer> seatNumbers) {
            selectedVoyage.setRevenue(selectedVoyage.getRevenue() + amount);

            // Formats seat numbers with dashes between them.
            StringBuilder seatNumbersFormatted = new StringBuilder();
            for (int i = 0; i < seatNumbers.size(); i++) {
                seatNumbersFormatted.append(seatNumbers.get(i));
                if (i < seatNumbers.size() - 1) {
                    seatNumbersFormatted.append("-");
                }
            }

            String formattedString = seatNumbersFormatted.toString();
            String message = (amount >= 0 ? "sold for " : "refunded for ") + String.format("%.2f", Math.abs(amount)).replace(",", ".") + " TL.";
            writeWithNewLine(filePath, "Seat " + formattedString + " of the Voyage " + voyageId + " from " + selectedVoyage.getVoyageFrom() + " to " + selectedVoyage.getVoyageTo() + " was successfully " + message);
        }
    }

    private static class PrintVoyage {
        /**
         * Executes the PRINT_VOYAGE command that prints the details of the voyage with the specified ID.
         *
         * @param filePath     The file path where the output of the command will be written.
         * @param commandLine  The command line arguments containing the voyage ID.
         * @param vehicles     The list of vehicles to search for the voyage.
         */
        private static void printVoyage(String filePath, String[] commandLine, List<Vehicle> vehicles) {
            if (commandLine.length != 2) {
                writeErroneousCommandUsage(filePath, "PRINT_VOYAGE");
                return;
            }

            Integer voyageId = parseInteger(filePath, commandLine[1], "voyageId", "PRINT_VOYAGE");
            if (voyageId == null || !isValidVoyageId(filePath, voyageId)) return;

            Vehicle selectedVoyage = findVoyageById(voyageId, vehicles);
            if (selectedVoyage != null) {
                selectedVoyage.displayVehicle(filePath);
            } else {
                writeErrorMessage(filePath, "There is no voyage with ID of " + voyageId + "!");
            }
        }
    }

    private static class CancelVoyage {
        /**
         * Executes the CANCEL_VOYAGE command that initializes the process of canceling the voyage with the specified ID.
         *
         * @param filePath     The file path where the output of the command will be written.
         * @param commandLine  The array containing the command line arguments.
         * @param vehicles     The list of vehicles where the voyage will be searched and removed.
         */
        private static void cancelVoyage(String filePath, String[] commandLine, List<Vehicle> vehicles) {
            if (commandLine.length != 2) {
                writeErroneousCommandUsage(filePath, "CANCEL_VOYAGE");
                return;
            }

            Integer voyageId = parseInteger(filePath, commandLine[1], "voyageId", "CANCEL_VOYAGE");
            if (voyageId == null || !isValidVoyageId(filePath, voyageId)) return;

            Vehicle selectedVoyage = findVoyageById(voyageId, vehicles);
            if (selectedVoyage != null) {
                cancelSelectedVoyage(filePath, selectedVoyage, voyageId, vehicles);
            } else {
                writeErrorMessage(filePath, "There is no voyage with ID of " + voyageId + "!");
            }
        }

        /**
         * Processes canceling the selected voyage and removes it from the list of vehicles.
         *
         * @param filePath        The name of the output file where the cancellation status and details will be written.
         * @param selectedVoyage  The vehicle representing the voyage to be cancelled.
         * @param voyageId        The ID of the voyage to be cancelled.
         * @param vehicles        The list of vehicles from which the voyage will be removed.
         */
        private static void cancelSelectedVoyage(String filePath, Vehicle selectedVoyage, int voyageId, List<Vehicle> vehicles) {
            writeWithNewLine(filePath, "Voyage " + voyageId + " was successfully cancelled!");
            writeWithNewLine(filePath, "Voyage details can be found below:");

            float refundFee = calculateRefundFeeToCustomer(selectedVoyage);
            selectedVoyage.setRevenue(selectedVoyage.getRevenue() - refundFee);
            selectedVoyage.displayVehicle(filePath);
            vehicles.remove(selectedVoyage);
        }

        /**
         * Calculates the total refund fee to the customer for the cancelled seats in the cancelled voyage.
         *
         * @param selectedVoyage The vehicle representing the voyage for which the refund fee will be calculated.
         * @return               The total refund fee for the cancelled seats in the voyage.
         */
        private static float calculateRefundFeeToCustomer(Vehicle selectedVoyage) {
            float totalReturnFee = 0;
            for (int seat = 1; seat <= selectedVoyage.getAvailableSeats().length; seat++) {
                if (!selectedVoyage.isSeatAvailable(seat)) {
                    if (selectedVoyage instanceof PremiumBus && ((PremiumBus) selectedVoyage).isSeatPremium(seat)) {
                        totalReturnFee += ((PremiumBus) selectedVoyage).getPremiumSeatPrice();
                    } else {
                        totalReturnFee += selectedVoyage.getStandardSeatPrice();
                    }
                }
            }
            return totalReturnFee;
        }
    }

    private static class ZReport {
        /**
         * Executes the Z_REPORT command, displaying details for all existing voyages sorted by their IDs, or notifies if there are no voyages.
         *
         * @param filePath    The file path where the output of the command will be written.
         * @param commandLine An array containing command line arguments.
         * @param vehicles    A list of vehicles representing the voyages to be included in the report.
         */
        private static void zReport(String filePath, String[] commandLine, List<Vehicle> vehicles) {
            if (commandLine.length != 1) {
                writeErroneousCommandUsage(filePath, "Z_REPORT");
                return;
            }

            if (vehicles.isEmpty()) {
                writeWithNewLine(filePath, "Z Report:");
                writeWithNewLine(filePath, "----------------");
                writeWithNewLine(filePath, "No Voyages Available!");
                writeWithNewLine(filePath, "----------------");
                return;
            }

            vehicles.sort(Comparator.comparingInt(Vehicle::getVoyageId));

            writeWithNewLine(filePath, "Z Report:");
            writeWithNewLine(filePath, "----------------");

            for (Vehicle vehicle : vehicles) {
                vehicle.displayVehicle(filePath);
                writeWithNewLine(filePath, "----------------");
            }
        }
    }
}
