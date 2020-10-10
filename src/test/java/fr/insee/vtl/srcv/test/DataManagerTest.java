package fr.insee.vtl.srcv.test;

import fr.insee.vtl.srcv.Configuration;
import fr.insee.vtl.srcv.DataManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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
}
