package util.dataload.csv.loaders;

import map.GridPoint;
import map.gridsquares.Planet;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;

import java.util.ArrayList;
import java.util.List;

public class Planets {

	public static Planets instance;
	private List<Planet> planets;
	private String csvInUse;

	private Planets(String csv) {
		instance = this;
		readPlanets(csv);
		csvInUse = csv;
	}

	public static Planets loadPlanets(String csv) {
		if (instance == null) return new Planets(csv);
		return instance;
	}

	public static List<Planet> loadPlanetsAsList(String csv) {
		if (instance == null) return new Planets(csv).planets;
		return instance.planets;
	}

	public Planet getPlanet(int index) {
		if (instance == null) return null;
		return planets.get(index);
	}

	private void readPlanets(String csv) {
		planets = new ArrayList<>();
		CSV planetsCSV = CSVReader.readCSV(csv);
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
