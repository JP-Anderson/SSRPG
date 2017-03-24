package map;

import map.gridsquares.EmptyGridSquare;
import map.gridsquares.GridSquare;
import ship.PlayerShip;
import util.rng.RNG;

import java.util.ArrayList;

public class GridMap {

	private static GridMap map = null;
	private int width;
	private int height;
	private static final ArrayList<ArrayList<GridSquare>> rows = new ArrayList<ArrayList<GridSquare>>();

	public static GridMap generateGridMap(int gridWidth, int gridHeight) {
		if (map == null) map = new GridMap(gridWidth, gridHeight);
		return map;
	}

	private GridMap(int w, int h) {
		width = w;
		height = h;
		for (int i = 0; i < h; i++) {
			rows.add(initializeRowWithEmptyGridSquares(i));
		}
	}

	private ArrayList<GridSquare> initializeRowWithEmptyGridSquares(int height) {
		ArrayList<GridSquare> newRow = new ArrayList<GridSquare>();
		for (int i = 0; i < width; i++) {
			newRow.add(EmptyGridSquare.generateEmptyGridSquare(new GridPoint(i, height)));
		}
		return newRow;
	}

	public void placePlayer(PlayerShip playerShip) {
		RNG rand = new RNG();
		int randomX = rand.randInt(0, width);
		int randomY = rand.randInt(0, height);
		playerShip.setLocation(new GridPoint(randomX, randomY));
	}

	public GridSquare getSquareAt(GridPoint searchPoint) {
		return rows.get(searchPoint.y).get(searchPoint.x);
	}

	public GridSquare getSquareAt(int width, int height) {
		return rows.get(height).get(width);
	}

	public void populateGridSquare(GridSquare gridSquare) {
		int x = gridSquare.gridPoint.x;
		int y = gridSquare.gridPoint.y;

		GridSquare squareToReplace = getSquareAt(gridSquare.gridPoint);
		gridSquare.zone = squareToReplace.zone;

		rows.get(y).set(x, gridSquare);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
