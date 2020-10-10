package fr.insee.vtl.srcv;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SRCVChecker {


    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    public SortedSet<String> getUndocumentedVariables() throws IOException {

        // Get CSV data file headers (capitalizing them)
        DataManager csvReader = new DataManager(new File(Configuration.SRCV_CSV_FILE_NAME));
        List<String> headers = csvReader.getHeaders().stream().map(String::toUpperCase).collect(Collectors.toList());
        // Get the list of documented variables from code dictionary
        String individualsTableText = FileUtils.readFileToString(new File(Configuration.SRCV_TXT_IND_TABLE), "UTF-8");
        SRCVVariableList variableList = SRCVVariableList.fromText(individualsTableText);
        // Undocumented variables are the ones in the headers but not in the code dictionary
        headers.removeAll(variableList.getVariables().keySet());
        SortedSet<String> undocumented = new TreeSet<String>(headers);

        return undocumented;
    }
}
