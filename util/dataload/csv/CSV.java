package util.dataload.csv;

import java.util.ArrayList;

public class CSV {

  private ArrayList<ArrayList<String>> data;
  public final int rows;
  public final int cols;

  public CSV(ArrayList<ArrayList<String>> newRows) {
    data = newRows;
    rows = data.size();
    cols = data.get(0).size();
  }

  public ArrayList<String> getZeroIndexedRow(int rowHeight) {
    return data.get(rowHeight);
  }

}
