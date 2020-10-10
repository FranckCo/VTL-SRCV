package fr.insee.vtl.srcv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SRCVExtractor {

	/** Log4J2 logger */
	public static Logger logger = LogManager.getLogger();

	PDDocument pdfDocument = null;
	File pdfFile = null;

	public SRCVExtractor(File pdfFile) {

		if (pdfFile == null) throw new IllegalArgumentException("Null value for PDF File object");
		if (Files.notExists(pdfFile.toPath())) throw new IllegalArgumentException("Cannot find file " + pdfFile.getAbsolutePath());
		this.pdfFile = pdfFile;
		logger.info("Created new SRCV extractor for PDF file " + this.pdfFile.getAbsolutePath());
	}

	public void extractAllText(File textFile) throws IOException {

		try (Writer textWriter = new BufferedWriter(new FileWriter(textFile))) {
			try (PDDocument pdfDocument = PDDocument.load(pdfFile)) {
				PDFTextStripper pdfStripper = new PDFTextStripper();
				pdfStripper.writeText(pdfDocument, textWriter);
			}
		}
	}

	public void extractSectionText(File textFile, int start, int end) throws IOException {

		try (Writer textWriter = new BufferedWriter(new FileWriter(textFile))) {
			try (PDDocument pdfDocument = PDDocument.load(pdfFile)) {
				PDFTextStripper pdfStripper = new PDFTextStripper();
				pdfStripper.setStartPage(start);
				pdfStripper.setEndPage(end);
				pdfStripper.writeText(pdfDocument, textWriter);
			}
		}
	}


	public void extractAllTextStruct(File textFile) throws IOException {

		try (Writer textWriter = new BufferedWriter(new FileWriter(textFile))) {
			try (PDDocument pdfDocument = PDDocument.load(pdfFile)) {
				List<COSObject> cosObjects = pdfDocument.getDocument().getObjects();
				for (COSObject cosObject : cosObjects) {
					textWriter.write(cosObject.toString());
				}
			}
		}
	}

	public static void main(String[] args) {

		String staticInFilePath = "src/main/resources/data/Dictionnaire_codes_SRCV2015.pdf";
		String outFilePath = "src/main/resources/data/Dictionnaire_codes_SRCV2015.txt";

		File pdfFile = new File(staticInFilePath);
		try {
			PDDocument document = PDDocument.load(pdfFile );
			System.out.println(document.getNumberOfPages());

			PDFTextStripper pdfStripper = new PDFTextStripper();
			String text = pdfStripper.getText(document);

			Files.write(Paths.get(outFilePath), text.getBytes());

			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}
