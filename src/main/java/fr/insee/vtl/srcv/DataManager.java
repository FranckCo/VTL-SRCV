package fr.insee.vtl.srcv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

public class DataManager {

    /** Log4J2 logger */
    public static Logger logger = LogManager.getLogger();

    File csvFile;

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

        List<String> headers;
        Reader reader = new FileReader(this.csvFile);
        try (CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            headers = records.getHeaderNames();
        }
        return headers;
    }

    public void exportAsJSON(File jsonFile, List<String> columns, SRCVVariableList variables) throws IOException {

        // TODO Replace following section by two static methods in SRCVVariable
        // Compute VTL types and roles for the columns
        SortedMap<String, SRCVVariable> variableMap = variables.getVariables();
        Map<String, String> typedColumns = new HashMap<>();
        Map<String, String> roles = new HashMap<>();
        for (String column : columns) {
            if (variableMap.containsKey(column)) {
                if (variableMap.get(column).getType().equals("Numérique")) {
                    typedColumns.put(column, "NUMBER");
                    roles.put(column, "MEASURE");
                }
                else {
                    typedColumns.put(column, "STRING");
                    roles.put(column, "ATTRIBUTE");
                }
            } else {
                // Unknown variables are treated like strings
                typedColumns.put(column, "STRING");
                if (column.startsWith("IDENT")) roles.put(column, "IDENTIFIER");
                else roles.put(column, "ATTRIBUTE");
            }
        }

        JSONObject trevasObject = new JSONObject();
        // Write the data structure part
        JSONArray dataStructure = new JSONArray();
        for (String column : columns) {
            JSONObject variableObject = new JSONObject();
            variableObject.put("name", column);
            variableObject.put("type", typedColumns.get(column));
            variableObject.put("role", roles.get(column));
            dataStructure.add(variableObject);
        }
        trevasObject.put("dataStructure", dataStructure);

        Reader reader = new FileReader(this.csvFile);
        try (CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {
            List<String> headers = records.getHeaderNames().stream().map(String::toUpperCase).collect(Collectors.toList());
            // Check that all requested columns are actual headers
            if (!headers.containsAll(columns)) throw new InvalidParameterException("Some requested columns do not exist in the data file");
            JSONArray dataPoints = new JSONArray();
            for (CSVRecord record : records)  {
                JSONArray recordArray = new JSONArray();
                for (String column : columns) {
                    recordArray.add(record.get(column)); // Leave all as strings for now
                }
                dataPoints.add(recordArray);
            }
            trevasObject.put("dataPoints", dataPoints);
        }
        // trevasObject.writeJSONString(new FileWriter(jsonFile)); This produces a truncated file
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            fileWriter.write(trevasObject.toJSONString());
            fileWriter.flush();
        }
    }

}
