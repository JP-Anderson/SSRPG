package util.dataload.csv;

import map.GridMap;
import org.junit.Test;

import base.SsrpgTest;

import static org.junit.Assert.*;

public class MapCSVReaderTest extends SsrpgTest {

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

		assertTrue(map.getSquareAt(1,3).getZone().id == 1);
		assertTrue(map.getSquareAt(2,4).getZone().id == 2);
		assertTrue(map.getSquareAt(3,0).getZone().id == 1);
		assertTrue(map.getSquareAt(4,0).getZone().id == 0);
	}

	private GridMap getMap() {
		return MapCSVReader.getMap(MAP_FILE);
	}

}