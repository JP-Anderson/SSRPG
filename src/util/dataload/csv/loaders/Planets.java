package util.dataload.csv.loaders;

import map.GridPoint;
import map.gridsquares.Planet;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import java.util.ArrayList;
import java.util.List;

public class Planets {

	private static List<Planet> planets;

	public static Planet getPlanet(int index) {
		if (planets == null) readPlanets();
		return planets.get(index);
	}

	public static List<Planet> planets() {
		if (planets == null) readPlanets();
		return planets;
	}

	private static void readPlanets() {
		planets = new ArrayList<>();
		CSV planetsCSV = CSVReader.readCSV("planets");
		for (int i = 1; i < planetsCSV.rows; i++) {
			ArrayList<String> planet = planetsCSV.getZeroIndexedRow(i);
			int id = Integer.parseInt(planet.get(0));
			String name = planet.get(1);
			int gridX = Integer.parseInt(planet.get(2));
			int gridY = Integer.parseInt(planet.get(3));
			int marketSize = Integer.parseInt(planet.get(4));
			planets.add(new Planet(id,
					name,
					new GridPoint(gridX, gridY),
					marketSize));
		}
	}


}
