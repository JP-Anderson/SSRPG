package ship;

import arch.view.View;
import map.GridMap;
import map.GridPoint;
import map.GridSquare;
import map.EmptyGridSquare;

import java.util.ArrayList;

public class Scanner {

	public static Scanner scanner = null;

	public final View view;

	public final int scannerGridSize;
	public final GridMap gridMap;
	public final static int SMALL = 3;
	public static final int blockSize = SMALL;

	private static GridPoint shipLocation;
	private ArrayList<ArrayList<GridPoint>> gridPointsInRange;
	private static final String xCoOrdinates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static Scanner getScanner(View view, int scannerSize, GridMap map, GridPoint shipGridPoint) {
		if (scanner == null) {
			scanner = new Scanner(view, scannerSize, map, shipGridPoint);
		}
		return scanner;
	}

	public void setShipLocation(GridPoint newLocation) {
		shipLocation = newLocation;
		getGridIndexesInScannerRange();
	}

	private Scanner(View view, int scannerSize, GridMap map, GridPoint shipGridPoint) {
		this.view = view;
		scannerGridSize = scannerSize;
		gridMap = map;
		shipLocation = shipGridPoint;
		getGridIndexesInScannerRange();
	}

	private void getGridIndexesInScannerRange() {
		int scannerRadius = (scannerGridSize - 1) / 2;
		int xIndex = shipLocation.x - scannerRadius;
		int yIndex = shipLocation.y - scannerRadius;
		gridPointsInRange = new ArrayList<ArrayList<GridPoint>>();
		for (int i = 0; i < scannerGridSize; i++) {
			gridPointsInRange.add(new ArrayList<GridPoint>());
			for (int j = 0; j < scannerGridSize; j++) {
				//view.outputHandler.sendStringToView("i " + i + "   j " + j);
				//view.outputHandler.sendStringToView("new GridPoint("+xIndex+","+yIndex+")");
				gridPointsInRange.get(i).add(new GridPoint(xIndex, yIndex));
				xIndex++;
			}
			xIndex = shipLocation.x - scannerRadius;
			yIndex++;
		}
	}

	public void scan() {
		printHeading();
		for (int rowIndex = 0; rowIndex < scannerGridSize; rowIndex++) {
			printRow(rowIndex);
		}
	}

	private void printHeading() {
		view.outputHandler.sendStringToView("  ");
		for (int colIndex = 0; colIndex < scannerGridSize; colIndex++) {
			for (int blockColIndex = 0; blockColIndex < blockSize; blockColIndex++) {
				if (indexIsInCentre(blockColIndex)) {
					int scannerCol = gridPointsInRange.get(0).get(colIndex).x;
					if (scannerCol >= 0) {
						view.outputHandler.sendStringToView(" " + xCoOrdinates.charAt(scannerCol) + " ");
					} else view.outputHandler.sendStringToView("   ");
				} else view.outputHandler.sendStringToView("   ");
			}
		}
		view.outputHandler.sendStringToView("");
		printHeadingUnderline();
	}

	private void printHeadingUnderline() {
		view.outputHandler.sendStringToView("  ");
		for (int colIndex = 0; colIndex < scannerGridSize; colIndex++) {
			printCharacterXTimes('_', blockSize * 3);
		}
		view.outputHandler.sendStringToView("");
	}

	private void printRow(int rowIndex) {
		for (int blockRowIndex = 0; blockRowIndex < blockSize; blockRowIndex++) {
			printBlockRow(rowIndex, blockRowIndex);
		}
	}

	private void printBlockRow(int rowIndex, int blockRowIndex) {
		for (int colIndex = 0; colIndex < scannerGridSize; colIndex++) {
			if (colIndex == 0) {
				printLeftBorder(rowIndex, blockRowIndex);
			}
			for (int blockColIndex = 0; blockColIndex < blockSize; blockColIndex++) {
				GridPoint p = gridPointsInRange.get(rowIndex).get(colIndex);
				//view.outputHandler.sendStringToView(p.x + "," + p.y);
				if (p.isValidSquare(gridMap.getWidth(), gridMap.getHeight())) {
					if (indexIsInCentre(blockColIndex) && indexIsInCentre(blockRowIndex)) {
						printBlockCentreRow(p.y, blockRowIndex, p.x);
					} else {
						printBlockOuterRow(blockColIndex, blockRowIndex);
					}
				} else {
					view.outputHandler.sendStringToView("   ");
				}
			}
		}
		view.outputHandler.sendStringToView("");
	}

	private void printLeftBorder(int rowIndex, int blockRowIndex) {
		if (indexIsInCentre(blockRowIndex)) {
			int scannerRow = gridPointsInRange.get(rowIndex).get(0).y;
			if (scannerRow >= 0) {
				if (scannerRow > 9) view.outputHandler.sendStringToView(scannerRow + "|");
				else view.outputHandler.sendStringToView(scannerRow + " |");
			}
		} else view.outputHandler.sendStringToView("  |");
	}

	private void printBlockCentreRow(int rowIndex, int blockRowIndex, int colIndex) {
		GridPoint point = new GridPoint(colIndex, rowIndex);
		GridSquare currentSquare = gridMap.getSquareAt(point);
		//view.outputHandler.sendStringToView("POINT " + point.x + "," + point.y + "   SHIP " + shipLocation.x + "," + shipLocation.y);
		if (point.x == shipLocation.x && point.y == shipLocation.y) {
			view.outputHandler.sendStringToView("-X-");
		} else {
			if (!(currentSquare instanceof EmptyGridSquare)) {
				view.outputHandler.sendStringToView("-O-");
			} else view.outputHandler.sendStringToView("   ");
		}
	}

	private void printBlockOuterRow(int blockColIndex, int blockRowIndex) {
		if (indexIsInCentre(blockColIndex)) {
			view.outputHandler.sendStringToView(" | ");
		} else if (indexIsInCentre(blockRowIndex)) {
			view.outputHandler.sendStringToView("---");
		} else view.outputHandler.sendStringToView("   ");
	}

	private boolean indexIsInCentre(int index) {
		return index == blockSize / 2;
	}

	private void printCharacterXTimes(char ch, int x) {
		if (x > 0) {
			view.outputHandler.sendStringToView(Character.toString(ch));
			printCharacterXTimes(ch, x - 1);
		}
	}

}
