package util.dataload.csv;

import map.GridMap;
import map.GridPoint;
import map.gridsquares.GridSquare;
import map.zones.Zone;

public class MapCSVReader extends CSVReader {

	private MapCSVReader(String file) {
		super(file);
	}

	public static GridMap getMap(String filename) {
		MapCSVReader reader = new MapCSVReader(filename);
		CSV mapCsv = reader.readCSV(filename);

		CSV zones = reader.readCSV("test\\test_zones");

		GridMap gridMap = GridMap.generateGridMap(mapCsv.cols, mapCsv.rows);

		for (int i = 0; i < mapCsv.rows; i++) {
			for (int j = 0; j < mapCsv.cols; j++) {
				int zoneInt = mapCsv.getZeroIndexedRowAsInts(i).get(j);
				GridSquare square = gridMap.getSquareAt(new GridPoint(j,i));

				square.zone = new Zone(zoneInt,zones.getZeroIndexedRow(zoneInt+1).get(1));
			}
		}

		return gridMap;
	}

}
