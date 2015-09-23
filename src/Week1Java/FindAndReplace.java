package Week1Java;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindAndReplace {

    private String inputFilePath;
    private String replacementsFilePath;
    private String outputFilePath;
    private List<String> outputLines;
    private Map<String, String> replacementStrings;
    private final Logger logger = Logger.getLogger(FindAndReplace.class);

    /**
     * Class constructor with zero arguments.
     */
    public FindAndReplace() {
        outputLines = new ArrayList<String>();
        replacementStrings = new HashMap<String,String>();
    }

    /**
     * Class constructor with one argument - an array of strings. Assigns this array to a class variable.
     * @param inputFile the file path for the input file
     * @param replacementsFile the file path for the file containing replacement strings
     * @param outputFile the file path where the output will be created
     */
    public FindAndReplace(String inputFile, String replacementsFile, String outputFile) {
        this();
        inputFilePath = inputFile;
        replacementsFilePath = replacementsFile;
        outputFilePath = outputFile;

    }

    /**
     * Driving method of the FindAndReplace class. References class variable with the string arguments array, and calls
     * processing methods.
     */
    public void run() {

        openReplacementsFile();
        openInputFile();
        writeOutputFile();
    }

    /**
     * Creates a new BufferedReader object to open and read the file containing keywords and replacement strings.
     * Passes the reader object to a processing method.
     */
    private void openReplacementsFile() {
        try (BufferedReader replacementReader = new BufferedReader(new FileReader(replacementsFilePath))) {
            processReplacementStrings(replacementReader);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Uses reader object to loop through the file of keywords and replacements, splitting the lines into key-value
     * pairs and storing them in an array of strings for further processing.
     * @param reader a BufferedReader object for the keyword and replacements file
     * @throws IOException
     */
    private void processReplacementStrings(BufferedReader reader) throws IOException {
        String line = "";
        String[] replacementArray = null;

        while (reader.ready()) {
            line = reader.readLine();
            replacementArray = line.split(",");
            processReplacementArray(replacementArray);
        }
    }

    /**
     * Takes in the array of keyword and replacement pairs, splits them, and calls a processing method on them.
     * @param replacementArray an array of keyword-replacement strings
     */
    private void processReplacementArray(String[] replacementArray) {
        String[] replacementSplit = null;

        for (int i = 0; i < replacementArray.length; i++) {
            String token = replacementArray[i];
            replacementSplit = token.split(":");
            processSplitReplacementArray(replacementSplit);
        }
    }

    /**
     * Takes in an array containing a keyword and a replacement value, and adds them to a class map variable.
     * @param splitArray an array of a key string and a replacement value string
     */
    private void processSplitReplacementArray(String[] splitArray) {
        if (splitArray.length == 2) {
            replacementStrings.put(splitArray[0], splitArray[1]);
        } else {
            //something went wrong??
        }
    }

    /**
     * Creates a new BufferedReader object to open and read the file containing text to be replaced.
     */
    private void openInputFile() {
        try (BufferedReader inputReader = new BufferedReader(new FileReader(inputFilePath))) {
            processInputFile(inputReader);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void processInputFile(BufferedReader reader) throws IOException {
        String line = "";

        while (reader.ready()) {
            line = reader.readLine();
            processInputLine(line);
        }
    }

    private void processInputLine(String line) {
        StringBuffer bufferedLine = new StringBuffer(line);
        Pattern searchPattern = Pattern.compile("<<[\\w]*>>");
        StringBuffer newLine = new StringBuffer();
        Matcher match = searchPattern.matcher(bufferedLine);

        if (match.find()) {
            String foundMatch = match.group();
            String replacementValue = replacementStrings.get(foundMatch);
            match.appendReplacement(newLine, replacementValue);
            while (match.find()) {
                foundMatch = match.group();
                replacementValue = replacementStrings.get(foundMatch);
                match.appendReplacement(newLine, replacementValue);
            }
        } else {
            newLine.append(bufferedLine);
        }

        outputLines.add(newLine.toString());
    }

    private void writeOutputFile() {
        try (PrintWriter writer = new PrintWriter(outputFilePath)) {
            writeFileLoop(writer);
            writer.close();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }
    }

    private void writeFileLoop(PrintWriter writer) {
        for (String line : outputLines) {
            writer.println(line);
        }
    }

}
