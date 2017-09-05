package util.dataload.csv.loaders;

import map.GridMap;
import util.dataload.csv.MapCSVReader;

public class MapLoader {
		
	public static GridMap loadMap() {
		return MapCSVReader.getMap("map");
	}

}
