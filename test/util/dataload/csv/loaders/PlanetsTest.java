package util.dataload.csv.loaders;

import org.junit.jupiter.api.Test;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import static org.junit.jupiter.api.Assertions.*;

class PlanetsTest {

	@Test
	void getPlanetReturnsCorrectPlanetAtIndexZero() {
		assertTrue(Planets.instance.getPlanet(0).name.equals("Earth"));
	}

	@Test
	void planetsReturnsCorrectCount() {
		CSV planetsCSV = CSVReader.readCSV("planets");
		assertTrue(Planets.loadPlanetsAsList("planets").size() == planetsCSV.rows-1);
	}

}