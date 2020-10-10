package fr.insee.vtl.srcv;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SRCVVariableList {

    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    SortedMap<String, SRCVVariable> variables;

    public SRCVVariableList() {

        this.variables = new TreeMap<>();
    }

    /**
     * Extracts a list of SRCV variables descriptions from text issued from PDF extraction.
     *
     * @param text The text containing the descriptions of variables.
     * @return A list of variable descriptions.
     */
    public static SRCVVariableList fromText(String text) {

        SRCVVariableList variableList = new SRCVVariableList();

        // We first split the text according to triple line breaks (one chunk corresponds to one or two variables)
        String[] chunks = text.split("\\n\\s+\\n\\s+\\n");
        logger.debug("Text split into large chunks, number of chunks found: " + chunks.length);
        for (String chunk : chunks) {
            // It happens that page numbers are isolated in the middle of a chunk (example: 121 in individuals table)
            // The following regex will match these cases, so we can split the chunk in two
            // More usually, page numbers are at the start of the chunk. Very rarely (e.g. 126) at the end
            String innerPageNumberRegex = "[\\S\\s]*(\\n\\s*\\n\\d+\\s*\\n\\s*\\n)[\\S\\s]*";
            Pattern pattern = Pattern.compile(innerPageNumberRegex);
            Matcher matcher = pattern.matcher(chunk);
            if (matcher.matches()) {
                // Inner line numbers are always between two variable descriptions
                String pageNumberSequence = matcher.group(1);
                logger.debug("Splitting chunk in two at line number " + StringUtils.normalizeSpace(pageNumberSequence));
                String[] smallChunks = chunk.split(pageNumberSequence);
                variableList.addVariable(SRCVVariable.fromLines(stripNumbers(smallChunks[0])));
                variableList.addVariable(SRCVVariable.fromLines(stripNumbers(smallChunks[1])));
            } else {
                variableList.addVariable(SRCVVariable.fromLines(stripNumbers(chunk)));
            }
        }
        logger.debug("Created variable list of size " + variableList);

        return variableList;
    }

    /**
     * Strips (page) numbers at the beginning or end of a chunk of text.
     * Also right trims each line and removes empty lines at the beginning or end of the text.
     * NB: heading space can be meaningful so we don't trim left.
     *
     * @param text The chunk of text to process.
     * @return The text stripped of numbers at beginning or end.
     */
    public static List<String> stripNumbers(String text) {

        // Transform the text into a list of strings
        List<String> lines = Stream.of(text.split(System.lineSeparator())).map(line -> StringUtils.stripEnd(line, " ")).collect(Collectors.toList());
        // List<String> lines = Stream.of(text.split("\\n")).map(String::trim).collect(Collectors.toList());
        logger.debug("Removing heading and trailing empty lines and page numbers from text with " + lines.size() + " lines");
        int realStart = 0;
        while(realStart <= lines.size()) {
            String test = lines.get(realStart);
            if ((test.length() == 0) || StringUtils.isNumeric(test)) {
                if (test.length() != 0) logger.debug("Removing page number " + test + " at the beginning of the text");
                realStart++;
            } else break;
        }
        int realEnd = lines.size() - 1;
        while (realEnd >= 0) {
            String test = lines.get(realEnd);
            if ((test.length() == 0) || StringUtils.isNumeric(test)) {
                if (test.length() != 0) logger.debug("Removing page number " + test + " at the end of the text");
                realEnd--;
            } else break;
        }
        logger.debug("Returning lines indexed " + realStart + " to " + realEnd);
        return lines.subList(realStart, realEnd + 1);
    }

    public SortedMap<String, SRCVVariable> getVariables() {
        return variables;
    }

    public void setVariables(SortedMap<String, SRCVVariable> variables) {
        this.variables = variables;
    }

    public void addVariable(SRCVVariable variable) {
        this.variables.put(variable.getIdentifier(), variable);
    }
}
