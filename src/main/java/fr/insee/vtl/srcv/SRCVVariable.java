package fr.insee.vtl.srcv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Description of a SRCV variable.
 */
public class SRCVVariable {

    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    /** Source text */
    List<String> sourceLines;
    /** Variable code */
    String identifier = null;
    /** Variable type */
    String type = null; // Replace by enumeration?
    /** Variable description */
    String description = null;
    /** Code list */
    SortedMap<String, String> codeList;
    /** Variable domain */
    String domain = null;
    /** Variable weight */
    String weight = null;

    public SRCVVariable() {

        sourceLines = new ArrayList<>();
        codeList = new TreeMap<>();
    }

    /**
     * Creates a variable description from lines of text resulting from PDF text extraction.
     *
     * @param lines The lines of text that contain the variable description.
     */
    public static SRCVVariable fromLines(List<String> lines) {

        SRCVVariable variable = new SRCVVariable();
        variable.sourceLines = new ArrayList<>(lines);

        String[] firstLine = lines.get(0).split(" ");
        variable.setIdentifier(firstLine[0].trim());
        variable.setType(firstLine[1].trim());
        logger.debug("Reading variable " + variable.getIdentifier() + " from " + lines.size() + " lines");

        return variable;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Variable SRCV ").append(identifier).append(" (type ").append(type).append(")\n");
        builder.append("Source:\n");
        for (String sourceLine : sourceLines) builder.append(sourceLine).append("\n");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SRCVVariable that = (SRCVVariable) o;
        return sourceLines.equals(that.sourceLines) &&
                identifier.equals(that.identifier) &&
                type.equals(that.type) &&
                description.equals(that.description) &&
                Objects.equals(codeList, that.codeList) &&
                domain.equals(that.domain) &&
                weight.equals(that.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, type, description, codeList, domain, weight);
    }

    public List<String> getSourceLines() { return sourceLines; }

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

    public String getWeight() {  return weight; }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
