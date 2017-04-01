package util.dataload.csv.loaders;

import org.junit.Test;

import base.SsrpgTest;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import static org.junit.Assert.*;

public class PlanetsTest extends SsrpgTest {

	@Test
	public void getPlanetReturnsCorrectPlanetAtIndexZero() {
		assertTrue(Planets.instance.getPlanet(0).name.equals("Earth"));
	}

	@Test
	public void planetsReturnsCorrectCount() {
		CSV planetsCSV = CSVReader.readCSV("planets");
		assertTrue(Planets.loadPlanetsAsList("planets").size() == planetsCSV.rows-1);
	}

}