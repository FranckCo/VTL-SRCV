package fr.insee.vtl.srcv;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SRCVChecker {

    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    /**
     * Get the list of undocumented variables (in the data files but not in the dictionary of codes).
     *
     * @return The list of undocumented variables as an ordered set.
     * @throws IOException In case of error reading source files.
     */
    public SortedSet<String> getUndocumentedVariables() throws IOException {

        // Get CSV data file headers (capitalizing them)
        DataManager csvReader = new DataManager(new File(Configuration.SRCV_CSV_FILE_NAME));
        List<String> headers = csvReader.getHeaders().stream().map(String::toUpperCase).collect(Collectors.toList());
        // Get the list of documented variables from code dictionary
        String individualsTableText = FileUtils.readFileToString(new File(Configuration.SRCV_TXT_IND_TABLE), "UTF-8");
        SRCVVariableList variableList = SRCVVariableList.fromText(individualsTableText);
        // Undocumented variables are the ones in the headers but not in the code dictionary
        headers.removeAll(variableList.getVariables().keySet());

        return new TreeSet<>(headers);
    }

    /**
     * Get the list of documented variables (in the data files and the dictionary of codes).
     *
     * @return The list of documented variables as an ordered set.
     * @throws IOException In case of error reading source files.
     */
    public SortedSet<String> getDocumentedVariables() throws IOException {

        // Get CSV data file headers (capitalizing them)
        DataManager csvReader = new DataManager(new File(Configuration.SRCV_CSV_FILE_NAME));
        List<String> headers = csvReader.getHeaders().stream().map(String::toUpperCase).collect(Collectors.toList());
        // Get the list of documented variables from code dictionary
        String individualsTableText = FileUtils.readFileToString(new File(Configuration.SRCV_TXT_IND_TABLE), "UTF-8");
        SRCVVariableList variableList = SRCVVariableList.fromText(individualsTableText);
        // Undocumented variables are the ones in the headers and in the code dictionary
        headers.retainAll(variableList.getVariables().keySet());

        return new TreeSet<>(headers);
    }

    /**
     * Checks if a variable description contains a line starting with a space.
     *
     * @param variable The SRCV variable to check.
     * @return The list of lines in the variable description that start with a space.
     */
    public static List<String> checkLineStartsWithSpace(SRCVVariable variable) {

        List<String> startingWithSpace = new ArrayList<>();
        for (String line : variable.getSourceLines())  if (line.startsWith(" ")) startingWithSpace.add(line);

        return startingWithSpace;
    }

    /**
     * Checks if a variable description does not end with a line describing the domain.
     *
     * @param variable The SRCV variable to check.
     * @return The last description line if it does not describe the domain, null otherwise.
     */
    public static String checkDomainIsLast(SRCVVariable variable) {

        List<String> sourceLines = variable.getSourceLines();
        String lastSourceLine = sourceLines.get(sourceLines.size() - 1);

        if (!lastSourceLine.startsWith("Champ : ")) return lastSourceLine;
        return null;
    }

    /**
     * Checks if a variable description contains an empty line.
     *
     * @param variable The SRCV variable to check.
     * @return A boolean equal to true if the variable description contains an empty line and false otherwise.
     */
    public static boolean checkContainsEmptyLine(SRCVVariable variable) {

        for (String line : variable.getSourceLines()) if (line.length() == 0) return true;
        return false;
    }
}
