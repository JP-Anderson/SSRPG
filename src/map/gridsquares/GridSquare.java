package map.gridsquares;

import map.GridPoint;
import map.zones.Zone;

public abstract class GridSquare {

	public final GridPoint gridPoint;
	public final char symbol;
	protected Zone zone;
	protected boolean isLandable;
	protected boolean isTradeable;

	public GridSquare(GridPoint gp, char gridSymbol) {
		gridPoint = gp;
		symbol = gridSymbol;
	}

	public int getDistance(GridSquare comparisonSquare) {
		return gridPoint.comparePoints(comparisonSquare.gridPoint);
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public boolean isLandable() {
		return isLandable;
	}

	public void setLandable(boolean isLandable) {
		this.isLandable = isLandable;
	}

	public boolean isTradeable() {
		return isTradeable;
	}

	public void setTradeable(boolean isTradeable) {
		this.isTradeable = isTradeable;
	}

}
