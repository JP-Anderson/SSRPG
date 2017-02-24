package map;

public abstract class GridSquare {

	public final GridPoint gridPoint;
	public final char symbol;

	public GridSquare(GridPoint gp, char gridSymbol) {
		gridPoint = gp;
		symbol = gridSymbol;
	}

	public int getDistance(GridSquare comparisonSquare) {
		return gridPoint.comparePoints(comparisonSquare.gridPoint);
	}

}
