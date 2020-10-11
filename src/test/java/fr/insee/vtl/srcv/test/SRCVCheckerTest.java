package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.Configuration;
import fr.insee.vtl.srcv.SRCVChecker;
import fr.insee.vtl.srcv.SRCVVariable;
import fr.insee.vtl.srcv.SRCVVariableList;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SRCVCheckerTest {

    // Count case where a line starts with a space
    @Test
    public void testGetUndocumentedVariables() throws IOException {

        SRCVChecker checker = new SRCVChecker();
        SortedSet<String> undocumentedVariables = checker.getUndocumentedVariables();
        System.out.println("Undocumented variables:");
        for (String undocumentedVariable : undocumentedVariables) System.out.println(undocumentedVariable);
    }

    @Test
    public void testGetDocumentedVariables() throws IOException {

        SRCVChecker checker = new SRCVChecker();
        SortedSet<String> documentedVariables = checker.getDocumentedVariables();
        System.out.println("Documented variables:");
        for (String documentedVariable : documentedVariables) System.out.println(documentedVariable);
    }

    @Test
    public void testChecksLineStartsWithSpace() throws IOException {

        String individualsTableText = FileUtils.readFileToString(new File(Configuration.SRCV_TXT_IND_TABLE), "UTF-8");
        SRCVVariableList variableList = SRCVVariableList.fromText(individualsTableText);
        List<SRCVVariable> matchingVariables = new ArrayList<>();
        for (SRCVVariable variable : variableList.getVariables().values()) {
            if (SRCVChecker.checksLineStartsWithSpace(variable).size() > 0)  matchingVariables.add(variable);
        }

        if (matchingVariables.size() > 0) {
            System.out.println("List of variable descriptions which contain at least one line starting with a space:");
            for (SRCVVariable matchingVariable : matchingVariables) {
                System.out.println(matchingVariable);
            }
        }
    }
}
