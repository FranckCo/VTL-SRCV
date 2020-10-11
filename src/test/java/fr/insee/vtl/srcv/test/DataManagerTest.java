package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.Configuration;
import fr.insee.vtl.srcv.DataManager;
import fr.insee.vtl.srcv.SRCVVariableList;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataManagerTest {

    @Test
    public void testReadFile() throws IOException {

        DataManager csvReader = new DataManager(new File(Configuration.SRCV_CSV_FILE_NAME));
        csvReader.readFile();
    }

    @Test
    public void testGetHeaders() throws IOException {

        DataManager csvReader = new DataManager(new File(Configuration.SRCV_CSV_FILE_NAME));
        System.out.println(csvReader.getHeaders());
        System.out.println(csvReader.getHeaders().stream().map(String::toUpperCase).collect(Collectors.toList()));
    }

    @Test
    public void testExportAsJSON() throws IOException {

        String individualsTableText = FileUtils.readFileToString(new File(Configuration.SRCV_TXT_IND_TABLE), "UTF-8");
        SRCVVariableList variableList = SRCVVariableList.fromText(individualsTableText);

        DataManager jsonExporter = new DataManager(new File(Configuration.SRCV_CSV_FILE_NAME));
        List<String> columns = Arrays.asList("DEPNAIS", "ETAMATRI");
        File jsonOutput = new File(Configuration.SRCV_JSON_IND_TABLE);

        jsonExporter.exportAsJSON(jsonOutput, columns, variableList);
    }
}
