package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.SRCVExtractor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class SRCVExtractorTest {

    @Test
    public void testExtractAll() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015.pdf"));
        extractor.extractAllText(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015.txt"));
    }

    @Test
    public void testExtractIndividualsTable() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015.pdf"));
        extractor.extractSectionText(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015_Ind.txt"),113, 174);
    }

    @Test
    public void testExtractHouseholdsTable() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015.pdf"));
        extractor.extractSectionText(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015_Hou.txt"),45, 109);
    }

    @Test
    public void testExtractAllStruct() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015.pdf"));
        extractor.extractAllTextStruct(new File("src/main/resources/data/Dictionnaire_codes_SRCV2015.txt"));
    }
}
