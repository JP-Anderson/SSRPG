package util.csv;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class CSVReader {

  public static String fileName;
  public static CSV output;
  public final static String datDirectory = "C:\\Workspaces\\SSRPG\\dat\\";

  public static CSV readCSV(String filename) {
    CSVReader reader = new CSVReader(filename);
    File file = reader.findFile(datDirectory + fileName + ".csv");
    return new CSV(reader.readLineByLine(file));
  }

  private CSVReader(String file) {
    fileName = file;
  }

  private File findFile(String filePath) {
    return new File(filePath);
  }

  private ArrayList<ArrayList<String>> readLineByLine(File f) {
    ArrayList<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();
    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
      String line;
      int lineCount = 0;
      while ((line = br.readLine()) != null) {
         rows.add(parseLine(line));
         lineCount++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rows;
  }

  private ArrayList<String> parseLine(String line) {
    ArrayList<String> newRow = new ArrayList<String>();
    String[] commaSeperatedValues = line.split(",");
    for (String v : commaSeperatedValues) {
      newRow.add(v);
    }
    return newRow;
  }







}
