package fr.insee.vtl.srcv;

public class Configuration {

    // Input files

    /** CSV file containing the SRCV test data */
    public static String SRCV_CSV_FILE_NAME = "src/main/resources/data/extrait_srcv.csv";
    /** SRCV dictionary of codes in PDF */
    public static String SRCV_PDF_CODE_DICT = "src/main/resources/data/Dictionnaire_codes_SRCV2015.pdf";
    /** Complete text extract of the PDF dictionary of codes */
    public static String SRCV_TXT_CODE_DICT = "src/main/resources/data/Dictionnaire_codes_SRCV2015.txt";
    /** Text extract of the PDF dictionary of codes for individuals table */
    public static String SRCV_TXT_IND_TABLE = "src/main/resources/data/Dictionnaire_codes_SRCV2015_Ind.txt";
    /** Text extract of the PDF dictionary of codes for households table */
    public static String SRCV_TXT_HOU_TABLE = "src/main/resources/data/Dictionnaire_codes_SRCV2015_Hou.txt";
}
