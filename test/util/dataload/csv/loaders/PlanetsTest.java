package util.dataload.csv.loaders;

import org.junit.jupiter.api.Test;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import static org.junit.jupiter.api.Assertions.*;
import static util.dataload.csv.loaders.Planets.getPlanet;
import static util.dataload.csv.loaders.Planets.planets;

class PlanetsTest {

	@Test
	void getPlanetReturnsCorrectPlanetAtIndexZero() {
		assertTrue(getPlanet(0).name.equals("Earth"));
	}

	@Test
	void planetsReturnsCorrectCount() {
		CSV planetsCSV = CSVReader.readCSV("planets");
		assertTrue(planets().size() == planetsCSV.rows-1);
	}

}