package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.SRCVVariable;
import fr.insee.vtl.srcv.SRCVVariableList;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SRCVVariableListTest {

    @Test
    public void testFromTextInd() throws IOException {

        String individualsTableFileName = "src/main/resources/data/Dictionnaire_codes_SRCV2015_Ind.txt";
        String individualsTableText = FileUtils.readFileToString(new File(individualsTableFileName), "UTF-8");

        SRCVVariableList variableList = SRCVVariableList.fromText(individualsTableText);
        Set<String> types = new HashSet<>();
        for (SRCVVariable variable : variableList.getVariables().values()) {
            System.out.println(variable.getIdentifier());
            types.add(variable.getType());
        }
        System.out.println("All types: " + types);

    }

    @Test
    public void testStripNumbersStart() {

        String testText = "113 \nACTAOU1 Caractère \nActivité courant août 2014";
        assertEquals("ACTAOU1 Caractère\nActivité courant août 2014",
                String.join("\n", SRCVVariableList.stripNumbers(testText)));
        assertEquals("ACTAOU1 Caractère\nActivité courant août 2014",
                String.join("\n", SRCVVariableList.stripNumbers("\n" + testText)));

        testText = "Activité courant août 2014";
        assertEquals(testText, String.join("\n", SRCVVariableList.stripNumbers(testText)));
    }

    @Test
    public void testStripNumbersEnd() {

        String testText = "Question non posée en cas de proxy (posée si RESIND = 1) \n126 ";
        assertEquals("Question non posée en cas de proxy (posée si RESIND = 1)",
                String.join("\n", SRCVVariableList.stripNumbers(testText)));
    }

    @Test
    public void testStripNumbersBoth() {

        String testText = "128 \nCRSA_I Numérique \nMontant \n";
        assertEquals("CRSA_I Numérique\nMontant",
                String.join("\n", SRCVVariableList.stripNumbers(testText)));

        testText = "116 \nACTJAN2 Caractère \n  \nChamp : répondants au QI (+ de 16 ans) Pond. : PB040 \n1";
        assertEquals("ACTJAN2 Caractère\n\nChamp : répondants au QI (+ de 16 ans) Pond. : PB040",
                String.join("\n", SRCVVariableList.stripNumbers(testText)));
    }
}
