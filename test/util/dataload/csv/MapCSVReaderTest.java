package util.dataload.csv;

import map.GridMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapCSVReaderTest {

	private static final String MAP_FILE = "test\\test_map";

	@Test
	public void zonesLoadCorrectlyWithGetMap() {
		GridMap map = getMap();
//      0 1 2 3 4
//		--------
//	0|	1,1,1,1,0
//	1|	1,1,1,0,0
//	2|	1,1,1,0,0
//	3|	1,1,1,0,0
//	4|	0,0,2,2,2

		assertTrue(map.getSquareAt(1,3).zone.id == 1);
		assertTrue(map.getSquareAt(2,4).zone.id == 2);
		assertTrue(map.getSquareAt(3,0).zone.id == 1);
		assertTrue(map.getSquareAt(4,0).zone.id == 0);
	}

	private GridMap getMap() {
		return MapCSVReader.getMap(MAP_FILE);
	}

}