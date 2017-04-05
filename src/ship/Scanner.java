package ship;

import map.GridMap;
import map.GridPoint;
import map.gridsquares.GridSquare;
import map.gridsquares.EmptyGridSquare;

import java.util.ArrayList;

public class Scanner {

	public static Scanner scanner = null;

	public final int scannerGridSize;
	public final GridMap gridMap;
	public final static int SMALL = 3;
	public static final int blockSize = SMALL;

	private static GridPoint shipLocation;
	private ArrayList<ArrayList<GridPoint>> gridPointsInRange;
	private static final String xCoOrdinates = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static Scanner getScanner(int scannerSize, GridMap map, GridPoint shipGridPoint) {
		if (scanner == null) {
			scanner = new Scanner(scannerSize, map, shipGridPoint);
		}
		return scanner;
	}

	public void setShipLocation(GridPoint newLocation) {
		shipLocation = newLocation;
		getGridIndexesInScannerRange();
	}

	private Scanner(int scannerSize, GridMap map, GridPoint shipGridPoint) {
		scannerGridSize = scannerSize;
		gridMap = map;
		shipLocation = shipGridPoint;
		getGridIndexesInScannerRange();
	}
	
	public ArrayList<ArrayList<GridPoint>> getGridIndexesAsArrayLists() {
		getGridIndexesInScannerRange();
		return gridPointsInRange;
	}

	private void getGridIndexesInScannerRange() {
		int scannerRadius = (scannerGridSize - 1) / 2;
		int xIndex = shipLocation.x - scannerRadius;
		int yIndex = shipLocation.y - scannerRadius;
		gridPointsInRange = new ArrayList<ArrayList<GridPoint>>();
		for (int i = 0; i < scannerGridSize; i++) {
			gridPointsInRange.add(new ArrayList<GridPoint>());
			for (int j = 0; j < scannerGridSize; j++) {
				//System.out.println("i " + i + "   j " + j);
				//System.out.println("new GridPoint("+xIndex+","+yIndex+")");
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
		System.out.print("  ");
		for (int colIndex = 0; colIndex < scannerGridSize; colIndex++) {
			for (int blockColIndex = 0; blockColIndex < blockSize; blockColIndex++) {
				if (indexIsInCentre(blockColIndex)) {
					int scannerCol = gridPointsInRange.get(0).get(colIndex).x;
					if (scannerCol >= 0) {
						System.out.print(" " + xCoOrdinates.charAt(scannerCol) + " ");
					} else System.out.print("   ");
				} else System.out.print("   ");
			}
		}
		System.out.println("");
		printHeadingUnderline();
	}

	private void printHeadingUnderline() {
		System.out.print("  ");
		for (int colIndex = 0; colIndex < scannerGridSize; colIndex++) {
			printCharacterXTimes('_', blockSize * 3);
		}
		System.out.println("");
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
				//System.out.println(p.x + "," + p.y);
				if (p.isValidSquare(gridMap.getWidth(), gridMap.getHeight())) {
					if (indexIsInCentre(blockColIndex) && indexIsInCentre(blockRowIndex)) {
						printBlockCentreRow(p.y, blockRowIndex, p.x);
					} else {
						printBlockOuterRow(blockColIndex, blockRowIndex, gridMap.getSquareAt(p));
					}
				} else {
					System.out.print("   ");
				}
			}
		}
		System.out.println("");
	}

	private void printLeftBorder(int rowIndex, int blockRowIndex) {
		if (indexIsInCentre(blockRowIndex)) {
			int scannerRow = gridPointsInRange.get(rowIndex).get(0).y;
			if (scannerRow >= 0) {
				if (scannerRow > 9) System.out.print(scannerRow + "|");
				else System.out.print(scannerRow + " |");
			}
		} else System.out.print("  |");
	}

	private void printBlockCentreRow(int rowIndex, int blockRowIndex, int colIndex) {
		GridPoint point = new GridPoint(colIndex, rowIndex);
		GridSquare currentSquare = gridMap.getSquareAt(point);
		//System.out.print("POINT " + point.x + "," + point.y + "   SHIP " + shipLocation.x + "," + shipLocation.y);
		if (point.x == shipLocation.x && point.y == shipLocation.y) {
			System.out.print("-X-");
		} else {
			if (!(currentSquare instanceof EmptyGridSquare)) {
				System.out.print("-O-");
			} else System.out.print("- -");
		}
	}

	private void printBlockOuterRow(int blockColIndex, int blockRowIndex, GridSquare blockSquare) {
		if (indexIsInCentre(blockColIndex)) {
			System.out.print(" | ");
		} else if (indexIsInCentre(blockRowIndex)) {
			System.out.print("---");
		} else System.out.print(" "+blockSquare.zone.id+" ");
	}

	private boolean indexIsInCentre(int index) {
		return index == blockSize / 2;
	}

	private void printCharacterXTimes(char ch, int x) {
		if (x > 0) {
			System.out.print(ch);
			printCharacterXTimes(ch, x - 1);
		}
	}

}
