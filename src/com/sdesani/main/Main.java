package com.sdesani.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class Main {

	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final Object[] FILE_HEADER = { "AGE", "AMONTH", "AWEEKEND", "CHRON1", "CHRON2", "CHRON3", "CHRON4",
			"CHRON5", "CHRON6", "CHRON7", "CHRON8", "CHRON9", "CHRON10", "CHRON11", "CHRON12", "CHRON13", "CHRON14",
			"CHRON15", "DIED_VISIT", "DISCWT", "DISP_ED", "DQTR", "DX1", "DX2", "DX3", "DX4", "DX5", "DX6", "DX7",
			"DX8", "DX9", "DX10", "DX11", "DX12", "DX13", "DX14", "DX15", "DXCCS1", "DXCCS2", "DXCCS3", "DXCCS4",
			"DXCCS5", "DXCCS6", "DXCCS7", "DXCCS8", "DXCCS9", "DXCCS10", "DXCCS11", "DXCCS12", "DXCCS13", "DXCCS14",
			"DXCCS15", "ECODE1", "ECODE2", "ECODE3", "ECODE4", "EDEVENT", "E_CCS1", "E_CCS2", "E_CCS3", "E_CCS4",
			"FEMALE", "HCUPFILE", "HOSP_ED", "INJURY", "INJURY_CUT", "INJURY_DROWN", "INJURY_FALL", "INJURY_FIRE",
			"INJURY_FIREARM", "INJURY_MACHINERY", "INJURY_MVT", "INJURY_NATURE", "INJURY_POISON", "INJURY_SEVERITY",
			"INJURY_STRUCK", "INJURY_SUFFOCATION", "INTENT_ASSAULT", "INTENT_SELF_HARM", "INTENT_UNINTENTIONAL",
			"KEY_ED", "MULTINJURY", "NDX", "NECODE", "NEDS_STRATUM", "PAY1", "PAY2", "PL_NCHS2006", "TOTCHG_ED", "YEAR",
			"ZIPINC_QRTL" };

	public static void main(String args[]) throws IOException {

		String inputPath = System.getProperty("user.home") + "/student.csv";
		writeCSVfile(processInputFile(inputPath));

		System.out.println("Success");
	}

	private static List<List<String>> processInputFile(String inputFilePath) throws IOException {
		List<List<String>> values = null;
	    try{
	      File inputF = new File(inputFilePath);
	      InputStream inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

			// lines = Files.lines(path);
			values = br.lines().skip(1).map(line -> Arrays.asList(line.split(",")))
					.filter(list -> list.get(74).equals("1")) // keep only items where the name
					// is "Neda"
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Caught FileNotFoundException|IOException");
	    }
		return values;
	}

	private static void writeCSVfile(List<List<String>> records) {
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {

			// initialize FileWriter object
			fileWriter = new FileWriter(System.getProperty("user.home") + "/student.csv");

			// initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

			// Create CSV file header
			csvFilePrinter.printRecord(FILE_HEADER);

			// Write a new CSV file
			for (List<String> record : records) {
				csvFilePrinter.printRecord(record);
			}

			System.out.println("CSV file was created successfully !!!");

		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter/csvPrinter !!!");
				e.printStackTrace();
			}
		}

	}
}
