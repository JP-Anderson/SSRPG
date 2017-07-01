package arch.sessions;

import java.util.ArrayList;

import arch.interfaces.MapSessionInterface;
import events.EventRunner;
import events.events.BanditEvent;
import events.events.CargoCheckEvent;
import goods.GoodsForSale;
import map.GridMap;
import map.GridPoint;
import map.gridsquares.GridSquare;
import map.gridsquares.OutOfBoundsGridSquare;
import map.gridsquares.Planet;
import ship.PlayerShip;
import util.dataload.csv.CSV;
import util.dataload.csv.CSVReader;
import util.dataload.csv.MapCSVReader;
import util.dataload.csv.loaders.Planets;

public class MapSession implements MapSessionInterface {
	
	private GridMap map;
	private PlayerShip p1;
	private ArrayList<GoodsForSale> goods;
	private boolean changes = false;

	@Override
	public void start(PlayerShip player) {
		p1 = player;
		CSV mapCsv = MapCSVReader.readCSV("map");
		GridPoint start = new GridPoint(3, 6);
		map = MapCSVReader.getMap("map");
		//map = GridMap.generateGridMap(11, 7);

		p1.initialiseMap(start, map);
		initMapAndGoodsList();
	}

	private final String xLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private void initMapAndGoodsList() {
		CSV goodsCSV = CSVReader.readCSV("goods");
		goods = new ArrayList<GoodsForSale>();

		for (int i = 1; i < goodsCSV.rows; i++) {
			ArrayList<String> values = goodsCSV.getZeroIndexedRow(i);
			goods.add(new GoodsForSale(Integer.parseInt(values.get(0)),
					values.get(1),
					Integer.parseInt(values.get(2)) == 0 ? false : true,
					Integer.parseInt(values.get(3))
			));
		}

		for (Planet planet : Planets.loadPlanetsAsList("planets")) {
			map.populateGridSquare(planet);
		}

	}
	
	@Override
	public ArrayList<ArrayList<GridSquare>> gridMap() {
		changes = false;
		ArrayList<ArrayList<GridPoint>> points = p1.getScanner().getGridIndexesAsArrayLists();
		ArrayList<ArrayList<GridSquare>> mapSegment = new ArrayList<>();
		
		for (ArrayList<GridPoint> pointRow : points) {
			ArrayList<GridSquare> gridRow = new ArrayList<GridSquare>();
			for (GridPoint point : pointRow) {
				try {
					gridRow.add(map.getSquareAt(point));
				} catch (IndexOutOfBoundsException e) {
					gridRow.add(new OutOfBoundsGridSquare(point));
				} catch (Exception e) {
					
				}
			}
			mapSegment.add(gridRow);
		}
		
		return mapSegment;		
	}

	@Override
	public boolean tryToTravel(GridPoint destination) {
		try {
			GridSquare destinationSquare = map.getSquareAt(destination);
			int distance = p1.getLocation().comparePoints(destination);

			boolean destinationIsAPlanet = destinationSquare instanceof Planet;

			String destinationString;
			if (destinationIsAPlanet) {
				Planet planet = (Planet) destinationSquare;
				destinationString = planet.name;
			} else {
				destinationString = "nowhere";
			}

			boolean canTravel = p1.travel(destination, distance);
			if (canTravel) {
				changes = true;
				return true;
			} else {
//				not enough fuel
			}
		} catch (IndexOutOfBoundsException ioobe) {
//			Square out of bounds!
			
		}
		return false;
	}

	@Override
	public boolean changes() {
		return changes;
	}

}
