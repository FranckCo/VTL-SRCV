package fr.insee.vtl.srcv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Description of a SRCV variable.
 */
public class SRCVVariable {

    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    /** Variable code */
    String identifier = null;
    /** Variable type */
    String type = null; // Replace by enumeration?
    /** Variable description */
    String description = null;
    /** Code list */
    SortedMap<String, String> codeList = null;
    /** Variable domain */
    String domain = null;
    /** Variable weight */
    String weight = null;


    public SRCVVariable() {

        codeList = new TreeMap<>();
    }

    /**
     * Creates a variable description from lines of text resulting from PDF text extraction.
     *
     * @param lines The lines of text that contain the variable description.
     */
    public static SRCVVariable fromLines(List<String> lines) {

        SRCVVariable variable = new SRCVVariable();
        String[] firstLine = lines.get(0).split(" ");
        variable.setIdentifier(firstLine[0].trim());
        variable.setType(firstLine[1].trim());
        logger.debug("Reading variable " + variable.getIdentifier() + " from " + lines.size() + " lines");

        return variable;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SortedMap<String, String> getCodeList() {
        return codeList;
    }

    public void setCodeList(SortedMap<String, String> codeList) {
        this.codeList = codeList;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
