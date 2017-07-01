package util.dataload.csv.loaders;

import map.GridMap;
import util.dataload.csv.MapCSVReader;

public class MapLoader {
		
	public static GridMap loadMap() {
		MapCSVReader.readCSV("map");
		return MapCSVReader.getMap("map");
	}

}
