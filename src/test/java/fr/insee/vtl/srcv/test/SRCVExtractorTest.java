package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.Configuration;
import fr.insee.vtl.srcv.SRCVExtractor;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class SRCVExtractorTest {

    @Test
    public void testExtractAll() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File(Configuration.SRCV_PDF_CODE_DICT));
        extractor.extractAllText(new File(Configuration.SRCV_TXT_CODE_DICT));
    }

    @Test
    public void testExtractIndividualsTable() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File(Configuration.SRCV_PDF_CODE_DICT));
        extractor.extractSectionText(new File(Configuration.SRCV_TXT_IND_TABLE),113, 174);
    }

    @Test
    public void testExtractHouseholdsTable() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File(Configuration.SRCV_PDF_CODE_DICT));
        extractor.extractSectionText(new File(Configuration.SRCV_TXT_HOU_TABLE),45, 109);
    }

    @Test
    public void testExtractAllStruct() throws IOException {

        SRCVExtractor extractor = new SRCVExtractor(new File(Configuration.SRCV_PDF_CODE_DICT));
        extractor.extractAllTextStruct(new File(Configuration.SRCV_TXT_CODE_DICT));
    }
}
