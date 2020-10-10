package fr.insee.vtl.srcv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

public class DataManager {

    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    File csvFile = null;

    public DataManager(File csvFile) {

        if (csvFile == null) throw new IllegalArgumentException("Null value for CSV File object");
        if (Files.notExists(csvFile.toPath())) throw new IllegalArgumentException("Cannot find file " + csvFile.getAbsolutePath());
        this.csvFile = csvFile;
        logger.info("Created new SRCV data manager for CSV file " + this.csvFile.getAbsolutePath());
    }

    public void readFile() throws IOException {

        Reader in = new FileReader(this.csvFile);
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        List<String> headers = records.getHeaderNames();
        System.out.println(headers);
        for (CSVRecord record : records) {
            String dwellingIdentifier = record.get("IDENT_LOG");
            System.out.println(dwellingIdentifier);
        }
        records.close();
    }

    /**
     * Returns the column names of the CSV file as a list of strings.
     *
     * @return A list of strings corresponding to the column names.
     * @throws IOException In case of problem reading the CSV file.
     */
    public List<String> getHeaders() throws IOException {

        List<String> headers = null;
        Reader in = new FileReader(this.csvFile);
        try (CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in)) {
            headers = records.getHeaderNames();
        }
        return headers;
    }
}
